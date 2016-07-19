package com.ava.dealer.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dealer.service.IViolationService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.Violation;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.TestNoDriveWeekFindVO;
import com.ava.domain.vo.TestNoDriveWeekVO;
import com.ava.domain.vo.VehicleFindVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.domain.vo.ViolationLineTimeDetailVO;
import com.ava.domain.vo.ViolationLineTimeVO;
import com.ava.domain.vo.ViolationVO;
import com.ava.exception.ProtocolParseBusinessException;
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
@RequestMapping("/dealer/violation")
public class ViolationController extends Base4MvcController {
	@Autowired
	private IViolationService violationService;
	
	@Autowired
	private IUserMenuService userMenuService;
	
	@RequestMapping(value = "/list.vti", method = RequestMethod.GET)
	public String listViolation(TransMsg transMsg,
			@ModelAttribute("violation")  Violation violation) {
		
		if(violation.getStartTime()==null&&violation.getEndTime()==null){
			
		}
		else if(violation.getStartTime()!=null&violation.getEndTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(violation.getEndTime(),violation.getStartTime())>0)
			{
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
				 GlobalConstant.MSG_RESULT_CONTENT, 
				 "开始时间大于结束时间,请修改查询时间!");
			return "/dealer/violation/listViolation";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
			 return "/dealer/violation/listViolation";
		}
		

		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		
		ResponseData rd = violationService.listViolation( transMsg ,companyId, violation,false);
		
		getRequest().setAttribute("violationList",rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		return "/dealer/violation/listViolation";
	}
	@RequestMapping(value = "/listWeek.vti", method = RequestMethod.GET)
	public String listViolationOfWeek(TransMsg transMsg,
			@ModelAttribute("violation")  Violation violation) {
		if(violation.getWeek()==null)
		{
			//当前年的当前周
			violation.setWeek(DateTime.getCurrentWeekOfYear().toString());
		}
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		//周数的校验
		if(violation.getWeek()!=null){
			if(!checkWeekValue(violation.getWeek())){
				return  "/dealer/violation/listViolationOfWeek";
			}
		}else{
			//单击左面菜单链接返回空值 不做查询
//			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
//					 GlobalConstant.MSG_RESULT_CONTENT, 
//					 "查询前 请输入周数!");
			return  "/dealer/violation/listViolationOfWeek";
		}
	
		ResponseData rd  = violationService.listViolationForWeek(transMsg,companyId, violation,false);
		List<ViolationVO> result =  (List<ViolationVO>) rd.get("result");
		
		getRequest().setAttribute("violationList",result);
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		return "/dealer/violation/listViolationOfWeek";
	}
	

	
	@RequestMapping(value = "/listIntevel.vti", method = RequestMethod.GET)
	public String listViolationOfIntevel(TransMsg transMsg,
			@ModelAttribute("violation")  Violation violation) {
		
//		if(violation.getStartTime()==null)
//		{
//			Date date[]=DateTime.getSomeDateZone(10);
//			violation.setStartTime(date[0]);
//			violation.setEndTime(date[1]);
//		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		if(violation.getStartTime()==null&&violation.getEndTime()==null){
//			return  "/dealer/violation/listViolationOfIntevel";
		}
		else if(violation.getStartTime()!=null&violation.getEndTime()!=null)//开始时间大于结束时间
		{
			//时间差小于90天
			String timeStart =DateTime.toNormalDateTime(violation.getStartTime());
			String timeEnd = DateTime.toNormalDateTime(violation.getEndTime());
			int days =DateTime.getDaysInterval(timeStart, timeEnd);
			if(days>90)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "时间间隔不能大于90天!");
				return "/dealer/violation/listViolationOfIntevel";
			}
			//得到两年前的一天
			Date twoYearsAgo= DateTime.minusDays(new Date(), 730);
			if(DateTime.getSecondDifference(twoYearsAgo,violation.getStartTime())<0){
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "只能查询两年以内的数据!");
				return "/dealer/violation/listViolationOfIntevel";
			}
			if(DateTime.getSecondDifference(violation.getEndTime(),violation.getStartTime())>0)
			{
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
				 GlobalConstant.MSG_RESULT_CONTENT, 
				 "开始时间大于结束时间,请修改查询时间!");
			return "/dealer/violation/listViolationOfIntevel";
			}
		}else{
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐查询时间!");
			 return "/dealer/violation/listViolationOfIntevel";
		}
		
		ResponseData rd = violationService.listViolationForIntevel(transMsg,companyId, violation,false);
		List<ViolationVO> result =  (List<ViolationVO>) rd.get("result");
		getRequest().setAttribute("violationList",result);
		getRequest().setAttribute("transMsg", transMsg);
		getRequest().setAttribute("startTime", DateTime.toNormalDateTime(violation.getStartTime()));
		getRequest().setAttribute("endTime", DateTime.toNormalDateTime(violation.getEndTime()));
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		return "/dealer/violation/listViolationOfIntevel";
	}
	
	@RequestMapping(value = "/listMonth.vti", method = RequestMethod.GET)
	public String listViolationOfMonth(TransMsg transMsg ,
			@ModelAttribute("violation")  Violation violation) {
//		 TransMsg transMsg =new TransMsg();
//		 List violationYearList = SelectOptionResource.violationYearOptions();
//		 getRequest().setAttribute("violationYearList", violationYearList);
		
//		 List<SelectOption>  violationYearList= violationService.getYearSelectOption();
//		 getRequest().setAttribute("violationYearList", violationYearList);	

		 List violationMonthList = SelectOptionResource.constantMonthOptions();
		 getRequest().setAttribute("violationMonthList", violationMonthList);
		 
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		//月份的校验
		if(violation.getMonth()!=null){
//			if(!checkWeekValue(violation.getWeek())){
//				return  "/dealer/violation/listViolationOfWeek";
//			}
		}else{
			//单击左面菜单链接查询当前月份的
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			String monthValue = String.valueOf(month);
			if(monthValue.length()==1)
			{
				monthValue="0"+monthValue;
			}
			 violation.setYear(year);
			 violation.setMonth(monthValue);
		}
		
		ResponseData rd = violationService.listViolationForMonth( transMsg ,companyId, violation,false);
		List<ViolationVO> result =  (List<ViolationVO>) rd.get("result");
		getRequest().setAttribute("violationList",result);
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		return "/dealer/violation/listViolationOfMonth";
	}
	
	@RequestMapping(value = "/listSeason.vti", method = RequestMethod.GET)
	public String listViolationOfSeason(TransMsg transMsg ,
			@ModelAttribute("violation")  Violation violation) {
		 
		 List violationSeasonList = SelectOptionResource.constantSeasonOptions();
		 getRequest().setAttribute("violationSeasonList", violationSeasonList);
		 
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		List<CarStyle> carStyleList=  CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		getRequest().setAttribute("carStyleList",carStyleList);
		//季度的校验
		if(violation.getSeason()!=null){
		}else{
			//单击左面菜单链接返回空值 不做查询
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			 violation.setYear(year);
			 if(month<4)
			 {
				 violation.setSeason(1);
			 }else if(month<7){
				 violation.setSeason(2);
			 }else if(month<10){
				 violation.setSeason(3);
			 }else {
				 violation.setSeason(4);
			 }
//			return  "/dealer/violation/listViolationOfMonth";
		}
		
		ResponseData rd = violationService.listViolationForSeason( transMsg ,companyId, violation,false);
		List<ViolationVO> result =  (List<ViolationVO>) rd.get("result");
		getRequest().setAttribute("violationList",result);
		getRequest().setAttribute("transMsg", transMsg);

		siteMenuType(rd);
		return "/dealer/violation/listViolationOfSeason";
	}
	@RequestMapping(value="/output.vti", method=RequestMethod.GET)
	public void outPutList(TransMsg transMsg ,@ModelAttribute("violation")  Violation violation,@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		transMsg.setPageSize(Integer.MAX_VALUE);
		ResponseData rd = violationService.listViolation(transMsg, companyId, violation,true);
		ArrayList<Violation> violationList = (ArrayList<Violation>)rd.getFirstItem();
		
		write2excel(violationList,0);//查询日期
	}
	
	@RequestMapping(value="/outputDetail.vti", method=RequestMethod.GET)
	public void outPutDetail(TransMsg transMsg ,
			@RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("vehicleId") Integer vehicleId,
			@RequestParam("startIndex") Integer startIndex,
			@RequestParam("plateNumber") String plateNumber,
			@RequestParam("week") Integer week, @RequestParam("companyName") String companyName,
			@RequestParam("typeId_nick") String typeId_nick) {
		ResponseData rd = violationService.
				listDetail(transMsg, vehicleId, driveLineId, startTime, endTime,true);
		
		List<Violation>  violationList = (List<Violation>) rd.get("vioList");
		
		List<Violation>  allList = (List<Violation>) rd.get("allList");
	
		if(violationList!=null)
		{
			for(Violation v: violationList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		
		if(allList!=null)
		{
			for(Violation v: allList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		getRequest().setAttribute("violationList",violationList);
		

		writeDetail2excel(allList,week,plateNumber,typeId_nick,companyName);//查询日期
		//new add 
		getRequest().setAttribute("typeId_nick", typeId_nick);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
		getRequest().setAttribute("plateNumber", plateNumber);
		getRequest().setAttribute("week", week);
		transMsg.setStartIndex(startIndex);
		getRequest().setAttribute("transMsg", transMsg);
//		return "/dealer/violation/viewViolation";
	}
	
	@RequestMapping(value="/outputDetailMonth.vti", method=RequestMethod.GET)
	public void outPutDetailMonth(TransMsg transMsg ,
			@RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("vehicleId") Integer vehicleId,
			@RequestParam("startIndex") Integer startIndex,
			@RequestParam("plateNumber") String plateNumber,
			@RequestParam("year") Integer year,
			@RequestParam("month") Integer month,@RequestParam("companyName") String companyName,
			@RequestParam("typeId_nick") String typeId_nick) {
//		transMsg.setPageSize(1);
		ResponseData rd = violationService.
				listDetail(transMsg, vehicleId, driveLineId, startTime, endTime,true);
//		rd.put("allList", allList);
//		rd.put("vioList", vioList);
		//显示局部页面
		List<Violation> violationList = (List<Violation>) rd.get("vioList");
		if(violationList!=null)
		{
			for(Violation v: violationList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		//excel打出来全部的list
		List<Violation> excelList = (List<Violation>) rd.get("allList");
		if(excelList!=null)
		{
			for(Violation v: excelList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		getRequest().setAttribute("violationList",violationList);
		
		if(violationList!=null)
		{
			writeDetail2excelMonth(excelList,year,month,plateNumber,typeId_nick,companyName);//查询日期
		}
	
		transMsg.setStartIndex(startIndex);
		getRequest().setAttribute("transMsg", transMsg);
		//new add 
		getRequest().setAttribute("typeId_nick", typeId_nick);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
		getRequest().setAttribute("plateNumber", plateNumber);
		
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("month", month);
//		return "/dealer/violation/viewViolationMonth";
	}
	
	@RequestMapping(value="/outputDetailSeason.vti", method=RequestMethod.GET)
	public void outPutDetailSeason(TransMsg transMsg ,
			@RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("vehicleId") Integer vehicleId,
			@RequestParam("startIndex") Integer startIndex,
			@RequestParam("plateNumber") String plateNumber,
			@RequestParam("year") Integer year,
			@RequestParam("season") Integer season,@RequestParam("companyName") String companyName,
			@RequestParam("typeId_nick") String typeId_nick) {
		ResponseData rd = violationService.
				listDetail(transMsg, vehicleId, driveLineId, startTime, endTime,true);
//		rd.put("allList", allList);
//		rd.put("vioList", vioList); 
		//显示局部页面
		List<Violation> violationList = (List<Violation>) rd.get("vioList");
		if(violationList!=null)
		{
			for(Violation v: violationList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		//excel打出来全部的list
		List<Violation> excelList = (List<Violation>) rd.get("allList");
		if(excelList!=null)
		{
			for(Violation v: excelList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		getRequest().setAttribute("violationList",violationList);
		
	
		writeDetail2excelSeason(excelList,year,season,plateNumber,typeId_nick,companyName);//查询日期
	
		transMsg.setStartIndex(startIndex);
		getRequest().setAttribute("transMsg", transMsg);
		//new add 
		getRequest().setAttribute("typeId_nick", typeId_nick);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
		getRequest().setAttribute("plateNumber", plateNumber);
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("season", season);
//		return "/dealer/violation/viewViolationSeason";
	}
	
	@RequestMapping(value="/outputDetailIntevel.vti", method=RequestMethod.GET)
	public void outPutDetailIntevel(TransMsg transMsg ,
			@RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("vehicleId") Integer vehicleId,
			@RequestParam("startIndex") Integer startIndex,
			@RequestParam("plateNumber") String plateNumber,@RequestParam("companyName") String companyName,
			@RequestParam("typeId_nick") String typeId_nick) {
		ResponseData rd = violationService.
				listDetail(transMsg, vehicleId, driveLineId, startTime, endTime,true);
		
		List<Violation> violationList = (List<Violation>) rd.get("vioList");
		if(violationList!=null)
		{
			for(Violation v: violationList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		//excel打出来全部的list
		List<Violation> excelList = (List<Violation>) rd.get("allList");
		if(excelList!=null)
		{
			for(Violation v: excelList){
				v.setTypeId_nick(typeId_nick);
			}
		}
		getRequest().setAttribute("violationList",violationList);
				
	
		if(startTime!=null&&!startTime.equals("")){
			writeDetail2excelIntevel(excelList,startTime.substring(0,10),endTime.substring(0,10),plateNumber,typeId_nick,companyName);//查询日期
		}else{
			writeDetail2excelIntevel(excelList,"","",plateNumber,typeId_nick,companyName);//查询日期
		}
		transMsg.setStartIndex(startIndex);
		getRequest().setAttribute("transMsg", transMsg);
		
		//new add 
		getRequest().setAttribute("typeId_nick", typeId_nick);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
		getRequest().setAttribute("plateNumber", plateNumber); 
		
//		return "/dealer/violation/viewViolationIntevel";
	}
	
	@RequestMapping(value="/outputWeek.vti", method=RequestMethod.GET)
	public void outPutListWeek(TransMsg transMsg,@ModelAttribute("violation")  Violation violation,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		 ResponseData rd = violationService.listViolationForWeek(transMsg,companyId, violation,true);
		 
		List<ViolationVO>  violationList = (List<ViolationVO>) rd.get("listAll");
		 
		 if(violation.getWeek()==null){
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, 
						"请填写周数 查询后再导出");
//			 return "/dealer/violation/listViolationOfWeek";
		 }
	
		write2excelWeek(violationList,violation.getWeek());//传入星期参数
//		TransMsg transMsg = new TransMsg();
//		transMsg.setStartIndex(startIndex);
		
//		return listViolationOfWeek(transMsg,violation);
	}
	@RequestMapping(value="/outputMonth.vti", method=RequestMethod.GET)
	public void outPutListMonth( TransMsg transMsg , @ModelAttribute("violation")  Violation violation,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = violationService.listViolationForMonth(transMsg,companyId, violation,true);
		List<ViolationVO>  violationList = (List<ViolationVO>) rd.get("listAll");
		
	
		write2excelMonth(violationList,violation.getYear(),violation.getMonth());//传入星期参数
//		TransMsg transMsg = new TransMsg();
//		transMsg.setStartIndex(startIndex);
		
//		return listViolationOfMonth( transMsg ,violation);
	}
	
	@RequestMapping(value="/outputSeason.vti", method=RequestMethod.GET)
	public void outPutListSeason( TransMsg transMsg , @ModelAttribute("violation")  Violation violation,
			@RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = violationService.listViolationForSeason(transMsg,companyId, violation,true);
		List<ViolationVO>  violationList = (List<ViolationVO>) rd.get("listAll");
		
		write2excelSeason(violationList,violation.getYear(),violation.getSeason());//传入星期参数
		
//		return listViolationOfSeason( transMsg ,violation);
	}
	
	@RequestMapping(value="/outputIntevel.vti", method=RequestMethod.GET)
	public void outPutListIntevel(TransMsg transMsg,@ModelAttribute("violation")  Violation violation,
			@RequestParam("startIndex") Integer startIndex) {
		
//		if(violation.getStartTime()==null)
//		{
//			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
//						GlobalConstant.MSG_RESULT_CONTENT, 
//						"请选择时间 查询后再导出");
////			return  "/dealer/violation/listViolationOfIntevel";
//		}
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = violationService.listViolationForIntevel(transMsg,companyId, violation,true);
		 
		List<ViolationVO>  violationList = (List<ViolationVO>) rd.get("listAll");
		
		write2excelIntevel(violationList,violation.getStartTime(),violation.getEndTime());//传入星期参数
		
//		return listViolationOfIntevel(transMsg,violation);
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void write2excel(List<Violation> violations,int timeTpye) {
		
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市","经销商名称","车牌","车型","VIN","违规类型","违规时间","违规次数"};
		
		String userDownLoadsUrl= System.getProperty("user.home")+"\\Downloads";
		String fileName ="";
		if(timeTpye==1)
		{
			fileName="违规明细单周统计"+violations.get(0).getWeek()+"周"+DateTime.toShortDateTime(new Date());
		}else if(timeTpye==0){
			fileName="违规明细"+DateTime.toShortDateTime(new Date());
		}

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
			for (int i = 0; i < violations.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				Violation violation = violations.get(i);
				HSSFCell cell = row.createCell(0);
				
				cell.setCellValue(violation.getIsKeyCity_Nick());
				cell = row.createCell(1);
				cell.setCellValue(violation.getDealerType_Nick());
				cell = row.createCell(2);
				cell.setCellValue(violation.getDealerCode());
				cell = row.createCell(3);
				cell.setCellValue(violation.getFenxiao_center());
				cell = row.createCell(4);
				cell.setCellValue(violation.getRegionProvinceId_Nick());
				cell = row.createCell(5);
				cell.setCellValue(violation.getRegionCityId_Nick());
				cell = row.createCell(6);
				cell.setCellValue(violation.getCompanyName());
				cell = row.createCell(7);
				cell.setCellValue(violation.getPlateNumber());
				cell = row.createCell(8);
				cell.setCellValue(violation.getCarStyleId_Nick());
				cell = row.createCell(9);
				cell.setCellValue(violation.getVin());
				cell = row.createCell(10);
				cell.setCellValue(violation.getTypeId_nick());
				cell = row.createCell(11);
				cell.setCellValue(DateTime.toNormalDateTime(violation.getCreationTime()));
				cell = row.createCell(12);
				cell.setCellValue(violation.getCountId());
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void writeDetail2excel(List<Violation> violations,int week,String plateNumber,String typeId_nick,String companyName) {
		
		String[] titles = {"违规时间","开始时间","结束时间","违规类型"};
		
		String fileName ="";
		fileName =companyName+"-"+plateNumber+"-"+"第"+week+"周违规明细"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Violation violation = violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(violation.getIsKeyCity_Nick());
			cell = row.createCell(0);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getCreationTime()));
			cell = row.createCell(1);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getStart_Time()));
			cell = row.createCell(2);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getEnd_Time()));
			cell = row.createCell(3);
			cell.setCellValue(typeId_nick);
			}
		}

		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}

	/**
	 * 导出数据到excel 季度
	 * @param violations
	 */
	public  void writeDetail2excelSeason(List<Violation> violations,int year,int season,String plateNumber,String typeId_nick,String companyName) {
		
		String[] titles = {"违规时间","开始时间","结束时间","违规类型"};
		
		String fileName ="";
		
		fileName =companyName+"-"+plateNumber+"-"+year+"第"+season+"季度违规明细"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Violation violation = violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(violation.getIsKeyCity_Nick());
			cell = row.createCell(0);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getCreationTime()));
			cell = row.createCell(1);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getStart_Time()));
			cell = row.createCell(2);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getEnd_Time()));
			cell = row.createCell(3);
			cell.setCellValue(typeId_nick);
		}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	/**
	 * 导出数据到excel 月
	 * @param violations
	 */
	public  void writeDetail2excelMonth(List<Violation> violations,int year,int month,String plateNumber,String typeId_nick,String companyName) {
		
		String[] titles = {"违规时间","开始时间","结束时间","违规类型"};
		
		String fileName ="";
		
		fileName =companyName+"-"+plateNumber+"-"+year+"第"+month+"月违规明细"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Violation violation = violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(violation.getIsKeyCity_Nick());
			cell = row.createCell(0);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getCreationTime()));
			cell = row.createCell(1);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getStart_Time()));
			cell = row.createCell(2);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getEnd_Time()));
			cell = row.createCell(3);
			cell.setCellValue(typeId_nick);
		}
		}
	
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}


	/**
	 * 导出数据到excel 时间段
	 * @param violations
	 */
	public  void writeDetail2excelIntevel(List<Violation> violations,String startTime, String endTime,String plateNumber,String typeId_nick,String companyName) {
		
		String[] titles = {"违规时间","开始时间","结束时间","违规类型"};
		
		String fileName ="";
		if(startTime==null||startTime.equals(""))
		{
			fileName =companyName+"-"+plateNumber+"违规明细"+DateTime.toShortDateTime(new Date());
		}else{
			fileName =companyName+"-"+plateNumber+"-时间段["+startTime.substring(0,10)+"-"+endTime.substring(0,10)+"]违规明细"+DateTime.toShortDateTime(new Date());
			
		}
		
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Violation violation = violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(violation.getIsKeyCity_Nick());
			cell = row.createCell(0);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getCreationTime()));
			cell = row.createCell(1);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getStart_Time()));
			cell = row.createCell(2);
			cell.setCellValue(DateTime.toNormalDateTime(violation.getEnd_Time()));
			cell = row.createCell(3);
			cell.setCellValue(typeId_nick);
		}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	/**
	 * 导出数据到excel 每周
	 * @param violations
	 */
	public  void write2excelWeek(List<ViolationVO> violations,String week) {
		
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市","经销商名称","车牌","车型","VIN","违规类型","违规次数"};
		
		String fileName ="";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		//默认
		if(week==null)
		{
		}
		else{
			fileName ="违规明细单周统计"+year+"第"+week+"周"+DateTime.toShortDateTime(new Date());
		}
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			ViolationVO violation = violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(violation.getIsKeyCity_Nick());
			cell = row.createCell(1);
			cell.setCellValue(violation.getDealerType_Nick());
			cell = row.createCell(2);
			cell.setCellValue(TypeConverter.toString(violation.getDealerCode()));
			cell = row.createCell(3);
			cell.setCellValue(violation.getFenxiao_center());
			cell = row.createCell(4);
			cell.setCellValue(violation.getRegionProvinceId_Nick());
			cell = row.createCell(5);
			cell.setCellValue(violation.getRegionCityId_Nick());
			cell = row.createCell(6);
			cell.setCellValue(violation.getCompanyName());
			cell = row.createCell(7);
			cell.setCellValue(violation.getPlateNumber());
			cell = row.createCell(8);
			cell.setCellValue(violation.getCarStyleId_Nick());
			cell = row.createCell(9);
			cell.setCellValue(violation.getVin());
			cell = row.createCell(10);
			cell.setCellValue(violation.getTypeId_nick());
			
			cell = row.createCell(11);
			cell.setCellValue(violation.getSumNum());
		}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	/**
	 * 导出数据到excel 每月
	 * @param violations
	 */
	public  void write2excelMonth(List<ViolationVO> violations,Integer year,String month) {
		
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市","经销商名称","车牌","车型","VIN","违规类型","违规次数"};
		
		String fileName ="";

		//默认
		if(year==null)
		{
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, 
					"请填写年,月条件 ,查询后再导出");
			return;
			//fileAddress =userDownLoadsUrl+"\\违规明细单周统计\\"+"本年全部数据"+DateTime.toShortDateTime(new Date())+".xls";
		}
		else{
			fileName ="违规明细单月统计"+year+"第"+month+"月"+DateTime.toShortDateTime(new Date());
		}
	
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			ViolationVO violation = (ViolationVO)violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(TypeConverter.toString(violation.getIsKeyCity_Nick()));
			cell = row.createCell(1);
			cell.setCellValue(TypeConverter.toString(violation.getDealerType_Nick()));
			cell = row.createCell(2);
			cell.setCellValue(TypeConverter.toString(violation.getDealerCode()));
			cell = row.createCell(3);
			cell.setCellValue(violation.getFenxiao_center());
			cell = row.createCell(4);
			cell.setCellValue(violation.getRegionProvinceId_Nick());
			cell = row.createCell(5);
			cell.setCellValue(violation.getRegionCityId_Nick());
			cell = row.createCell(6);
			cell.setCellValue(violation.getCompanyName());
			cell = row.createCell(7);
			cell.setCellValue(violation.getPlateNumber());
			cell = row.createCell(8);
			cell.setCellValue(violation.getCarStyleId_Nick());
			cell = row.createCell(9);
			cell.setCellValue(violation.getVin());
			cell = row.createCell(10);
			cell.setCellValue(violation.getTypeId_nick());
			
			cell = row.createCell(11);
			cell.setCellValue(violation.getSumNum());
		}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	/**
	 * 导出数据到excel 每月
	 * @param violations
	 */
	public  void write2excelSeason(List<ViolationVO> violations,Integer year,Integer season) {
		
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市","经销商名称","车牌","车型","VIN","违规类型","违规次数"};
		
		String fileName ="";

		//默认
		if(year==null)
		{
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, 
					"请填写年,月条件 ,查询后再导出");
			return;
		}
		else{
			fileName ="违规明细单月统计"+year+"第"+season+"季度"+DateTime.toShortDateTime(new Date());
		}
			
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			ViolationVO violation = (ViolationVO)violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(TypeConverter.toString(violation.getIsKeyCity_Nick()));
			cell = row.createCell(1);
			cell.setCellValue(TypeConverter.toString(violation.getDealerType_Nick()));
			cell = row.createCell(2);
			cell.setCellValue(TypeConverter.toString(violation.getDealerCode()));
			cell = row.createCell(3);
			cell.setCellValue(violation.getFenxiao_center());
			cell = row.createCell(4);
			cell.setCellValue(violation.getRegionProvinceId_Nick());
			cell = row.createCell(5);
			cell.setCellValue(violation.getRegionCityId_Nick());
			cell = row.createCell(6);
			cell.setCellValue(violation.getCompanyName());
			cell = row.createCell(7);
			cell.setCellValue(violation.getPlateNumber());
			cell = row.createCell(8);
			cell.setCellValue(violation.getCarStyleId_Nick());
			cell = row.createCell(9);
			cell.setCellValue(violation.getVin());
			cell = row.createCell(10);
			cell.setCellValue(violation.getTypeId_nick());
			cell = row.createCell(11);
			cell.setCellValue(violation.getSumNum());
		}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	
	
	/**
	 * 导出数据到excel 时间段
	 * @param violations
	 */
	public  void write2excelIntevel(List<ViolationVO> violations,Date start,Date end) {
		
		String[] titles = {"经销商状态","网络形态","网络代码","分销中心","省份",
				"城市","经销商名称","车牌","车型","VIN","违规类型","违规次数"};
		
		String fileName ="";
		
		String startTime= "";
		String endTime= "";
	
		if(start==null)
		{
			fileName ="违规明细统计"+DateTime.toShortDateTime(new Date());
		}else{
			startTime =DateTime.toNormalDate(start) ;
			endTime =DateTime.toNormalDate(end) ;
			fileName ="违规明细统计["+startTime+"-"+endTime+"]"+DateTime.toShortDateTime(new Date());
		}
			
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("违规明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (violations != null && violations.size() > 0) {
		for (int i = 0; i < violations.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			ViolationVO violation = violations.get(i);
			HSSFCell cell = row.createCell(0);
			
			cell.setCellValue(violation.getIsKeyCity_Nick());
			cell = row.createCell(1);
			cell.setCellValue(violation.getDealerType_Nick());
			cell = row.createCell(2);
			cell.setCellValue(TypeConverter.toString(violation.getDealerCode()));
			cell = row.createCell(3);
			cell.setCellValue(violation.getFenxiao_center());
			cell = row.createCell(4);
			cell.setCellValue(violation.getRegionProvinceId_Nick());
			cell = row.createCell(5);
			cell.setCellValue(violation.getRegionCityId_Nick());
			cell = row.createCell(6);
			cell.setCellValue(violation.getCompanyName());
			cell = row.createCell(7);
			cell.setCellValue(violation.getPlateNumber());
			cell = row.createCell(8);
			cell.setCellValue(violation.getCarStyleId_Nick());
			cell = row.createCell(9);
			cell.setCellValue(violation.getVin());
			cell = row.createCell(10);
			cell.setCellValue(violation.getTypeId_nick());
			cell = row.createCell(11);
			cell.setCellValue(violation.getSumNum());
		}
		}
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	
	/*
	 * 查看违规明细 周
	 */
	@RequestMapping(value="/detail.vti", method=RequestMethod.GET)
	public String getDetail(TransMsg transMsg, @RequestParam("vehicleId") Integer vehicleId,
			 @RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("week") Integer week,
			 @RequestParam("typeId_nick") String typeId_nick,
				@RequestParam("carStyleId_Nick") String carStyleId_Nick,
				 @RequestParam("vin") String vin,
					@RequestParam("plateNumber") String plateNumber	,
					@RequestParam("companyName") String companyName,
					@RequestParam("vinValue") String vinValue,
					@RequestParam("plateNumberValue") String plateNumberValue,
					@RequestParam("dealerCode") String dealerCode)	{

		//查询起始时间
		String startTime="";
		String endTime ="";
		//处理单周统计
		if(week!=null)
		{
			String[] days = DateTime.getRangeOfWeek(week); 
			Date end  = DateTime.addDays(DateTime.toDate(days[1]),1);
			
			startTime = DateTime.toNormalDate(DateTime.toDate(days[0]));
			endTime = DateTime.toNormalDate(end);
		}
		 ResponseData rd  = 
				violationService.listDetail(transMsg, vehicleId, driveLineId, startTime, endTime,false);
		
		
		String titleVechie="";
		
		List<Violation> violationList = (List<Violation>) rd.get("vioList");
		if(violationList!=null)
		{
			for(Violation v: violationList){
				v.setTypeId_nick(typeId_nick);
			}
			titleVechie+="车辆:"+ plateNumber+"[vin码:"+vin+"-车型:"+ carStyleId_Nick+"]";
		}
		
		getRequest().setAttribute("violationList",violationList);
//		getRequest().setAttribute("transMsg", transMsg);
		getRequest().setAttribute("titleVechie", titleVechie);
		getRequest().setAttribute("typeId_nick", typeId_nick);
		getRequest().setAttribute("vin", vin);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
		getRequest().setAttribute("week", week);
		getRequest().setAttribute("plateNumber", plateNumber); 
		
		getRequest().setAttribute("vinValue", vinValue);
		getRequest().setAttribute("plateNumberValue", plateNumberValue); 
		getRequest().setAttribute("dealerCode", dealerCode); 
//		TransMsg transMsg = new TransMsg();
//		transMsg.setStartIndex(startIndex);
		getRequest().setAttribute("companyName", companyName); 
//		transMsg.setPageSize(1);
		getRequest().setAttribute("transMsg", transMsg);
		
		return "/dealer/violation/viewViolation";
	}
	
	
	/*
	 * 查看违规明细 月
	 */
	@RequestMapping(value="/detailMonth.vti", method=RequestMethod.GET)
	public String getDetailMonth(TransMsg transMsg,@RequestParam("vehicleId") Integer vehicleId,
			 @RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("year") Integer year,
			@RequestParam("month") String month,
			 @RequestParam("typeId_nick") String typeId_nick,
				@RequestParam("carStyleId_Nick") String carStyleId_Nick,
				 @RequestParam("vin") String vin,
					@RequestParam("plateNumber") String plateNumber	,@RequestParam("companyName") String companyName,
					@RequestParam("vinValue") String vinValue,
					@RequestParam("plateNumberValue") String plateNumberValue,
					@RequestParam("dealerCode") String dealerCode)
		{
					
		//查询起始时间
		String startTime= "";
		String endTime= "";
		//处理单月统计
		//得到选中月份的第一天
		String date = year.toString()+month+"01";  //toDate
		Date dateFirst = DateTime.toDate(date);
		
		String[] days= DateTime.getRangeOfMonth(dateFirst);
		Date end  = DateTime.addDays(DateTime.toDate(days[1]),1);
		
		startTime = DateTime.toNormalDate(DateTime.toDate(days[0]));
		endTime = DateTime.toNormalDate(end);
		
		
		ResponseData rd = 
				violationService.listDetail(transMsg, vehicleId, driveLineId, startTime, endTime,false);
		String titleVechie="";
		
		List<Violation> violationList = (List<Violation>) rd.get("vioList");
		if(violationList!=null)
		{
			for(Violation v: violationList){
				v.setTypeId_nick(typeId_nick);
			}
			titleVechie+="车辆:"+ plateNumber+"[vin码:"+vin+"-车型:"+ carStyleId_Nick+"]";
		}
		getRequest().setAttribute("violationList",violationList);
		getRequest().setAttribute("titleVechie", titleVechie);
		getRequest().setAttribute("typeId_nick", typeId_nick);
		getRequest().setAttribute("vin", vin);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
//		getRequest().setAttribute("week", week);
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("month", month);
		getRequest().setAttribute("plateNumber", plateNumber); 
		getRequest().setAttribute("vinValue", vinValue);
		getRequest().setAttribute("plateNumberValue", plateNumberValue); 
		getRequest().setAttribute("dealerCode", dealerCode); 
//		TransMsg transMsg = new TransMsg();
//		transMsg.setStartIndex(startIndex);
		getRequest().setAttribute("companyName", companyName); 
//		transMsg.setPageSize(1);
		getRequest().setAttribute("transMsg", transMsg);
		
		return "/dealer/violation/viewViolationMonth";
	}
	
	/*
	 * 查看违规明细 季度
	 */
	@RequestMapping(value="/detailSeason.vti", method=RequestMethod.GET)
	public String getDetailSeason(TransMsg transMsg,@RequestParam("vehicleId") Integer vehicleId,
			 @RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("year") Integer year,
			@RequestParam("season") Integer season,
			 @RequestParam("typeId_nick") String typeId_nick,
				@RequestParam("carStyleId_Nick") String carStyleId_Nick,
				 @RequestParam("vin") String vin,
					@RequestParam("plateNumber") String plateNumber,
					@RequestParam("vinValue") String vinValue,
					@RequestParam("plateNumberValue") String plateNumberValue,
					@RequestParam("dealerCode") String dealerCode, @RequestParam("companyName") String companyName){
					
//		TransMsg transMsg =new TransMsg();
		//查询起始时间
		String startTime= "";
		String endTime= "";
		
		if (season != null) {
			if (season == 1) {
				startTime = year + "-01-01 00:00:00";
				endTime = year + "-03-31 23:59:59";
			} else if (season == 2) {
				startTime = year + "-04-01 00:00:00";
				endTime = year + "-06-30 23:59:59";

			} else if (season == 3) {
				startTime = year + "-07-01 00:00:00";
				endTime = year + "-09-30 23:59:59";

			} else if (season == 4) {
				startTime = year + "-10-01 00:00:00";
				endTime = year + "-12-31 23:59:59";
			}
		}
		ResponseData rd = 
				violationService.listDetail(transMsg, vehicleId, driveLineId, startTime, endTime,false);
		String titleVechie="";
		
		List<Violation> violationList = (List<Violation>) rd.get("vioList");
		if(violationList!=null)
		{
			for(Violation v: violationList){

				v.setTypeId_nick(typeId_nick);
			}
			titleVechie+="车辆:"+ plateNumber+"[vin码:"+vin+"-车型:"+ carStyleId_Nick+"]";
		}
		getRequest().setAttribute("violationList",violationList);
		getRequest().setAttribute("titleVechie", titleVechie);
		getRequest().setAttribute("typeId_nick", typeId_nick);
		
		getRequest().setAttribute("vin", vin);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
//		getRequest().setAttribute("week", week);
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("season", season);
		getRequest().setAttribute("plateNumber", plateNumber); 
		getRequest().setAttribute("vinValue", vinValue);
		getRequest().setAttribute("plateNumberValue", plateNumberValue); 
		getRequest().setAttribute("dealerCode", dealerCode); 
//		transMsg.setStartIndex(startIndex);
		getRequest().setAttribute("companyName", companyName); 
//		transMsg.setPageSize(1);
		getRequest().setAttribute("transMsg", transMsg);
		
		return "/dealer/violation/viewViolationSeason";
	}
	
	/*
	 * 查看违规明细 时间段
	 */
	@RequestMapping(value="/detailIntevel.vti", method=RequestMethod.GET)
	public String getDetailIntevel(TransMsg transMsg, @RequestParam("vehicleId") Integer vehicleId,
			 @RequestParam("driveLineId") Integer driveLineId,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			 @RequestParam("typeId_nick") String typeId_nick,
				@RequestParam("carStyleId_Nick") String carStyleId_Nick,
				 @RequestParam("vin") String vin,
					@RequestParam("plateNumber") String plateNumber,
					@RequestParam("vinValue") String vinValue,
					@RequestParam("plateNumberValue") String plateNumberValue,
					@RequestParam("dealerCode") String dealerCode,
					@RequestParam("companyName") String companyName
					){

		//查询起始时间
		ResponseData rd= violationService.listDetail(transMsg, vehicleId, driveLineId, startTime, 
				endTime,false);
		
		String titleVechie="";
		
		List<Violation> violationList = (List<Violation>) rd.get("vioList");
		if(violationList!=null)
		{
			for(Violation v: violationList){
				v.setTypeId_nick(typeId_nick);
			}
			titleVechie+="车辆:"+ plateNumber+"[vin码:"+vin+"-车型:"+ carStyleId_Nick+"]";
		}
	
		getRequest().setAttribute("violationList",violationList);
		getRequest().setAttribute("transMsg", transMsg);
		getRequest().setAttribute("titleVechie", titleVechie);
		getRequest().setAttribute("typeId_nick", typeId_nick);
		getRequest().setAttribute("vin", vin);
		//隐藏属性
		getRequest().setAttribute("driveLineId",driveLineId);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("vehicleId", vehicleId);
	
		getRequest().setAttribute("plateNumber", plateNumber); 
		getRequest().setAttribute("companyName", companyName);  
		getRequest().setAttribute("vinValue", vinValue);
		getRequest().setAttribute("plateNumberValue", plateNumberValue); 
		getRequest().setAttribute("dealerCode", dealerCode); 
//		TransMsg transMsg = new TransMsg();
//		transMsg.setStartIndex(startIndex);
		
//		transMsg.setPageSize(1);
		getRequest().setAttribute("transMsg", transMsg);
		
		return "/dealer/violation/viewViolationIntevel";
	}
	
	/*
	 * 校验week的值
	 */
	private boolean checkWeekValue(String week) {
		if (ValidatorUtil.isNumeric(TypeConverter.toString(week))) {
			if (TypeConverter.toInteger(week)> 52 || TypeConverter.toInteger(week) <= 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "周数条件 请输入 0-52之间数字！");
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
/*	private boolean checkWeekValue( Integer week) {
		if(ValidatorUtil.isNumeric(TypeConverter.toString(week)))
		{
			if(week>52 ||week<=0)
			{
				
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "请输入 0-52之间数字！");
				return false;
			}else{
				return true;
			}
		}else{
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "请输入数字！");
			return false;
		}
	}*/
	
	@RequestMapping(value = "/listLineTimeViolation.vti", method = RequestMethod.GET)
	public String listLineViolation(TransMsg transMsg ,Integer selectSaleCenterId,Integer violationType,String vin,String plateNumber,String dealerCode,String violationYear,String violationWeek,String violationMonth,String violationQuarter) {
		try{
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			siteMenuType(rd);
			
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			if(null==selectSaleCenterId){
				selectSaleCenterId=orgList.get(0).getId();
			}
			getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			
			getRequest().setAttribute("vin", vin);
			getRequest().setAttribute("plateNumber", plateNumber);
			getRequest().setAttribute("dealerCode", dealerCode);
			if(ValidatorUtil.isBlankOrNull(violationYear)){
				getRequest().setAttribute("violationYear", violationYear=DateTime.getYear());
			}else{
				getRequest().setAttribute("violationYear", violationYear);
			}
			getRequest().setAttribute("violationWeek", violationWeek);
			getRequest().setAttribute("violationMonth", violationMonth);
			getRequest().setAttribute("violationQuarter", violationQuarter);
			getRequest().setAttribute("transMsg", transMsg);
			if(null==transMsg || null==transMsg.getStartIndex()){
				getRequest().setAttribute("myPageIndex",0);
			}else{
				getRequest().setAttribute("myPageIndex", transMsg.getStartIndex());
			}
			List<ViolationLineTimeVO> violationList=violationService.listViolation(transMsg, currentCompanyId, currentUserId, selectSaleCenterId, violationType, vin, plateNumber, dealerCode, violationYear, violationWeek, violationMonth, violationQuarter,false);
			getRequest().setAttribute("violationList", violationList);
			if(violationType==GlobalConstant.TIME_VIOLATION_TYPE){
				return "/dealer/violation/listTimeViolation";
			}else{
				return "/dealer/violation/listLineViolation";
			}
		}catch(Exception e){
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("violationList", null);
			if(violationType==GlobalConstant.TIME_VIOLATION_TYPE){
				return "/dealer/violation/listTimeViolation";
			}else{
				return "/dealer/violation/listLineViolation";
			}
		}
	}
	
	/**
	 * 连续无试驾查询
	 * @param transMsg
	 * @param selectSaleCenterId
	 * @param vin
	 * @param plateNumber
	 * @param dealerCode
	 * @param violationYear
	 * @param violationWeek
	 * @param violationMonth
	 * @param violationQuarter
	 * @return
	 */
	@RequestMapping(value = "/listNoTest.vti", method = RequestMethod.GET)
	public String listNoTest(TransMsg transMsg ,Integer selectSaleCenterId,String vin,String plateNumber,String dealerCode,String violationYear,String violationWeek,String violationMonth,String violationQuarter) {
		try{
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			if(null!=selectSaleCenterId && selectSaleCenterId.intValue()>=1){
				getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			}else{
				getRequest().setAttribute("selectedSaleCenterId", orgList.get(0).getId());
			}
			
			getRequest().setAttribute("vin", vin);
			getRequest().setAttribute("plateNumber", plateNumber);
			getRequest().setAttribute("dealerCode", dealerCode);
			if(ValidatorUtil.isBlankOrNull(violationYear)){
				getRequest().setAttribute("violationYear", violationYear=DateTime.getYear());
			}else{
				getRequest().setAttribute("violationYear", violationYear);
			}
			getRequest().setAttribute("violationWeek", violationWeek);
			getRequest().setAttribute("violationMonth", violationMonth);
			getRequest().setAttribute("violationQuarter", violationQuarter);
			getRequest().setAttribute("transMsg", transMsg);
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			siteMenuType(rd);
			
			List<TestNoDriveWeekVO> testNoDriveWeekList=violationService.listTestNoDriveWeek(transMsg, currentCompanyId, currentUserId,selectSaleCenterId,vin,plateNumber, dealerCode, violationYear, violationWeek, violationMonth, violationQuarter,false);
			getRequest().setAttribute("testNoDriveWeekList", testNoDriveWeekList);
			return "/dealer/violation/listNoTest";
		}catch(Exception e){
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("testNoDriveWeekList", null);
			return "/dealer/violation/listNoTest";
		}
	}
	
	/**
	 * 非活跃试驾明细
	 * @author liuq 
	 * @version 0.1 
	 * @param model
	 * @param transMsg
	 * @param violationVO
	 * @return
	 */
	@RequestMapping(value = "/listNoTestDetail.vti", method = RequestMethod.GET)
	public String listNoTestDetail(Model model, TransMsg transMsg, @ModelAttribute("violationVO") ViolationVO violationVO) {
		try {
			
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer userId = SessionManager.getCurrentUserId(getRequest());
			
			ResponseData rd = violationService.listNoTestDetail(transMsg, companyId, userId, violationVO, false);
			
			model.addAttribute("testNoDriveWeekList", rd.getFirstItem());
			model.addAttribute("violationVO", violationVO);
			model.addAttribute("transMsg", transMsg);
			
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
			model.addAttribute("orgList", orgList);
			
			siteMenuType(model,rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/dealer/violation/listNoTestDetail";
	}
	
	/**
	 * 删除非活跃试驾
	 * @author liuq 
	 * @version 0.1 
	 * @param testNoDriveWeekId
	 * @param vehicleId
	 * @param companyId
	 */
	@RequestMapping(value="/deleteTestNoDriveWeek.vti", method=RequestMethod.GET)
	public void deleteTestNoDriveWeek(@RequestParam("testNoDriveWeekId") Integer testNoDriveWeekId) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = violationService.deleteTestNoDriveWeek(testNoDriveWeekId, userId);
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
	 * 违规明细
	 * @param transMsg
	 * @param myPageIndex
	 * @param vehicleId
	 * @param selectSaleCenterId
	 * @param violationType
	 * @param vin
	 * @param plateNumber
	 * @param dealerCode
	 * @param violationYear
	 * @param violationWeek
	 * @param violationMonth
	 * @param violationQuarter
	 * @return
	 */
	@RequestMapping(value = "/viewViolationDetail.vti", method = RequestMethod.GET)
	public String viewViolationDetail(TransMsg transMsg ,Integer myPageIndex,Integer vehicleId,Integer selectSaleCenterId,Integer violationType,String vin,String plateNumber,String dealerCode,String violationYear,String violationWeek,String violationMonth,String violationQuarter) {
		try{
			getRequest().setAttribute("vehicleId", vehicleId);
			getRequest().setAttribute("selectSaleCenterId", selectSaleCenterId);
			getRequest().setAttribute("vin", vin);
			getRequest().setAttribute("plateNumber", plateNumber);
			getRequest().setAttribute("dealerCode", dealerCode);
			getRequest().setAttribute("violationYear", violationYear);
			getRequest().setAttribute("violationWeek", violationWeek);
			getRequest().setAttribute("violationMonth", violationMonth);
			getRequest().setAttribute("violationQuarter", violationQuarter);
			getRequest().setAttribute("violationType", violationType);
			getRequest().setAttribute("myPageIndex", myPageIndex);
			getRequest().setAttribute("transMsg", transMsg);
			if(GlobalConstant.TIME_VIOLATION_TYPE==violationType){
				getRequest().setAttribute("locationMessage", "违规明细> 时间违规 > 违规详情");
			}else{
				getRequest().setAttribute("locationMessage", "违规明细> 路线违规 > 违规详情");
			}
			List<ViolationLineTimeDetailVO> violationDetailList=violationService.viewViolationDetail(transMsg, vehicleId,violationType,violationYear,violationWeek, violationMonth, violationQuarter,false);
			getRequest().setAttribute("violationList", violationDetailList);
			return "/dealer/violation/viewViolationDetail";
		}catch(Exception e){
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("violationList", null);
			return "/dealer/violation/viewViolationDetail";
		}
	}
	
	/**
	 * 违规明细返回上一级
	 * @param myPageIndex
	 * @param selectSaleCenterId
	 * @param violationType
	 * @param vin
	 * @param plateNumber
	 * @param dealerCode
	 * @param violationYear
	 * @param violationWeek
	 * @param violationMonth
	 * @param violationQuarter
	 * @return
	 */
	@RequestMapping(value = "/returnLineTimeViolation.vti", method = RequestMethod.GET)
	public String returnLineTimeViolation(Integer myPageIndex ,Integer selectSaleCenterId,Integer violationType,String vin,String plateNumber,String dealerCode,String violationYear,String violationWeek,String violationMonth,String violationQuarter) {
		try{
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			TransMsg transMsg=new TransMsg();
			transMsg.setStartIndex(myPageIndex);
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
			
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			if(null!=selectSaleCenterId && selectSaleCenterId.intValue()>=1){
				getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			}else{
				getRequest().setAttribute("selectedSaleCenterId", orgList.get(0).getId());
			}
			getRequest().setAttribute("vin", vin);
			getRequest().setAttribute("plateNumber", plateNumber);
			getRequest().setAttribute("dealerCode", dealerCode);
			if(ValidatorUtil.isBlankOrNull(violationYear)){
				getRequest().setAttribute("violationYear", violationYear=DateTime.getYear());
			}else{
				getRequest().setAttribute("violationYear", violationYear);
			}
			getRequest().setAttribute("violationWeek", violationWeek);
			getRequest().setAttribute("violationMonth", violationMonth);
			getRequest().setAttribute("violationQuarter", violationQuarter);
			getRequest().setAttribute("transMsg", transMsg);
			getRequest().setAttribute("myPageIndex",myPageIndex);
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			siteMenuType(rd);
			
			List<ViolationLineTimeVO> violationList=violationService.listViolation(transMsg, currentCompanyId, currentUserId, selectSaleCenterId, violationType, vin, plateNumber, dealerCode, violationYear, violationWeek, violationMonth, violationQuarter,false);
			getRequest().setAttribute("violationList", violationList);
			if(violationType==GlobalConstant.TIME_VIOLATION_TYPE){
				return "/dealer/violation/listTimeViolation";
			}else{
				return "/dealer/violation/listLineViolation";
			}
		}catch(Exception e){
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("violationList", null);
			if(violationType==GlobalConstant.TIME_VIOLATION_TYPE){
				return "/dealer/violation/listTimeViolation";
			}else{
				return "/dealer/violation/listLineViolation";
			}
		}
	}
	
	/**
	 * 违规统计（时间违规、路线违规）导出
	 * @param transMsg
	 * @param selectSaleCenterId
	 * @param violationType
	 * @param vin
	 * @param plateNumber
	 * @param dealerCode
	 * @param violationYear
	 * @param violationWeek
	 * @param violationMonth
	 * @param violationQuarter
	 * @return
	 */
	@RequestMapping(value = "/exportViolation.vti", method = RequestMethod.GET)
	public void exportViolation(TransMsg transMsg ,Integer selectSaleCenterId,Integer violationType,String vin,String plateNumber,String dealerCode,String violationYear,String violationWeek,String violationMonth,String violationQuarter) {
		List<ViolationLineTimeVO> violationList=null;
		try {
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			
			violationList=violationService.listViolation(transMsg, currentCompanyId, currentUserId, selectSaleCenterId, violationType, vin, plateNumber, dealerCode, violationYear, violationWeek, violationMonth, violationQuarter,true);
			exportViolation2Excel(violationList,violationType);
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			if(null!=selectSaleCenterId && selectSaleCenterId.intValue()>=1){
				getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			}else{
				getRequest().setAttribute("selectedSaleCenterId", orgList.get(0).getId());
			}
			getRequest().setAttribute("violationList", violationList);
			getRequest().setAttribute("vin", vin);
			getRequest().setAttribute("plateNumber", plateNumber);
			getRequest().setAttribute("dealerCode", dealerCode);
			getRequest().setAttribute("violationYear", violationYear);
			getRequest().setAttribute("violationWeek", violationWeek);
			getRequest().setAttribute("violationMonth", violationMonth);
			getRequest().setAttribute("violationQuarter", violationQuarter);
			getRequest().setAttribute("transMsg", transMsg);
		}catch(Exception e){
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("violationList", violationList);
		}finally{
			violationList=null;
		}
	}
	
	/**
	 * 非活跃试驾导出
	 * @param transMsg
	 * @param selectSaleCenterId
	 * @param violationType
	 * @param vin
	 * @param plateNumber
	 * @param dealerCode
	 * @param violationYear
	 * @param violationWeek
	 * @param violationMonth
	 * @param violationQuarter
	 * @return
	 */
	@RequestMapping(value = "/exportNoTest.vti", method = RequestMethod.GET)
	public void exportNoTest(TransMsg transMsg ,Integer selectSaleCenterId,Integer violationType,String vin,String plateNumber,String dealerCode,String violationYear,String violationWeek,String violationMonth,String violationQuarter) {
		try{
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			if(null!=selectSaleCenterId && selectSaleCenterId.intValue()>=1){
				getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			}else{
				getRequest().setAttribute("selectedSaleCenterId", orgList.get(0).getId());
			}
			
			getRequest().setAttribute("vin", vin);
			getRequest().setAttribute("plateNumber", plateNumber);
			getRequest().setAttribute("dealerCode", dealerCode);
			if(ValidatorUtil.isBlankOrNull(violationYear)){
				getRequest().setAttribute("violationYear", violationYear=DateTime.getYear());
			}else{
				getRequest().setAttribute("violationYear", violationYear);
			}
			getRequest().setAttribute("violationWeek", violationWeek);
			getRequest().setAttribute("violationMonth", violationMonth);
			getRequest().setAttribute("violationQuarter", violationQuarter);
			getRequest().setAttribute("transMsg", transMsg);
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			siteMenuType(rd);
			
			List<TestNoDriveWeekVO> testNoDriveWeekList=violationService.listTestNoDriveWeek(transMsg, currentCompanyId, currentUserId,selectSaleCenterId,vin,plateNumber, dealerCode, violationYear, violationWeek, violationMonth, violationQuarter,true);
			getRequest().setAttribute("testNoDriveWeekList", testNoDriveWeekList);
			exportNoTest2Excel(testNoDriveWeekList);
		}catch(Exception e){
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("testNoDriveWeekList", null);
		}
	}
	
	/**
	 * 违规明细导出
	 * @param transMsg
	 * @param selectSaleCenterId
	 * @param violationType
	 * @param vin
	 * @param plateNumber
	 * @param dealerCode
	 * @param violationYear
	 * @param violationWeek
	 * @param violationMonth
	 * @param violationQuarter
	 * @return
	 */
	@RequestMapping(value = "/exportViolationDetail.vti", method = RequestMethod.GET)
	public void exportViolationDetail(TransMsg transMsg ,Integer myPageIndex,Integer vehicleId,Integer selectSaleCenterId,Integer violationType,String vin,String plateNumber,String dealerCode,String violationYear,String violationWeek,String violationMonth,String violationQuarter) {
		try {
			getRequest().setAttribute("vehicleId", vehicleId);
			getRequest().setAttribute("selectSaleCenterId", selectSaleCenterId);
			getRequest().setAttribute("vin", vin);
			getRequest().setAttribute("plateNumber", plateNumber);
			getRequest().setAttribute("dealerCode", dealerCode);
			getRequest().setAttribute("violationYear", violationYear);
			getRequest().setAttribute("violationWeek", violationWeek);
			getRequest().setAttribute("violationMonth", violationMonth);
			getRequest().setAttribute("violationQuarter", violationQuarter);
			getRequest().setAttribute("violationType", violationType);
			getRequest().setAttribute("myPageIndex", myPageIndex);
			getRequest().setAttribute("transMsg", transMsg);
			if(1==violationType){
				getRequest().setAttribute("locationMessage", "违规明细> 时间违规 > 违规详情");
			}else{
				getRequest().setAttribute("locationMessage", "违规明细> 路线违规 > 违规详情");
			}
			List<ViolationLineTimeDetailVO> violationDetailList=violationService.viewViolationDetail(transMsg, vehicleId,violationType,violationYear,violationWeek, violationMonth, violationQuarter,true);
			getRequest().setAttribute("violationList", violationDetailList);
			exportViolationDetail2Excel(violationDetailList,violationType);
		}catch(Exception e){
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("violationList", null);
		}
	}
	
	/**
	 * 导出违规统计数据（时间违规、路线违规）到excel
	 * @param violations
	 */
	public  void exportViolation2Excel(List<ViolationLineTimeVO> violationList,Integer violationType) {
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态","车型","VIN","车牌","违规次数"};
		String fileName=null;
		if(violationType==GlobalConstant.TIME_VIOLATION_TYPE){
			fileName="时间违规_"+DateTime.toShortDateTime(new Date());
		}else{
			fileName="线路违规_"+DateTime.toShortDateTime(new Date());
		}
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet(fileName);
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if(null!=violationList && !violationList.isEmpty()){
			for (int i = 0; i < violationList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ViolationLineTimeVO violationLineTime = violationList.get(i);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(violationLineTime.getSaleCenterName());
				cell = row.createCell(1);
				cell.setCellValue(violationLineTime.getProvinceName());
				cell = row.createCell(2);
				cell.setCellValue(violationLineTime.getCityName());
				cell = row.createCell(3);
				cell.setCellValue(violationLineTime.getDealerCode());
				cell = row.createCell(4);
				cell.setCellValue(violationLineTime.getCompanyName());
				cell = row.createCell(5);
				cell.setCellValue(violationLineTime.getIsKeyCityNick());
				cell = row.createCell(6);
				cell.setCellValue(violationLineTime.getDealerTypeNick());
				cell = row.createCell(7);
				cell.setCellValue(violationLineTime.getVehicleStyle());
				cell = row.createCell(8);
				cell.setCellValue(violationLineTime.getVin());
				cell = row.createCell(9);
				cell.setCellValue(violationLineTime.getPlateNumber());
				cell = row.createCell(10);
				cell.setCellValue(violationLineTime.getSumNum().intValue());
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	/**
	 * 导出非活跃试驾数据到excel
	 * @param violations
	 */
	public  void exportNoTest2Excel(List<TestNoDriveWeekVO> testNoDriveWeekList) {
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态","车型","VIN","车牌","连续周数"};
		String fileName=null;
		fileName="非活跃试驾_"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet(fileName);
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if(null!=testNoDriveWeekList && !testNoDriveWeekList.isEmpty()){
			for (int i = 0; i < testNoDriveWeekList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				TestNoDriveWeekVO testNoDriveWeek = testNoDriveWeekList.get(i);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(testNoDriveWeek.getSaleCenterName());
				cell = row.createCell(1);
				cell.setCellValue(testNoDriveWeek.getProvinceName());
				cell = row.createCell(2);
				cell.setCellValue(testNoDriveWeek.getCityName());
				cell = row.createCell(3);
				cell.setCellValue(testNoDriveWeek.getDealerCode());
				cell = row.createCell(4);
				cell.setCellValue(testNoDriveWeek.getCompanyName());
				cell = row.createCell(5);
				cell.setCellValue(testNoDriveWeek.getIsKeyCityNick());
				cell = row.createCell(6);
				cell.setCellValue(testNoDriveWeek.getDealerTypeNick());
				cell = row.createCell(7);
				cell.setCellValue(testNoDriveWeek.getVehicleStyle());
				cell = row.createCell(8);
				cell.setCellValue(testNoDriveWeek.getVin());
				cell = row.createCell(9);
				cell.setCellValue(testNoDriveWeek.getPlateNumber());
				cell = row.createCell(10);
				cell.setCellValue(testNoDriveWeek.getSumNum().intValue());
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}

	/**
	 * 导出违规明细数据到excel
	 * @param violations
	 */
	public  void exportViolationDetail2Excel(List<ViolationLineTimeDetailVO> violationList,Integer violationType) {
		String[] titles = {"车型","VIN","车牌","试驾开始时间","试驾结束时间","违规时间"};
		String fileName=null;
		if(violationType==1){
			fileName="时间违规详情_"+DateTime.toShortDateTime(new Date());
		}else{
			fileName="线路违规详情_"+DateTime.toShortDateTime(new Date());
		}
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet(fileName);
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if(null!=violationList && !violationList.isEmpty()){
			for (int i = 0; i < violationList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ViolationLineTimeDetailVO violationLineTime = violationList.get(i);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(violationLineTime.getVehicleStyle());
				cell = row.createCell(1);
				cell.setCellValue(violationLineTime.getVin());
				cell = row.createCell(2);
				cell.setCellValue(violationLineTime.getPlateNumber());
				cell = row.createCell(3);
				cell.setCellValue(DateTime.toNormalDateTime(violationLineTime.getStartTime()));
				cell = row.createCell(4);
				cell.setCellValue(DateTime.toNormalDateTime(violationLineTime.getEndTime()));
				cell = row.createCell(5);
				cell.setCellValue(DateTime.toNormalDateTime(violationLineTime.getCreationTime()));
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	
	/**
	 *  非活跃试驾明细列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param model
	 * @param transMsg
	 * @param violationVO
	 */
	@RequestMapping(value = "/exportNoTestDetail.vti", method = RequestMethod.GET)
	public void exportNoTestDetail(Model model, TransMsg transMsg, @ModelAttribute("violationVO") ViolationVO violationVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = violationService.listNoTestDetail(transMsg, companyId, currentUserId, violationVO, true);

		testNoDriveWeekExcel((List<TestNoDriveWeekFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出非活跃试驾明细列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param testNoDriveWeekFind
	 */
	public void testNoDriveWeekExcel(List<TestNoDriveWeekFindVO> testNoDriveWeekFind) {
		
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","经销商状态","网络形态","车型","VIN","车牌","年","月","周","开始时间","结束时间"};
		
		String fileName ="";
	
		fileName="非活跃试驾明细列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("非活跃试驾明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (testNoDriveWeekFind != null && testNoDriveWeekFind.size() > 0) {
			for (int i = 0; i < testNoDriveWeekFind.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				TestNoDriveWeekFindVO testNoDriveWeekFindVO = testNoDriveWeekFind.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(testNoDriveWeekFindVO.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getYear());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getMonth());
				cell = row.createCell(++ y);
				cell.setCellValue(testNoDriveWeekFindVO.getWeek());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDate(testNoDriveWeekFindVO.getStartTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDate(testNoDriveWeekFindVO.getEndTime()));
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
}
