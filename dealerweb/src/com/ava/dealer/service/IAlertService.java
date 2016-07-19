package com.ava.dealer.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Alert;

@Service
public interface IAlertService{
	public Integer saveAlert(Alert alert);
	
	public void updateAlert(Alert alert);
	
	public List<Alert> find(HashMap parameters, String additionalCondition) ;

	public List<Alert> findByPage(TransMsg msg, String additionalCondition);
}
