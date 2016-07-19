package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.ITestDriveRateDao;
import com.ava.domain.entity.TestDriveRate;

@Repository
public class TestDriveRateDao implements ITestDriveRateDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	
	@Override
	public List<TestDriveRate> find(HashMap parameters, String additionalCondition) {
		List<TestDriveRate> objList = hibernateDao.find("TestDriveRate", parameters, additionalCondition);
		return objList;
	}
	
	public List<TestDriveRate> findByPage(TransMsg transMsg, String additionalCondition) {
		List objList = hibernateDao.findByPage("TestDriveRate", transMsg, additionalCondition);
		return objList;
	}
	
	public TestDriveRate getByName(String name) {
		if (name == null || name.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List testDriveRates = find(parameters, "");
		if (testDriveRates != null && testDriveRates.size() > 0) {
			return (TestDriveRate) testDriveRates.get(0);
		}
		return null;
	}
	
	
	public int save(TestDriveRate testDriveRate) {
		if (testDriveRate != null) {
			try {
				return (Integer) hibernateDao.save(testDriveRate);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(TestDriveRate testDriveRate) {
		if (testDriveRate != null) {
			hibernateDao.edit(testDriveRate);
		}
	}
	
	public TestDriveRate get(Integer testDriveRateId) {
		if (testDriveRateId == null) {
			return null;
		}
		TestDriveRate testDriveRate = (TestDriveRate) hibernateDao.get(TestDriveRate.class, testDriveRateId);
		return testDriveRate;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(TestDriveRate.class, id);
	}
	
}
