package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.ICarSeriesDao;
import com.ava.domain.entity.CarSeries;

@Repository
public class CarSeriesDao implements ICarSeriesDao {
	@Autowired
	private IHibernateDao hibernateDao;

	public CarSeries get(Integer id) {
		if (id == null) {
			return null;
		}
		CarSeries obj = (CarSeries) hibernateDao.get(CarSeries.class, id);
		return obj;
	}

	@Override
	public int save(CarSeries carSeries) {
		if (carSeries == null) {
			return 0;
		}
		Integer objId = (Integer) hibernateDao.save(carSeries);
		return objId;
	}

	@Override
	public List<CarSeries> findByPage(TransMsg msg, String additionalCondition) {
		List<CarSeries> objList = hibernateDao.findByPage("CarSeries", msg, additionalCondition);
		return objList;

	}

	@Override
	public List<CarSeries> find(Map<Object, Object> parameters, String additionalCondition) {
		List<CarSeries> objList = hibernateDao.find("CarSeries", parameters, additionalCondition);
		return objList;
	}

	@Override
	public CarSeries getCarSeriesName(String name) {
		if (name == null || name.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List<CarSeries> objs = hibernateDao.find("CarSeries", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (CarSeries) objs.get(0);
		}
		return null;
	}

	@Override
	public void update(CarSeries carSeries) {
		hibernateDao.update(carSeries);
		
	}

	@Override
	public void delete(Integer id) {
		hibernateDao.delete(CarSeries.class, id);
		
	}
}
