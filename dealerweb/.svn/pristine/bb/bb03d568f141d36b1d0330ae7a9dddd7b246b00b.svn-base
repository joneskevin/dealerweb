package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.NoTestDrive;

public interface INoTestDriveDao {
	
	public List<NoTestDrive> find(HashMap parameters, String additionalCondition);
	
	public List<NoTestDrive> findByPage(TransMsg msg, String additionalCondition);
	
	public int save(NoTestDrive noTestDrive);
	
	public void edit(NoTestDrive noTestDrive);		
	
	public NoTestDrive get(Integer noTestDriveId);
	
	public void delete(Integer id);
	
	public void delete(NoTestDrive noTestDrive);
	
}
