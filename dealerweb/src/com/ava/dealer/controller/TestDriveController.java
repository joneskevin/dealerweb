package com.ava.dealer.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dealer.service.ITestDriveService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Org;
import com.ava.domain.vo.NoTestDriveVO;
import com.ava.domain.vo.TestDrive4StatVO;
import com.ava.domain.vo.TestDriveDetailFindVO;
import com.ava.domain.vo.TestDriveFindVO;
import com.ava.domain.vo.TestDriveStatisticFindVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping("/dealer/testDrive")
public class TestDriveController extends Base4MvcController {
	
	Logger logger= Logger.getLogger(TestDriveController.class);
	
	@Autowired
	private ITestDriveService testDriveService;
	
	/**
	 * 有效试驾
	 * @param transMsg
	 * @param testDrive
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/listValidTestDrive.vti", method = RequestMethod.GET)
	public String listValidTestDrive(Model model, TransMsg transMsg, @ModelAttribute("testDrive") TestDriveVO testDrive, String startTime, String endTime) {
		try {
			
			if (StringUtils.isNotBlank(startTime)) {
				model.addAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			} else {
				startTime = DateTime.toNormalDate(DateTime.minusDays(new Date(), 30)) + " 00:00:00 ";
				model.addAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			}
			
			if (StringUtils.isNotBlank(endTime)) {
				model.addAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			} else {
				endTime = DateTime.getNormalDate()+ " 23:59:59 ";
				model.addAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}
			
			Integer companyId = getCurrentCompanyId();
			Integer userId = getCurrentUserId();
			testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_VALID);
			ResponseData rd = testDriveService.listTestDrive(transMsg, testDrive, startTime, endTime, companyId, userId, false);

			model.addAttribute("testDriveList", rd.getFirstItem());
			model.addAttribute("testDrive", testDrive);
			model.addAttribute("transMsg", transMsg);

			initSelectList(model,companyId, userId);
			siteMenuType(model,rd);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "/dealer/testDrive/listValidTestDrive";
	}
	
	/**
	 * 无效试驾明细
	 * @param transMsg
	 * @param testDrive
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/listInValidTestDrive.vti", method = RequestMethod.GET)
	public String listInValidTestDrive(Model model, TransMsg transMsg, @ModelAttribute("testDrive") TestDriveVO testDrive, String startTime, String endTime) {
		try {
			
			if (StringUtils.isNotBlank(startTime)) {
				model.addAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			} else {
				startTime = DateTime.toNormalDate(DateTime.minusDays(new Date(), 30)) + " 00:00:00 ";
				model.addAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			}
			
			if (StringUtils.isNotBlank(endTime)) {
				model.addAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			} else {
				endTime = DateTime.getNormalDate()+ " 23:59:59 ";
				model.addAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}
			
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer userId = SessionManager.getCurrentUserId(getRequest());
			testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_INVALID);
			ResponseData rd = testDriveService.listTestDrive(transMsg, testDrive, startTime, endTime, companyId, userId, false);
			
			model.addAttribute("testDriveList", rd.getFirstItem());
			model.addAttribute("testDrive", testDrive);
			model.addAttribute("transMsg", transMsg);
			
			initSelectList(model,companyId, userId);
			siteMenuType(model,rd);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return "/dealer/testDrive/listInValidTestDrive";
	}
	
	/**
	 * 连续无试驾查询
	 * @param transMsg
	 * @param noTestDrive
	 * @return
	 */
	@RequestMapping(value = "/listNoTestDrive.vti", method = RequestMethod.GET)
	public String listNoTestDrive(Model model, TransMsg transMsg, @ModelAttribute("noTestDrive") NoTestDriveVO noTestDrive) {
		if (noTestDrive.getDurationDayCount() == null) {
			noTestDrive.setDurationDayCount(5);
		} else {
			if (noTestDrive.getDurationDayCount().intValue() < 5) {
				noTestDrive.setDurationDayCount(5);
			}
		}
		
		if (noTestDrive.getSeason() ==  null) {
			noTestDrive.setSeason(DateTime.getSeason(new Date()));
		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = testDriveService.listNoTestDrive(transMsg, noTestDrive, companyId, false);
		
		model.addAttribute("noTestDriveList", rd.getFirstItem());
		model.addAttribute("transMsg", transMsg);
		
		List<Org> departmentList = CacheOrgManager.getChildrenOrgTree4Select(null,companyId);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("testDriveSeasonList", SelectOptionResource.constantSeasonOptions());
		
		siteMenuType(model,rd);
		
		return "/dealer/testDrive/listNoTestDrive";
	}
	
	/**
	 * 有效试驾导出
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param startTime
	 * @param endTime
	 */
	@RequestMapping(value = "/exportValidTestDrive.vti", method = RequestMethod.GET)
	public void exportValidTestDrive(TransMsg transMsg, @ModelAttribute("testDrive") TestDriveVO testDrive, String startTime, String endTime) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_VALID);
		ResponseData rd = testDriveService.listTestDrive(transMsg, testDrive, startTime, endTime, companyId, userId, true);
		validTestDriveExcel((List<TestDriveFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出有效试驾数据到EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param testDriveFindVOs
	 */
	public void validTestDriveExcel(List<TestDriveFindVO> testDriveFindVOs) {
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态",
				"车型","VIN","车牌","线路","次数","里程[km]","试驾日期","开始时间","结束时间","状态","一级网点代码","一级网点名称"};
	
		String fileName ="有效试驾信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("有效试驾");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (testDriveFindVOs != null && testDriveFindVOs.size() > 0) {
			for (int i = 0; i < testDriveFindVOs.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				TestDriveFindVO vo = testDriveFindVOs.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vo.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getLineNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getLoopCount());
				cell = row.createCell(++ y);
				String mileage = "0";
				if (vo.getMileage() != null && vo.getMileage() > 0) {
					mileage = new DecimalFormat("#.#").format((float)vo.getMileage() / (float)1000);
				}
				cell.setCellValue(mileage);
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDate(vo.getTestDriveDate()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getStartTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getEndTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getStatusNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentCompanyName());
				cell = row.createCell(++ y);
			}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 无效试驾导出
	 * @author liuq 
	 * @version 0.1 
	 * @param model
	 * @param transMsg
	 * @param testDrive
	 * @param startTime
	 * @param endTime
	 */
	@RequestMapping(value = "/exportInValidTestDrive.vti", method = RequestMethod.GET)
	public void exportInValidTestDrive(Model model, TransMsg transMsg, @ModelAttribute("testDrive") TestDriveVO testDrive, String startTime, String endTime) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		
		testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_INVALID);
		ResponseData rd = testDriveService.listTestDrive(transMsg, testDrive, startTime, endTime, companyId, userId, true);
		
		model.addAttribute("testDriveList", rd.getFirstItem());
		model.addAttribute("transMsg", transMsg);
		
		inValidTestDriveExcel((List<TestDriveFindVO>)rd.getFirstItem());
	}

	/**
	 * 导出无效试驾数据到EXCEL
	 * @param violations
	 */
	public void inValidTestDriveExcel(List<TestDriveFindVO> testDriveFindVOs) {
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态",
				"车型","VIN","车牌","线路","里程[km]","开始时间","结束时间","一级网点代码","一级网点名称"};
		String fileName="无效试驾"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("无效试驾");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (testDriveFindVOs != null && testDriveFindVOs.size() > 0) {
			for (int i = 0; i < testDriveFindVOs.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				TestDriveFindVO vo = testDriveFindVOs.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vo.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getLineNick());
				cell = row.createCell(++ y);
				String mileage = "0";
				if (vo.getMileage() != null && vo.getMileage() > 0) {
					mileage = new DecimalFormat("#.#").format((float)vo.getMileage() / (float)1000);
				}
				cell.setCellValue(mileage);
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getStartTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getEndTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentCompanyName());
				cell = row.createCell(++ y);
			}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 连续无试驾导出EXCEL
	 * @param transMsg
	 * @param testDrive
	 */
	@RequestMapping(value = "/outputListNoTestDriveExcel.vti", method = RequestMethod.GET)
	public void outputListNoTestDriveExcel(TransMsg transMsg,
			@ModelAttribute("noTestDrive") NoTestDriveVO noTestDrive) {
		transMsg.setPageSize(Integer.MAX_VALUE);
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = testDriveService.listNoTestDrive(transMsg, noTestDrive, companyId, true);
		write2excelNoTestDrive((List<NoTestDriveVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出数据到excel 连续无试驾
	 * @param violations
	 */
	public  void write2excelNoTestDrive(List<NoTestDriveVO> noTestDriveVOs) {
		String[] titles = {"分销中心","省份","城市","经销商名称",
				"网络代码","经销商状态","网络形态","车牌","车型","VIN","连续天数","开始时间","结束时间"};
		String fileName ="";
		
		fileName="非活跃试驾明细"+DateTime.toShortDateTime(new Date());
		
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("非活跃试驾明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (noTestDriveVOs != null && noTestDriveVOs.size() > 0) {
		for (int i = 0; i < noTestDriveVOs.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			NoTestDriveVO noTestDriveVO = noTestDriveVOs.get(i);
			HSSFCell cell = row.createCell(0);
			Integer y = 1;
			
			cell.setCellValue(noTestDriveVO.getDealer().getParentName());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getDealer().getRegionProvinceId_Nick());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getDealer().getRegionCityId_Nick());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getDealer().getCnName());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getDealer().getDealerCode());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getDealer().getIsKeyCity_Nick());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getDealer().getDealerType_Nick());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getVehicle().getPlateNumber());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getVehicle().getCarStyleId_Nick());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getVehicle().getVin());
			cell = row.createCell(++ y);
			cell.setCellValue(noTestDriveVO.getDurationDayCount());
			cell = row.createCell(++ y);
			cell.setCellValue(DateTime.toNormalDateTime(noTestDriveVO.getStartTime()));
			cell = row.createCell(++ y);
			cell.setCellValue(DateTime.toNormalDateTime(noTestDriveVO.getEndTime()));
			cell = row.createCell(++ y);
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 单周统计-查询
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param frmTestDrive
	 * @param plateNumber
	 * @param vin
	 * @return
	 */
	@RequestMapping(value = "/listWeek.vti", method = RequestMethod.GET)
	public String listTestDriveOfWeek(Model model, TransMsg transMsg, @ModelAttribute("testDrive") TestDrive4StatVO frmTestDrive,
			@RequestParam( value= "dealerCode", required = false) String dealerCode,
			@RequestParam( value= "plateNumber", required = false) String plateNumber,
			@RequestParam( value= "vin", required = false) String vin) {
		
		siteViewDetailCondition(frmTestDrive, dealerCode, plateNumber, vin, null, null);
		if (frmTestDrive.getWeek() == null) {
			// 当前年的当前周
			frmTestDrive.setWeek(DateTime.getCurrentWeekOfYear().toString());
		}
		// 按周数的值判断是否需要进行统计
		if (frmTestDrive.getWeek() != null) {
			if (!checkWeekValue(frmTestDrive.getWeek())) {
				return "/dealer/testDrive/listTestDriveOfWeek";
			}
		}

		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, frmTestDrive, companyId, userId, false, false);
		List<TestDriveStatisticFindVO> testDriveVOList = (List<TestDriveStatisticFindVO>) rd.getFirstItem();

		model.addAttribute("testDriveVOList", testDriveVOList);
		model.addAttribute("transMsg", transMsg);

		initSelectList(model,companyId, userId);
		siteMenuType(model,rd);
		return "/dealer/testDrive/listTestDriveOfWeek";
	}
	
	/**
	 * 月度统计-查询
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param frmTestDrive
	 * @param plateNumber
	 * @param vin
	 * @return
	 */
	@RequestMapping(value = "/listMonth.vti", method = RequestMethod.GET)
	public String listTestDriveOfMonth(Model model, TransMsg transMsg, @ModelAttribute("testDrive") TestDrive4StatVO frmTestDrive,
			@RequestParam( value= "dealerCode", required = false) String dealerCode,
			@RequestParam( value= "plateNumber", required = false)String plateNumber,
			@RequestParam( value= "vin", required = false) String vin) {
		siteViewDetailCondition(frmTestDrive, dealerCode, plateNumber, vin, null, null);
		if (frmTestDrive.getMonth() == null) {
			// 单击左面菜单链接查询当前月份的
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String monthValue = String.valueOf(month);
			if (monthValue.length() == 1) {
				monthValue = "0" + monthValue;
			}
			frmTestDrive.setYear(year);
			frmTestDrive.setMonth(monthValue);
		}
		model.addAttribute("testDriveMonthList",SelectOptionResource.constantMonthOptions());
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, frmTestDrive, companyId, userId, false, false);
		List<TestDriveStatisticFindVO> testDriveVOList = (List<TestDriveStatisticFindVO>) rd.getFirstItem();
		
		model.addAttribute("testDriveVOList", testDriveVOList);
		model.addAttribute("transMsg", transMsg);

		initSelectList(model,companyId, userId);
		siteMenuType(model,rd);
		return "/dealer/testDrive/listTestDriveOfMonth";
	}
	
	/**
	 * 季度统计
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param frmTestDrive
	 * @param plateNumber
	 * @param vin
	 * @return
	 */
	@RequestMapping(value = "/listSeason.vti", method = RequestMethod.GET)
	public String listTestDriveOfSeason(Model model, TransMsg transMsg, @ModelAttribute("testDrive") TestDrive4StatVO frmTestDrive,
			@RequestParam( value= "dealerCode", required = false) String dealerCode,
			@RequestParam( value= "plateNumber", required = false)String plateNumber,
			@RequestParam( value= "vin", required = false) String vin) {
		siteViewDetailCondition(frmTestDrive, dealerCode, plateNumber, vin, null, null);
		// 季度的校验
		if (frmTestDrive.getSeason() == null) {
			// 单击左面菜单链接返回空值 不做查询
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			frmTestDrive.setYear(year);
			if (month < 4) {
				frmTestDrive.setSeason(1);
			} else if (month < 7) {
				frmTestDrive.setSeason(2);
			} else if (month < 10) {
				frmTestDrive.setSeason(3);
			} else {
				frmTestDrive.setSeason(4);
			}
		}
		model.addAttribute("testDriveSeasonList",SelectOptionResource.constantSeasonOptions());

		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, frmTestDrive, companyId, userId, false, false);
		List<TestDriveStatisticFindVO> testDriveVOList = (List<TestDriveStatisticFindVO>) rd.getFirstItem();

		model.addAttribute("testDriveVOList", testDriveVOList);
		model.addAttribute("transMsg", transMsg);

		initSelectList(model,companyId, userId);
		siteMenuType(model,rd);
		return "/dealer/testDrive/listTestDriveOfSeason";
	}
	
	/**
	 * 时间段统计
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param frmTestDrive
	 * @param plateNumber
	 * @param vin
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/listInterval.vti", method = RequestMethod.GET)
	public String listTestDriveOfInterval(Model model, TransMsg transMsg, @ModelAttribute("testDrive") TestDrive4StatVO frmTestDrive,
			@RequestParam( value= "dealerCode", required = false) String dealerCode,
			@RequestParam( value= "plateNumber", required = false) String plateNumber,
			@RequestParam( value= "vin", required = false) String vin,
			@RequestParam( value= "startTime", required = false) String startTime,
			@RequestParam( value= "endTime", required = false) String endTime) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		siteViewDetailCondition(frmTestDrive, dealerCode, plateNumber, vin, startTime, endTime);
		initSelectList(model,companyId, userId);

		// 开始时间大于结束时间
		 if (frmTestDrive.getStartTime() != null & frmTestDrive.getEndTime() != null) {
			// 时间差小于90天
			String timeStart = DateTime.toNormalDateTime(frmTestDrive.getStartTime());
			String timeEnd = DateTime.toNormalDateTime(frmTestDrive.getEndTime());
			int days = DateTime.getDaysInterval(timeStart, timeEnd);
			if (days > 90) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "时间间隔不能大于90天!");
				return "/dealer/testDrive/listTestDriveOfInterval";
			}
			// 得到两年前的一天
			Date twoYearsAgo = DateTime.minusDays(new Date(), 730);
			if (DateTime.getSecondDifference(twoYearsAgo,
					frmTestDrive.getStartTime()) < 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "只能查询两年以内的数据!");
				return "/dealer/testDrive/listTestDriveOfInterval";
			}
			if (DateTime.getSecondDifference(frmTestDrive.getEndTime(),
					frmTestDrive.getStartTime()) > 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT,
						"开始时间大于结束时间,请修改查询时间!");
				return "/dealer/testDrive/listTestDriveOfInterval";
			}
		} else {
			return "/dealer/testDrive/listTestDriveOfInterval";
		}
			
		
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, frmTestDrive, companyId, userId, false, false);
		List<TestDriveStatisticFindVO> testDriveVOList = (List<TestDriveStatisticFindVO>) rd.getFirstItem();
		model.addAttribute("testDriveVOList", testDriveVOList);
		model.addAttribute("transMsg", transMsg);

		model.addAttribute("startTime",DateTime.toNormalDateTime(frmTestDrive.getStartTime()));
		model.addAttribute("endTime", DateTime.toNormalDateTime(frmTestDrive.getEndTime()));

		siteMenuType(model,rd);
		return "/dealer/testDrive/listTestDriveOfInterval";
	}
	
	/**
	 * 设置查看完明细后返回后的条件
	 * @author liuq 
	 * @version 0.1 
	 * @param frmTestDrive
	 * @param dealerCode
	 * @param plateNumber
	 * @param vin
	 * @param startTime
	 * @param endTime
	 */
	private void siteViewDetailCondition(TestDrive4StatVO frmTestDrive, String dealerCode, String plateNumber, String vin, 
		String startTime, String endTime) {
		if (frmTestDrive.getDealer().getDealerCode() == null) {
			if (StringUtils.isNotBlank(dealerCode)){
				frmTestDrive.getDealer().setDealerCode(dealerCode.trim());
			}
		}
		if (frmTestDrive.getVehicle().getPlateNumber() == null) {
			if (StringUtils.isNotBlank(plateNumber)){
				frmTestDrive.getVehicle().setPlateNumber(plateNumber.trim());
			}
		}
		if(frmTestDrive.getVehicle().getVin() == null) {
			if(StringUtils.isNotBlank(vin)){
				frmTestDrive.getVehicle().setVin(vin.trim());
			}
		}
		if (frmTestDrive.getStartTime()==null) {
			if(startTime!=null&& !startTime.equals("")){
				frmTestDrive.setStartTime(DateTime.toDate(startTime));
			}
		}
		if (frmTestDrive.getEndTime() == null) {
			if (endTime != null && !endTime.equals("")) {
				frmTestDrive.setStartTime(DateTime.toDate(endTime));
			}	
		}
	}
	
	/**
	 * 单周、月度、季度、时间段统计查看明细
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param frmTestDrive
	 * @param vinValue
	 * @param plateNumberValue
	 * @param dealerCode
	 * @param year
	 * @param month
	 * @param week
	 * @param season
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/listTestDriveDetail.vti", method = RequestMethod.GET)
	public String listTestDriveStatisticDetail(Model model, TransMsg transMsg,
			@ModelAttribute("testDrive") TestDrive4StatVO frmTestDrive,
			@RequestParam("vinValue") String vinValue,
			@RequestParam("plateNumberValue") String plateNumberValue,
			@RequestParam("dealerCode") String dealerCode,
			@RequestParam(value= "year", required = false) Integer year,
			@RequestParam(value= "month", required = false)  String month,
			@RequestParam(value= "week", required = false) String week,
			@RequestParam(value= "season", required = false)  Integer season,
			@RequestParam(value= "startTime", required = false) String startTime,
			@RequestParam(value= "endTime", required = false)  String endTime) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, frmTestDrive, companyId, userId, true, false);
		List<TestDriveDetailFindVO> testDriveDetailVOList = (List<TestDriveDetailFindVO>) rd.getFirstItem();
		model.addAttribute("testDriveDetailVOList", testDriveDetailVOList);
		model.addAttribute("transMsg", transMsg);

		List<CarStyle> carStyleList = CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		model.addAttribute("carStyleList", carStyleList);
		model.addAttribute("startTimeNormal", startTime);
		model.addAttribute("endTimeNormal", endTime);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("season", season);
		model.addAttribute("vinValue", vinValue);
		model.addAttribute("plateNumberValue", plateNumberValue); 
		model.addAttribute("dealerCode", dealerCode); 
		model.addAttribute("higherPage", getIntegerParameter("higherPage")); 
		
		String timeType = "", timeString="";
		if (frmTestDrive.getWeek() != null && !frmTestDrive.getWeek().equals("")) {
			timeType ="Week";
			timeString= "单周统计";
		} else if (frmTestDrive.getMonth()!=null&&!frmTestDrive.getMonth().equals("")) {
			timeType ="Month";
			timeString= "月度统计";
		} else if(frmTestDrive.getSeason()!=null&&!frmTestDrive.getSeason().equals("")) {
			timeType ="Season";
			timeString= "季度统计";
		} else {
			timeType ="Interval";
			timeString= "时间段统计";
		}
		model.addAttribute("timeType", timeType);
		model.addAttribute("timeString", timeString);
		if (testDriveDetailVOList != null && testDriveDetailVOList.size() > 0) {
			model.addAttribute("vehicleId",testDriveDetailVOList.get(0).getVehicleId());
			model.addAttribute("lineId",testDriveDetailVOList.get(0).getLineId());
		}
		return "/dealer/testDrive/listTestDriveDetail";
	}
	
	/**
	 * 单周统计导出
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param startIndex
	 */
	@RequestMapping(value = "/outputWeek.vti", method = RequestMethod.GET)
	public void outputByWeek(TransMsg transMsg, @ModelAttribute("testDrive") TestDrive4StatVO testDrive, @RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, testDrive, companyId, userId, false, true);
		List<TestDriveStatisticFindVO> TestDriveStatisticFindVOs = (List<TestDriveStatisticFindVO>) rd.getFirstItem();
		
		String fileName = "试驾周统计" + DateTime.getYear() + "第" + testDrive.getWeek() + "周" + DateTime.toShortDateTime(new Date());
		write2excelIntevel(TestDriveStatisticFindVOs, fileName);
	}
	
	/**
	 * 月度统计导出
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param startIndex
	 */
	@RequestMapping(value = "/outputMonth.vti", method = RequestMethod.GET)
	public void outputByMonth(TransMsg transMsg, @ModelAttribute("testDrive") TestDrive4StatVO testDrive, @RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, testDrive, companyId, userId, false, true);
		List<TestDriveStatisticFindVO> TestDriveStatisticFindVOs = (List<TestDriveStatisticFindVO>) rd.getFirstItem();

		String fileName = "试驾月统计" + testDrive.getYear() + "第" + testDrive.getMonth() + "月" + DateTime.toShortDateTime(new Date());
		write2excelIntevel(TestDriveStatisticFindVOs, fileName);
	}

	/**
	 * 季度统计导出
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param startIndex
	 */
	@RequestMapping(value = "/outputSeason.vti", method = RequestMethod.GET)
	public void outputBySeason(TransMsg transMsg, @ModelAttribute("testDrive") TestDrive4StatVO testDrive, @RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, testDrive, companyId, userId, false, true);

		List<TestDriveStatisticFindVO> TestDriveStatisticFindVOs = (List<TestDriveStatisticFindVO>) rd.getFirstItem();

		String fileName = "试驾季度统计" + testDrive.getYear() + "第" + testDrive.getSeason() + "季度" + DateTime.toShortDateTime(new Date());
		write2excelIntevel(TestDriveStatisticFindVOs, fileName);
	}

	/**
	 * 时间段统计导出
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param startTime
	 * @param endTime
	 * @param startIndex
	 */
	@RequestMapping(value = "/outputIntevel.vti", method = RequestMethod.GET)
	public void outputByIntevel(TransMsg transMsg,
			@ModelAttribute("testDrive") TestDrive4StatVO testDrive,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("startIndex") Integer startIndex) {

		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		testDrive.setStartTime(DateTime.toDate(startTime));
		testDrive.setEndTime(DateTime.toDate(endTime));
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, testDrive, companyId, userId, false, true);
		List<TestDriveStatisticFindVO> TestDriveStatisticFindVOs = (List<TestDriveStatisticFindVO>) rd.getFirstItem();

		String fileName = "";
		if(startTime == "") {
			fileName ="试驾时间段统计"+ DateTime.toShortDateTime(new Date());
		}else{
			fileName ="试驾时间段统计[" + startTime + "-"+endTime +"]"+ DateTime.toShortDateTime(new Date());
		}
		write2excelIntevel(TestDriveStatisticFindVOs, fileName);
	}
	
	/**
	 * 导出数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param testDriveStatisticFindVOs
	 * @param fileName
	 */
	public void write2excelIntevel(List<TestDriveStatisticFindVO> testDriveStatisticFindVOs, String  fileName) {
		String[] titles = { "分销中心","省份", "城市", "网络代码","经销商名称", "经销商状态", "网络形态", 
							"车型","VIN", "车牌", "总次数","总里程[km]" };
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("试驾明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];

		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}

		if (testDriveStatisticFindVOs != null && testDriveStatisticFindVOs.size() > 0) {
			for (int i = 0; i < testDriveStatisticFindVOs.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				TestDriveStatisticFindVO vo = (TestDriveStatisticFindVO) testDriveStatisticFindVOs.get(i);
				HSSFCell cell = row.createCell(0);
				int  y = 0;
				cell.setCellValue(vo.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getLoopTotal());
				cell = row.createCell(++ y);
				String mileage = "0";
				if (vo.getMileageTotal() != null && vo.getMileageTotal() > 0) {
					mileage = new DecimalFormat("#.#").format((float)vo.getMileageTotal() / (float)1000);
				}
				cell.setCellValue(mileage);
				cell = row.createCell(++ y);
			}
		} 
		
		String userAgent = getRequest().getHeader("User-Agent");
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), userAgent);
	}
	
	/**
	 * 单周、月度、季度、时间段统计明细导出
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param testDrive
	 * @param startTime
	 * @param endTime
	 * @param startIndex
	 */
	@RequestMapping(value = "/outputIntevelDetail.vti", method = RequestMethod.GET)
	public void outputByIntevelDetail(TransMsg transMsg,
			@ModelAttribute("testDrive") TestDrive4StatVO testDrive,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("startIndex") Integer startIndex) {
		if (startTime == null) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "请选择时间条件 ,查询后再导出");
			return;
		}

		testDrive.setStartTime(DateTime.toDate(startTime));
		testDrive.setEndTime(DateTime.toDate(endTime));
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = testDriveService.listTestDriveStatistic(transMsg, testDrive, companyId, userId, true, true);
		List<TestDriveDetailFindVO> testDriveDetailVOList = (List<TestDriveDetailFindVO>) rd.getFirstItem();

		write2excelIntevelDetail(testDriveDetailVOList);
	}
	
	/**
	 * 导出统计明细数据到EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param testDrives
	 */
	public void write2excelIntevelDetail(List<TestDriveDetailFindVO> testDrives) {
		String fileName = "试驾明细" + DateTime.toShortDateTime(new Date());
		String[] titles = { "试驾日期","开始时间", "结束时间", "用时", "线路", "里程(km)"};
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("试驾明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
		cells[0] = firstRow.createCell(i);
		cells[0].setCellValue(titles[i]);
		}
		
		if (testDrives != null && testDrives.size() > 0) {
		for (int i = 0; i < testDrives.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			TestDriveDetailFindVO vo = (TestDriveDetailFindVO) testDrives.get(i);
			HSSFCell cell = row.createCell(0);
			int  y = 0;
			cell.setCellValue(DateTime.toNormalDate(vo.getTestDriveDate()));
			cell = row.createCell(++ y);
			cell.setCellValue(DateTime.toNormalDateTime(vo.getStartTime()));
			cell = row.createCell(++ y);
			cell.setCellValue(DateTime.toNormalDateTime(vo.getEndTime()));
			cell = row.createCell(++ y);
			cell.setCellValue(vo.getUseTimeStr());
			cell = row.createCell(++ y);
			cell.setCellValue(vo.getLineNick());
			cell = row.createCell(++ y);
			String mileage = "0";
			if (vo.getMileage() != null && vo.getMileage() > 0) {
				mileage = new DecimalFormat("#.#").format((float)vo.getMileage() / (float)1000);
			}
			cell.setCellValue(mileage);
			cell = row.createCell(++ y);
		}
		} 
		
		String userAgent = getRequest().getHeader("User-Agent");
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), userAgent);
	}
	
	/**
	 * 加载下拉列表
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @param userId
	 */
	private void initSelectList(Model model, Integer companyId, Integer userId) {
		List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
		model.addAttribute("orgList", orgList);
	}
	
	/**
	 * 校验week的值
	 * @author liuq 
	 * @version 0.1 
	 * @param week
	 * @return
	 */
	private boolean checkWeekValue(String week) {
		if (ValidatorUtil.isNumeric(TypeConverter.toString(week))) {
			if (TypeConverter.toInteger(week) > 52 || TypeConverter.toInteger(week) <= 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "请输入 0-52之间数字！");
				return false;
			} else {
				return true;
			}
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "周数条件 请输入数字！");
			return false;
		}
	}
	
}
