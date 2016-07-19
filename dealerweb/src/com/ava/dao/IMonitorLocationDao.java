package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.domain.entity.MonitorLocation;

public interface IMonitorLocationDao {
	public Integer save(MonitorLocation monitorLocation);
	
	public void update(MonitorLocation monitorLocation);
	
	public MonitorLocation get(Integer id);
	
	/**获得最新的一条记录*/
	public MonitorLocation getLastByVehicleId(Integer vehicleId);
	
	public List<MonitorLocation> find(HashMap<Object, Object> parameters, String additionalCondition);

}
