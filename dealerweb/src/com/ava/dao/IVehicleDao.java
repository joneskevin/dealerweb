package com.ava.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Vehicle;


public interface IVehicleDao {
	
	public Integer save(Vehicle vehicle);
	
	public void delete(Integer id);
	
	public void update(Vehicle vehicle);
	
	public List findNatively(Map parameters);
	
	public Vehicle get(Integer id);
	
	public Vehicle getByVin(String vin);
	
	public Vehicle getByPalteNumber(String plateNumber);
	
	public List findByPlateNumber(String plateNumber, String additionalCondition);
	
	public List<Integer> findIds(HashMap parameters, String additionalCondition);
	
	public List<Integer> findIdsByPlateNumber(String plateNumber, String additionalCondition);
	
	public List findByCompanyId(Integer companyId);
	
	public List find(HashMap parameters, String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public Vehicle getBySerialNumber(String serialNumber);
}
