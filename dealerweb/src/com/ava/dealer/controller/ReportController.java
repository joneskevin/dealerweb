package com.ava.dealer.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.multipart.MultipartFile;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dealer.service.IReportService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.vo.CompanyCarStyleVO;
import com.ava.domain.vo.CompanyFilingProposalVO;
import com.ava.domain.vo.CompanyProposalVO;
import com.ava.domain.vo.ReportBaseVO;
import com.ava.domain.vo.TestDriveInfoVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping("/dealer/report")
public class ReportController extends Base4MvcController {
	@Autowired
	IReportService reportService;
	
	@Autowired
	private IUserMenuService userMenuService;
	
	/*
	 * excel导入界面显示 测试用 start
	 */
	
	@RequestMapping(value = "/excelExport.vti", method = RequestMethod.GET)
	public String exportExcelDisplay() {
	
		return "/dealer/report/excelExportNew";
	}
	
	/*
	 * excel导入
	 */
	
	@RequestMapping(value = "/excelExportInput.vti", method = RequestMethod.POST)
	public String exportExcelList(
			//@RequestParam("userFile") MultipartFile file
			@RequestParam(value = "userFile", required = false) MultipartFile file
			) throws FileNotFoundException, IOException, ParseException {
		
		if(file!=null)
		{
			reportService.excelExport(file);
		}
		
		return "/dealer/report/excelExportNew";
	}

	/*
	 * excel导入界面显示 测试用 end 
	 */
	

	/*z
	 * 大区报表 部分代码 start 
	 */
	@RequestMapping(value = "/listBigAreaInfo.vti", method = RequestMethod.GET)
	public String listBigAreaInfoList(
			TransMsg transMsg,
			@ModelAttribute("companyCarStyle") CompanyCarStyleVO companyCarStyleVO) {
		if(companyCarStyleVO.getStartTime()==null&&companyCarStyleVO.getEndTime()==null){
			
		}
		else if(companyCarStyleVO.getStartTime()!=null&companyCarStyleVO.getEndTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(companyCarStyleVO.getEndTime(),companyCarStyleVO.getStartTime())>0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始时间大于结束时间,请修改查询时间!");
				 return "/dealer/report/listAllContent";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
			 return "/dealer/report/listAllContent";
		}
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		CarStyle heji = new CarStyle();
		heji.setName("合计");
		carStyleList.add(heji);
		getRequest().setAttribute("carStyleList",carStyleList);
		
		ResponseData rd = new ResponseData(0);
//		rd = reportService.listBigAreaInfo(transMsg, companyCarStyleVO, companyId, true);
		// 全部数据
		List<CompanyCarStyleVO>  result  = (List<CompanyCarStyleVO> )rd.get("result");
		getRequest().setAttribute("companyCarStyleVOList", result);
//		getRequest().setAttribute("transMsg", transMsg);

		//尺寸
	    int sizeOfCarStyleList = carStyleList.size();
	    int width = sizeOfCarStyleList*150+300;
	    getRequest().setAttribute("width", width); 
	    
	    rd = userMenuService.siteMenuType(getRequest());
	    getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		return "/dealer/report/listAllContent";
	}
	
	

	/*
	 * 大区报表 部分代码  end 
	 */
	
	
	

	/*
	 * 试驾率报表 部分代码 start 
	 */
	@RequestMapping(value = "/listTestDrivePercent.vti", method = RequestMethod.GET)
	public String listTestDrivePercentList(
			TransMsg transMsg,
			@ModelAttribute("companyCarStyle") CompanyCarStyleVO companyCarStyleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		ResponseData rd = new ResponseData(0);
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		//尺寸
	    int sizeOfCarStyleList = carStyleList.size();
	    int width = sizeOfCarStyleList*240+940+20;
	    getRequest().setAttribute("width", width); 
	    
//		if(companyCarStyleVO.getStartTime()==null)
//		{
//			Date date[]=DateTime.getSomeDateZone(10);
//			companyCarStyleVO.setStartTime(date[0]);
//			companyCarStyleVO.setEndTime(date[1]);
//		}  
//		//开始时间大于结束时间
		
		if(companyCarStyleVO.getStartTime()==null&&companyCarStyleVO.getEndTime()==null){
			
		}
		else if(companyCarStyleVO.getStartTime()!=null&companyCarStyleVO.getEndTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(companyCarStyleVO.getEndTime(),companyCarStyleVO.getStartTime())>0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始时间大于结束时间,请修改查询时间!");
				 return "/dealer/report/listTestDrivePercent";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
			 return "/dealer/report/listTestDrivePercent";
		}
		
		rd = reportService.listTestDrivePercent(transMsg, companyCarStyleVO,companyId,false);
		List<CompanyCarStyleVO>  result  = (List<CompanyCarStyleVO> )rd.get("result");
		// 全部数据
		getRequest().setAttribute("companyCarStyleVOList", result);
		getRequest().setAttribute("transMsg", transMsg);
	    
		return "/dealer/report/listTestDrivePercent";
	}
	
	@RequestMapping(value="/outputTestDrivePercentExcel.vti", method=RequestMethod.GET)
	public void outPutTestDrivePercentList(TransMsg transMsg ,@ModelAttribute("companyCarStyleVO")  CompanyCarStyleVO companyCarStyleVO,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);

		ResponseData rd = new ResponseData(0);
		rd = reportService.listTestDrivePercent(transMsg, companyCarStyleVO,companyId,true);
		// 全部数据
		List<CompanyCarStyleVO> allDataForExcel  = (List<CompanyCarStyleVO> )rd.get("listAll");
		
		write2excelForTestDrivePercentInfo(allDataForExcel,carStyleList,companyCarStyleVO.getStartTime(), companyCarStyleVO.getEndTime());//查询日期
		transMsg.setStartIndex(startIndex);
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void write2excelForTestDrivePercentInfo(List<CompanyCarStyleVO> companyCarStyleVOList,
			List<CarStyle> carStyleList	,Date  startTime ,Date  endTime) {
		
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市", "经销商名称","","全车型", ""};
				//"车牌","车型","VIN","违规类型","违规时间","违规次数"};
		//总标题
		ArrayList titleList= new ArrayList();
		for(String s: titles)
		{
			titleList.add(s);
		}
		for(CarStyle carStyle: carStyleList){
			titleList.add("");
			titleList.add(carStyle.getName());
			titleList.add("");
		}
		//第二行标题list
		ArrayList secondTitleList= new ArrayList();
		for(int i=0;i<7;i++)
		{
			secondTitleList.add("");
		}
		secondTitleList.add("试驾次数");//试驾次数,有望客户数,试驾率
		secondTitleList.add("有望客户数");
		secondTitleList.add("试驾率");
		for(CarStyle carStyle: carStyleList){
			secondTitleList.add("试驾次数");//试驾次数,有望客户数,试驾率
			secondTitleList.add("有望客户数");
			secondTitleList.add("试驾率");
		}
		String timeEnd ;
		String timeStart ;
		String fileName ;
		if(startTime==null)
		{
			 fileName ="试驾率报表"+DateTime.toShortDateTime(new Date());
		}else{
			 timeStart =DateTime.toNormalDate(startTime);
			 timeEnd = DateTime.toNormalDate(endTime);
			 fileName ="试驾率报表["+timeStart+"-"+timeEnd+"]"+DateTime.toShortDateTime(new Date());
		}
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("试驾率");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFRow secondRow = sheet.createRow(1);
		HSSFCell cells[] = new HSSFCell[titleList.size()];
		//显示标题
		for (int i = 0; i < titleList.size(); i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(TypeConverter.toString(titleList.get(i)));
		}
		//显示第二行
		for (int i = 0; i < titleList.size(); i++) {
			cells[1] = secondRow.createCell(i);
			cells[1].setCellValue(TypeConverter.toString(secondTitleList.get(i)));
		}
		if (companyCarStyleVOList != null && companyCarStyleVOList.size() > 0) {
			for (int i = 0; i < companyCarStyleVOList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 2);
				CompanyCarStyleVO  c = companyCarStyleVOList.get(i);
				HSSFCell cell = row.createCell(0);
				
				cell.setCellValue(c.getIsKeyCity_Nick());
				cell = row.createCell(1);
				cell.setCellValue(c.getDealerType_Nick());
				cell = row.createCell(2);
				cell.setCellValue(c.getDealerCode());
				cell = row.createCell(3);
				cell.setCellValue(c.getFenxiao_center_nick());
				cell = row.createCell(4);
				cell.setCellValue(c.getRegionProvinceId_Nick());
				cell = row.createCell(5);
				cell.setCellValue(c.getRegionCityId_Nick());
				cell = row.createCell(6);
				cell.setCellValue(c.getName());
				cell = row.createCell(7);
				cell.setCellValue(TypeConverter.toString(c.getTestDriveNum()));
				cell = row.createCell(8);
				cell.setCellValue(TypeConverter.toString(c.getHopefulCustomer()));
				cell = row.createCell(9);
				cell.setCellValue(TypeConverter.toString(c.getDrivePercent()));
				cell = row.createCell(10);
				//导出具体车型 各个的数量
				for(int j=0;j< c.getCarStyleList().size();j++)
				{
					cell.setCellValue(TypeConverter.toString(c.getCarStyleList().get(j)));
					cell = row.createCell(11+j);
				}
			}
			
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}

	/*
	 * 试驾率报表 部分代码 end  
	 */
	
	
	/*
	 * 申请明细 部分代码 start 
	 */
	
	@RequestMapping(value = "/listProposal.vti", method = RequestMethod.GET)
	public String queryProposalList(
			TransMsg transMsg,           
			@ModelAttribute("companyProposalVO") CompanyProposalVO companyProposalVO) {
		
		if(companyProposalVO.getStartTime()==null&&companyProposalVO.getEndTime()==null){
			
		}
		else if(companyProposalVO.getStartTime()!=null&companyProposalVO.getEndTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(companyProposalVO.getEndTime(),companyProposalVO.getStartTime())>0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始时间大于结束时间,请修改查询时间!");
				 return "/dealer/report/listProposalReport";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
			 return "/dealer/report/listProposalReport";
		}
		
		/*if(companyProposalVO.getStartTime()==null)
		{
			Date date[]=DateTime.getSomeDateZone(10);
			companyProposalVO.setStartTime(date[0]);
			companyProposalVO.setEndTime(date[1]);
			companyProposalVO.setEndTime(new Date());
			companyProposalVO.setStartTime(DateTime.minusDays(new Date(), 10));
		}*/
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		//申请状态
		 List approvalStatusList = SelectOptionResource.approvalStatusOptions();
		 getRequest().setAttribute("approvalStatusList", approvalStatusList);
		
		//申请类型
		 List proposalReportTypeList = SelectOptionResource.proposalReportTypeOptions();
		 getRequest().setAttribute("proposalReportTypeList", proposalReportTypeList);
		 		
		ResponseData rd = new ResponseData(0);
		rd = reportService.listProposalInfo(transMsg, companyProposalVO, companyId);
		List<CompanyProposalVO> result  = (List<CompanyProposalVO> )rd.get("result");
		
		
		// 全部数据
//		List<CompanyCarStyleVO> allDataForExcel  = (List<CompanyCarStyleVO> )rd.get("listAll");
		getRequest().setAttribute("companyProposalVOList", result);
		getRequest().setAttribute("transMsg", transMsg);
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		return "/dealer/report/listProposalReport";
	}
	
	
	@RequestMapping(value="/outputProposalExcel.vti", method=RequestMethod.GET)
	public void outPutListForProposal(TransMsg transMsg ,
			@ModelAttribute("companyProposalVO") CompanyProposalVO companyProposalVO,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);

		//申请状态
		 List approvalStatusList = SelectOptionResource.approvalStatusOptions();
		 getRequest().setAttribute("approvalStatusList", approvalStatusList);
		
		//申请类型
		 List proposalReportTypeList = SelectOptionResource.proposalReportTypeOptions();
		 getRequest().setAttribute("proposalReportTypeList", proposalReportTypeList);
		
		if(companyProposalVO.getStartTime()!=null&&companyProposalVO.getEndTime()!=null){
//			if(!checkWeekValue(violation.getWeek())){
//				return  "/dealer/violation/listViolationOfWeek";
//			}
		}else{
			//单击左面菜单链接返回空值 不做查询
//			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
//					GlobalConstant.MSG_RESULT_CONTENT, "时间必须填写！");
		}
		
		ResponseData rd = new ResponseData(0);
		Integer  type = companyProposalVO.getType();
		String type_nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.proposalReportTypeArray,type);
		
		rd = reportService.listProposalInfo(transMsg, companyProposalVO,companyId);
		// 全部数据
		List<CompanyProposalVO> allDataForExcel  = (List<CompanyProposalVO> )rd.get("listAll");
		
		if(allDataForExcel!=null)
		{
			write2excelForProposal(allDataForExcel,companyProposalVO.getStartTime(),
					companyProposalVO.getEndTime(),type_nick);//查询日期
		}
		transMsg.setStartIndex(startIndex);
	}
	
	/**
	 * 导出数据到excel
	 * @param 
	 */
	public void write2excelForProposal(List<CompanyProposalVO> companyProposalVOList,
			Date startTime,Date endTime,String type_nick
			) {
		String timeStart = DateTime.toNormalDate(startTime) ;//startTime.substring(0,9);
		String timeEnd =  DateTime.toNormalDate(endTime) ;//endTime.substring(0,9);

		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市", "经销商名称",   "车牌","车型","VIN",
				"申请时间","状态"};
		
		//确定文档标题是什么类型的报备
		String fileName ="";
		fileName =type_nick+"明细["+timeStart+"_"+timeEnd+"]"+DateTime.toShortDateTime(new Date());
		

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("申请明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		for (int i = 0; i < companyProposalVOList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			CompanyProposalVO  c = companyProposalVOList.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(c.getIsKeyCity_Nick());
			cell = row.createCell(1);
			cell.setCellValue(c.getDealerType_Nick());
			cell = row.createCell(2);
			cell.setCellValue(c.getDealerCode());
			cell = row.createCell(3);
			cell.setCellValue(c.getFenxiao_center_nick());
			cell = row.createCell(4);
			cell.setCellValue(c.getRegionProvinceId_Nick());
			cell = row.createCell(5);
			cell.setCellValue(c.getRegionCityId_Nick());
			cell = row.createCell(6);
			cell.setCellValue(c.getName());
			cell = row.createCell(7);
			cell.setCellValue(c.getPlateNumber());
			cell = row.createCell(8);
			cell.setCellValue(c.getCarStyleId_Nick());
			cell = row.createCell(9);
			cell.setCellValue(c.getVin());
			cell = row.createCell(10);
					
			cell.setCellValue(DateTime.toNormalDateTime(c.getProposalTime()));
			cell = row.createCell(11);
			cell.setCellValue(c.getStatus_nick());
			cell = row.createCell(12);
			}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}

	
	/*
	 * 申请明细 部分代码 end 
	 */
	
	
	
	
	/*
	 * 报备明细 部分代码 start 
	 */
	
	@RequestMapping(value = "/listFillingProposal.vti", method = RequestMethod.GET)
	public String queryFillingProposalList(
			TransMsg transMsg,
			@ModelAttribute("companyFilingProposalVO") CompanyFilingProposalVO companyFilingProposalVO) {
		
		
		if(companyFilingProposalVO.getStartTime()==null&&companyFilingProposalVO.getEndTime()==null){
			
		}
		else if(companyFilingProposalVO.getStartTime()!=null&companyFilingProposalVO.getEndTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(companyFilingProposalVO.getEndTime(),companyFilingProposalVO.getStartTime())>0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始时间大于结束时间,请修改查询时间!");
				 return "/dealer/report/listFillingProposal";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
			 return "/dealer/report/listFillingProposal";
		}
//		if(companyFilingProposalVO.getStartTime()==null)
//		{
//			Date date[]=DateTime.getSomeDateZone(10);
//			companyFilingProposalVO.setStartTime(date[0]);
//			companyFilingProposalVO.setEndTime(date[1]);
//		}

		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);

		//报备类型
		 List filingProposalList = SelectOptionResource.filingProposalOptions();
		 getRequest().setAttribute("filingProposalList", filingProposalList);
		 
		
		
		ResponseData rd = new ResponseData(0);
		rd = reportService.listFillingProposalInfo(transMsg, companyFilingProposalVO, companyId);
		List<CompanyFilingProposalVO> result  = (List<CompanyFilingProposalVO> )rd.get("result");
		
		
		// 全部数据
//		List<CompanyCarStyleVO> allDataForExcel  = (List<CompanyCarStyleVO> )rd.get("listAll");
		getRequest().setAttribute("companyFilingProposalVOList", result);
		getRequest().setAttribute("transMsg", transMsg);
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		return "/dealer/report/listFillingProposal";
	}
	
	
	@RequestMapping(value="/outputFillingProposalExcel.vti", method=RequestMethod.GET)
	public void outPutListForFillingProposal(TransMsg transMsg ,
			@ModelAttribute("companyFilingProposalVO") CompanyFilingProposalVO companyFilingProposalVO,
			@RequestParam("startIndex") Integer startIndex) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
//		if (!checkFormForTestDriveVOInfo(testDriveInfoVO)) {
//			return "/dealer/report/listTestDrive";
//		}
		 List filingProposalList = SelectOptionResource.filingProposalOptions();
		 getRequest().setAttribute("filingProposalList", filingProposalList);
		
		if(companyFilingProposalVO.getStartTime()!=null&&companyFilingProposalVO.getEndTime()!=null){
//			if(!checkWeekValue(violation.getWeek())){
//				return  "/dealer/violation/listViolationOfWeek";
//			}
		}else{
			//单击左面菜单链接返回空值 不做查询
//			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
//					GlobalConstant.MSG_RESULT_CONTENT, "时间必须填写！");
		}
		
		ResponseData rd = new ResponseData(0);
		Integer  type = companyFilingProposalVO.getType();
		String type_nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.filingProposalTypeArray,type);
		
		rd = reportService.listFillingProposalInfo(transMsg, companyFilingProposalVO,companyId);
		// 全部数据
		List<CompanyFilingProposalVO> allDataForExcel  = (List<CompanyFilingProposalVO> )rd.get("listAll");
		
		if(allDataForExcel!=null)
		{
			write2excelForFillingProposal(allDataForExcel,companyFilingProposalVO.getStartTime(),
					companyFilingProposalVO.getEndTime(),type_nick);//查询日期
		}
		transMsg.setStartIndex(startIndex);
//		return queryFillingProposalList(transMsg,companyFilingProposalVO);
	}
	
	/**
	 * 导出数据到excel
	 * @param 
	 */
	public void write2excelForFillingProposal(List<CompanyFilingProposalVO> companyFilingProposalVOList,
			Date startTime,Date endTime,String type_nick
			) {
		String timeStart = DateTime.toNormalDate(startTime) ;//startTime.substring(0,9);
		String timeEnd =  DateTime.toNormalDate(endTime) ;//endTime.substring(0,9);

		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市", "经销商名称",   "车牌","车型","VIN",
				"开始时间","结束时间"};
		
		//确定文档标题是什么类型的报备
		String fileName ="";
		fileName =type_nick+"明细["+timeStart+"_"+timeEnd+"]"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("报备明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		for (int i = 0; i < companyFilingProposalVOList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			CompanyFilingProposalVO  c = companyFilingProposalVOList.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(c.getIsKeyCity_Nick());
			cell = row.createCell(1);
			cell.setCellValue(c.getDealerType_Nick());
			cell = row.createCell(2);
			cell.setCellValue(c.getDealerCode());
			cell = row.createCell(3);
			cell.setCellValue(c.getFenxiao_center_nick());
			cell = row.createCell(4);
			cell.setCellValue(c.getRegionProvinceId_Nick());
			cell = row.createCell(5);
			cell.setCellValue(c.getRegionCityId_Nick());
			cell = row.createCell(6);
			cell.setCellValue(c.getName());
			cell = row.createCell(7);
			cell.setCellValue(c.getPlateNumber());
			cell = row.createCell(8);
			cell.setCellValue(c.getCarStyleId_Nick());
			cell = row.createCell(9);
			cell.setCellValue(c.getVin());
			cell = row.createCell(10);
		
			cell.setCellValue(DateTime.toNormalDateTime(c.getStart_Time()));
			cell = row.createCell(11);
			cell.setCellValue(DateTime.toNormalDateTime(c.getEnd_Time()));
			cell = row.createCell(12);

		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}

	
	/*
	 * 报备明细 部分代码 end 
	 */
	

	/*
	 * 试驾明细 部分代码 start 
	 */
	
	@RequestMapping(value = "/listTestDrive.vti", method = RequestMethod.GET)
	public String queryTestDriveInfoList(
			TransMsg transMsg,
			@ModelAttribute("testDriveInfoVO") TestDriveInfoVO testDriveInfoVO) {
		
//		if(testDriveInfoVO.getStartTime()==null)
//		{
//			Date date[]=DateTime.getSomeDateZone(10);
//			testDriveInfoVO.setStartTime(date[0]);
//			testDriveInfoVO.setEndTime(date[1]);
//		}
		
		if(testDriveInfoVO.getStartTime()==null&&testDriveInfoVO.getEndTime()==null){
			
		}
		else if(testDriveInfoVO.getStartTime()!=null&testDriveInfoVO.getEndTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(testDriveInfoVO.getEndTime(),testDriveInfoVO.getStartTime())>0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始时间大于结束时间,请修改查询时间!");
				 return "/dealer/report/listTestDrive";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
			 return "/dealer/report/listTestDrive";
		}
		
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		
		
		ResponseData rd = new ResponseData(0);
		rd = reportService.listTestDriveInfo(transMsg, testDriveInfoVO,companyId,false);
		List<TestDriveInfoVO> result  = (List<TestDriveInfoVO> )rd.get("result");
		
		// 全部数据
//		List<CompanyCarStyleVO> allDataForExcel  = (List<CompanyCarStyleVO> )rd.get("listAll");
		getRequest().setAttribute("testDriveInfoVOList", result);
		getRequest().setAttribute("transMsg", transMsg);
		
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		return "/dealer/report/listTestDrive";
	}
	
	
	@RequestMapping(value="/outputTestDriveInfoExcel.vti", method=RequestMethod.GET)
	public void outPutListForTestDrive(TransMsg transMsg ,@ModelAttribute("testDriveInfoVO") TestDriveInfoVO testDriveInfoVO,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);

		
		if(testDriveInfoVO.getStartTime()!=null&&testDriveInfoVO.getEndTime()!=null){
//			if(!checkWeekValue(violation.getWeek())){
//				return  "/dealer/violation/listViolationOfWeek";
//			}
		}else{
			//单击左面菜单链接返回空值 不做查询
//			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
//					GlobalConstant.MSG_RESULT_CONTENT, "时间必须填写！");
//			return "/dealer/report/listTestDrive";
		}
		
		ResponseData rd = new ResponseData(0);
		rd = reportService.listTestDriveInfo(transMsg, testDriveInfoVO,companyId,true);
		// 全部数据
		List<TestDriveInfoVO> allDataForExcel  = (List<TestDriveInfoVO> )rd.get("listAll");
		
		if(allDataForExcel!=null)
		{
			write2excelForTestDrive(allDataForExcel,testDriveInfoVO.getStartTime(),
					testDriveInfoVO.getEndTime());//查询日期
		}
		transMsg.setStartIndex(startIndex);
//		return queryTestDriveInfoList(transMsg,testDriveInfoVO);
	}
	
	/**
	 * 导出数据到excel
	 * @param 
	 */
	public void write2excelForTestDrive(List<TestDriveInfoVO> testDriveInfoVOList,Date startTime,Date endTime
			) {
		String timeStart = DateTime.toNormalDate(startTime) ;//startTime.substring(0,9);
		String timeEnd =  DateTime.toNormalDate(endTime) ;//endTime.substring(0,9);

		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市", "经销商名称",   "车牌","车型","VIN",
				"线路", "次数",    "里程/km","开始时间","结束时间","用时(分)"};
				//"车牌","车型","VIN","违规类型","违规时间","结束次数"};
		//总标题
	
		String fileName ="";
		fileName ="试驾明细["+timeStart+"_"+timeEnd+"]"+DateTime.toShortDateTime(new Date());
	
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("试驾明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		for (int i = 0; i < testDriveInfoVOList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			TestDriveInfoVO  c = testDriveInfoVOList.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(c.getIsKeyCity_Nick());
			cell = row.createCell(1);
			cell.setCellValue(c.getDealerType_Nick());
			cell = row.createCell(2);
			cell.setCellValue(c.getDealerCode());
			cell = row.createCell(3);
			cell.setCellValue(c.getFenxiao_center_nick());
			cell = row.createCell(4);
			cell.setCellValue(c.getRegionProvinceId_Nick());
			cell = row.createCell(5);
			cell.setCellValue(c.getRegionCityId_Nick());
			cell = row.createCell(6);
			cell.setCellValue(c.getName());
			cell = row.createCell(7);
			cell.setCellValue(c.getPlateNumber());
			cell = row.createCell(8);
			cell.setCellValue(c.getCarStyleId_Nick());
			cell = row.createCell(9);
			cell.setCellValue(c.getVin());
			cell = row.createCell(10);
			
			
			cell.setCellValue(c.getLineId_Nick());
			cell = row.createCell(11);
			cell.setCellValue(c.getLoopCount());
			cell = row.createCell(12);
			
			cell.setCellValue(TypeConverter.toString(c.getMILEAGE()));
			cell = row.createCell(13);
			cell.setCellValue(DateTime.toNormalDateTime(c.getStart_Time()));
			cell = row.createCell(14);
			cell.setCellValue(DateTime.toNormalDateTime(c.getEnd_Time()));
			cell = row.createCell(15);
			cell.setCellValue(TypeConverter.toString(c.getCostTime()));
			cell = row.createCell(16);

		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	protected boolean checkFormForTestDriveVOInfo(TestDriveInfoVO testDriveInfoVO) {

		
		if(testDriveInfoVO ==null)
		{
//			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
//			GlobalConstant.MSG_RESULT_CONTENT, "时间必须填写！");
			return true;
		}else if(testDriveInfoVO.getStart_Time()==null||testDriveInfoVO.getEnd_Time()==null)
		{
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "时间必须填写！");
			return true;
		}
		return false;
	
	}
	
	
	/*
	 * 试驾明细 部分代码 end 
	 */
	
	
	
	/*
	 * 配置报表 部分代码 start 
	 */
	@RequestMapping(value = "/listConfigure.vti", method = RequestMethod.GET)
	public String queryConfigureInfoList(
			TransMsg transMsg,
			@ModelAttribute("companyCarStyleVO") CompanyCarStyleVO companyCarStyleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		List seasonList = SelectOptionResource.constantSeasonOptions();
		getRequest().setAttribute("seasonList", seasonList);
//		if(companyCarStyleVO.getYear()==null)
//		{
//			Calendar cal = Calendar.getInstance();
//			int year = cal.get(Calendar.YEAR);
//			companyCarStyleVO.setYear(year);
//		}else if (!checkFormForCompanyCarStyleVOInfo(companyCarStyleVO)) {
//			return "/dealer/report/listConfigure";
//		}
		if(companyCarStyleVO.getSeason()!=null){
			
		}else{
			//单击左面菜单链接返回空值 不做查询
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			companyCarStyleVO.setYear(year);
			 if(month<4)
			 {
				 companyCarStyleVO.setSeason("1");
			 }else if(month<7){
				 companyCarStyleVO.setSeason("2");
			 }else if(month<10){
				 companyCarStyleVO.setSeason("3");
			 }else {
				 companyCarStyleVO.setSeason("4");
			 }
		}
		ResponseData rd = new ResponseData(0);
//		rd = reportService.listConfigureInfo(transMsg, companyCarStyleVO, companyId,false);
		rd = reportService.listConfigureInfoForSK(transMsg, companyCarStyleVO, companyId,false);
		List<CompanyCarStyleVO> result  = (List<CompanyCarStyleVO> )rd.get("result");
		// 全部数据
		getRequest().setAttribute("companyCarStyleVOList", result);
		getRequest().setAttribute("transMsg", transMsg);
		//尺寸
	    int sizeOfCarStyleList = carStyleList.size();
	    int width = sizeOfCarStyleList*240+730+60;
	    getRequest().setAttribute("width", width);
	    
	    rd = userMenuService.siteMenuType(getRequest());
	    getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		return "/dealer/report/listConfigure";
	}
	

	/**
	 * 配置报表
	 * @param transMsg
	 * @param companyCarStyleVO
	 * @return
	 */
	@RequestMapping(value = "/listConfigureNew.vti", method = RequestMethod.GET)
	public String queryConfigureInfoListNew(TransMsg transMsg,@ModelAttribute("companyCarStyleVO") CompanyCarStyleVO companyCarStyleVO) {
		ResponseData rd = new ResponseData(0);
		
		if (companyCarStyleVO.getWeek()==null || companyCarStyleVO.getWeek().length() == 0) {
			//当前年的当前周
			companyCarStyleVO.setWeek(DateTime.getCurrentWeekOfYear().toString());
		} 
		  
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		
		List orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
		getRequest().setAttribute("orgList", orgList);
		
		rd = reportService.listConfigureInfo(transMsg, companyCarStyleVO, companyId, userId, false);
		getRequest().setAttribute("configureInfoList", rd.get("configureInfoList"));
		
		List dealerTypeList = SelectOptionResource.dealerTypeOptions();
		getRequest().setAttribute("dealerTypeList", dealerTypeList);
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		return "/dealer/report/listConfigureNew";
	}
	
	@RequestMapping(value="/outputConfigureInfoExcelNew.vti", method=RequestMethod.GET)
	public void outputConfigureInfoExcelNew(TransMsg transMsg ,@ModelAttribute("companyCarStyleVO")  CompanyCarStyleVO companyCarStyleVO,
			@RequestParam("startIndex") Integer startIndex) {
		ResponseData rd = new ResponseData(0);
		if (companyCarStyleVO.getWeek()==null || companyCarStyleVO.getWeek().length() == 0) {
			//当前年的当前周
			companyCarStyleVO.setWeek(DateTime.getCurrentWeekOfYear().toString());
		} 
		
		List dealerTypeList = SelectOptionResource.dealerTypeOptions();
		getRequest().setAttribute("dealerTypeList", dealerTypeList);
		  
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		rd = reportService.listConfigureInfo(transMsg, companyCarStyleVO, companyId, userId, true);
		List<ReportBaseVO> configureInfoList = (List<ReportBaseVO>) rd.get("configureInfoList");
		writeExcelConfigureInfo(configureInfoList);
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public void writeExcelConfigureInfo(List<ReportBaseVO> configureInfoList) {
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态","车型","配置数量","一级网点代码","一级网点名称"};
		String fileName ="";
	
		fileName="配置报表信息"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("配置报表列表");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (configureInfoList != null && configureInfoList.size() > 0) {
		for (int i = 0; i < configureInfoList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			ReportBaseVO reportBase = configureInfoList.get(i);
			HSSFCell cell = row.createCell(0);
			int y = 0;
			cell.setCellValue(reportBase.getSaleCenterName());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getProvinceName());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getCityName());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getDealerCode());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getCompanyName());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getIsKeyCityNick());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getDealerTypeNick());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getCarStyleName());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getRealityConfigCount());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getParentCompanyName());
			cell = row.createCell(++ y);
			cell.setCellValue(reportBase.getParentDealerCode());
			cell = row.createCell(++ y);
		}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	@RequestMapping(value="/outputConfigureInfoExcel.vti", method=RequestMethod.GET)
	public void outPutList(TransMsg transMsg ,@ModelAttribute("companyCarStyleVO")  CompanyCarStyleVO companyCarStyleVO,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if(startIndex==null)
		{
			startIndex =0;
		}
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		List seasonList = SelectOptionResource.constantSeasonOptions();
		getRequest().setAttribute("seasonList", seasonList);

		if(companyCarStyleVO.getSeason()!=null){
			
		}else{
			//单击左面菜单链接返回空值 不做查询
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			companyCarStyleVO.setYear(year);
			 if(month<4)
			 {
				 companyCarStyleVO.setSeason("1");
			 }else if(month<7){
				 companyCarStyleVO.setSeason("2");
			 }else if(month<10){
				 companyCarStyleVO.setSeason("3");
			 }else {
				 companyCarStyleVO.setSeason("4");
			 }
		}
		ResponseData rd = new ResponseData(0);
		rd = reportService.listConfigureInfoForSK(transMsg, companyCarStyleVO, companyId, true);
		// 全部数据
		List<CompanyCarStyleVO> allDataForExcel  = (List<CompanyCarStyleVO> )rd.get("listAll");
	
		write2excelForConfigureInfo(allDataForExcel,carStyleList,companyCarStyleVO.getYear());//查询日期
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void write2excelForConfigureInfo(List<CompanyCarStyleVO> companyCarStyleVOList,
			List<CarStyle> carStyleList	,int year) {
		
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市", "经销商名称","配置数量"};
				//"车牌","车型","VIN","违规类型","违规时间","违规次数"};
		//总标题
		ArrayList titleList= new ArrayList();
		for(String s: titles)
		{
			titleList.add(s);
		}
		for(CarStyle carStyle: carStyleList){
			titleList.add(carStyle.getName());
		}
		String fileName ="";
		
		fileName ="配置报表"+year+"年"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("配置明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titleList.size()];
	
		for (int i = 0; i < titleList.size(); i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(TypeConverter.toString(titleList.get(i)));
		}
		
		if (companyCarStyleVOList != null && companyCarStyleVOList.size() > 0) {
			for (int i = 0; i < companyCarStyleVOList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				CompanyCarStyleVO  c = companyCarStyleVOList.get(i);
				HSSFCell cell = row.createCell(0);
				
				cell.setCellValue(c.getIsKeyCity_Nick());
				cell = row.createCell(1);
				cell.setCellValue(c.getDealerType_Nick());
				cell = row.createCell(2);
				cell.setCellValue(c.getDealerCode());
				cell = row.createCell(3);
				cell.setCellValue(c.getFenxiao_center_nick());
				cell = row.createCell(4);
				cell.setCellValue(c.getRegionProvinceId_Nick());
				cell = row.createCell(5);
				cell.setCellValue(c.getRegionCityId_Nick());
				cell = row.createCell(6);
				cell.setCellValue(c.getName());
				cell = row.createCell(7);
				cell.setCellValue(c.getConfigureNum());
				cell = row.createCell(8);
				//导出具体车型 各个的数量
				for(int j=0;j< c.getCarStyleList().size();j++)
				{
					cell.setCellValue((Integer)c.getCarStyleList().get(j));
					cell = row.createCell(9+j);
				}
				
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}

	protected boolean checkFormForCompanyCarStyleVOInfo(CompanyCarStyleVO companyCarStyleVO) {

//		Integer dealerCode = companyCarStyleVO.getYear();
		String year =TypeConverter.toString(companyCarStyleVO.getYear());
		if (!TypeConverter.sizeLagerZero(companyCarStyleVO.getYear())) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "年份必须填写！");
			return false;

		} else if (!ValidatorUtil.isNumeric(year)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "年份填写数字！");
			return false;
		} 
		
		return true;
	
	}
	
	/*
	 * 配置报表 部分代码 end  
	 */
	
	

	/*
	 * 违规报表 部分代码 start 
	 */
	@RequestMapping(value = "/listViolation.vti", method = RequestMethod.GET)
	public String queryViolationInfoList(
			TransMsg transMsg,
			@ModelAttribute("companyCarStyleVO") CompanyCarStyleVO companyCarStyleVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		
		//尺寸
	    int sizeOfCarStyleList = carStyleList.size();
	    int width = sizeOfCarStyleList*240+730+60;
	    getRequest().setAttribute("width", width);
		
	    ResponseData rd = new ResponseData(0);
	    rd = userMenuService.siteMenuType(getRequest());
	    getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		/*if(companyCarStyleVO.getStartTime()==null)
		{
			Date date[]=DateTime.getSomeDateZone(10);
			companyCarStyleVO.setStartTime(date[0]);
			companyCarStyleVO.setEndTime(date[1]);
			
		}*/
		if(companyCarStyleVO.getStartTime()==null&&companyCarStyleVO.getEndTime()==null){
			
		}
		else if(companyCarStyleVO.getStartTime()!=null&companyCarStyleVO.getEndTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(companyCarStyleVO.getEndTime(),companyCarStyleVO.getStartTime())>0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始时间大于结束时间,请修改查询时间!");
					return "/dealer/report/listViolation";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
				return "/dealer/report/listViolation";
		}
	
		
		rd = reportService.listViolationInfo(transMsg, companyCarStyleVO,companyId,false);
		List<CompanyCarStyleVO> result  = (List<CompanyCarStyleVO> )rd.get("result");
		// 全部数据
//		List<CompanyCarStyleVO> allDataForExcel  = (List<CompanyCarStyleVO> )rd.get("listAll");
		getRequest().setAttribute("companyCarStyleVOList", result);
		getRequest().setAttribute("transMsg", transMsg);
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		return "/dealer/report/listViolation";
	}
	
	@RequestMapping(value="/outputViolationInfoExcel.vti", method=RequestMethod.GET)
	public void outPutViolationList(TransMsg transMsg ,@ModelAttribute("companyCarStyleVO")  CompanyCarStyleVO companyCarStyleVO,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);

		ResponseData rd = new ResponseData(0);
		rd = reportService.listViolationInfo(transMsg, companyCarStyleVO,companyId,true);
		// 全部数据
		List<CompanyCarStyleVO> allDataForExcel  = (List<CompanyCarStyleVO> )rd.get("listAll");
		
	
		write2excelForViolationInfo(allDataForExcel,carStyleList,companyCarStyleVO.getStartTime(),
					companyCarStyleVO.getEndTime());//查询日期
		transMsg.setStartIndex(startIndex);
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void write2excelForViolationInfo(List<CompanyCarStyleVO> companyCarStyleVOList,
			List<CarStyle> carStyleList	,Date startTime,Date endTime) {
	
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市","经销商名称", "违规数量"};
				//"车牌","车型","VIN","违规类型","违规时间","违规次数"};
		//总标题
		ArrayList titleList= new ArrayList();
		for(String s: titles)
		{
			titleList.add(s);
		}
		for(CarStyle carStyle: carStyleList){
			titleList.add(carStyle.getName());
		}
		String fileName ="";
		
		String timeEnd ;
		String timeStart ;
		if(startTime==null)
		{
			 fileName ="违规报表"+DateTime.toShortDateTime(new Date());
		}else{
			 timeStart =DateTime.toNormalDate(startTime);
			 timeEnd = DateTime.toNormalDate(endTime);
			 fileName ="违规报表["+timeStart+"-"+timeEnd+"]"+DateTime.toShortDateTime(new Date());
		}
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titleList.size()];
	
		for (int i = 0; i < titleList.size(); i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(TypeConverter.toString(titleList.get(i)));
		}
		if (companyCarStyleVOList != null && companyCarStyleVOList.size() > 0) {
			for (int i = 0; i < companyCarStyleVOList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				CompanyCarStyleVO  c = companyCarStyleVOList.get(i);
				HSSFCell cell = row.createCell(0);
				
				cell.setCellValue(c.getIsKeyCity_Nick());
				cell = row.createCell(1);
				cell.setCellValue(c.getDealerType_Nick());
				cell = row.createCell(2);
				cell.setCellValue(c.getDealerCode());
				cell = row.createCell(3);
				cell.setCellValue(c.getFenxiao_center_nick());
				cell = row.createCell(4);
				cell.setCellValue(c.getRegionProvinceId_Nick());
				cell = row.createCell(5);
				cell.setCellValue(c.getRegionCityId_Nick());
				cell = row.createCell(6);
				cell.setCellValue(c.getName());
				cell = row.createCell(7);
				cell.setCellValue(TypeConverter.toString(c.getViolationNum()));
				cell = row.createCell(8);
				//导出具体车型 各个的数量
				for(int j=0;j< c.getCarStyleList().size();j++)
				{
					cell.setCellValue(TypeConverter.toString(c.getCarStyleList().get(j)));
					cell = row.createCell(9+j);
				}
		}
			
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}

	/*
	 * 违规报表 部分代码 end  
	 */
	
}
