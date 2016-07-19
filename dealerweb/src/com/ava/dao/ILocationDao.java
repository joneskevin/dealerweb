package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.domain.entity.Location;

public interface ILocationDao {
	public Long save(Location location);
	
	public Location get(Long id);
	
	/**获得最新的一条记录*/
	public Location getLastByVehicleId(Integer vehicleId);
	
	public List find(HashMap parameters, String additionalCondition);
	
	public List find(HashMap parameters, String additionalCondition, Integer fetchSize);

}
