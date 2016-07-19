package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.INoTestDriveDao;
import com.ava.domain.entity.NoTestDrive;

@Repository
public class NoTestDriveDao implements INoTestDriveDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List<NoTestDrive> find(HashMap parameters, String additionalCondition) {
		List<NoTestDrive> objList =  hibernateDao.find("NoTestDrive", parameters, additionalCondition);
		return objList;
	}
	
	public List<NoTestDrive> findByPage(TransMsg transMsg, String additionalCondition) {
		List<NoTestDrive> objList = hibernateDao.findByPage("NoTestDrive", transMsg, additionalCondition);
		return objList;
	}
	
	
	public int save(NoTestDrive noTestDrive) {
		if (noTestDrive != null) {
			try {
				return (Integer) hibernateDao.save(noTestDrive);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(NoTestDrive noTestDrive) {
		if (noTestDrive != null) {
			hibernateDao.edit(noTestDrive);
		}
	}
	
	public NoTestDrive get(Integer noTestDriveId) {
		if (noTestDriveId == null) {
			return null;
		}
		NoTestDrive noTestDrive = (NoTestDrive) hibernateDao.get(NoTestDrive.class, noTestDriveId);
		return noTestDrive;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(NoTestDrive.class, id);
	}

	@Override
	public void delete(NoTestDrive noTestDrive) {
		hibernateDao.delete(noTestDrive);
		
	}
	
}
