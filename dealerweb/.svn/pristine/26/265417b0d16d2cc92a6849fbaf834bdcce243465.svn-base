package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.UserMenuRelation;

public interface IUserMenuRelationDao {
	
	public List<UserMenuRelation> find(HashMap parameters, String additionalCondition);
	
	public List<UserMenuRelation> findByPage(TransMsg msg, String additionalCondition);
	
	public UserMenuRelation getByName(String name);
	
	public int save(UserMenuRelation userMenuRelation);
	
	public void edit(UserMenuRelation userMenuRelation);		
	
	public UserMenuRelation get(Integer userMenuRelationId);
	
	public void delete(Integer id);
	
}
