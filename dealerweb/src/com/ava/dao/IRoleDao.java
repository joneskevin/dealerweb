package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Role;

public interface IRoleDao {
	
	public List find(HashMap parameters,String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public Role getByName(String name);
	
	public int save(Role role);
	
	public void edit(Role role);		
	
	public Role get(Integer roleId);
	
	public void delete(Integer id);
	
}
