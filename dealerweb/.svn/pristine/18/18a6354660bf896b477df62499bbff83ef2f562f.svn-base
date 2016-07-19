package com.ava.baseSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IOrgService;
import com.ava.baseSystem.service.IUserRegisterLoginService;
import com.ava.domain.entity.Org;
import com.ava.facade.IOrgFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.util.MyStringUtils;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;

@Controller
@RequestMapping("/base/org")
public class OrgController extends Base4MvcController {
	@Autowired
	private IOrgService service;
	
	@Autowired
	IUserRegisterLoginService userRegisterLoginService;
	
	@Autowired
	IOrgFacade orgFacade;

	/**
	 * 显示组织树
	 * 
	 * @return String
	 */
	@RequestMapping(value="/displayOrgTree.vti", method=RequestMethod.GET)
	public String displayOrgTree() {
		Integer orgId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = service.displayOrgTree(orgId);
		getRequest().setAttribute("dhtmlXtreeXML", rd.getFirstItem().toString());
		
		return "/base/baseSystem/orgnization/orgTree"; 
	}

	/**
	 * 显示添加组织页面
	 * @return String
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddOrg(@ModelAttribute("orgAdd") Org org) {
		Integer orgId = SessionManager.getCurrentCompanyId(getRequest());
		Integer parentId = getIntegerParameter("currentNodeId");
		if(parentId == null || parentId.intValue() == 0) {
			parentId = getIntegerParameter("parentId");
		}
		ResponseData rd = service.displayAddOrg(org, orgId, parentId);
		
		getRequest().setAttribute("orgAdd", rd.get("org"));
		
		List orgTypes = SelectOptionResource.orgTypeOptions();
		getRequest().setAttribute("orgTypes", orgTypes);
		
		List functionTypes = SelectOptionResource.functionTypeOptions();
		getRequest().setAttribute("functionTypes", functionTypes);
		
		getRequest().setAttribute("obName", getStringParameter("obName"));
		getRequest().setAttribute("loginName", getStringParameter("loginName"));
		getRequest().setAttribute("adminEmail", getStringParameter("adminEmail"));
		
		if (rd.getCode() == 1) {
			return "/base/baseSystem/orgnization/addOrg";

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayOrgTree();
	}

	/**
	 * 添加组织
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addOrg(@ModelAttribute("org") Org org) {

		if (!check4AddOrg(org)) {
			return displayAddOrg(org);
		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		String obName = getStringParameter("obName");
		String adminEmail = getStringParameter("adminEmail");
		
		ResponseData rd = orgFacade.addOrg(org, companyId, obName, adminEmail);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayOrgTree();

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		return displayAddOrg(org);

	}

	/**
	 * 显示编辑组织页面
	 * @param orgId
	 * @return String
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditOrg(@RequestParam("currentNodeId") Integer orgId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if(orgId == null || orgId.intValue() <= 0){
			orgId = getIntegerParameter("Id");
		}
		
		ResponseData rd =service.displayEditOrg(companyId, orgId);
		getRequest().setAttribute("org", rd.getFirstItem());
		getRequest().setAttribute("orgEdit", rd.getFirstItem());
		
		List functionTypes = SelectOptionResource.functionTypeOptions();
		getRequest().setAttribute("functionTypes", functionTypes);
		
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/base/baseSystem/orgnization/editOrg";

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		return displayOrgTree();
	}

	/**
	 * 编辑组织
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editOrg(@ModelAttribute("orgEdit") Org org) {
		if (!check4EditOrg(org)) {
			return displayEditOrg(org.getId());
		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = service.editOrg(org, companyId);
		
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		return displayEditOrg(org.getId());
	}

	/**
	 * 删除组织
	 * 
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value="/delete.vti", method=RequestMethod.GET)
	public void deleteOrg(@RequestParam("currentNodeId") Integer orgId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		String userLoginName = SessionManager.getCurrentUserLoginName(getRequest());
		String obName = userLoginName.substring(userLoginName.lastIndexOf("@") + 1, userLoginName.length());
		
		ResponseData rd = service.deleteOrg(companyId, orgId, obName);

		writeRd(rd);
	}

	/**
	 * 添加部门/公司表单验证
	 * 
	 * @param form1
	 * @param request
	 * @return
	 */
	protected boolean check4AddOrg(Org org) {
		int orgType = org.getOrgType();
		if (orgType <= 0) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "类型必须选择！");
			return false;
		}
		
/*		int functionType = org.getFunctionType();
		if (functionType <= 0) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "职能类型必须选择！");
			return false;
		}*/

		String orgName = org.getName();
		if (MyStringUtils.isBlank(orgName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "中文名称必须填写！");
			return false;
		}

		if (GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY == orgType) {// 公司类型
			String obName = getStringParameter("obName");
			if (MyStringUtils.isBlank(obName)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "英文简称必须填写！");
				return false;
			} else if (!ValidatorUtil.isAlphabet(obName)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "英文简称格式不正确！");
				return false;
			} else if (userRegisterLoginService.obNameIsExistence(obName)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
						GlobalConstant.MSG_RESULT_CONTENT, "英文简称已存在！");
				return false;
			}

			String adminEmail = getStringParameter("adminEmail");
			if (!TypeConverter.sizeLagerZero(adminEmail)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "管理员email必须填写");
				return false;
			} else if (!ValidatorUtil.isEmail(adminEmail)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "管理员email格式不正确！");
				return false;
			} else if (userRegisterLoginService.emailIsExistence(adminEmail)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
						GlobalConstant.MSG_RESULT_CONTENT, "管理员email已存在！");
				return false;
			}

		}
		return true;
	}
	
	protected boolean check4EditOrg(Org org) {
/*		int functionType = org.getFunctionType();
		if (functionType <= 0) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "职能类型必须选择！");
			return false;
		}*/

		String orgName = org.getName();
		if (MyStringUtils.isBlank(orgName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "中文名称必须填写！");
			return false;
		}
		return true;
	}

}
