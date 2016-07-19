package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IMonitorLocationDao;
import com.ava.domain.entity.MonitorLocation;

@Repository
public class MonitorLocationDao implements IMonitorLocationDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	public Integer save(MonitorLocation monitorLocation){
		Integer objId = (Integer) hibernateDao.save(monitorLocation);
		return objId;
	}
	
	public void update(MonitorLocation monitorLocation) {
		hibernateDao.update(monitorLocation);
	}

	public MonitorLocation get(Integer id) {
		if (id == null) {
			return null;
		}
		MonitorLocation obj = (MonitorLocation) hibernateDao.get(MonitorLocation.class, id);
		return obj;
	}

	public MonitorLocation getLastByVehicleId(Integer vehicleId){
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vehicleId", vehicleId);
		List<MonitorLocation> objs = find(parameters, "order by creationTime desc limit 1");
		if (objs != null && objs.size() > 0) {
			return (MonitorLocation) objs.get(0);
		}
		return null;
	}
	
	public List<MonitorLocation> find(HashMap<Object, Object> parameters, String additionalCondition) {
		List<MonitorLocation> objList = hibernateDao.find("MonitorLocation", parameters, additionalCondition);
		return objList;
	}

}
