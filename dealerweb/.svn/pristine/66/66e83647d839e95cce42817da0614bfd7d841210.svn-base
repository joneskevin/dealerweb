package com.ava.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IUserLogDao;
import com.ava.domain.entity.UserLog;

@Repository
public class UserLogDao implements IUserLogDao{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List findByPage(TransMsg msg,
			String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.findByPage("UserLog", msg, additionalCondition);
		return objList;
	
	}

	@Override
	public List<UserLog> findByUserId(Integer userId) {
		
		return null;
	}
	


	@Override
	public int save(UserLog userLog) {
		if(userLog == null ){
			return 0;
		} 
	
		return (Integer) hibernateDao.save(userLog);
	}
}
