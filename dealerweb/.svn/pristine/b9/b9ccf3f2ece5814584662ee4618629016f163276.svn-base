package com.ava.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ava.api.vo.UserApiVo;
import com.ava.base.domain.ResponseData;


public interface IUserApiService {
	
	public ResponseData login(HttpServletRequest request, HttpServletResponse response, UserApiVo user);
	
	/**
	 * 发送临时密码
	 * @param userId
	 * @param tempPassword
	 * @param toMobile 可以为空
	 * @param toEmail 可以为空
	 * @return
	 */
	public int sendTempPassowrd(Integer userId, String tempPassword, String toMobile, String toEmail);
	
	/**
	 * 发送账户密码给经销商
	 * @param userId
	 * @param password
	 * @param toMobile 可以为空
	 * @param toEmail 可以为空
	 * @return
	 */
	public int sendPassowrd(Integer userId, String password, String toEmail);
	
	/**
	 * 发送账户密码给大众总部
	 * @param userId
	 * @param password
	 * @param toMobile 可以为空
	 * @param toEmail 可以为空
	 * @return
	 */
	public int sendPasswordToHeadquarters(Integer userId, String password);
	
	/**
	 * 重发临时密码
	 * @param userId
	 * @param password
	 * @param toMobile 可以为空
	 * @param toEmail 可以为空
	 * @return
	 */
	public int reSendTempPassowrd(Integer userId, String tempPassword, String toMobile, String toEmail);
	
	public UserApiVo getUser(Integer userId);
	
	public List<UserApiVo> findUser(Integer orgId);
	
}
