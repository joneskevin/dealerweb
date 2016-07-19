package com.ava.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.admin.dao.ICurrentGpsMessageDao;
import com.ava.admin.domain.vo.CurrentGpsMessageVo;
import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.admin.service.IAdminService;
import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dealer.service.impl.BaseService;
import com.ava.domain.vo.TestDriveFindVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.gateway.domain.entity.CurrentGpsMessage;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

@Service
public class AdminServiceImpl extends BaseService implements IAdminService {

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Resource(name="currentGpsMessageDao")
	private ICurrentGpsMessageDao currentGpsMessageDao;
	
	@Override
	public ResponseData findGpsPoint(TransMsg transMsg,CurrentGpsMessageVo currentGpsMessageVo) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		
		//试驾信息
		exeSql.append(" t.VIN as vin, "+
					  " t.SERIAL_NUMBER as serialNumber, "+
					  " t.MESSAGE_CODE as messageCode, "+
					  " t.FIRE_STATE as fireState, "+
					  " DATE_FORMAT(t.MESSAGE_DATE,'%Y-%c-%d %H:%i:%s') as messageDate, "+
					  " DATE_FORMAT(t.CREATE_TIME,'%Y-%c-%d %H:%i:%s') as createTime, "+
					  " t.IMSI as imsi, "+
					  " t.LNG as lng, "+
					  " t.LAT as lat, "+
					  " t.BAIDU_LNG as baiduLng, "+
					  " t.BAIDU_LAT as baiduLat, "+
					  " t.SPEED as speed, "+
					  " t.KO3_KILOMETER as ko3Kilometer ");

		exeSql.append(" FROM t_message_current_gps t where 1=1 "); 
		exeCountSql.append(" FROM t_message_current_gps t where 1=1 "); 
		if(currentGpsMessageVo.getVin()!=null && !currentGpsMessageVo.getVin().isEmpty()){
			exeSql.append(" AND t.VIN = '"+currentGpsMessageVo.getVin()+"'");
			exeCountSql.append(" AND t.VIN = '"+currentGpsMessageVo.getVin()+"'");
		}
		if (currentGpsMessageVo.getStartDate() != null && !currentGpsMessageVo.getStartDate().isEmpty()) {
			exeSql.append(" AND t.MESSAGE_DATE >= '" + currentGpsMessageVo.getStartDate()+"'");
			exeCountSql.append(" AND t.MESSAGE_DATE >= '" + currentGpsMessageVo.getStartDate()+"'");
		}
		if (currentGpsMessageVo.getEndDate() != null  && !currentGpsMessageVo.getEndDate().isEmpty()) {
			exeSql.append(" AND t.MESSAGE_DATE <= '" + currentGpsMessageVo.getEndDate()+"'");
			exeCountSql.append(" AND t.MESSAGE_DATE <= '" + currentGpsMessageVo.getEndDate()+"'");
		}
		if (currentGpsMessageVo.getMessageCode() != null) {
			exeSql.append(" AND t.MESSAGE_CODE = '" + currentGpsMessageVo.getMessageCode()+"'");
			exeCountSql.append(" AND t.MESSAGE_CODE = '" + currentGpsMessageVo.getMessageCode()+"'");
		}
		
		if (currentGpsMessageVo.getSerialNumber()!= null  && !currentGpsMessageVo.getSerialNumber().isEmpty()) {
			exeSql.append(" AND t.SERIAL_NUMBER = '" + currentGpsMessageVo.getSerialNumber()+"'");
			exeCountSql.append(" AND t.SERIAL_NUMBER = '" + currentGpsMessageVo.getSerialNumber()+"'");
		}
		
		if (currentGpsMessageVo.getOrderType() != null) {
			if (currentGpsMessageVo.getOrderType().equals("createTime")){
				exeSql.append(" order by t.CREATE_TIME");
			}else if (currentGpsMessageVo.getOrderType().equals("messageDate")){
				exeSql.append(" order by t.MESSAGE_DATE");
			}
		}
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, false));
		
		List<CurrentGpsMessageVo> testDriveList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), CurrentGpsMessageVo.class);
		rd.setFirstItem(testDriveList);
		return rd;
	}


	
	/**
	 * 查询试驾信息
	 * @author tangqingsong
	 * @param transMsg
	 * @param testDrive
	 * @param companyId
	 * @param userId
	 * @param isExport
	 * @return
	 */
	public ResponseData findTestDrive(TransMsg transMsg, TestDriveVO testDrive, Integer companyId, Integer userId, boolean isExport) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		//试驾信息
		exeSql.append(" td.LINE_ID as lineId, td.LOOP_COUNT as loopCount, td.MILEAGE as mileage, ");
		exeSql.append(" td.TEST_DRIVE_DATE as testDriveDate, td.START_TIME as startTime, td.END_TIME as endTime, td.STATUS as status, ");
		exeSql.append( "td.CURRENT_STATUS as currentStatus,"); 
		exeSql.append(" (CASE td.STATUS ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_VALID).append(" THEN '合规' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_INVALID).append(" THEN '无效' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_UNLAW).append(" THEN '违规' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_COMPANY_INNER).append(" THEN '4S店内试跑' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_FUEL).append(" THEN '加油' ");
		exeSql.append(" END) as statusNick, ");
		exeSql.append(" dl.NAME as lineNick "); 
		exeSql.append(" FROM t_test_drive td"); 
		exeCountSql.append(" FROM t_test_drive td"); 
		//关联线路表
		exeSql.append(" LEFT JOIN t_drive_line dl on dl.ID = td.LINE_ID ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = td.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = td.COMPANY_ID ");
		
		String vehicleSql = " INNER JOIN t_vehicle v ON v.ID = td.VEHICLE_ID ";
		exeSql.append(vehicleSql);
		exeCountSql.append(vehicleSql);
		//关联车型
		exeSql.append(getVehicleJoinCarStyleSql());
		
		//试驾状态为试驾结束的
		//exeSql.append(" WHERE td.CURRENT_STATUS = 1 ");
		//exeCountSql.append(" WHERE td.CURRENT_STATUS = 1 ");
		if (testDrive.getStatus() != null) {
			exeSql.append(" AND td.STATUS = " + testDrive.getStatus());
			exeCountSql.append(" AND td.STATUS = " + testDrive.getStatus());
		}
		
		if (testDrive.getStartTime_Nick() != null && !testDrive.getStartTime_Nick().isEmpty()) {
			String startTimeSql = " AND td.START_TIME >= ' " + testDrive.getStartTime_Nick() + " ' ";
			exeSql.append(startTimeSql);
			exeCountSql.append(startTimeSql);
			
		}
		
		if (testDrive.getEndTime_Nick() != null  && !testDrive.getEndTime_Nick().isEmpty()) {
			String endTimeSql = " AND td.START_TIME <= ' " + testDrive.getEndTime_Nick() + " ' ";
			exeSql.append(endTimeSql);
			exeCountSql.append(endTimeSql);
			
		}
		
		VehicleVO vehicleVO = testDrive.getVehicle();
		//添加权限过滤条件
		exeSql.append(getAccessSql(companyId, userId));
		exeCountSql.append(getAccessSql(companyId, userId));
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(testDrive.getDealer(), userId));
		exeCountSql.append(getDealerConditionSql(testDrive.getDealer(), userId));
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , td.START_TIME DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		
		List<TestDriveFindVO> testDriveList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TestDriveFindVO.class);
		rd.setFirstItem(testDriveList);
		return rd;
	}
	
	public ResponseData findTaskMessage(TransMsg transMsg,TaskMessageVo taskMessageVo) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//任务信息
		exeSql.append(" t.id, "+
					" t.TASK_NAME as taskName, "+
					" t.COMPLETE_RATE as completeRate, "+
					" (CASE t.STATUS "+
					" WHEN 10 THEN '准备运行' "+
					" WHEN 20 THEN '运行中' "+
					" WHEN 30 THEN '运行失败' "+
					" WHEN 40 THEN '运行完成' "+
					" WHEN 50 THEN '运行错误' "+
					" end) as status, "+
					" t.CREATE_TIME as createTime, "+
					" t.COMPLETE_TIME as completeTime, "+
					" t.vin, "+
					" t.START_TIME as startTime, "+
					" t.END_TIME as endTime, "+
					" t.DURATION_TIME as durationTime, "+
					" t.COMPLETE_ESTIMATE as completeEstimate, "+
					" t.COMPANY_ID as companyId "+
					" from t_task_message t where 1=1 ");
		exeCountSql.append(" from t_task_message t where 1=1 ");
		
		if(taskMessageVo.getVin()!=null && !taskMessageVo.getVin().isEmpty()){
			exeSql.append(" AND t.VIN = '"+taskMessageVo.getVin()+"'");
			exeCountSql.append(" AND t.VIN = '"+taskMessageVo.getVin()+"'");
		}
		if (taskMessageVo.getStartTimeNick()!= null && !taskMessageVo.getStartTimeNick().isEmpty()) {
			exeSql.append(" AND t.START_TIME >= '" + taskMessageVo.getStartTimeNick()+"'");
			exeCountSql.append(" AND t.START_TIME >= '" + taskMessageVo.getStartTimeNick()+"'");
		}
		if (taskMessageVo.getEndTimeNick()!= null && !taskMessageVo.getEndTimeNick().isEmpty()) {
			exeSql.append(" AND t.END_TIME <= '" + taskMessageVo.getEndTimeNick()+"'");
			exeCountSql.append(" AND t.END_TIME <= '" + taskMessageVo.getEndTimeNick()+"'");
		}
		if (taskMessageVo.getId()!= null) {
			exeSql.append(" AND t.id = '" + taskMessageVo.getId()+"'");
			exeCountSql.append(" AND t.id = '" + taskMessageVo.getId()+"'");
		}
		if (taskMessageVo.getCreateStartTimeNick()!= null && !taskMessageVo.getCreateStartTimeNick().isEmpty()) {
			exeSql.append(" AND t.CREATE_TIME >= '" + taskMessageVo.getCreateStartTimeNick()+"'");
			exeCountSql.append(" AND t.CREATE_TIME >= '" + taskMessageVo.getCreateStartTimeNick()+"'");
		}
		if (taskMessageVo.getCreateEndTimeNick()!= null  && !taskMessageVo.getCreateEndTimeNick().isEmpty()) {
			exeSql.append(" AND t.CREATE_TIME <= '" + taskMessageVo.getCreateEndTimeNick()+"'");
			exeCountSql.append(" AND t.CREATE_TIME <= '" + taskMessageVo.getCreateEndTimeNick()+"'");
		}
		if(taskMessageVo.getStatus()!=null && !taskMessageVo.getStatus().isEmpty()){
			exeSql.append(" AND t.status = '"+taskMessageVo.getStatus()+"'");
			exeCountSql.append(" AND t.status = '"+taskMessageVo.getStatus()+"'");
		}
		exeSql.append(" order by t.CREATE_TIME desc");

		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, false));

		List<TaskMessageVo> taskList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TaskMessageVo.class);
		rd.setFirstItem(taskList);
		return rd;
	}
	
	public ResponseData findLogMessage(TransMsg transMsg,TaskMessageLogVo taskMessageLogVo,boolean isExport) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//任务信息
		exeSql.append(" t.id, "+
					" t.vin as vin, "+
					" t.START_DATE as startDate, "+
					" t.END_DATE as endDate, "+
					" (CASE t.LOG_CODE "+
					" WHEN 10003 THEN '用户身份验证错误' "+
					" WHEN 20006 THEN '报文上报延迟' "+
					" WHEN 20005 THEN 'GPS缺失' "+
					" WHEN 20004 THEN '点火与熄火缺失' "+
					" WHEN 20003 THEN '熄火缺失' "+
					" WHEN 20002 THEN '点火缺失' "+
					" end) as logCode, "+
					" t.CURTIME as curtime, "+
					" t.COUNTER as counter, "+
					" t.SERIAL_NUMBER as serialNumber "+
					" from t_task_message_log t where status="+GlobalConstant.LOG_HANDLE_NORMAL);
		exeCountSql.append(" from t_task_message_log t where status="+GlobalConstant.LOG_HANDLE_NORMAL);
		//目前只显示用户验证失败和缺失GPS的错误
		String showLog = " and LOG_CODE IN (" +
				"'" +GlobalConstant.LOG_USER_NOT_EXIST+"',"+
				"'" +GlobalConstant.LOG_DEFECT_GPS+"')";
		exeSql.append(showLog);
		exeCountSql.append(showLog);
		if(taskMessageLogVo.getVin()!=null && !taskMessageLogVo.getVin().isEmpty()){
			exeSql.append(" AND t.VIN = '"+taskMessageLogVo.getVin()+"'");
			exeCountSql.append(" AND t.VIN = '"+taskMessageLogVo.getVin()+"'");
		}
		if (taskMessageLogVo.getStartDateNick()!= null && !taskMessageLogVo.getStartDateNick().isEmpty()) {
			exeSql.append(" AND t.START_DATE >= '" + taskMessageLogVo.getStartDateNick()+"'");
			exeCountSql.append(" AND t.START_DATE >= '" + taskMessageLogVo.getStartDateNick()+"'");
		}
		if (taskMessageLogVo.getEndDateNick()!=null && !taskMessageLogVo.getEndDateNick().isEmpty()) {
			exeSql.append(" AND t.END_DATE <= '" + taskMessageLogVo.getEndDateNick()+"'");
			exeCountSql.append(" AND t.END_DATE <= '" + taskMessageLogVo.getEndDateNick()+"'");
		}
		if(taskMessageLogVo.getLogCode()!=null && !taskMessageLogVo.getLogCode().isEmpty()){
			exeSql.append(" AND t.LOG_CODE = '"+taskMessageLogVo.getLogCode()+"'");
			exeCountSql.append(" AND t.LOG_CODE = '"+taskMessageLogVo.getLogCode()+"'");
		}
		exeSql.append(" AND t.STATUS = "+GlobalConstant.LOG_HANDLE_NORMAL+"");
		exeCountSql.append(" AND t.STATUS = "+GlobalConstant.LOG_HANDLE_NORMAL+"");
		exeSql.append(" order by t.CURTIME desc");

		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));

		List<TaskMessageLogVo> logList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TaskMessageLogVo.class);
		rd.setFirstItem(logList);
		return rd;
	}
	
	public void deleteLog(String id) {
		ResponseData rd = new ResponseData(1);
		StringBuffer sql = new StringBuffer(" delete from t_task_message_log where id=:id ");
		Map<String,Object> argMap = new HashMap<String,Object>();
		argMap.put("id", id);
		hibernateDao.executeSQLUpdate(sql.toString(),argMap);
	}
	
	/**
	 * 修复点火和熄火信息
	 * @author tangqingsong
	 * @version 
	 * @param argMap
	 */
	public void repairFireOnOrOut(Map<String,Object> argMap){
		List<CurrentGpsMessage> gpsList = currentGpsMessageDao.findCurrentGpsMessage(argMap);
		if(gpsList==null || gpsList.isEmpty()){
			return ;
		}
		CurrentGpsMessage lastMessage = null;
		int lastState = -1;
		for(int i=0;i<gpsList.size();i++){
			CurrentGpsMessage currentMessage = gpsList.get(i);
			if(i==0 && currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP)){
				//第一条，造点火
				createGpsMessage(currentMessage,-10,0);
				lastState = 0;
			}else if(i==(gpsList.size()-1) && currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP)){
				//最后一条，造熄火
				lastMessage = createGpsMessage(currentMessage,10,1);//创建熄火
				lastState = 1;
			}
			if(lastMessage!=null){
				if( DateTime.getSecondDifference(lastMessage.getMessageDate(), currentMessage.getMessageDate())
						>=300){
					//两个报文间相隔2分钟以上，就分段
					//根据当前GPS创建点火或熄火信息
					if(lastState==0){
						//上次是点火，建熄火
						if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT) &&
								currentMessage.getFireState().equals(1)	){
							//如果当前报文是熄火，就不再补熄火 
							lastState = 1;
						}else if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT) &&
								currentMessage.getFireState().equals(0)	&&
								lastMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP)){
							//如果当前报文是点火，补熄火 
							createGpsMessage(lastMessage,10,1);
							lastState = 1;
						}else if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP) &&
								lastMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP)) {
							//如果上一个报文是gps，当前报文也是gps，点火和熄火都缺失，同是补两个
							createGpsMessage(lastMessage,10,1);
							createGpsMessage(currentMessage,-10,0);
							lastState = 0;
						}
					}else if(lastState==1){
						//上次是熄火，建点火
						if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT) &&
								currentMessage.getFireState().equals(0)	){
							//如果当前报文是点火，就不再补点火 
							lastState = 0;
						}else if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP)){
							//如果当前报文是GPS，中间缺点火，补点火 
							createGpsMessage(currentMessage,-10,0);
							lastState = 1;
						}else if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP) &&
								lastMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP)) {
							//如果上一个报文是gps，当前报文也是gps，点火和熄火都缺失，同是补两个
							createGpsMessage(currentMessage,-10,0);
							createGpsMessage(lastMessage,10,1);	
							lastState = 1;
						}
					}
				}else if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT) &&
						(currentMessage.getFireState()!=null && currentMessage.getFireState().equals(1))	){
					//上次是熄火
					lastState = 1;
				}else if(currentMessage.getMessageCode().equals(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT) &&
						(currentMessage.getFireState()!=null && currentMessage.getFireState().equals(0))		){
					//上次是点火
					lastState = 0;
				}
			}
			lastMessage = currentMessage;
		}
	}
	
    public CurrentGpsMessage createGpsMessage(CurrentGpsMessage oldMessage,int seconds,int fireState){
	    	CurrentGpsMessage message = new CurrentGpsMessage();
			MyBeanUtils.copyPropertiesContainsDate(message,oldMessage);
			message.setId(null);//去掉id
			message.setFireState(fireState);//设置为点火报文
			message.setCreateTime(new Date());
			message.setMessageDate(DateTime.addSeconds(oldMessage.getMessageDate(),seconds));//往后推10秒
			message.setMessageCode(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT);
			message.setMfnSensorwarn(1);
			currentGpsMessageDao.saveCurrentGpsMessage(message);
			return message;
    }
}
