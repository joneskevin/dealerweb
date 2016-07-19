package com.ava.baseSystem.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.ICompanyService;
import com.ava.baseSystem.service.IOrgService;
import com.ava.baseSystem.service.IUserService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;

@Controller
@RequestMapping("/base/company")
public class CompanyController extends Base4MvcController {

	@Autowired
	private ICompanyService service;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IOrgService orgService;
	

	/**
	 * 显示修改组织信息页面
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditOrgInfo(@ModelAttribute("frmCompany") Company Company) {
		User currentUser = SessionManager.getCurrentUser(getRequest());
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if (currentUser.getUserRight().isRightOrgMangementAll()) {
			ResponseData rd = service.displayEditQrgInfo(companyId);
			
			Map data = rd.getData();
			getRequest().setAttribute("company", data.get("company"));
			
			Boolean orgStructureNotBuild = (Boolean) data.get("orgStructureNotBuild");
			Boolean orgInfoNotBuild = (Boolean) data.get("orgInfoNotBuild");
			
			if (orgStructureNotBuild) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "请把组织信息填写完整！");
			}
			if (orgInfoNotBuild) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT,
						"还没有添加用户，请到用户管理添加用户！");
			}
			return "/base/baseSystem/orgnization/editOrgInfo";

		} else {
			return "/pub/noPermission";
		}

	}
	
	
	/**显示注册经销商页面*/
	@RequestMapping(value = "/displayRegister.vti", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute("user") User user) {
		Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rdUser = userService.displayAddUser(user);
		getRequest().setAttribute("userAdd", rdUser.get("userAdd"));
		getRequest().setAttribute("companyAdd", new Company());
		initSelectList(currentCompanyId);
		return "base/baseSystem/registerLogin/registerDealer";
	}
	
	 public void initSelectList(Integer cmpanyId) {
		  List dealerTypeList = SelectOptionResource.dealerTypeOptions();
		  getRequest().setAttribute("dealerTypeList", dealerTypeList);
		  
		  Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		  if(companyId==null)
		  {
			  companyId =1;
		  }
		  List departmentList = CacheOrgManager.getChildrenOrgTree4Select(null, companyId);
		  getRequest().setAttribute("departmentList", departmentList);
	}
	/**
	 * 新增公司（经销商 ）by JF
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String orgAdd(@ModelAttribute("companyAdd") Company company,
			@ModelAttribute("userAdd") User user) throws IOException {
						
		if (!checkFormForCompanyInfo(company,user)) {
			return displayRegister(new User());
			//"/base/baseSystem/registerLogin/registerDealer"; 
		}else{
			ResponseData rd  =service.addDealer(company);
//			ResponseData rd = service.addCompany(company, user);
			
			if(rd.getCode()== 1)
			{
				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
						GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			}else {
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
						GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
				}
			}
		
		return displayRegister(new User());
	}
	/**
	 * 我的公司
	 */
	@RequestMapping(value="/view.vti", method=RequestMethod.GET)
	public String viewOrgInfo() {
		User currentUser = SessionManager.getCurrentUser(getRequest());
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = service.displayEditQrgInfo(companyId);
		
		getRequest().setAttribute("company", rd.get("company"));
		
		return "/base/baseSystem/orgnization/viewOrgInfo";

	}

	/**
	 * 修改组织信息
	 * 
	 * @param Company
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editOrgInfo(@ModelAttribute("companyEdit") Company company) {
			if (!checkFormForOrgInfo(company)) {
				;
			} else {
				ResponseData rd = service.editOrgInfo(company);
				if (rd.getCode() == 1) {
					message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
							GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
				}else {
					message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
							GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
				}
				
			}
			
			return displayEditOrgInfo(new Company());
			
	}

	
	protected boolean checkFormForCompanyInfo(Company company,User user) {
		Integer regionProvinceId = company.getRegionProvinceId();
	
		
		String dealerCode = company.getDealerCode();
		if (!TypeConverter.sizeLagerZero(dealerCode)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "网络形态必须填写！");
			return false;
			
		} else if (!ValidatorUtil.isNumeric(dealerCode)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "网络形态格式不正确！");
			return false;
		}
		
		String cnName = company.getCnName();
		if (!TypeConverter.sizeLagerZero(cnName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "经销商名称必须填写！");
			return false;
		}
		
		String obName = company.getObName();
		if (!TypeConverter.sizeLagerZero(obName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "英文简称必须填写！");
			return false;
		}
		
//		Integer isKeyCity = company.getIsKeyCity();
//		if (!TypeConverter.sizeLagerZero(isKeyCity)) {
//			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
//					GlobalConstant.MSG_RESULT_CONTENT, "经销商状态必须选择！");
//			return false;
//		}
	
		
		String email = company.getEmail();
			if (!TypeConverter.sizeLagerZero(email)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱必须填写！");
			return false;
			} else if (!ValidatorUtil.isEmail(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "Email格式不正确");
				return false;
			} else if (orgService.emailIsExistence(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "email已存在！");
				return false;
		}
	
		if (dealerCode.length()>8) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "网络形态小于8位！");
			return false;
		}
		Integer dealerType = company.getDealerType();
		if (!TypeConverter.sizeLagerZero(dealerType)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "网络形态必须填写！");
			return false;
		}

		return true;
	}
	
	protected boolean checkFormForOrgInfo(Company company) {

		String cnName = company.getCnName();
		if (!TypeConverter.sizeLagerZero(cnName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "公司全称必须填写！");
			return false;
		}
		String name = company.getName();
		if (!TypeConverter.sizeLagerZero(name)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "中文简称必须填写！");
			return false;
		}
		
		if(cnName.equals(name)){
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "公司全称不能和中文简称相同！");
			return false;
		}
		
		String email = company.getEmail();
		if (TypeConverter.sizeLagerZero(email)) {
			if (!ValidatorUtil.isEmail(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "email格式不正确");
				return false;
			}
		}
		Integer orgType = company.getOrgType();
		if (orgType != null && orgType.intValue() == -1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "组织类型必须选择！");
			return false;
		}
		return true;
	}

	/** 上传logo */
//	public ActionForward uploadOrgLogo(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//			ICompanyService service = (ICompanyService) getBean("companyService");
//			CompanyForm form1 = (CompanyForm) form;
//			ResponseData rd = service.uploadOrgLogo(form1, request, response);
//			if (rd.getCode() == 1) {
//				return mapping.findForward("uploadOrgLogo");
//			} else {
//				message(rd.getMessage());
//			}
//			return mapping.findForward("alertAndHistoryBack");
//
//	}

	/** 裁剪logo */
//	public ActionForward cutOrgLogo(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//			ICompanyService service = (ICompanyService) getBean("companyService");
//			CompanyForm form1 = (CompanyForm) form;
//			int result = service.cutOrgLogo(form1, request, response);
//			if (result == 1) {
//				message("Logo更新成功！");
//			}
//			return displayOrgInfo(mapping, form1, request, response);
//
//	}
}
