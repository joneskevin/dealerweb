package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IVehicleInstallationPlanDao;
import com.ava.domain.entity.VehicleInstallationPlan;

@Repository
public class VehicleInstallationPlanDao implements IVehicleInstallationPlanDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List<VehicleInstallationPlan> find(HashMap parameters, String additionalCondition) {
		List<VehicleInstallationPlan> objList =  hibernateDao.find("VehicleInstallationPlan", parameters, additionalCondition);
		return objList;
	}
	
	public List<VehicleInstallationPlan> findByPage(TransMsg transMsg, String additionalCondition) {
		List<VehicleInstallationPlan> objList = hibernateDao.findByPage("VehicleInstallationPlan", transMsg, additionalCondition);
		return objList;
	}

	public int save(VehicleInstallationPlan vehicleInstallationPlan) {
		if (vehicleInstallationPlan != null) {
			try {
				return (Integer) hibernateDao.save(vehicleInstallationPlan);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(VehicleInstallationPlan vehicleInstallationPlan) {
		if (vehicleInstallationPlan != null) {
			hibernateDao.edit(vehicleInstallationPlan);
		}
	}
	
	public VehicleInstallationPlan get(Integer vehicleInstallationPlanId) {
		if (vehicleInstallationPlanId == null) {
			return null;
		}
		VehicleInstallationPlan vehicleInstallationPlan = (VehicleInstallationPlan) hibernateDao.get(VehicleInstallationPlan.class, vehicleInstallationPlanId);
		return vehicleInstallationPlan;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(VehicleInstallationPlan.class, id);
	}
	
}
