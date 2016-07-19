 package com.ava.dealer.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dealer.service.ICompanyCarStyleRelationService;
import com.ava.dealer.service.IProposalService;
import com.ava.dealer.service.IVehicleInstallationPlanService;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.CarSeries;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.DesignateOrder;
import com.ava.domain.entity.VehicleInstallationPlan;
import com.ava.domain.vo.DesignateOrderExcelVO;
import com.ava.domain.vo.DesignateOrderFindVO;
import com.ava.domain.vo.VehicleInstallationPlanVO;
import com.ava.facade.IPoposalFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.util.DateTime;
import com.ava.util.excel.ExcelUtil;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping(value="/dealer/installationPlan")
public class InstallationPlanController extends Base4MvcController {
	
	@Autowired
	IProposalService proposalService;
	
	@Autowired
	IPoposalFacade poposalFacade;
	
	@Autowired
	IVehicleService vehicleService;
	
	@Autowired
	ICompanyCarStyleRelationService companyCarStyleRelationService;
	
	@Autowired
	IVehicleInstallationPlanService vehicleInstallationPlanService;
	
	/**
	 * 显示车系列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param carSeries
	 * @return
	 */
	@RequestMapping(value="/listCarSeries.vti", method=RequestMethod.GET)
	public String listCarSeries(TransMsg transMsg, @ModelAttribute("carSeries") CarSeries carSeries) {
		ResponseData rd = vehicleInstallationPlanService.listCarSeries(transMsg, carSeries, false);
		
		getRequest().setAttribute("carSeries", carSeries);
		getRequest().setAttribute("carSeriesList", rd.get("carSeriesList"));
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		return "/dealer/installationPlan/listCarSeries";
	}
	
	/**
	 * 车系列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param carSeries
	 */
	@RequestMapping(value = "/exportCarSeries.vti", method = RequestMethod.GET)
	public void exportCarSeries(TransMsg transMsg, @ModelAttribute("carSeries") CarSeries carSeries) {
		ResponseData rd = vehicleInstallationPlanService.listCarSeries(transMsg, carSeries, true);

		carSeriesExcel((List<CarSeries>)rd.get("carSeriesList"));
	}
	
	/**
	 * 导出车系列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param carSeriesList
	 */
	public void carSeriesExcel(List<CarSeries> carSeriesList) {
		
		String[] titles = {"车系ID","车系名称"};
		
		String fileName ="";
	
		fileName="车系列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("车系明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (carSeriesList != null && carSeriesList.size() > 0) {
			for (int i = 0; i < carSeriesList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				CarSeries carSeries = carSeriesList.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(carSeries.getId());
				cell = row.createCell(++ y);
				cell.setCellValue(carSeries.getName());
				cell = row.createCell(++ y);
				
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 显示新增车系界面
	 * @author liuq 
	 * @version 0.1 
	 * @param carSeries
	 * @return
	 */
	@RequestMapping(value="/displayAddCarSeries.vti", method=RequestMethod.GET)
	public String displayAddCarSeries(@ModelAttribute("carSeries") CarSeries carSeries) {
		getRequest().setAttribute("carSeries", new CarSeries());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/installationPlan/addCarSeries";
	}

	/**
	 * 添加车系
	 * @author liuq 
	 * @version 0.1 
	 * @param carSeries
	 * @return
	 */
	@RequestMapping(value="/addCarSeries.vti", method=RequestMethod.POST)
	public String addCarSeries(@ModelAttribute("carSeries") CarSeries carSeries) {
		ResponseData rd = vehicleInstallationPlanService.addCarSeries(carSeries);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddCarSeries(carSeries);
	}
	
	/**
	 * 显示编辑车系界面
	 * @author liuq 
	 * @version 0.1 
	 * @param carSeriesId
	 * @return
	 */
	@RequestMapping(value="/displayEditCarSeries.vti", method=RequestMethod.GET)
	public String displayEditCarSeries(@RequestParam("carSeriesId") Integer carSeriesId) {
		ResponseData rd = vehicleInstallationPlanService.displayEditCarSeries(carSeriesId);
		getRequest().setAttribute("carSeries", rd.getFirstItem());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/installationPlan/editCarSeries";
	}

	/**
	 * 编辑车系
	 * @author liuq 
	 * @version 0.1 
	 * @param carSeries
	 * @return
	 */
	@RequestMapping(value="/editCarSeries.vti", method=RequestMethod.POST)
	public String editCarSeries(@ModelAttribute("carSeries") CarSeries carSeries) {
		ResponseData rd = vehicleInstallationPlanService.editCarSeries(carSeries);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditCarSeries(carSeries.getId());
	}
	
	/**
	 * 删除车系
	 * @author liuq 
	 * @version 0.1 
	 * @param carSeriesId
	 */
	@RequestMapping(value="/deleteCarSeries.vti", method=RequestMethod.GET)
	public void deleteCarSeries(@RequestParam("carSeriesId") Integer carSeriesId) {
		ResponseData rd = vehicleInstallationPlanService.deleteCarSeries(carSeriesId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		writeRd(rd);
	}
	
	/**
	 * 显示车型列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param carStyle
	 * @return
	 */
	@RequestMapping(value="/listCarStyle.vti", method=RequestMethod.GET)
	public String listCarStyle(TransMsg transMsg, @ModelAttribute("carStyle") CarStyle carStyle) {
		ResponseData rd = vehicleInstallationPlanService.listCarStyle(transMsg, carStyle, false);
		
		getRequest().setAttribute("carStyle", carStyle);
		getRequest().setAttribute("carStyleList", rd.get("carStyleList"));
		getRequest().setAttribute("transMsg", transMsg);
		
		List<CarSeries> carSeriesList = vehicleInstallationPlanService.getCarSeriesList();
		getRequest().setAttribute("carSeriesList", carSeriesList);
		
		siteMenuType(rd);
		
		return "/dealer/installationPlan/listCarStyle";
	}
	
	/**
	 * 车型列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param carStyle
	 */
	@RequestMapping(value = "/exportCarStyle.vti", method = RequestMethod.GET)
	public void exportCarStyle(TransMsg transMsg, @ModelAttribute("carStyle") CarStyle carStyle) {
		ResponseData rd = vehicleInstallationPlanService.listCarStyle(transMsg, carStyle, true);

		carStyleExcel((List<CarStyle>)rd.get("carStyleList"));
	}
	
	/**
	 * 导出车型列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param carStyleList
	 */
	public void carStyleExcel(List<CarStyle> carStyleList) {
		
		String[] titles = {"车型ID","车型名称","车系ID","车系名称"};
		
		String fileName ="";
	
		fileName="车型列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("车型明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (carStyleList != null && carStyleList.size() > 0) {
			for (int i = 0; i < carStyleList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				CarStyle carStyle = carStyleList.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(carStyle.getId());
				cell = row.createCell(++ y);
				cell.setCellValue(carStyle.getName() + " " + carStyle.getYearType());
				cell = row.createCell(++ y);
				cell.setCellValue(carStyle.getSeriesId());
				cell = row.createCell(++ y);
				cell.setCellValue(carStyle.getSeriesName());
				cell = row.createCell(++ y);
				
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 显示新增车型界面
	 * @author liuq 
	 * @version 0.1 
	 * @param carStyle
	 * @return
	 */
	@RequestMapping(value="/displayAddCarStyle.vti", method=RequestMethod.GET)
	public String displayAddCarStyle(@ModelAttribute("carStyle") CarStyle carStyle) {
		getRequest().setAttribute("carStyle", new CarStyle());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		List<CarSeries> carSeriesList = vehicleInstallationPlanService.getCarSeriesList();
		getRequest().setAttribute("carSeriesList", carSeriesList);
		
		return "/dealer/installationPlan/addCarStyle";
	}

	/**
	 * 添加车型
	 * @author liuq 
	 * @version 0.1 
	 * @param carStyle
	 * @return
	 */
	@RequestMapping(value="/addCarStyle.vti", method=RequestMethod.POST)
	public String addCarStyle(@ModelAttribute("carStyle") CarStyle carStyle) {
		ResponseData rd = vehicleInstallationPlanService.addCarStyle(carStyle);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddCarStyle(carStyle);
	}
	
	/**
	 * 显示编辑车型界面
	 * @author liuq 
	 * @version 0.1 
	 * @param carStyleId
	 * @return
	 */
	@RequestMapping(value="/displayEditCarStyle.vti", method=RequestMethod.GET)
	public String displayEditCarStyle(@RequestParam("carStyleId") Integer carStyleId) {
		ResponseData rd = vehicleInstallationPlanService.displayEditCarStyle(carStyleId);
		getRequest().setAttribute("carStyle", rd.getFirstItem());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/installationPlan/editCarStyle";
	}

	/**
	 * 编辑车型
	 * @author liuq 
	 * @version 0.1 
	 * @param carStyle
	 * @return
	 */
	@RequestMapping(value="/editCarStyle.vti", method=RequestMethod.POST)
	public String editCarStyle(@ModelAttribute("carStyle") CarStyle carStyle) {
		ResponseData rd = vehicleInstallationPlanService.editCarStyle(carStyle);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditCarStyle(carStyle.getId());
	}
	
	/**
	 * 删除车型
	 * @author liuq 
	 * @version 0.1 
	 * @param carStyleId
	 */
	@RequestMapping(value="/deleteCarStyle.vti", method=RequestMethod.GET)
	public void deleteCarStyle(@RequestParam("carStyleId") Integer carStyleId) {
		ResponseData rd = vehicleInstallationPlanService.deleteCarStyle(carStyleId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		writeRd(rd);
	}
	
	/**
	 * 显示换装、拆除计划列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleInstallationPlan
	 * @return
	 */
	@RequestMapping(value="/listInstallationPlan.vti", method=RequestMethod.GET)
	public String listInstallationPlan(TransMsg transMsg, @ModelAttribute("vehicleInstallationPlan") VehicleInstallationPlanVO vehicleInstallationPlanVO) {
		ResponseData rd = vehicleInstallationPlanService.listVehicleInstallationPlan(transMsg, vehicleInstallationPlanVO, false);

		getRequest().setAttribute("vehicleInstallationPlan", vehicleInstallationPlanVO);
		getRequest().setAttribute("vehicleInstallationPlanList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList = companyCarStyleRelationService.findByCompanyId(companyId, null);
		getRequest().setAttribute("carStyleList", carStyleList);
		
		siteMenuType(rd);
		
		return "/dealer/installationPlan/listInstallationPlan";
	}
	
	/**
	 * 显示换装、拆除计划添加页面
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleInstallationPlan
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/displayAddInstallationPlan.vti", method=RequestMethod.GET)
	public String displayAddInstallationPlan(@ModelAttribute("vehicleInstallationPlan") VehicleInstallationPlan vehicleInstallationPlan, @RequestParam("type") Integer type) {
		getRequest().setAttribute("vehicleInstallationPlan", new VehicleInstallationPlan());
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList = companyCarStyleRelationService.findByCompanyId(companyId, null);
		getRequest().setAttribute("carStyleList", carStyleList);
		if (type == 1) {
			return "/dealer/installationPlan/addReplacePlan";
		} else {
			return "/dealer/installationPlan/addDemolitionPlan";
		}
	}
	
	/**
	 * 【启用】换装/拆除计划
	 * @author liuq 
	 * @version 0.1 
	 * @param installationPlanId
	 */
	@RequestMapping(value="/enableInstallationPlan.vti", method=RequestMethod.GET)
	public void enableInstallationPlan(@RequestParam("installationPlanId") Integer installationPlanId) {
		ResponseData rd = vehicleInstallationPlanService.enableInstallationPlan(installationPlanId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		writeRd(rd);
	}
	
	/**
	 * 【停用】换装/拆除计划
	 * @author liuq 
	 * @version 0.1 
	 * @param installationPlanId
	 */
	@RequestMapping(value="/disableInstallationPlan.vti", method=RequestMethod.GET)
	public void disableInstallationPlan(@RequestParam("installationPlanId") Integer installationPlanId) {
		ResponseData rd = vehicleInstallationPlanService.disableInstallationPlan(installationPlanId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		writeRd(rd);
	}
	
	/**
	 * 【删除】换装/拆除计划
	 * @author liuq 
	 * @version 0.1 
	 * @param installationPlanId
	 */
	@RequestMapping(value="/deleteInstallationPlan.vti", method=RequestMethod.GET)
	public void deleteInstallationPlan(@RequestParam("installationPlanId") Integer installationPlanId) {
		ResponseData rd = vehicleInstallationPlanService.deleteInstallationPlan(installationPlanId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		writeRd(rd);
	}
		
	/**
	 * 添加换装/拆除计划
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleInstallationPlan
	 * @return
	 */
	@RequestMapping(value="/addInstallationPlan.vti", method=RequestMethod.POST)
	public String addInstallationPlan(@ModelAttribute("vehicleInstallationPlan") VehicleInstallationPlan vehicleInstallationPlan) {
		ResponseData rd = vehicleInstallationPlanService.addInstallationPlan(vehicleInstallationPlan);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddInstallationPlan(vehicleInstallationPlan, vehicleInstallationPlan.getType());
	}
	
	/**
	 * 显示指派订单列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param designateOrder
	 * @return
	 */
	@RequestMapping(value="/listDesignateOrder.vti", method=RequestMethod.GET)
	public String listDesignateOrder(TransMsg transMsg, @ModelAttribute("designateOrder") DesignateOrder designateOrder) {
		ResponseData rd = vehicleInstallationPlanService.listDesignateOrder(transMsg, designateOrder, false);

		getRequest().setAttribute("designateOrder", designateOrder);
		getRequest().setAttribute("designateOrderList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		return "/dealer/installationPlan/listDesignateOrder";
	}
	
	/**
	 * 删除指派订单
	 * @author liuq 
	 * @version 0.1 
	 * @param designateOrderId
	 */
	@RequestMapping(value="/deleteDesignateOrder.vti", method=RequestMethod.GET)
	public void deleteDesignateOrder(@RequestParam("designateOrderId") Integer designateOrderId) {
		ResponseData rd = vehicleInstallationPlanService.deleteDesignateOrder(designateOrderId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		writeRd(rd);
	}
	
	/**
	 * 指派订单导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param designateOrder
	 */
	@RequestMapping(value = "/exportDesignateOrder.vti", method = RequestMethod.GET)
	public void exportDesignateOrder(TransMsg transMsg, @ModelAttribute("designateOrder") DesignateOrder designateOrder) {
		ResponseData rd = vehicleInstallationPlanService.listDesignateOrder(transMsg, designateOrder, true);

		designateOrderExcel((List<DesignateOrderFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出指派订单列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param designateOrderList
	 */
	public void designateOrderExcel(List<DesignateOrderFindVO> designateOrderList) {
		
		String[] titles = {"网络代码","经销商名称","换装前VIN","换装前车型","换装后车型","创建时间"};
		
		String fileName ="";
	
		fileName="指派订单列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("指派订单明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (designateOrderList != null && designateOrderList.size() > 0) {
			for (int i = 0; i < designateOrderList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				DesignateOrderFindVO designateOrder = designateOrderList.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(designateOrder.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(designateOrder.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(designateOrder.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(designateOrder.getTestDriveCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(designateOrder.getDessCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(designateOrder.getCreationTime()));
				cell = row.createCell(++ y);
				
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	
	/**
	 * 显示导入指派订单页面
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	@RequestMapping(value="/displayImportDesignateOrder.vti", method=RequestMethod.GET)
	public String displayImportDesignateOrder() {
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		return "/dealer/installationPlan/addDesignateOrder";
	}
	
	/**
	 * 导入指派订单
	 * @author liuq 
	 * @version 0.1 
	 * @param importExcelPacket
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/importDesignateOrder.vti")
	public String importDesignateOrder(@RequestParam("importExcelPacket") CommonsMultipartFile importExcelPacket) throws IOException{
		ExcelUtil<DesignateOrderExcelVO> excelUtil = new ExcelUtil<DesignateOrderExcelVO>(DesignateOrderExcelVO.class);
		String fileType=excelUtil.CheckMultipartFile(importExcelPacket);
		if(fileType.equals("xls")||fileType.equals("xlsx")){
			List<DesignateOrderExcelVO> importList = excelUtil.importExcel("", importExcelPacket.getInputStream());
			ResponseData rd = vehicleInstallationPlanService.upLoadDesignateOrder(importList);
			if (rd.getCode() == 1) {
				return "/pub/jsPages/closeDialogAndReloadParent";
				
			} else {
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
						GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			}
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, fileType);
		}
		return displayImportDesignateOrder();
	}

	
}
