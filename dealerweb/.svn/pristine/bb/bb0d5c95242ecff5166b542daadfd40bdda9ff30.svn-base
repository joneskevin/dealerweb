package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.BoxOperation;

public interface IBoxOperationDao {
	
	public List find(HashMap parameters,String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public int save(BoxOperation boxOperation);
	
	public void edit(BoxOperation boxOperation);		
	
	public BoxOperation get(Integer boxOperationId);
	
	public void delete(Integer id);
	
}
