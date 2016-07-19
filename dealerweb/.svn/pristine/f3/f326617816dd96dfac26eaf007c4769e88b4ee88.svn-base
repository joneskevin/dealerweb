package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.DesignateOrder;

public interface IDesignateOrderDao {
	
	public DesignateOrder get(Integer id);
	
	public int save(DesignateOrder designateOrder);
	
	public void update(DesignateOrder designateOrder);
	
	public void delete(Integer id);
	
	public List<DesignateOrder> find(Map<Object, Object> parameters, String additionalCondition);
	
	public List<DesignateOrder> findByPage(TransMsg msg, String additionalCondition);
}
