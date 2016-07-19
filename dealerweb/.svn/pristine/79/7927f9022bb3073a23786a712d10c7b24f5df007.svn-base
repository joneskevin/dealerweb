package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Box;


public interface IBoxDao {
	
	public Integer save(Box box);
	
	public void delete(Integer id);
	
	public void update(Box box);
	
	public Integer merge(Box box);
	
	public Box get(Integer id);
	
	public Box getByVehicleId(Integer vehicleId);
	
	public Box getBySimId(String simId);
	
	public Box getByUniqueId(String uniqueId);

	public List<Box> findByCompanyId(Integer companyId);
	
	public List<Box> find(HashMap<Object, Object> parameters, String additionalCondition);
	
	public List<Box> findByPage(TransMsg msg, String additionalCondition);
}
