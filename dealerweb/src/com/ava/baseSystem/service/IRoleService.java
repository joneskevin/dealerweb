package com.ava.baseSystem.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Role;
import com.ava.domain.entity.User;

public interface IRoleService {

	public List<Role> listUserRole();
	
	public Role findUserRole(int roleId);
	
	/**
	 * 查询角色列表
	 * 
	 * @param transMsg
	 * @param currentRole
	 * @param request
	 * @return ResponseData
	 */
	public ResponseData listRole(TransMsg transMsg, Role currentRole, HttpServletRequest request);

	/**
	 * 显示角色添加页面
	 * 
	 * @param RoleAdd
	 * @return ResponseData
	 */
	public ResponseData displayAddRole(Role roleAdd);
	
	/**
	 * 添加角色
	 * 
	 * @param roleAdd
	 * @return roleIds
	 */
	public ResponseData addRole(Role roleAdd);
	
	/**
	 * 显示编辑角色页面
	 * 
	 * @param roleId
	 * @return ResponseData
	 */
	public ResponseData displayEditRole(Integer roleId);

	/**
	 * 修改角色
	 * 
	 * @param roleEdit
	 * @return ResponseData
	 */
	public ResponseData editRole(Role roleEdit);
	

	/**
	 * 显示角色权限编辑页面
	 * 
	 * @param roleId
	 * @return
	 */
	public ResponseData displayEditRoleRightsCode(Integer roleId);

	/**
	 * 编辑角色权限
	 * 
	 * @param role
	 * @param request
	 * @return
	 */
	public ResponseData editRoleRightsCode(Role role, HttpServletRequest request);
	
	/**
	 * 删除一个角色
	 * 
	 * @param roleId
	 * @return ResponseData
	 */
	public ResponseData deleteRole(Integer roleId);
	
	/**
	 * 权限转换处理，根据user得到对应的权限
	 * @param user
	 */
	public void processRightCode(User user);
	

}
