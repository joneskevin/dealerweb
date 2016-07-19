package com.ava.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IOperationLogDao;
import com.ava.domain.entity.OperationLog;

@Repository
public class OperationLogDao implements IOperationLogDao {
	@Autowired
	private IHibernateDao hibernateDao;

	public Integer save(OperationLog operationLog) {
		Integer objId = null;
		if (operationLog != null) {
			try {
				objId = (Integer) hibernateDao.save(operationLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return objId;
	}

	public void delete(Integer id) {
		hibernateDao.delete(OperationLog.class, id);
	}

	public void update(OperationLog operationLog) {
		hibernateDao.update(operationLog);
	}
}
