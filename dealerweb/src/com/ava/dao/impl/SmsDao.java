package com.ava.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.ISmsDao;
import com.ava.domain.entity.Sms;

@Repository
public class SmsDao  implements ISmsDao{
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	public List findByPage(TransMsg msg, String additionalCondition){
		List<Object> objList = (List<Object>) hibernateDao.findByPage("Sms", msg, additionalCondition);
		return objList;
	}

	public void edit(Sms sms) {
     hibernateDao.edit(sms);		
	}

}
