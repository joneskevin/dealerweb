package com.ava.dealer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.ava.dao.IUserTokenDao;
import com.ava.dealer.service.IUserTokenService;
import com.ava.domain.entity.UserToken;
import com.ava.domain.vo.UserTokenVO;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.MyRandomUtils;

@Service
public class UserTokenService implements IUserTokenService{

	@Autowired
	private IUserTokenDao userTokenDao;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public boolean varifyTokenExists(String userName,String userToken){
		UserTokenVO userTokenVO=getToken(userName,userToken);
		return checkUserTokenExists(userTokenVO);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public boolean verifyUserToken(String userName,String userToken){
		UserTokenVO userTokenVO=getToken(userName,userToken);
		return checkUserTokenValid(userTokenVO);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public boolean verifyUserToken(String userName,String userToken,Integer userType){
		UserTokenVO userTokenVO=getToken(userName,userToken,userType);
		return checkUserTokenValid(userTokenVO);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public String refreshUserToken(String userName,String oldToken){
		UserTokenVO userTokenVO=getToken(userName,oldToken);
		
		if(!checkUserTokenExists(userTokenVO))
			return null;
		
		String newToken=MyRandomUtils.getUUID();
		userTokenVO.setToken(newToken);
		userTokenVO.setInvalidTime(DateTime.getOffsetMonthDate(new Date(),3));
		
		updateToken(userTokenVO);
		
		return newToken;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public UserTokenVO get(int id) {
		UserToken token =  userTokenDao.get(id);
		if(null==token)
			return null;
		UserTokenVO userTokenVO=new UserTokenVO();
		MyBeanUtils.copyPropertiesContainsDate(userTokenVO, token);
		return userTokenVO;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private UserTokenVO getToken(String userName) {
		if(null==userName || "".equals(userName.trim()))
			return null;
		Map<String,String> parameters=new HashMap<String,String>();
		parameters.put("userName", userName);
		UserToken token = userTokenDao.getToken(parameters);
		if(null==token)
			return null;
		UserTokenVO userTokenVO=new UserTokenVO();
		MyBeanUtils.copyPropertiesContainsDate(userTokenVO, token);
		return userTokenVO;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public UserTokenVO getToken(String userName,String token) {
		if(null==userName || "".equals(userName.trim()))
			return null;
		if(null==token || "".equals(token.trim()))
			return null;
		Map<String,String> parameters=new HashMap<String,String>();
		parameters.put("userName", userName);
		parameters.put("token", token);
		UserToken userToken = userTokenDao.getToken(parameters);
		if(null==userToken)
			return null;
		UserTokenVO userTokenVO=new UserTokenVO();
		MyBeanUtils.copyPropertiesContainsDate(userTokenVO, userToken);
		return userTokenVO;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public UserTokenVO getToken(String userName, String token,Integer userType) {
		if(null==userName || "".equals(userName.trim()))
			return null;
		if(null==token || "".equals(token.trim()))
			return null;
		Map<String,Object> parameters=new HashMap<String,Object>();
		parameters.put("userName", userName);
		parameters.put("token", token);
		parameters.put("userType", userType);
		UserToken userToken = userTokenDao.getToken(parameters);
		UserTokenVO userTokenVO=new UserTokenVO();
		MyBeanUtils.copyPropertiesContainsDate(userTokenVO, userToken);
		return userTokenVO;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int add(String userName) {
		UserTokenVO userTokenVO=getToken(userName);
		if(null==userTokenVO){
			Date date=new Date();
			UserToken userToken=new UserToken();
			userToken.setUserName(userName);
			userToken.setUserType(2);
			userToken.setCreateTime(date);
			userToken.setToken(MyRandomUtils.getUUID());
			userToken.setInvalidTime(DateTime.getOffsetMonthDate(date,3));
			userTokenDao.add(userToken);
			return 0;
		}
		return -1;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int add(String userName,Integer userType) {
		UserTokenVO userTokenVO=getToken(userName);
		if(null==userTokenVO){
			Date date=new Date();
			UserToken userToken=new UserToken();
			userToken.setUserName(userName);
			userToken.setUserType(userType);
			userToken.setCreateTime(date);
			userToken.setToken(MyRandomUtils.getTimeRandomString());
			userToken.setInvalidTime(DateTime.getOffsetMonthDate(date,3));
			userTokenDao.add(userToken);
			return 0;
		}
		return -1;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateToken(UserTokenVO userTokenVO) {
		UserToken userToken=new UserToken();
		MyBeanUtils.copyPropertiesContainsDate(userToken, userTokenVO);
		userTokenDao.updateToken(userToken);
	}
	
	public boolean checkUserTokenExists(UserTokenVO userTokenVO){
		if(null==userTokenVO)
			return false;
		if(null==userTokenVO.getInvalidTime())
			return false;
		return true;
	}
	
	public boolean checkUserTokenValid(UserTokenVO userTokenVO){
		if(null==userTokenVO)
			return false;
		if(null==userTokenVO.getInvalidTime())
			return false;
		if(DateTime.getSecondDifference(new Date(),userTokenVO.getInvalidTime())<=60)
			return false;
		return true;
	}
}
