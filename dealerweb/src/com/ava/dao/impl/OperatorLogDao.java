package com.ava.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IOperatorLogDao;
import com.ava.domain.entity.OperatorLog;
@Repository
public class OperatorLogDao implements IOperatorLogDao{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List findByPage(TransMsg msg,
			String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.findByPage("OperatorLog", msg, additionalCondition);
		return objList;
	
	}

	@Override
	public int save(OperatorLog operatorlog) {
		if(operatorlog == null ){
			return 0;
		} 
	
		return (Integer) hibernateDao.save(operatorlog);
	}
}
