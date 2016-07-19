package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IRoleDao;
import com.ava.domain.entity.Role;

@Repository
public class RoleDao implements IRoleDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Role", parameters, additionalCondition);
		return objList;
	}
	
	public List findByPage(TransMsg transMsg, String additionalCondition) {
		List objList = hibernateDao.findByPage("Role", transMsg, additionalCondition);
		return objList;
	}
	
	public Role getByName(String name) {
		if (name == null || name.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List roles = find(parameters, "");
		if (roles != null && roles.size() > 0) {
			return (Role) roles.get(0);
		}
		return null;
	}
	
	
	public int save(Role role) {
		if (role != null) {
			try {
				return (Integer) hibernateDao.save(role);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(Role role) {
		if (role != null) {
			hibernateDao.edit(role);
		}
	}
	
	public Role get(Integer roleId) {
		if (roleId == null) {
			return null;
		}
		Role role = (Role) hibernateDao.get(Role.class, roleId);
		return role;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(Role.class, id);
	}
	
}
