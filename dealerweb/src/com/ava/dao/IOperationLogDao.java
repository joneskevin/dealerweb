package com.ava.dao;

import com.ava.domain.entity.OperationLog;


public interface IOperationLogDao {
	
	public Integer save(OperationLog operationLog);
	
	public void delete(Integer id);
	
	public void update(OperationLog operationLog);
	
}
