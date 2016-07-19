package com.ava.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IHopefulCustomerDao;
import com.ava.domain.entity.HopefulCustomer;
@Repository
public class HopefulCustomerDao implements IHopefulCustomerDao{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List<HopefulCustomer> find(Map parameters, String additionalCondition) {
		List<HopefulCustomer> testDriveList =  hibernateDao.find("HopefulCustomer", parameters, additionalCondition);
		return testDriveList;
	}
}
