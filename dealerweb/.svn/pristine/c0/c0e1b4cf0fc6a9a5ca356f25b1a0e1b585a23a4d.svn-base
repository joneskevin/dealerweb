package com.ava.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ILocationDao;
import com.ava.domain.entity.Location;
import com.ava.util.DateTime;

@Repository("locationHistoryDao")
public class LocationHistoryDao implements ILocationDao {
	@Resource(name="historyHibernateDao")
	private IHibernateDao hibernateDao;
	
	public Long save(Location location){
		Long objId = (Long) hibernateDao.save(location);
		return objId;
	}

	public Location get(Long id) {
		if (id == null) {
			return null;
		}
		Location obj = (Location) hibernateDao.get(Location.class, id);
		return obj;
	}

	public Location getLastByVehicleId(Integer vehicleId){
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vehicleId", vehicleId);
		String beginDate = DateTime.toShortDateTime(DateTime.addDays(new Date(),-10));
		@SuppressWarnings("rawtypes")
		List objs = find(parameters, " and creationTime>"+beginDate+" order by creationTime desc");
		if (objs != null && objs.size() > 0) {
			return (Location) objs.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Location", parameters, additionalCondition);
		return objList;
	}
	
	@SuppressWarnings("rawtypes")
	public List find(HashMap parameters, String additionalCondition, Integer fetchSize) {
		List<Object> objList = (List<Object>) hibernateDao.find("Location", parameters, additionalCondition, fetchSize);
		return objList;
	}
}
