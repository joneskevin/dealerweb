package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IMenuDao;
import com.ava.domain.entity.Menu;

@Repository
public class MenuDao implements IMenuDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List<Menu> find(HashMap parameters, String additionalCondition) {
		List<Menu> objList =  hibernateDao.find("Menu", parameters, additionalCondition);
		return objList;
	}
	
	public List<Menu> findByPage(TransMsg transMsg, String additionalCondition) {
		List<Menu> objList = hibernateDao.findByPage("Menu", transMsg, additionalCondition);
		return objList;
	}
	
	public Menu getByName(String name) {
		if (name == null || name.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List<Menu> roles = find(parameters, "");
		if (roles != null && roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}
	
	
	public int save(Menu role) {
		if (role != null) {
			try {
				return (Integer) hibernateDao.save(role);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(Menu role) {
		if (role != null) {
			hibernateDao.edit(role);
		}
	}
	
	public Menu get(Integer roleId) {
		if (roleId == null) {
			return null;
		}
		Menu role = (Menu) hibernateDao.get(Menu.class, roleId);
		return role;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(Menu.class, id);
	}
	
}
