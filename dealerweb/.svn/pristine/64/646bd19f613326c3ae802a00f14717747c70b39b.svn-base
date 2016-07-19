package com.ava.dealer.controller;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.ICompanyService;
import com.ava.dealer.service.ICompanyCarStyleRelationService;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.vo.RebateFindVO;
import com.ava.domain.vo.VehicleFindVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping("/dealer/vehicle")
public class VehicleController extends Base4MvcController {
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private	ICompanyService companyService;
	
	@Autowired
	ICompanyCarStyleRelationService companyCarStyleRelationService;
	
	/**
	 * 查询车辆列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @return
	 */
	@RequestMapping(value = "/list.vti", method = RequestMethod.GET)
	public String listVehicle(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listVehicle(transMsg, vehicleVO, companyId, currentUserId, false);

		getRequest().setAttribute("vehicleList", rd.getFirstItem());
		getRequest().setAttribute("vehicle", vehicleVO);
		getRequest().setAttribute("transMsg", transMsg);

		siteMenuType(rd);
		initSelectList(companyId);
		return "/dealer/vehicle/listVehicle";
	}
	
	/**
	 * 查询历史车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @return
	 */
	@RequestMapping(value = "/listHistory.vti", method = RequestMethod.GET)
	public String listHistoryVehicle(TransMsg transMsg,
			@ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		vehicleVO.setIsHistroy(GlobalConstant.TRUE);
		vehicleVO.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED);
		ResponseData rd = vehicleService.listHistoryVehicle(transMsg, vehicleVO, companyId, currentUserId, false);
		
		getRequest().setAttribute("vehicleList", rd.getFirstItem());
		getRequest().setAttribute("vehicle", vehicleVO);
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);

		initSelectList(companyId);
		return "/dealer/vehicle/listHistoryVehicle";
	}
	
	/**
	 * 查询车辆返利
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @return
	 */
	@RequestMapping(value = "/listVehicleRebate.vti", method = RequestMethod.GET)
	public String listVehicleRebate(TransMsg transMsg,
			@ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listVehicleRebate(transMsg, vehicleVO, companyId, currentUserId, false);

		getRequest().setAttribute("vehicleRebateList", rd.getFirstItem());
		getRequest().setAttribute("vehicle", vehicleVO);
		getRequest().setAttribute("transMsg", transMsg);

		siteMenuType(rd);

		initSelectList(companyId);
		return "/dealer/vehicle/listVehicleRebate";
	}
	
	/**
	 * 车辆维护列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @return
	 */
	@RequestMapping(value = "/listMaintainVehicle.vti", method = RequestMethod.GET)
	public String listMaintainVehicle(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listMaintainVehicle(transMsg, vehicleVO, companyId, currentUserId, false);
		if(rd == null){
			getRequest().setAttribute("vehicleList", null);
		} else {
			getRequest().setAttribute("vehicleList", rd.getFirstItem());
		}

		getRequest().setAttribute("vehicle", vehicleVO);
		getRequest().setAttribute("transMsg", transMsg);

		siteMenuType(rd);

		initSelectList(companyId);
		return "/dealer/vehicle/listMaintainVehicle";
	}
	
	/**
	 * 车辆OBD监控列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @return
	 */
	@RequestMapping(value = "/listMonitorVehicleObd.vti", method = RequestMethod.GET)
	public String listMonitorVehicleObd(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listMonitorVehicleObd(transMsg, vehicleVO, companyId, currentUserId, false);
		if(rd == null){
			getRequest().setAttribute("vehicleList", null);
		} else {
			getRequest().setAttribute("vehicleList", rd.getFirstItem());
		}
		
		getRequest().setAttribute("vehicle", vehicleVO);
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		initSelectList(companyId);
		return "/dealer/vehicle/listMonitorVehicleObd";
	}
	
	/**
	 * 车辆维护-显示新增车辆界面
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAdd(@ModelAttribute("vehicle") VehicleVO vehicle) {
		ResponseData rd = vehicleService.displayAddMaintainVehicle(vehicle);
		getRequest().setAttribute("vehicleVO", rd.getFirstItem());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		initSelectList(companyId);
		
		return "/dealer/vehicle/addMaintainVehicle";
	}

	/**
	 * 车辆维护-添加车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addVehicle(@ModelAttribute("vehicle") VehicleVO vehicle) {
		ResponseData rd = vehicleService.addMaintainVehicle(vehicle);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAdd(vehicle);
	}
	
	/**
	 * 车辆维护-显示编辑车辆界面
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleId
	 * @return
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEdit(@RequestParam("vehicleId") Integer vehicleId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd = vehicleService.displayEditMaintainVehicle(vehicleId);
		getRequest().setAttribute("vehicleVO", rd.getFirstItem());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(companyId);
		return "/dealer/vehicle/editMaintainVehicle";
	}

	/**
	 * 车辆维护-编辑车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editVehicle(@ModelAttribute("vehicle") VehicleVO vehicle) {
		ResponseData rd  = vehicleService.editMaintainVehicle(vehicle);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEdit(vehicle.getId());
	}
	
	/**
	 * 历史车辆-显示恢复车辆界面
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleId
	 * @return
	 */
	@RequestMapping(value="/displayRegainVehcile.vti", method=RequestMethod.GET)
	public String displayRegainVehcile(@RequestParam("vehicleId") Integer vehicleId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd = vehicleService.displayEditMaintainVehicle(vehicleId);
		getRequest().setAttribute("vehicleVO", rd.getFirstItem());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(companyId);
		return "/dealer/vehicle/regainVehicle";
	}
	
	/**
	 * 历史车辆-恢复车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value="/regainVehcile.vti", method=RequestMethod.POST)
	public String regainVehcile(@ModelAttribute("vehicle") VehicleVO vehicle) {
		ResponseData rd  = vehicleService.regainVehcile(vehicle);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayRegainVehcile(vehicle.getId());
	}
	
	/**
	 * 车辆列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicle
	 */
	@RequestMapping(value = "/exportVehicle.vti", method = RequestMethod.GET)
	public void exportVehicle(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listVehicle(transMsg, vehicleVO,
				companyId, currentUserId, true);

		vehicleExcel((List<VehicleFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出车辆列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicles
	 */
	public void vehicleExcel(List<VehicleFindVO> vehicles) {
		
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","经销商状态","网络形态","车型","VIN","车牌","设备号","安装时间","状态","可拆除","新入网经销商","一级网点代码","一级网点名称"};
		
		String fileName ="";
	
		fileName="车辆列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("车辆明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (vehicles != null && vehicles.size() > 0) {
			for (int i = 0; i < vehicles.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				VehicleFindVO vehicle = vehicles.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vehicle.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getObdNo());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vehicle.getInstallationTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getConfigureStatusNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getIsDemolitionNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getIsNetWorkDealerNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getParentCompanyName());
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 历史车辆导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicle
	 */
	@RequestMapping(value = "/exportHistoryVehicle.vti", method = RequestMethod.GET)
	public void exportHistoryVehicle(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		vehicleVO.setIsHistroy(GlobalConstant.TRUE);
		vehicleVO.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED);
		ResponseData rd = vehicleService.listHistoryVehicle(transMsg, vehicleVO, companyId, currentUserId, true);
		
		histroyVehicelExcel((List<VehicleFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出历史车辆到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicles
	 */
	public void histroyVehicelExcel(List<VehicleFindVO> vehicles) {
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","经销商状态","网络形态","车型","VIN","车牌","设备号","状态","安装时间","obd拆除时间","拆除申请时间","一级网点代码","一级网点名称"};
		
		String fileName ="";
		fileName="历史车辆列表"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("车辆明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (vehicles != null && vehicles.size() > 0) {
			for (int i = 0; i < vehicles.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				VehicleFindVO vehicle = vehicles.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vehicle.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getObdNo());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getConfigureStatusNick());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vehicle.getInstallationTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vehicle.getDemolitionTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vehicle.getDeletionTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getParentCompanyName());
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 车辆返利导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicle
	 */
	@RequestMapping(value = "/exportVehicleRebate.vti", method = RequestMethod.GET)
	public void exportVehicleRebate(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listVehicleRebate(transMsg, vehicleVO, companyId, currentUserId, true);
		vehicleReBateExcel((List<RebateFindVO>) rd.getFirstItem());
	}
	
	/**
	 * 导出车辆返利到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param rebates
	 */
	public void vehicleReBateExcel(List<RebateFindVO> rebates) {
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","经销商状态","网络形态","车牌","车型","VIN","季度","返利情况","一级网点代码","一级网点名称"};
		
		String fileName ="";
		fileName="车辆返利列表"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("返利明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (rebates != null && rebates.size() > 0) {
			for (int i = 0; i < rebates.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				RebateFindVO rebate = rebates.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(rebate.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue("Q" + rebate.getSeason());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getIsRebateNick());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(rebate.getParentCompanyName());
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 车辆维护列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicle
	 */
	@RequestMapping(value = "/exportMaintainVehicle.vti", method = RequestMethod.GET)
	public void exportMaintainVehicle(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listMaintainVehicle(transMsg, vehicleVO, companyId, currentUserId, true);

		maintainVehicleExcel((List<VehicleFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出车辆维护列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicles
	 */
	public void maintainVehicleExcel(List<VehicleFindVO> vehicles) {
		
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","网络形态","车型","VIN","车牌","设备号","安装时间","状态","新入网经销商"};
		
		String fileName ="";
	
		fileName="车辆维护列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("车辆明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (vehicles != null && vehicles.size() > 0) {
			for (int i = 0; i < vehicles.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				VehicleFindVO vehicle = vehicles.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vehicle.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getObdNo());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vehicle.getInstallationTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getConfigureStatusNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getIsNetWorkDealerNick());
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 车辆OBD监控列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicle
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportMonitorVehicleObd.vti", method = RequestMethod.GET)
	public void exportMonitorVehicleObd(TransMsg transMsg, @ModelAttribute("vehicle") VehicleVO vehicleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = vehicleService.listMonitorVehicleObd(transMsg, vehicleVO, companyId, currentUserId, true);
		
		monitorVehicleObdExcel((List<VehicleFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出车辆OBD监控列表数据到excel
	 * @param vehicles
	 */
	public void monitorVehicleObdExcel(List<VehicleFindVO> vehicles) {
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","网络形态","车型","VIN","车牌","设备号","状态","OBD"};
		
		String fileName ="";
		fileName = "车辆OBD监控列表信息"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("车辆明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (vehicles != null && vehicles.size() > 0) {
			for (int i = 0; i < vehicles.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				VehicleFindVO vehicle = vehicles.get(i);
				Integer y = 0;
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(vehicle.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getObdNo());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getConfigureStatusNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vehicle.getObdFlagNick());
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 加载下拉列表
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 */
	private void initSelectList(Integer companyId) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		List orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
		getRequest().setAttribute("orgList", orgList);
		  
		List installationList = SelectOptionResource.configureStatusOptions();
		getRequest().setAttribute("installationList", installationList);
		
		List<CarStyle> carStyleList = companyCarStyleRelationService.findByCompanyId(companyId, null);
		getRequest().setAttribute("carStyleList", carStyleList);
			
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	private void write2excel3(List<VehicleVO> vehicles) {
		String fileName = "车辆列表" + DateTime.toShortDateTime(new Date());
		String[][] properties = new String[][]{
				{"分销中心", "distributionSaleCenterName"},
				{"网络代码", "dealerCode"},
				{"经销商名称", "cnName"},
				{"经销商状态", "isKeyCity_Nick"},
				{"网络形态", "dealerType_Nick"},
				{"省份", "regionProvinceId_Nick"},
				{"城市", "regionCityId_Nick"},
				{"车牌", "plateNumber"},
				{"车型", "carStyleId_Nick"},
				{"VIN", "vin"},
				{"通讯号", "simMobile"},
				{"设备号", "uniqueId"},
				{"状态", "configureStatus_Nick"},
				{"安装时间", "operationTime"},
				{"可拆除", "status"}
		};
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheetWithTitle = ExportExcelUtil.buildHSSFRowOfTitle(workbook, fileName, properties);
		if (vehicles != null && vehicles.size() > 0) {
			for (int i = 0; i < vehicles.size(); i++) {
				HSSFRow row = sheetWithTitle.createRow(i + 1);
				VehicleVO vehicleVO = vehicles.get(i);
				ExportExcelUtil.toHSSFRow(new Object[]{vehicleVO.getDealer(), vehicleVO, vehicleVO.getBox(), vehicleVO.getBoxOperationVO()}, row, properties);
			}
		}
		ExportExcelUtil.sendFileToUser(fileName, workbook, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	/**
	 * 通过VIN查询车辆信息
	 * @author liuq 
	 * @version 0.1
	 */
	@RequestMapping(value = "/findVehicleInfo.vti", method = RequestMethod.GET)
	public void findVehicleInfo() {
		ResponseData rd = new ResponseData(0);
		String vin = getStringParameter("vin");
		if (vin != null) {
			VehicleVO vehicle = vehicleService.getVehcileByVin(vin);
			if  (vehicle != null) {
				 String licensingTime = DateTime.toNormalDate(vehicle.getLicensingTime());
				 vehicle.setLicensingTimeStr(licensingTime);
			     rd.put("vehicle", vehicle);
			}
			rd.setCode(1);
		} else {
			rd.setCode(-1);
			rd.setMessage("系统错误");
		}
		
		writeRd(rd);
	}
	
	/**
	 * 通过经销商代码查询经销商
	 * @author liuq 
	 * @version 0.1 
	 * @param dealerCode
	 */
	@RequestMapping(value = "/findDealerInfo.vti", method = RequestMethod.GET)
	public void findDealerInfo(@RequestParam("dealerCode") String dealerCode) {
		ResponseData rd = new ResponseData(0);
		if (dealerCode != null) {
			Company company = companyService.getCompanyByDealerCode(dealerCode);
			if  (company != null) {
			     rd.put("company", company);
			}
			rd.setCode(1);
		} else {
			rd.setCode(-1);
			rd.setMessage("系统错误");
		}
		
		writeRd(rd);
	}

}
