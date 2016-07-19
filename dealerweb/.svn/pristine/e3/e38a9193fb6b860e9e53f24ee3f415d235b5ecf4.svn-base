package com.ava.baseSystem.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.User;

public interface IUserRegisterLoginService  {
	
	public boolean obNameIsExistence(String obName);
	/** 验证Email是否存在 */
	public boolean emailIsExistence(String firstEmail);
    
	/** public int register(User frmUser, String cnName, String adminPassword, String adminFingerTemplate);*/
    
    /**用户取回密码操作，生成一个临时重置密码，暂时不修改用户旧密码 */
	public ResponseData fetchPassword(String email);
	
	/**用户重新激活 */
	public ResponseData reActivation(String email);
	
	/**显示首次登录编辑密码页面*/
	public ResponseData displayeditFirstLoginPassword(User frmUser);
	
	/**用户首次登录系统修改密码*/
	public ResponseData editFirstLoginPassword(User formUser, String pagePassword);
    	
	/**用户激活新密码操作，用临时重置密码替换用户旧密码，同时清空临时密码防止再次激活*/
	public ResponseData activeTempPassword(String loginName, String tempPassword);
    
	/**用户登录*/
	public ResponseData login(String loginName, String password, String isRememberMe, HttpServletRequest request, HttpServletResponse response);
	
	/**用户注销*/
	public int logout(HttpServletRequest request, HttpServletResponse response);
}
