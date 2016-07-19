package com.ava.dealer.service;

import com.ava.domain.vo.UserTokenVO;

public interface IUserTokenService {
	
	boolean varifyTokenExists(String userName,String userToken);
	
	boolean verifyUserToken(String userName,String userToken);
	
	boolean verifyUserToken(String userName,String userToken,Integer userType);
	
	String refreshUserToken(String userName,String oldToken);
	
	UserTokenVO get(int id);
	
	UserTokenVO getToken(String userName,String token);
	
	int add(String userName);
	
	int add(String userName,Integer userType);
	
	void updateToken(UserTokenVO userTokenVO);
	
	boolean checkUserTokenExists(UserTokenVO userTokenVO);
	
	boolean checkUserTokenValid(UserTokenVO userTokenVO);
}
