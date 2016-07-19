package com.ava.dao;

import java.util.Map;

import com.ava.domain.entity.UserToken;

public interface IUserTokenDao {
	UserToken get(int id);
	
	UserToken getToken(Map parameters);
		
	int add(UserToken token);
	
	void updateToken(UserToken token);
	
	void delete(int id);
}
