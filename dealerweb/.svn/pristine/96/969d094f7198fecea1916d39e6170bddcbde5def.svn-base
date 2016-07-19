package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.domain.entity.UserRoleRelation;
import com.ava.resource.GlobalConstant;
@Repository
public class UserRoleRelationDao implements IUserRoleRelationDao{
	@Autowired
	private IHibernateDao hibernateDao;
	@Override
	public Integer save(UserRoleRelation userRoleRelation) {
		return (Integer) hibernateDao.save(userRoleRelation);
	}

	@Override
	public void edit(UserRoleRelation userRoleRelation) {
		hibernateDao.edit(userRoleRelation);		
	}

	@Override
	public <List> UserRoleRelation get(Integer id) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		hibernateDao.delete(UserRoleRelation.class, id);
	}

	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("UserRoleRelation", parameters, additionalCondition);
		return objList;
	}

	@Override
	public List findByPage(TransMsg msg, String additionalCondition) {
		return null;
	}
	
	public Boolean isUserCompriseRole(Integer userId, Integer roleId) {
		HashMap parameters = new HashMap();
		parameters.put("userId", userId);
		parameters.put("roleId", roleId);
		ArrayList<UserRoleRelation> roleRelationlist = (ArrayList<UserRoleRelation>) this.find(parameters, null);
		if (roleRelationlist != null && roleRelationlist.size() > 0) {
			return true;
		} 
		return false;
	}

	@Override
	public Integer getMaxRoleId(Integer userId) {
		Integer roleId = 0;
		if (userId != null) {
			if (userId == GlobalConstant.TRUE) {
				roleId = GlobalConstant.HEADQUARTERS_ROLE_ID;
			}
			
			HashMap parameters = new HashMap();
			parameters.put("userId", userId);
			ArrayList<UserRoleRelation> roleRelationlist = (ArrayList<UserRoleRelation>) this.find(parameters, "ORDER BY ROLE_ID ASC");
			if (roleRelationlist != null && roleRelationlist.size() > 0) {
				roleId = roleRelationlist.get(0).getRoleId();
				
			} 
		}
		return roleId;
	}

}
