package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.TestDriveRate;

public interface ITestDriveRateDao {
	
	public List<TestDriveRate> find(HashMap parameters,String additionalCondition);
	
	public List<TestDriveRate> findByPage(TransMsg msg, String additionalCondition);
	
	public TestDriveRate getByName(String name);
	
	public int save(TestDriveRate testDriveRate);
	
	public void edit(TestDriveRate testDriveRate);		
	
	public TestDriveRate get(Integer testDriveRateId);
	
	public void delete(Integer id);
	
}
