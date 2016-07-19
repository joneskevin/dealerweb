package com.ava.dealer.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IAlertDao;
import com.ava.dealer.service.IAlertService;
import com.ava.domain.entity.Alert;

@Service
public class AlertService implements IAlertService {
	@Autowired
	private IAlertDao alertDao;
	
	public Integer saveAlert(Alert alert){
		return (Integer) alertDao.saveAlert(alert);
	}
	
	public void updateAlert(Alert alert){
		alertDao.updateAlert(alert);
	}
	
	@Override
	public List<Alert> find(HashMap parameters, String additionalCondition) {
		List<Alert> objList = alertDao.find(parameters, additionalCondition);
		return objList;
	}

	@Override
	public List<Alert> findByPage(TransMsg msg, String additionalCondition) {
		List<Alert> objList = alertDao.findByPage(msg, additionalCondition);
		return objList;
	}
}
