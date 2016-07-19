package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IBigAreaReportItemDao;
import com.ava.domain.entity.BigAreaReportItem;

@Repository
public class BigAreaReportItemDao implements IBigAreaReportItemDao {

	@Autowired
	private IHibernateDao hibernateDao;
	@Override
	public List<BigAreaReportItem> find(HashMap parameters,
			String additionalCondition) {
		List<BigAreaReportItem> objList =  hibernateDao.find("BigAreaReportItem", parameters, additionalCondition);
		return objList;
	}

	@Override
	public List<BigAreaReportItem> findByPage(TransMsg msg,
			String additionalCondition) {
		List<BigAreaReportItem> objList = hibernateDao.findByPage("NoTestDrive", msg, additionalCondition);
		return objList;
	}

	@Override
	public int save(BigAreaReportItem bigAreaReportItem) {
		if (bigAreaReportItem != null) {
			try {
				return (Integer) hibernateDao.save(bigAreaReportItem);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	@Override
	public void delete(Integer id) {

	}
	
	@Override
	public void delete(BigAreaReportItem bigAreaReportItem) {
		hibernateDao.delete(bigAreaReportItem);
	}
}
