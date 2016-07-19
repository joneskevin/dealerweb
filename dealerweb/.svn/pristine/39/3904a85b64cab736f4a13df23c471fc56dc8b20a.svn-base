package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.VehicleInstallationPlan;

public interface IVehicleInstallationPlanDao {
	
	public List<VehicleInstallationPlan> find(HashMap<Object, Object> parameters, String additionalCondition);
	
	public List<VehicleInstallationPlan> findByPage(TransMsg msg, String additionalCondition);
	
	public int save(VehicleInstallationPlan vehicleInstallationPlan);
	
	public void edit(VehicleInstallationPlan vehicleInstallationPlan);		
	
	public VehicleInstallationPlan get(Integer vehicleInstallationPlanId);
	
	public void delete(Integer id);
	
}
