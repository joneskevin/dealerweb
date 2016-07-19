package com.ava.baseSystem.service;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;

public interface IUserService {

	/**
	 * 查询用户列表
	 * 
	 * @param transMsg
	 * @param currentUser
	 * @param companyId
	 * @param request
	 * @return ResponseData
	 */
	public ResponseData listUser(TransMsg transMsg, User currentUser, Integer companyId, HttpServletRequest request, boolean isExport);
	
	/**
	 * 查询登录日志列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param dealerCode
	 * @param startTime
	 * @param endTime
	 * @param isExport
	 * @return
	 */
	public ResponseData listUserLog(TransMsg transMsg,  String dealerCode, String startTime, String endTime, boolean isExport);

	/**
	 * 显示用户添加页面
	 * 
	 * @param userAdd
	 * @return ResponseData
	 */
	public ResponseData displayAddUser(User userAdd);
	
	/**
	 *  添加用户
	 * @param userAdd
	 * @param companyId
	 * @param obName
	 * @return
	 */
	public ResponseData addUser(User userAdd, Integer companyId, String obName);
	
	/**
	 * 添加经销商管理员
	 * @param company
	 * @param companyId
	 * @return
	 */
	public ResponseData addAdmin(Company company, Integer companyId);

	
	/**
	 * 显示个人中心
	 * 
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public ResponseData displayEditPersonalInfo(Integer userId, Integer companyId);
	
	/**
	 * 显示编辑用户页面
	 * 
	 * @param userId
	 * @param companyId
	 * @return ResponseData
	 */
	public ResponseData displayEditUser(Integer userId, Integer companyId);

	/**
	 * 修改用户
	 * 
	 * @param userEdit
	 * @param request
	 * @return ResponseData
	 */
	public ResponseData editUser(User userEdit, HttpServletRequest request);
	

	/**
	 * 显示用户权限编辑页面
	 * 
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public ResponseData displayEditUserRightsCode(Integer userId,Integer companyId);

	/**
	 * 编辑用户权限
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	public ResponseData editUserRightsCode(User user, HttpServletRequest request);

	/**
	 * 重发临时密码
	 * 
	 * @param userId
	 * @return
	 */
	public ResponseData reSendTempPassword(Integer userId);

	/**
	 * 删除一个用户,设置用户状态为离职
	 * 
	 * @param userId
	 * @param request
	 * @return ResponseData
	 */
	public ResponseData deleteUser(Integer userId, HttpServletRequest request);

	/**
	 * 用户管理-设置新密码
	 * 
	 * @param user
	 * @return
	 */
	public ResponseData editUserPassword(User user);
	
	/**
	 * 个人中心-修改
	 * 
	 * @param userEdit
	 * @param request
	 * @return ResponseData
	 */
	public ResponseData editPersonalInfo(User userEdit, HttpServletRequest request, Date birthday);

	/**
	 * 个人中心-修改密码
	 * 
	 * @param user
	 * @return
	 */
	public ResponseData editPersonalPassword(User user, String pageOldPassword, String pagePassword);

	/**
	 * 上传用户头像
	 * 
	 * @param user
	 * @param userId
	 * @param originalFilename
	 * @param is
	 * @return
	 */
	public ResponseData uploadPersonalAvatar(User user, Integer userId, String originalFilename, InputStream is);

	/**
	 * 裁剪用户头像
	 * 
	 * @param user
	 * @param userId
	 * @param request
	 * @return
	 */
	public ResponseData cutUserAvatar(User user, Integer userId, HttpServletRequest request);

	/**
	 * 判断email是否存在
	 * 
	 * @param email
	 * @return
	 */
	public boolean emailIsExistence(String email);
	
	public ResponseData viewUser(Integer userId);

}