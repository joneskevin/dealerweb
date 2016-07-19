package com.ava.dao;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.OperatorLog;


public interface IOperatorLogDao {
	public int save(OperatorLog operatorlog);
	
	/**
	 * 
	 * @param msg
	 * @param additionalCondition
	 * @return
	 */
	public List<OperatorLog> findByPage(TransMsg msg, String additionalCondition);
}
