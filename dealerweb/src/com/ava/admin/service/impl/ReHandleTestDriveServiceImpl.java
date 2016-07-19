package com.ava.admin.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ava.admin.dao.ICurrentGpsMessageDao;
import com.ava.admin.dao.IReHandleTestDriveDao;
import com.ava.admin.domain.vo.CurrentGpsMessageVo;
import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.admin.job.TestDriveTask;
import com.ava.admin.job.TestDriveThreadPool;
import com.ava.admin.service.IReHandleTestDriveService;
import com.ava.dealer.service.impl.BaseService;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.LocationVO;
import com.ava.domain.vo.TestDriveInfoVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.TestNoDriveWeekVO;
import com.ava.domain.vo.ViolationVO;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.gateway.business.IProtocolParseBusinessService;
import com.ava.gateway.facade.IProtocolParseBusinessFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;

@Service("reHandleTestDriveService")
public class ReHandleTestDriveServiceImpl extends BaseService implements IReHandleTestDriveService {
	
	private Logger loger=Logger.getLogger(ReHandleTestDriveServiceImpl.class);

	@Resource(name="testDriver.web2gatewayFacade")
	private IProtocolParseBusinessFacade protocolParseBusinessFacade;
		
	@Resource(name="reHandleTestDriveDao")
	private IReHandleTestDriveDao reHandleTestDriveDao;
	
	@Resource(name="currentGpsMessageDao")
	private ICurrentGpsMessageDao currentGpsMessageDao;
	
	@Resource(name="testDriver.web2gatewayService")
	IProtocolParseBusinessService protocolParseBusinessService;
	private int handleDay = -1;


	
	/**
	 * 开始重跑
	 * @author tangqingsong
	 * @version 
	 * @param currentGpsMessageVo
	 */
	@SuppressWarnings("unchecked")
	public void reHandle(Map<String,Object> argMap){
		TaskMessageVo taskMessageVo = (TaskMessageVo) argMap.get("taskMessageVo");
		//增加执行次数
		int counter = taskMessageVo.getCounter()==null?1:taskMessageVo.getCounter()+1;
		taskMessageVo.setCounter(counter);
		loger.info("试驾重跑任务开始：taskId:"+taskMessageVo.getId()+",重跑类型："+taskMessageVo.getTaskType());
		//1、读取报文
		Queue<String> jsonQueue = null;
		if(argMap.containsKey("gpsList")){
			jsonQueue = (Queue<String>) argMap.get("gpsList");
		}else{
			jsonQueue = currentGpsMessageDao.findMessage(argMap,GlobalConstant.TEST_DRIVE_REHANDLE_CODE+taskMessageVo.getId());
		}
	    if(jsonQueue==null || jsonQueue.isEmpty()){
			loger.info("试驾重跑-报文为空，不能重跑：taskId:"+taskMessageVo.getId()+",重跑类型："+taskMessageVo.getTaskType());
			taskMessageVo.setStatus(GlobalConstant.TASK_STAUTS_FAIL);
			reHandleTestDriveDao.updateTaskMessage(taskMessageVo);
			return;
	    }
		taskMessageVo.setStatus(GlobalConstant.TASK_STAUTS_RUNNING);
		reHandleTestDriveDao.updateTaskMessage(taskMessageVo);
		//2、备份和清除现有数据
		backupAndClear(argMap,taskMessageVo);
//		int n=1/0;
		//3、开始重跑
		int queueCount = jsonQueue.size();
		int interval = queueCount<10000?100:250;//计算更新进度的频率，数据越多，更新的频率会降低；
	    long begin = System.currentTimeMillis();
		for(int i=0;i<queueCount;i++){
			String json = jsonQueue.poll();
			try{
	    		protocolParseBusinessFacade.handleMessage(json);
	    		if((i!=0 && i%interval==0) || i==10){
	    			//更新进度..
	    			getCompleteRate(taskMessageVo,(i+1),queueCount,begin,System.currentTimeMillis());
	    			reHandleTestDriveDao.updateTaskMessage(taskMessageVo);
	    			loger.info("试驾重跑-进度更新：taskId:"+taskMessageVo.getId()+",重跑类型："+taskMessageVo.getTaskType()+",当前进度："+i);
	    		}
			}catch( ProtocolParseBusinessException pe){
				loger.error("taskId:"+taskMessageVo.getId()+"reHandle-handleMessage--------原始消息信息:"+json+";异常代码:"+pe.getResultCode()+";消息内容:"+pe.getDesc());
			}catch(Exception e){
//				e.printStackTrace();
	    		if(null!=e.getCause()){
	    			loger.error("taskId:"+taskMessageVo.getId()+"reHandle-handleMessage--------原始消息信息:"+json+";异常信息:"+e.getCause().getClass() + "," + e.getCause().getMessage());
	    		}else{
	    			loger.error("taskId:"+taskMessageVo.getId()+"reHandle-handleMessage--------原始消息信息:"+json+";异常信息:"+e.getMessage());
	    		}
	    	}
		}
		//增加删除非活跃试驾周
		deleteNotActiveTestDrive(argMap,taskMessageVo);
		getCompleteRate(taskMessageVo,queueCount,queueCount,begin,System.currentTimeMillis());
		taskMessageVo.setStatus(GlobalConstant.TASK_STAUTS_COMPLETE);
		taskMessageVo.setCompleteTime(new Date());
		reHandleTestDriveDao.updateTaskMessage(taskMessageVo);
		if(argMap.containsKey("taskLog")){
			TaskMessageLogVo taskLog = (TaskMessageLogVo) argMap.get("taskLog");
			//更新日志为已重跑处理
			taskLog.setStatus(GlobalConstant.LOG_HANDLE_END);
			taskLog.setCurtime(new Date());
			reHandleTestDriveDao.updateTaskMessageLogStatus(taskLog);
		}
//		long completeTime = ((System.currentTimeMillis()-begin)/1000);
//		System.out.println("处理完成.....所用时间:"+completeTime);
		loger.info("试驾重跑完成：taskId:"+taskMessageVo.getId()+",重跑类型："+taskMessageVo.getTaskType());
	}
	
	/**
	 * 计算进度、持续时间和估计完成时间
	 * @author tangqingsong
	 * @version
	 */
	public void getCompleteRate(TaskMessageVo taskMessageVo,int complete,int size,long begin,long end){
		BigDecimal durationTime = new BigDecimal((end-begin)/1000);//持续时间
		durationTime = durationTime.intValue()<=0?new BigDecimal(1):durationTime;//最小持续时间为1秒
		BigDecimal completeSize = new BigDecimal(complete);  
		BigDecimal countSize = new BigDecimal(size);  
		BigDecimal secondHandleSize = completeSize.divide(durationTime, 4, BigDecimal.ROUND_HALF_UP);//每秒处理数
		//int secondHandleSize = complete/durationTime;//每秒处理数
		int completeEstimate = secondHandleSize.doubleValue()<=0?secondHandleSize.intValue():countSize.divide(secondHandleSize, 4, BigDecimal.ROUND_HALF_UP).intValue();
		//计算完成率
		//BigDecimal completeSize = new BigDecimal(complete);  
		BigDecimal count = new BigDecimal(size);  
		Double completeRate = new Double(completeSize.divide(count, 4, BigDecimal.ROUND_HALF_UP).doubleValue());  
		taskMessageVo.setDurationTime(durationTime.intValue());
		taskMessageVo.setCompleteEstimate(completeEstimate);
		taskMessageVo.setCompleteRate(completeRate);
	}
	
	public void saveTaskMessage(TaskMessageVo taskMessageVo){
		reHandleTestDriveDao.saveTaskMessage(taskMessageVo);
	}
	
	public void updateTaskMessage(TaskMessageVo taskMessageVo){
		reHandleTestDriveDao.updateTaskMessage(taskMessageVo);
	}
	

	/**
	 * 备份和清除现有库数据
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 * @param taskMessageVo
	 */
	public void backupAndClear(Map<String,Object> argMap,TaskMessageVo taskMessageVo){
		//备份和清除试驾数据
		List<TestDriveVO> testDriveList = reHandleTestDriveDao.findTestDriveByList(argMap);
		if(testDriveList!=null && !testDriveList.isEmpty()){
			//插入历史库
			List<Long> idList = reHandleTestDriveDao.saveBatchTestDrive(testDriveList);
			//删除现有库
			reHandleTestDriveDao.deleteBatchById("t_test_drive",idList);
			loger.info("taskId:"+taskMessageVo.getId()+",vin:"+argMap.get("vin")+",startDate:"+argMap.get("startDate")+
					",endDate:"+argMap.get("endDate")+",备份t_test_drive："+testDriveList.size()+"/条");
		}
		//备份和清除违规数据
		List<ViolationVO> violationList = reHandleTestDriveDao.findViolationByList(argMap);
		if(violationList!=null && !violationList.isEmpty()){
			//插入历史库
			List<Long> idList = reHandleTestDriveDao.saveBatchViolation(violationList);
			//删除现有库
			reHandleTestDriveDao.deleteBatchById("t_violation",idList);
			loger.info("taskId:"+taskMessageVo.getId()+",vin:"+argMap.get("vin")+",startDate:"+argMap.get("startDate")+
					",endDate:"+argMap.get("endDate")+",备份t_violation："+testDriveList.size()+"/条");
		}
		//备份和清除定位数据
		List<LocationVO> locationList = reHandleTestDriveDao.findLocationByList(argMap);
		if(locationList!=null && !locationList.isEmpty()){
			//插入历史库
			List<Long> idList = reHandleTestDriveDao.saveBatchLocation(locationList);
			//删除现有库
			reHandleTestDriveDao.deleteBatchById("t_location",idList);
			loger.info("taskId:"+taskMessageVo.getId()+",vin:"+argMap.get("vin")+",startDate:"+argMap.get("startDate")+
					",endDate:"+argMap.get("endDate")+",备份t_location："+testDriveList.size()+"/条");
		}
	}
	
	/**
	 * 运行错误的而导致的失败任务重新运行
	 * @author tangqingsong
	 * @version
	 */
	public void failTaskRehandle(){
		//失败任务重新运行..
		List<TaskMessageVo> taskList = reHandleTestDriveDao.findFailTaskMessage();
		if(taskList==null || taskList.isEmpty()){
			return;
		}
		for(TaskMessageVo taskVo : taskList){
			Map<String, Object> argMap = new HashMap<String,Object>();
			String startDate = taskVo.getStartTime();
			String endDate = taskVo.getEndTime();
			argMap.put("vin", taskVo.getVin());
			argMap.put("startDate", startDate);
			argMap.put("startDateLong",startDate.replace(":", "").replace(" ", "").replace("-", "").trim());
			argMap.put("endDate", endDate);
			argMap.put("endDateLong",endDate.replace(":", "").replace(" ", "").replace("-", "").trim());
			Vehicle vehicle=CacheVehicleManager.getVehicleByVin(taskVo.getVin());
			if(vehicle!=null && vehicle.getId()!=null){
				argMap.put("vehicleId",vehicle.getId());
			}else{
				loger.info("重跑试驾分析-reHandleByDelay,taskId:"+taskVo.getId()+"，车辆ID不存在");
				return;
			}
			taskVo.setStatus(GlobalConstant.TASK_STAUTS_READY);
			argMap.put("taskMessageVo", taskVo);
			//提交任务
			TestDriveTask task = new TestDriveTask(this, argMap);
			TestDriveThreadPool.execute(task);
		}
	}
	
	/**
	 * 分析延迟上报的重跑
	 * @author tangqingsong
	 * @version
	 */
	public void analyseByDelay(){
		loger.info("重跑试驾分析[analyseReHandleByDelay]开始...");
		//修复因为报文顺序错乱的，重跑主要针对两类、无效试驾和4S店内试驾
		Map<String,Object> argMap = new HashMap<String,Object>();
		argMap.put("status",GlobalConstant.LOG_HANDLE_NORMAL); 
		argMap.put("logCode",GlobalConstant.LOG_REQUEST_TIME_OUT); 
		//1修复延迟上报的
		List<TaskMessageLogVo> logList = reHandleTestDriveDao.findLogMessage(argMap);
		for(TaskMessageLogVo log : logList){
			reHandleByDelay(log);
		}
		loger.info("重跑试驾分析[analyseReHandleByDelay]结束...");
	}
	
	/**
	 * 分析无效试驾重跑
	 * @author tangqingsong
	 * @version
	 */
	public void analyseByInvalid(){
		loger.info("重跑试驾分析[analyseByInvalid]开始...");
		//修复因为报文顺序错乱的，重跑主要针对两类、无效试驾和4S店内试驾
		Map<String,Object> argMap = new HashMap<String,Object>();
		SimpleDateFormat dateFmt=new SimpleDateFormat("yyyy-MM-dd"); 
		Date now = new Date();
		now = DateTime.addDays(now,handleDay);//减1天
		argMap.put("startTime", dateFmt.format(now)+" 00:00:00");
		argMap.put("endTime", dateFmt.format(now)+" 23:59:59");
		argMap.put("status", "0"); 
		//2、重跑无效试驾..
		List<TestDriveInfoVO> testDriveList1 = reHandleTestDriveDao.findTestDrive(argMap);
		for(TestDriveInfoVO testDrive : testDriveList1){
			analyseReHandleByTestDrive(testDrive,false);
		}
		loger.info("重跑试驾分析[analyseByInvalid]结束...");
	}
	
	/**
	 * 分析4S店内重跑
	 * @author tangqingsong
	 * @version
	 */
	public void analyseByInCommpany(){
		loger.info("重跑试驾分析[analyseByInCommpany]开始...");
		//修复因为报文顺序错乱的，重跑主要针对两类、无效试驾和4S店内试驾
		Map<String,Object> argMap = new HashMap<String,Object>();
		SimpleDateFormat dateFmt=new SimpleDateFormat("yyyy-MM-dd"); 
		Date now = new Date();
		now = DateTime.addDays(now, handleDay);//减1天
		argMap.put("startTime", dateFmt.format(now)+" 00:00:00");
		argMap.put("endTime", dateFmt.format(now)+" 23:59:59");
		argMap.put("status", "4");
		argMap.put("intervalTime", "600");
		List<TestDriveInfoVO> testDriveList2 = reHandleTestDriveDao.findTestDrive(argMap);
		for(TestDriveInfoVO testDrive : testDriveList2){
			analyseReHandleByTestDrive(testDrive,false);
		}
		loger.info("重跑试驾分析[analyseByInCommpany]结束...");
	}
	
	/**
	 * 重跑延迟1天以上的报文
	 * @author tangqingsong
	 * @version 
	 * @param log
	 */
	private void reHandleByDelay(TaskMessageLogVo log){
		if(DateTime.getSecondDifference(log.getCurtime(), new Date())<3600){
			//近1小时内有上报报文，那么先不作重跑，以避免影响到正常上报
			return;
		}
		Map<String, Object> argMap = new HashMap<String,Object>();
		String startDate = DateTime.getDate(log.getStartDate(), DateTime.PATTERN_DATE)+" 00:00:00";
		String endDate = DateTime.getDate(log.getEndDate(), DateTime.PATTERN_DATE)+" 23:59:59";
		argMap.put("vin", log.getVin());
		argMap.put("startDate", startDate);
		argMap.put("startDateLong",startDate.replace(":", "").replace(" ", "").replace("-", "").trim());
		argMap.put("endDate", endDate);
		argMap.put("endDateLong",endDate.replace(":", "").replace(" ", "").replace("-", "").trim());
		Vehicle vehicle=CacheVehicleManager.getVehicleByVin(log.getVin());
		if(vehicle!=null && vehicle.getId()!=null){
			argMap.put("vehicleId",vehicle.getId());
		}else{
			loger.info("重跑试驾分析-reHandleByDelay,vin"+log.getVin()+"，车辆ID不存在");
			return;
		}
		TaskMessageVo taskMessageVo = new TaskMessageVo();
		taskMessageVo.setCreateTime(new Date());
		taskMessageVo.setStatus(GlobalConstant.TASK_STAUTS_READY);
		taskMessageVo.setVin(log.getVin());
		taskMessageVo.setVehicleId(vehicle.getId());
		taskMessageVo.setStartTime(startDate);
		taskMessageVo.setEndTime(endDate);
		taskMessageVo.setTaskType(0);
		this.saveTaskMessage(taskMessageVo);
		argMap.put("taskMessageVo", taskMessageVo);
		argMap.put("taskLog", log);
		//更新日志为已开始重跑处理
		log.setStatus(GlobalConstant.LOG_HANDLE_BEGIN);
		log.setCurtime(new Date());
		reHandleTestDriveDao.updateTaskMessageLogStatus(log);
		//提交任务
		TestDriveTask task = new TestDriveTask(this, argMap);
		TestDriveThreadPool.execute(task);
	}
	
	/**
	 * 
	 * 分析是否需要重跑，如果需要重跑，提交重跑任务
	 * @author tangqingsong
	 * @version 
	 * @param testDrive
	 * @param isNotCheck 是否需要校验重跑
	 */
	private void analyseReHandleByTestDrive(TestDriveInfoVO testDrive,boolean isNotCheck){
		if(testDrive.getVehicleId()==null || testDrive.getStartTime()==null
				|| testDrive.getEndTime()==null){
			return;
		}
		Map<String, Object> argMap = new HashMap<String,Object>();
		String startDate = DateTime.getDate(testDrive.getStartTime(), DateTime.PATTERN_DATETIME);
		String endDate = DateTime.getDate(testDrive.getEndTime(), DateTime.PATTERN_DATETIME);
		argMap.put("vin", testDrive.getVin());
		argMap.put("startDate", startDate);
		argMap.put("startDateLong",startDate.replace(":", "").replace(" ", "").replace("-", "").trim());
		argMap.put("endDate", endDate);
		argMap.put("endDateLong",endDate.replace(":", "").replace(" ", "").replace("-", "").trim());
		argMap.put("vehicleId", testDrive.getVehicleId());
		//查询出疑似需要重跑的试驾报文
		List<CurrentGpsMessageVo> gpsList = currentGpsMessageDao.findGpsMessage(argMap);
		String defectCode = isDefect(gpsList);
		//报文有缺失，无法重跑...
		if(!defectCode.equals(GlobalConstant.LOG_DEFECT_NOT)){
//			//记录无法处理的问题
//			TaskMessageLogVo log = new TaskMessageLogVo();
//			log.setLogCode(defectCode);
//			log.setStartDate(testDrive.getStartTime());
//			log.setEndDate(testDrive.getEndTime());
//			log.setVin(testDrive.getVin());
//			log.setCurtime(new Date());
//			reHandleTestDriveDao.saveOrUpdateTaskMessageLog(log);
		}

		if(defectCode.equals(GlobalConstant.LOG_DEFECT_NOT) && (isReHandle(gpsList) || isNotCheck)){
			//判断试驾是否需要重跑
			//创建任务
			TaskMessageVo taskMessageVo = createTaskMessage(testDrive);
			this.saveTaskMessage(taskMessageVo);
			argMap.put("taskMessageVo", taskMessageVo);
			argMap.put("gpsList", getGpsStringList(gpsList,taskMessageVo.getId()));
			//提交任务
			TestDriveTask task = new TestDriveTask(this, argMap);
			TestDriveThreadPool.execute(task);
		}
	}
	
	private Queue<String> getGpsStringList(List<CurrentGpsMessageVo> gpsList,Integer taskId){
		Queue<String> queue = new LinkedList<String>();
		for(CurrentGpsMessageVo gpsMessageVo : gpsList){
			gpsMessageVo.setTaskId(taskId.toString());
			String json = gpsMessageVo.getJson();
			queue.add(json);
		}
		return queue;
	}
	/**
	 * 创建任务
	 * @author tangqingsong
	 * @version 
	 * @param request
	 * @return
	 */
	private TaskMessageVo createTaskMessage(TestDriveInfoVO testDrive){
		TaskMessageVo taskMessageVo = new TaskMessageVo();
		taskMessageVo.setCreateTime(new Date());
		taskMessageVo.setStatus(GlobalConstant.TASK_STAUTS_READY);
		taskMessageVo.setVin(testDrive.getVin());
		taskMessageVo.setVehicleId(testDrive.getVehicleId());
		taskMessageVo.setStartTime(DateTime.getDate(testDrive.getStartTime(), DateTime.PATTERN_DATETIME));
		taskMessageVo.setEndTime(DateTime.getDate(testDrive.getEndTime(), DateTime.PATTERN_DATETIME));
		taskMessageVo.setTaskType(0);
		return taskMessageVo;
	}
	
	
	/**
	 * 报文是否有缺失
	 * @author tangqingsong
	 * @version 
	 * @return
	 */
	private String isDefect(List<CurrentGpsMessageVo> gpsList){
		if(gpsList==null || gpsList.isEmpty()){
			return GlobalConstant.LOG_DEFECT_EMPTY;
		}
		CurrentGpsMessageVo fireOn = gpsList.get(0);
		CurrentGpsMessageVo fireOut = gpsList.get(gpsList.size()-1);
		String result = GlobalConstant.LOG_DEFECT_NOT;
		if(!fireOn.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT)
				|| fireOn.getFireState().intValue()!=0){
			//无点火
			result = GlobalConstant.LOG_DEFECT_ON;
		}

		if(!fireOut.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT)
				|| fireOut.getFireState().intValue()!=1){
			//无熄火火
			if(result.equals(GlobalConstant.LOG_DEFECT_ON)){
				//点火熄火都没有
				result = GlobalConstant.LOG_DEFECT_ON_OUT;
			}else{
				result = GlobalConstant.LOG_DEFECT_OUT;
			}
		}

		if((gpsList.size()-2)<=0){
			//无GPS
			if(result.equals(GlobalConstant.LOG_DEFECT_ON_OUT)){
				//点火熄火/GPS都没有
				result = GlobalConstant.LOG_DEFECT_EMPTY;
			}else{
				result = GlobalConstant.LOG_DEFECT_GPS;
			}
		}
		return result;
		
	}

	/**
	 * 判断是否需要重跑 
	 * @author tangqingsong
	 * @version 
	 * @param gpsList
	 * @return
	 */
	private boolean isReHandle(List<CurrentGpsMessageVo> gpsList){
		//根据创建时间排序
		List<CurrentGpsMessageVo> orderList = listOrderByCreateTime(gpsList);
		CurrentGpsMessageVo fireOn = orderList.get(0);
		CurrentGpsMessageVo fireOut = orderList.get(gpsList.size()-1);
		if(!fireOn.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT)
				|| fireOn.getFireState().intValue()!=0){
			//第1个报文不是点火，说明顺序错乱，需要重跑
			return true;
		}else if(!fireOut.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT)
				|| fireOut.getFireState().intValue()!=1){
			//无熄火火
			return true;
		}else if((gpsList.size()-2)<10){
			//GPS小于10不启用重跑..
			return false;
		}
		
		return false;
	}
	
	/**
	 * 排序并返回新的list
	 * @author tangqingsong
	 * @version 
	 * @param gpsList
	 * @return
	 */
	private List<CurrentGpsMessageVo> listOrderByCreateTime(List<CurrentGpsMessageVo> gpsList){
		List<CurrentGpsMessageVo> list = new LinkedList<CurrentGpsMessageVo>();
		list.addAll(gpsList);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Collections.sort(list,new Comparator<CurrentGpsMessageVo>() {
			@Override
			public int compare(CurrentGpsMessageVo o1, CurrentGpsMessageVo o2) {
				int result=0;
				try {
					long t1 = sdf.parse(o1.getCreateTime()).getTime();
					long t2 = sdf.parse(o2.getCreateTime()).getTime();
					//创建时间
					result = (int)(t1-t2);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return result;
			}
		});
        return list;
	}
	
	public void updateLogStatus(TaskMessageLogVo taskMessageLogVo){
		taskMessageLogVo.setCurtime(new Date());
		reHandleTestDriveDao.updateTaskMessageLogStatus(taskMessageLogVo);
	}
	
	/**
	 * 删除有效试驾周里的非活跃记录
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 * @param taskMessageVo
	 */
	public void deleteNotActiveTestDrive(Map<String,Object> argMap,TaskMessageVo taskMessageVo){
		//查询出是有效试驾周里里的非活跃记录
		List<TestNoDriveWeekVO> result = reHandleTestDriveDao.findVialdNotActiveTestDrive(argMap);
		if(result==null || result.isEmpty()){
			return;
		}
		List<Long> idList = new LinkedList<>();
		for(TestNoDriveWeekVO vo : result){
			idList.add(vo.getId().longValue());
		}
		//删除非活跃试驾
		reHandleTestDriveDao.deleteBatchById("t_test_no_drive_week",idList);
		loger.info("taskId:"+taskMessageVo.getId()+",vin:"+argMap.get("vin")+",startDate:"+argMap.get("startDate")+
				",endDate:"+argMap.get("endDate")+",删除非活跃试驾："+idList.size()+"/条");

	}
	public static void main(String args[]){


	}
	
	public ICurrentGpsMessageDao getCurrentGpsMessageDao() {
		return currentGpsMessageDao;
	}

	public void setCurrentGpsMessageDao(ICurrentGpsMessageDao currentGpsMessageDao) {
		this.currentGpsMessageDao = currentGpsMessageDao;
	}
	

	
}
