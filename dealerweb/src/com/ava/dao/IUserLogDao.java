package com.ava.dao;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.UserLog;

public interface IUserLogDao {
    
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserLog> findByUserId(Integer userId);
	
	
	
	public int save(UserLog userLog);
	
	/**
	 * 
	 * @param msg
	 * @param additionalCondition
	 * @return
	 */
	public List<UserLog> findByPage(TransMsg msg, String additionalCondition);
}
