package com.ava.baseSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IRoleService;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.domain.entity.Role;
import com.ava.resource.GlobalConstant;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;

@Controller
@RequestMapping(value="/base/role")
public class RoleController extends Base4MvcController {
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUserMenuService userMenuService;
	
	/**
	 * 显示角色列表
	 * 
	 * @param transMsg
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String listRole(TransMsg transMsg, @ModelAttribute("role") Role role) {
		ResponseData rd = roleService.listRole(transMsg, role, getRequest());
		
		getRequest().setAttribute("roleList", rd.getFirstItem());
		getRequest().setAttribute("role", role);
		getRequest().setAttribute("transMsg", transMsg);
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		
		return "/dealer/role/listRole";
	}
	

	/**
	 * 显示新增角色的页面
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddRole(@ModelAttribute("roleAdd") Role role) {
		ResponseData rd = roleService.displayAddRole(role);
		getRequest().setAttribute("roleAdd", rd.get("roleAdd"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/role/addRole";
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addRole(@ModelAttribute("roleAdd") Role role) {
		if (!checkaddRole(role)) {
			return displayAddRole(role);
		}
		
		ResponseData rd = roleService.addRole(role);
		if (rd.getCode() == 1) {
			
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddRole(role);
	}
	
	
	/**
	 * 管理员编辑角色页面
	 * 
	 * @param role
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditRole(@ModelAttribute("frmRole") Role frmRole) {
		Integer roleId = frmRole.getId();
		if (frmRole.getId() == null) {
			roleId = getIntegerParameter("roleId");
		}
		ResponseData rd = roleService.displayEditRole(roleId);
		Role role = (Role) rd.get("role");
		// 为了保持当前修改的状态
		MyBeanUtils.copyPropertiesContainsDate(role, frmRole);
		getRequest().setAttribute("role", role);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/role/editRole";
	}

	/**
	 * 管理员编辑角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editRole(@ModelAttribute("roleEdit") Role role) {
		if (!check2editRole(role)) {
			return displayEditRole(role);
		}
		
		ResponseData rd  = roleService.editRole(role);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditRole(role);
	}
	
	/**
	 * 显示角色权限编辑页面
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/displayEditRoleRightsCode.vti", method=RequestMethod.GET)
	public String displayEditRoleRightsCode(@RequestParam("roleId") Integer roleId) {
		ResponseData rd  = roleService.displayEditRoleRightsCode(roleId);
		getRequest().setAttribute("role", rd.get("role"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		getRequest().setAttribute("dhtmlXtreeXML", rd.get("dhtmlXtreeXML"));
		
		if (rd.getCode() == -1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			return listRole(transMsg, new Role());
		}
		
		return "/dealer/role/editRoleRightsCode";
		
	}

	/**
	 * 编辑角色权限
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/editRoleRightsCode.vti", method=RequestMethod.POST)
	public String editRoleRightsCode(@ModelAttribute("role") Role role) {
		ResponseData rd = roleService.editRoleRightsCode(role, getRequest());
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, "角色权限分配成功！");
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			return listRole(transMsg, new Role());
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, "角色权限分配失败！");
		}

		return displayEditRoleRightsCode(role.getId());
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/delete.vti", method=RequestMethod.GET)
	public void deleteRole(@RequestParam("roleId") Integer roleId) {
		ResponseData rd = roleService.deleteRole(roleId);
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
	 * 添加角色表单验证
	 * 
	 * @param role
	 * @return
	 */
	protected boolean checkaddRole(Role role) {
		String name = role.getName();
		if (!TypeConverter.sizeLagerZero(name)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "角色名称必须填写！");
			return false;
		}
		return true;

	}


	/**
	 * 管理员编辑角色表单验证
	 * 
	 * @param role
	 * @return
	 */
	protected boolean check2editRole(Role role) {
		String name = role.getName();
		if (!TypeConverter.sizeLagerZero(name)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "名称必须填写！");
			return false;
		}
		return true;
	}

}
