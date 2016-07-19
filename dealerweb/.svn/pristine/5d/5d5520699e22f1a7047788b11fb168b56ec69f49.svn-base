package com.ava.admin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ava.admin.domain.vo.CurrentGpsMessageVo;
import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.admin.job.TestDriveThreadPool;
import com.ava.admin.job.TestDriveTask;
import com.ava.admin.service.IAdminService;
import com.ava.admin.service.IReHandleTestDriveService;
import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.TestDriveVO;
import com.ava.gateway.facade.IProtocolParseBusinessFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping(value="/admin/testDrive")
public class AdminController extends Base4MvcController {

	@Resource(name="reHandleTestDriveService")
	private IReHandleTestDriveService reHandleTestDriveService; 
	
	@Autowired
	private IAdminService adminService;
	
	@Resource(name="testDriver.web2gatewayFacade")
	IProtocolParseBusinessFacade protocolParseBusinessFacade;
	
	private Logger loger=Logger.getLogger(AdminController.class);

	/**
	 * 
	 *查询所有试驾
	 * @author tangqingsong
	 * @version 
	 * @param transMsg
	 * @param testDrive
	 * @return
	 */
	@RequestMapping(value = "/listTestDrive.vti")
	public String listTestDrive(TransMsg transMsg, @ModelAttribute("testDrive") TestDriveVO testDrive){
		Integer companyId = getCurrentCompanyId();
		Integer userId = getCurrentUserId();
		initSelectList(companyId, userId);
		if(voladtionTestDrive(testDrive)){
	//		testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_INVALID);
			ResponseData rd = adminService.findTestDrive(transMsg, testDrive, companyId, userId, false);
			
			getRequest().setAttribute("testDriveList", rd.getFirstItem());
			getRequest().setAttribute("testDrive", testDrive);
			getRequest().setAttribute("transMsg", transMsg);
			
			siteMenuType(rd);
		}
		return "admin/listTestDrive";
	} 
	
	/**
	 * 检验查询参数
	 * @author tangqingsong
	 * @version 
	 * @param currentGpsMessageVo
	 * @return
	 */
	private boolean voladtionTestDrive(TestDriveVO testDrive){
		if(testDrive==null){
			return false;
		}else if(testDrive.getStartTime_Nick()!=null && !testDrive.getStartTime_Nick().isEmpty()){
			return true;
		}else if(testDrive.getEndTime_Nick()!=null && !testDrive.getEndTime_Nick().isEmpty()){
			return true;
		}
		
		if(testDrive.getDealer()!=null){
			if(testDrive.getDealer().getDealerCode()!=null && !testDrive.getDealer().getDealerCode().isEmpty()){
				return true;
			}
		}
		if(testDrive.getVehicle()!=null){
			if(testDrive.getVehicle().getVin()!=null && !testDrive.getVehicle().getVin().isEmpty()){
				return true;
			}else if(testDrive.getVehicle().getPlateNumber()!=null && !testDrive.getVehicle().getPlateNumber().isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查询GPS点
	 * @author tangqingsong
	 * @version 
	 * @param transMsg
	 * @param currentGpsMessageVo
	 * @return
	 */
	@RequestMapping(value = "/listGpsPoint.vti")
	public String listGpsPoint(TransMsg transMsg,
			@ModelAttribute("currentGpsMessageVo") CurrentGpsMessageVo currentGpsMessageVo){
		if(voladtionGpsData(currentGpsMessageVo)){
			ResponseData rd = adminService.findGpsPoint(transMsg, currentGpsMessageVo);
			getRequest().setAttribute("gpsPointList", rd.getFirstItem());
			getRequest().setAttribute("transMsg", transMsg);
		}
		return "admin/listGpsPoint";
	}
	
	/**
	 * 检验查询参数
	 * @author tangqingsong
	 * @version 
	 * @param currentGpsMessageVo
	 * @return
	 */
	private boolean voladtionGpsData(CurrentGpsMessageVo currentGpsMessageVo){
		if(currentGpsMessageVo==null){
			return false;
		}else if(currentGpsMessageVo.getSerialNumber()!=null && !currentGpsMessageVo.getSerialNumber().isEmpty()){
			return true;
		}else if(currentGpsMessageVo.getVin()!=null && !currentGpsMessageVo.getVin().isEmpty()){
			return true;
		}else if(currentGpsMessageVo.getStartDate()!=null && !currentGpsMessageVo.getStartDate().isEmpty()){
			return true;
		}else if(currentGpsMessageVo.getEndDate()!=null && !currentGpsMessageVo.getEndDate().isEmpty()){
			return true;
		}
		return false;
	}
	
	@RequestMapping(value="/rehandleList.vti")
	public String rehandleList(){
		return "admin/rehandle";
	}
	
	@RequestMapping(value = "/listTask.vti")
	public String listTask(TransMsg transMsg, @ModelAttribute("taskMessageVo") TaskMessageVo taskMessageVo ){
			ResponseData rd = adminService.findTaskMessage(transMsg, taskMessageVo);
			getRequest().setAttribute("taskList", rd.getFirstItem());
			getRequest().setAttribute("transMsg", transMsg);
		return "admin/listTask";
	} 
	
	@RequestMapping(value="/rehandle.vti", method = RequestMethod.POST)
	public void rehandle() throws IOException{
		Map<String,Object> taskMessageMap = createTaskMessage(getRequest());
		if(taskMessageMap==null){
			getResponse().getWriter().write("任务提交失败,条件必须大于等于1项！");
			loger.info("试驾重跑任务提交失败");
			return;
		}
		//保存提交的任务
		TaskMessageVo taskMessageVo = (TaskMessageVo) taskMessageMap.get("taskMessageVo");
		reHandleTestDriveService.saveTaskMessage(taskMessageVo);
		TestDriveTask task = new TestDriveTask(reHandleTestDriveService, taskMessageMap);
		TestDriveThreadPool.execute(task);
		getResponse().getWriter().write("任务提交成功！");
		loger.info("试驾重跑任务提交成功：任务ID:"+taskMessageVo.getId());
//		reHandleTestDriveService.analyseByDelay();
	}
    
	/**
	 * 创建任务VO
	 * @author tangqingsong
	 * @version 
	 * @param request
	 * @return
	 */
	private Map<String,Object> createTaskMessage(HttpServletRequest request){
		Map<String,Object> argMap = new HashMap<String,Object>();
		String vin = getRequest().getParameter("vin");
		String startDate = getRequest().getParameter("startDate");
		String endDate = getRequest().getParameter("endDate");
		String companyId = getRequest().getParameter("companyId");
		
		TaskMessageVo taskMessageVo = new TaskMessageVo();
		taskMessageVo.setCreateTime(new Date());
		taskMessageVo.setStatus(GlobalConstant.TASK_STAUTS_READY);
		taskMessageVo.setTaskType(1);//手工重跑
		boolean validate = false;
		if(vin!=null && !vin.isEmpty()){
			taskMessageVo.setVin(vin);
			argMap.put("vin", vin);
			Vehicle vehicle=CacheVehicleManager.getVehicleByVin(vin);
			if(vehicle!=null && vehicle.getId()!=null){
				taskMessageVo.setVehicleId(vehicle.getId());
				argMap.put("vehicleId",vehicle.getId());
				validate = true;
			}
		}
		if(startDate!=null && !startDate.isEmpty()){
			taskMessageVo.setStartTime(startDate);
			argMap.put("startDate",startDate);
			argMap.put("startDateLong",startDate.replace(":", "").replace(" ", "").replace("-", "").trim());
			validate = true;
		}
		if(endDate!=null && !endDate.isEmpty()){
			taskMessageVo.setEndTime(endDate);
			argMap.put("endDate",endDate);
			argMap.put("endDateLong",endDate.replace(":", "").replace(" ", "").replace("-", "").trim());
			validate = true;
		}
		if(companyId!=null && !companyId.isEmpty()){
			taskMessageVo.setCompanyId(Integer.valueOf(companyId));
			argMap.put("companyId",companyId);
			validate = true;
		}
		if(!validate){
			//没有条件
			return null;
		}else{
			argMap.put("taskMessageVo", taskMessageVo);
			return argMap;
		}
	}
	
	/**
	 * 加载下拉列表
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @param userId
	 */
	private void initSelectList(Integer companyId, Integer userId) {
		List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
		getRequest().setAttribute("orgList", orgList);
	}
	
	@RequestMapping(value = "/listLog.vti")
	public String listLog(TransMsg transMsg, @ModelAttribute("taskMessageLogVo") TaskMessageLogVo taskMessageLogVo ){
			ResponseData rd = adminService.findLogMessage(transMsg, taskMessageLogVo,false);
			getRequest().setAttribute("logList", rd.getFirstItem());
			getRequest().setAttribute("transMsg", transMsg);
		return "admin/listLog";
	} 
	
	@RequestMapping(value = "/editLog.vti")
	public String editLog(TransMsg transMsg,@ModelAttribute("taskMessageLogVo") TaskMessageLogVo taskMessageLogVo ){
		taskMessageLogVo.setStatus(GlobalConstant.LOG_HANDLE_END);
		reHandleTestDriveService.updateLogStatus(taskMessageLogVo);
		return listLog(transMsg,new TaskMessageLogVo());
	} 
	
	@RequestMapping(value = "/exportLog.vti")
	public void exportLog(TransMsg transMsg, @ModelAttribute("taskMessageLogVo") TaskMessageLogVo taskMessageLogVo ){
			ResponseData rd = adminService.findLogMessage(transMsg, taskMessageLogVo,true);
			getRequest().setAttribute("logList", rd.getFirstItem());
			getRequest().setAttribute("transMsg", transMsg);
			List<TaskMessageLogVo> result = (List<TaskMessageLogVo>) rd.getFirstItem();
			write2excel(result);
	} 
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void write2excel(List<TaskMessageLogVo> logVoList) {
		
		String[] titles = {"ID","VIN",	"设备号","创建时间","状态",
				"起始时间","结束时间","累计"};

		String userDownLoadsUrl= System.getProperty("user.home")+"\\Downloads";
		String fileName ="异常日志查询";

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (logVoList != null && logVoList.size() > 0) {
			HSSFRow row ;
			HSSFCell cell ;
			TaskMessageLogVo logVo;
			for (int i = 0; i < logVoList.size(); i++) {
				 row = sheet.createRow(i + 1);
				 logVo = logVoList.get(i);
				cell = row.createCell(0);
				cell.setCellValue(logVo.getId());
				cell = row.createCell(1);
				cell.setCellValue(logVo.getVin());
				cell = row.createCell(2);
				cell.setCellValue(logVo.getSerialNumber());
				cell = row.createCell(3);
				String createTime = logVo.getCurtime()==null?"":DateTime.getDate(logVo.getCurtime(), DateTime.PATTERN_DATETIME);
				cell.setCellValue(createTime);
				cell = row.createCell(4);
				cell.setCellValue(logVo.getLogCode());
				cell = row.createCell(5);
				String startDate = logVo.getStartDate()==null?"":DateTime.getDate(logVo.getStartDate(), DateTime.PATTERN_DATETIME);
				cell.setCellValue(startDate);
				cell = row.createCell(6);
				String endDate = logVo.getEndDate()==null?"":DateTime.getDate(logVo.getEndDate(), DateTime.PATTERN_DATETIME);
				cell.setCellValue(endDate);
				cell = row.createCell(7);
				cell.setCellValue(logVo.getCounter()==null?0:logVo.getCounter());
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	@RequestMapping(value="/repairList.vti")
	public String repairList(){
		return "admin/repairGps";
	}
	
	@RequestMapping(value="/repairOnOutGps.vti")
	public void repairOnOutGps() throws IOException{
		Map<String,Object> argMap = getRequestParameter(getRequest());
		if(argMap==null){
			getResponse().getWriter().write("修复失败,条件必须大于等于1项！");
			loger.info("修复失败,条件必须大于等于1项！");
			return;
		}
		adminService.repairFireOnOrOut(argMap);
	}
	
	private Map<String,Object> getRequestParameter(HttpServletRequest request){
		Map<String,Object> argMap = new HashMap<String,Object>();
		String vin = getRequest().getParameter("vin");
		String startDate = getRequest().getParameter("startDate");
		String endDate = getRequest().getParameter("endDate");
		boolean validate = false;
		if(vin!=null && !vin.isEmpty()){
			argMap.put("vin", vin);
			Vehicle vehicle=CacheVehicleManager.getVehicleByVin(vin);
			if(vehicle!=null && vehicle.getId()!=null){
				argMap.put("vehicleId",vehicle.getId());
				validate = true;
			}
		}
		if(startDate!=null && !startDate.isEmpty()){
			argMap.put("startDate",startDate);
			argMap.put("startDateLong",startDate.replace(":", "").replace(" ", "").replace("-", "").trim());
			validate = true;
		}
		if(endDate!=null && !endDate.isEmpty()){
			argMap.put("endDate",endDate);
			argMap.put("endDateLong",endDate.replace(":", "").replace(" ", "").replace("-", "").trim());
			validate = true;
		}
		if(!validate){
			//没有条件
			return null;
		}else{
			return argMap;
		}
	}
}
