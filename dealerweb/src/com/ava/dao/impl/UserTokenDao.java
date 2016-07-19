package com.ava.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IUserTokenDao;
import com.ava.domain.entity.UserToken;

@Repository
public class UserTokenDao implements IUserTokenDao{
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	public UserToken get(int id){
		return hibernateDao.get(UserToken.class, id);
	}
		
	public UserToken getToken(Map parameters){
		List<UserToken>  userTokens=hibernateDao.find("UserToken", parameters);
		if(null==userTokens || userTokens.isEmpty() || userTokens.size()<=0)
			return null;
		return userTokens.get(0);
	}
	
	public int add(UserToken token){
		Date date=new Date();
		token.setCreateTime(date);
		token.setUpdateTime(date);
		return  (Integer) hibernateDao.save(token);
	}
	

	public void updateToken(UserToken token){
		Date date=new Date();
		token.setUpdateTime(date);
		hibernateDao.update(token);
	}
	

	public void delete(int id){
		hibernateDao.delete(UserToken.class, id);
	}

}
