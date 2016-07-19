package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IBoxOperationDao;
import com.ava.domain.entity.BoxOperation;
import com.ava.domain.entity.Role;

@Repository
public class BoxOperationDao implements IBoxOperationDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("BoxOperation", parameters, additionalCondition);
		return objList;
	}
	
	public List findByPage(TransMsg transMsg, String additionalCondition) {
		List objList = hibernateDao.findByPage("BoxOperation", transMsg, additionalCondition);
		return objList;
	}
	
	public int save(BoxOperation boxOperation) {
		if (boxOperation != null) {
			try {
				return (Integer) hibernateDao.save(boxOperation);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(BoxOperation boxOperation) {
		if (boxOperation != null) {
			hibernateDao.edit(boxOperation);
		}
	}
	
	public BoxOperation get(Integer boxOperationId) {
		if (boxOperationId == null) {
			return null;
		}
		BoxOperation boxOperation = (BoxOperation) hibernateDao.get(BoxOperation.class, boxOperationId);
		return boxOperation;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(Role.class, id);
	}
	
}
