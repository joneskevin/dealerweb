package com.ava.facade.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IRoleService;
import com.ava.baseSystem.service.IUserRegisterLoginService;
import com.ava.domain.entity.User;
import com.ava.facade.IUserRegisterLoginFacade;
import com.ava.resource.SessionManager;

@Service
public class UserRegisterLoginFacade implements IUserRegisterLoginFacade {
	
	@Autowired
	private IUserRegisterLoginService userRegisterLoginService;
	
	@Autowired
	private IRoleService roleService;

	public ResponseData login(String loginName, String password, String isRememberMe, 
			HttpServletRequest request, HttpServletResponse response){
		ResponseData rd = new ResponseData(0);
		
		rd = userRegisterLoginService.login(loginName, password, isRememberMe, request, response);
		
		//给登录用户菜单权限初始化
		if (rd.getCode() == 1) {
			User user = SessionManager.getCurrentUser(request);
			roleService.processRightCode(user);
		}
		return rd;
	}	

}
