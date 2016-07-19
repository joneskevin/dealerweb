package com.ava.baseSystem.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.ICompanyService;
import com.ava.baseSystem.service.IOrgService;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.domain.entity.Company;
import com.ava.facade.ICompanyFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping(value="/base/dealer")
public class DealerController extends Base4MvcController {
	
	@Autowired
	private ICompanyService companyService;
	
	@Autowired
	private IOrgService orgService;
	
	@Autowired
	private ICompanyFacade companyFacade;
	
	/**
	 * 查看经销商对应车型的配置方式列表
	 * 
	 * @param transMsg
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/findCarStyle.vti", method = RequestMethod.GET)
	public void findCarStyle() {
//		Integer years = getIntegerParameter("years");
		Integer currentCompanyId = getIntegerParameter("companyId");
		
		ResponseData rd = companyService.displayConfigCarStyle(currentCompanyId);

		writeRd(rd);
	}
	
	/**
	 * 查询经销商信息
	 */
	@RequestMapping(value = "/findDealerInfo.vti", method = RequestMethod.GET)
	public void findDealerInfo() {
		ResponseData rd = new ResponseData(0);
		Integer currentCompanyId = getIntegerParameter("orgId");
		if (currentCompanyId != null) {
			Company company = CacheCompanyManager.getCompany(currentCompanyId);
			if  (company != null) {
			     rd.put("org", company);
			}
			rd.setCode(1);
		} else {
			rd.setCode(-1);
			rd.setMessage("系统错误");
		}
		
		writeRd(rd);
	}
	
	/**
	 * 显示经销商列表
	 * 
	 * @param transMsg
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String listDealer(TransMsg transMsg, @ModelAttribute("company") Company company) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = companyService.listCompany(transMsg, company, companyId, getRequest(), false);
		
		getRequest().setAttribute("companyList", rd.getFirstItem());
		getRequest().setAttribute("company", company);
		
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		initSelectList(companyId);
		
		return "/dealer/dealer/listDealer";
	}
	
	/**
	 * 经销商数据导出Excel
	 * 
	 * @param transMsg
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/exportDealerExcel.vti", method=RequestMethod.GET)
	public void exportDealerExcel(TransMsg transMsg, @ModelAttribute("company") Company company) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		transMsg.setPageSize(Integer.MAX_VALUE);
		ResponseData rd = companyService.listCompany(transMsg, company, companyId, getRequest(), true);
		
		List<Company> dealerList = (List<Company>) rd.getFirstItem();
		writeExcelDealer(dealerList);
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public void writeExcelDealer(List<Company> dealerList) {
		
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态","入网时间","状态","退网时间"};
		String fileName ="";
	
		fileName="经销商信息"+DateTime.toShortDateTime(new Date());
		

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("经销商列表");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (dealerList != null && dealerList.size() > 0) {
		for (int i = 0; i < dealerList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Company company = (Company) dealerList.get(i);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(company.getParentName());
			cell = row.createCell(1);
			cell.setCellValue(company.getRegionProvinceId_Nick());
			cell = row.createCell(2);
			cell.setCellValue(company.getRegionCityId_Nick());
			cell = row.createCell(3);
			cell.setCellValue(company.getDealerCode());
			cell = row.createCell(4);
			cell.setCellValue(company.getCnName());
			cell = row.createCell(5);
			cell.setCellValue(company.getIsKeyCity_Nick());
			cell = row.createCell(6);
			cell.setCellValue(company.getDealerType_Nick());
			cell = row.createCell(7);
			cell.setCellValue(DateTime.toNormalDate(company.getCreationTime()));
			cell = row.createCell(8);
			if (company.getDeletionFlag() == 0) {
				cell.setCellValue("正常");
			} else if (company.getDeletionFlag() == 1) {
				cell.setCellValue("退网");
			} else {
				cell.setCellValue("高危");
			}
			cell = row.createCell(9);
			cell.setCellValue(DateTime.toNormalDate(company.getDeletionTime()));
			cell = row.createCell(10);
		}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	
	/**
	 * 显示新增经销商的页面
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddDealer(@ModelAttribute("companyAdd") Company company) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = companyService.displayAddCompany(company);
		getRequest().setAttribute("companyAdd", rd.get("companyAdd"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(companyId);
		
		return "/dealer/dealer/addDealer";
	}
	
	/**
	 * 显示编辑经销商页面
	 * 
	 * @param frmCompany
	 * @return
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditDealer(@ModelAttribute("frmCompany") Company frmCompany) {
		Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer companyId = frmCompany.getId();
		if (frmCompany.getId() == null) {
			companyId = getIntegerParameter("companyId");
		}
		ResponseData rd = companyService.displayEditCompany(companyId);
		
		Company company = (Company) rd.get("company");
		Integer parentCompanyId = company.getCompanyId();
		// 为了保持当前修改的状态
		MyBeanUtils.copyPropertiesContainsDate(company, frmCompany);
		company.setParentCompanyName(CacheCompanyManager.getName(parentCompanyId));
		getRequest().setAttribute("company", company);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(currentCompanyId);
		
		return "/dealer/dealer/editDealer";
	}
	
	
	/**
	 * 新增经销商
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addDealer(@ModelAttribute("companyAdd") Company company) throws IOException {
		if (!check4AddDealer(company)) {
			return displayAddDealer(company);
		}
		
		ResponseData rd = companyFacade.addDealer(company);
		if (rd.getCode() == 1) {
			
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddDealer(company);
	}
	
	/**
	 * 编辑经销商
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editDealer(@ModelAttribute("dealerEdit") Company company) {
		if (!check4EditDealer(company)) {
			return displayEditDealer(company);
		}
		
		ResponseData rd  = companyService.editCompany(company);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditDealer(company);
	}
	
	/**
	 * 显示设置经销商位置界面
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/setDealerCurrentPositionView.vti", method=RequestMethod.GET)
	public String setDealerPointView(@ModelAttribute("frmCompany") Company frmCompany) {
		if(null!=frmCompany && null!=frmCompany.getCompanyId()){
			getRequest().setAttribute("companyId", frmCompany.getCompanyId());
			Company company=companyService.getOrgById(frmCompany.getCompanyId());
			if(null!=company && null!=company.getBaiduLng() && null!=company.getBaiduLat() && company.getBaiduLng()>0 && company.getBaiduLat()>0){
				getRequest().setAttribute("viewLng", company.getBaiduLng());
				getRequest().setAttribute("viewLat", company.getBaiduLat());
			}else{
				getRequest().setAttribute("viewLng", "");
				getRequest().setAttribute("viewLat", "");
			}
		}else{
			getRequest().setAttribute("companyId", -1000);
			getRequest().setAttribute("viewLng", "");
			getRequest().setAttribute("viewLat", "");
		}
		return "/dealer/dealer/setDealerCurrentPosition";
	}
	
	/**
	 * 设置经销商位置
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/saveDealerCurrentPosition.vti", method=RequestMethod.POST)
	public String saveDealerCurrentPosition(@ModelAttribute("frmCompany") Company frmCompany,String lng,String lat) {
		if(null!=frmCompany && null!=frmCompany.getCompanyId()){
			Company company=companyService.getOrgById(frmCompany.getCompanyId());
			if(null!=company){
				if(null!=lng && null!=lat && !"".equals(lng.trim()) && !"".equals(lat)){
					company.setBaiduLng(Double.parseDouble(lng));
					company.setBaiduLat(Double.parseDouble(lat));
					companyService.editCompany(company);
				}
			}
		}
		return setDealerPointView(frmCompany);
		//return "/pub/jsPages/closeDialogAndReloadParent";
	}
	
	/**
	 * 显示经销商配置车型页面
	 * 
	 * @param frmCompany
	 * @return
	 */
	@RequestMapping(value="/displayConfigCarStyle.vti", method=RequestMethod.GET)
	public String displayConfigCarStyle(@ModelAttribute("frmCompany") Company frmCompany) {
		Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer companyId = frmCompany.getId();
		if (frmCompany.getId() == null) {
			companyId = getIntegerParameter("companyId");
		}
		
		ResponseData rd = companyService.displayConfigCarStyle(companyId);
		getRequest().setAttribute("company", rd.get("company"));
		getRequest().setAttribute("carStyleVOList", rd.get("carStyleVOList"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(currentCompanyId);
		
		return "/dealer/dealer/configCarStyle";
	}
	
	/**
	 * 配置车型
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/configCarStyle.vti", method=RequestMethod.POST)
	public String configCarStyle(@ModelAttribute("dealerEdit") Company company) {
		
		ResponseData rd  = companyFacade.configCarStyle(company, getRequest());
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayConfigCarStyle(company);
	}
	
	 public void initSelectList(Integer companyId) {
		  Integer userId = SessionManager.getCurrentUserId(getRequest());
		  Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
		  List orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, currentCompanyId);
		  getRequest().setAttribute("orgList", orgList);
		  
		  List parentOrgList = CacheOrgManager.getOrgTree4Select(userId, companyId, GlobalConstant.OPERATIONS_ADMIN_ROLE_ID);
		  getRequest().setAttribute("parentOrgList", parentOrgList);
		  
		  List inOfficeList = SelectOptionResource.inOfficeOptions();
		  getRequest().setAttribute("inOfficeList", inOfficeList);
		  
		  List<Object> dealerStatusList = SelectOptionResource.dealerStatusOptions();
		  getRequest().setAttribute("dealerStatusList", dealerStatusList);
		  
		  List inPositionList = SelectOptionResource.inPositionOptions();
		  getRequest().setAttribute("inPositionList", inPositionList);
		  
		  List isKeyCityList = SelectOptionResource.isKeyCityOptions();
		  getRequest().setAttribute("isKeyCityList", isKeyCityList);
		  
		  List dealerTypeList = SelectOptionResource.dealerTypeOptions();
		  getRequest().setAttribute("dealerTypeList", dealerTypeList);
		  
		  List workTimeList = SelectOptionResource.workTimeOptions();
		  getRequest().setAttribute("workTimeList", workTimeList);
		  
	}
	 
		/**
		 * 查看用户详细信息
		 * 
		 * @param companyId
		 * @return 
		 */
		@RequestMapping(value="/view.vti", method=RequestMethod.GET)
		public String viewDealer(@RequestParam("companyId") Integer companyId) {
			ResponseData rd = companyService.viewCompany(companyId);
			getRequest().setAttribute("company", rd.get("company"));

			return "/dealer/dealer/viewDealer";
			
		}
		
		/**
		 * 删除经销商
		 * 
		 * @param userId
		 * @return
		 */
		@RequestMapping(value="/delete.vti", method=RequestMethod.GET)
		public void deleteDealer(@RequestParam("companyId") Integer companyId) {
			
			ResponseData rd = companyService.deleteCompany(companyId);
			if (rd.getCode() == 1) {
				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
						GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

			} else {
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
						GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			}
			
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			writeRd(rd);
		}
		
		protected boolean check4AddDealer(Company company) {

			Integer regionProvinceId = company.getRegionProvinceId();
			if (regionProvinceId < 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "省份必须选择！");
				return false;
			}
			
			Integer regionCityId = company.getRegionCityId();
			if (regionCityId < 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "城市必须选择！");
				return false;
			}
			
			String dealerCode = company.getDealerCode();
			if (!TypeConverter.sizeLagerZero(dealerCode)) {	
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "网络代码必须填写！");
				return false;
				
			} else if (!ValidatorUtil.isNumeric(dealerCode)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "网络代码格式不正确！");
				return false;
			}
			
			String cnName = company.getCnName();
			if (!TypeConverter.sizeLagerZero(cnName)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "经销商名称必须填写！");
				return false;
			}
			
			Integer isKeyCity = company.getIsKeyCity();
			if (!TypeConverter.sizeLagerZero(isKeyCity)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "经销商状态必须选择！");
				return false;
			}
			
			Integer dealerType = company.getDealerType();
			if (!TypeConverter.sizeLagerZero(dealerType)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "网络形态必须选择！");
				return false;
			}
			
			String email = company.getEmail();
			if (!TypeConverter.sizeLagerZero(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱不能为空！");
				return false;
			} 
			
			if (!ValidatorUtil.isEmail(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "Email格式不正确");
				return false;
			} else if (orgService.emailIsExistence(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "email已存在！");
				return false;
			}

			return true;
		}
		
		
		protected boolean check4EditDealer(Company company) {
			Integer regionProvinceId = company.getRegionProvinceId();
			if (regionProvinceId < 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "省份必须选择！");
				return false;
			}
			
			Integer regionCityId = company.getRegionCityId();
			if (regionCityId < 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "城市必须选择！");
				return false;
			}
			
			String dealerCode = company.getDealerCode();
			if (!TypeConverter.sizeLagerZero(dealerCode)) {	
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "网络代码必须填写！");
				return false;
				
			} else if (!ValidatorUtil.isNumeric(dealerCode)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "网络代码格式不正确！");
				return false;
			}
			
			String cnName = company.getCnName();
			if (!TypeConverter.sizeLagerZero(cnName)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "经销商名称必须填写！");
				return false;
			}
			
			Integer isKeyCity = company.getIsKeyCity();
			if (!TypeConverter.sizeLagerZero(isKeyCity)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "经销商状态必须选择！");
				return false;
			}
			
			String email = company.getEmail();
			if (!TypeConverter.sizeLagerZero(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱不能为空！");
				return false;
			} 
			
			if (!ValidatorUtil.isEmail(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "Email格式不正确");
				return false;
			} 
			
			return true;
		}
		
		/**
		 * 删除经销商
		 * 
		 * @param userId
		 * @return
		 */
		@RequestMapping(value="/retreat.vti", method=RequestMethod.GET)
		@ResponseBody
		public ResponseData retreat(@RequestParam("companyId") Integer companyId) {
			return companyService.retreatCompany(companyId);
		}
		
		
		/**
		 * 恢复经销商
		 * 
		 * @param userId
		 * @return
		 */
		@RequestMapping(value="/rollbackRetreat.vti", method=RequestMethod.GET)
		@ResponseBody
		public ResponseData rollbackRetreat(@RequestParam("companyId") Integer companyId) {
			return companyService.rollbackRetreatCompany(companyId);
		}
}
