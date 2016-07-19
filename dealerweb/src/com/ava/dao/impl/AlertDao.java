package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IAlertDao;
import com.ava.domain.entity.Alert;

@Repository
public class AlertDao implements IAlertDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	public Integer saveAlert(Alert alert){
		return (Integer) hibernateDao.save(alert);
	}
	
	public void updateAlert(Alert alert){
		hibernateDao.update(alert);
	}
	
	@Override
	public List<Alert> find(HashMap parameters, String additionalCondition) {
		List<Alert> objList = hibernateDao.find("Alert", parameters, additionalCondition);
		return objList;
	}

	@Override
	public List<Alert> findByPage(TransMsg msg, String additionalCondition) {
		List<Alert> objList = hibernateDao.findByPage("Alert", msg, additionalCondition);
		return objList;
	}
}
