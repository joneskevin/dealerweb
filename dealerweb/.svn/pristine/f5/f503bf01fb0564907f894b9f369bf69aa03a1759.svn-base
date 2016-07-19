package com.ava.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IDesignateOrderDao;
import com.ava.domain.entity.DesignateOrder;

@Repository
public class DesignateOrderDao implements IDesignateOrderDao {
	@Autowired
	private IHibernateDao hibernateDao;

	public DesignateOrder get(Integer id) {
		if (id == null) {
			return null;
		}
		DesignateOrder obj = (DesignateOrder) hibernateDao.get(DesignateOrder.class, id);
		return obj;
	}

	@Override
	public int save(DesignateOrder designateOrder) {
		if (designateOrder == null) {
			return 0;
		}
		Integer objId = (Integer) hibernateDao.save(designateOrder);
		return objId;
	}

	@Override
	public List<DesignateOrder> findByPage(TransMsg msg, String additionalCondition) {
		List<DesignateOrder> objList = hibernateDao.findByPage("DesignateOrder", msg, additionalCondition);
		return objList;

	}

	@Override
	public List<DesignateOrder> find(Map<Object, Object> parameters, String additionalCondition) {
		List<DesignateOrder> objList = hibernateDao.find("DesignateOrder", parameters, additionalCondition);
		return objList;
	}

	@Override
	public void update(DesignateOrder designateOrder) {
		hibernateDao.update(designateOrder);
		
	}

	@Override
	public void delete(Integer id) {
		hibernateDao.delete(DesignateOrder.class, id);
	}
}
