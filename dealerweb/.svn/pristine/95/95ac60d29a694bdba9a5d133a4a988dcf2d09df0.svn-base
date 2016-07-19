package com.ava.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.UserRoleRelation;

public interface IUserRoleRelationDao {
	
	public Integer save(UserRoleRelation userRoleRelation);
	
	public void edit(UserRoleRelation userRoleRelation);
	
	public <List>UserRoleRelation get(Integer id);
	
	public void delete(Integer id);
	
	public List find(HashMap parameters,String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	/**
	 * 判断一个用户的角色是否包含某个角色
	 * @param userId 用户ID
	 * @param roleId 包含的角色ID
	 * @return Boolean
	 */
	public Boolean isUserCompriseRole(Integer userId, Integer roleId);
	
	/**
	 * 通过用户ID返回该用户最大权限的角色
	 * @param userId
	 * @return
	 */
	public Integer getMaxRoleId(Integer userId);
	
}
