package com.ava.baseSystem.service;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.User;
import com.ava.domain.entity.UserRoleRelation;

public interface IUserRoleRelationService {
	
	public Integer add(UserRoleRelation userRoleRelation);
	
	public void edit(UserRoleRelation userRoleRelation);
	
	public void delete(Integer userid);
	
	public List find(HashMap parameters,String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	/**
	 * 通过用户设置权限
	 * @param userEdit
	 */
	public void configRoleRelation(User userEdit);
	
}
