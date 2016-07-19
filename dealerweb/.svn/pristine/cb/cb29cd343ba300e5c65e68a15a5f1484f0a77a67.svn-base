package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IUserMenuRelationDao;
import com.ava.domain.entity.UserMenuRelation;

@Repository
public class UserMenuRelationDao implements IUserMenuRelationDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List<UserMenuRelation> find(HashMap parameters, String additionalCondition) {
		List<UserMenuRelation> objList =  hibernateDao.find("UserMenuRelation", parameters, additionalCondition);
		return objList;
	}
	
	public List<UserMenuRelation> findByPage(TransMsg transMsg, String additionalCondition) {
		List<UserMenuRelation> objList = hibernateDao.findByPage("UserMenuRelation", transMsg, additionalCondition);
		return objList;
	}
	
	public UserMenuRelation getByName(String name) {
		if (name == null || name.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List<UserMenuRelation> userMenuRelations = find(parameters, "");
		if (userMenuRelations != null && userMenuRelations.size() > 0) {
			return userMenuRelations.get(0);
		}
		return null;
	}
	
	
	public int save(UserMenuRelation userMenuRelation) {
		if (userMenuRelation != null) {
			try {
				return (Integer) hibernateDao.save(userMenuRelation);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(UserMenuRelation userMenuRelation) {
		if (userMenuRelation != null) {
			hibernateDao.edit(userMenuRelation);
		}
	}
	
	public UserMenuRelation get(Integer userMenuRelationId) {
		if (userMenuRelationId == null) {
			return null;
		}
		UserMenuRelation userMenuRelation = (UserMenuRelation) hibernateDao.get(UserMenuRelation.class, userMenuRelationId);
		return userMenuRelation;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(UserMenuRelation.class, id);
	}
	
}
