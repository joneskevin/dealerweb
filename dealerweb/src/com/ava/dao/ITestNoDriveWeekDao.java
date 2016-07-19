package com.ava.dao;

import com.ava.domain.entity.TestNoDriveWeek;


public interface ITestNoDriveWeekDao {
	
	public Integer save(TestNoDriveWeek testNoDriveWeek);
	
	public void delete(Integer id);
	
	public void update(TestNoDriveWeek testNoDriveWeek);
	
	public TestNoDriveWeek get(Integer id);
	
}
