package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.ICarStyleDao;
import com.ava.domain.entity.CarStyle;
import com.ava.resource.cache.CacheCarManager;

@Repository
public class CarStyleDao implements ICarStyleDao {
	@Autowired
	private IHibernateDao hibernateDao;

	public CarStyle get(Integer id) {
		if (id == null) {
			return null;
		}
		CarStyle obj = (CarStyle) hibernateDao.get(CarStyle.class, id);
		return obj;
	}

	public List<Integer> findIdsByBrandId(Integer brandId, Integer level){
		if (brandId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("brandId", brandId);
		parameters.put("level", level); //获得有年款的车型 层级ID为1

		List<String> fields = new ArrayList<String>();
		fields.add("id");
		
		return hibernateDao.find(fields, "CarStyle", parameters, "order by seriesId, yearType desc");
	}

	@Override
	public int save(CarStyle carStyle) {
		if (carStyle == null) {
			return 0;
		}
		Integer objId = (Integer) hibernateDao.save(carStyle);
		CacheCarManager.removeCarStyleById(objId);
		return objId;
	}

	@Override
	public List<CarStyle> findByPage(TransMsg msg, String additionalCondition) {
		List<CarStyle> objList = hibernateDao.findByPage("CarStyle", msg, additionalCondition);
		return objList;

	}

	@Override
	public List<CarStyle> find(Map parameters, String additionalCondition) {
		List<CarStyle> objList = hibernateDao.find("CarStyle", parameters, additionalCondition);
		return objList;
	}

	@Override
	public void update(CarStyle carStyle) {
		hibernateDao.update(carStyle);
		
	}

	@Override
	public CarStyle getCarStyleName(String name) {
		if (name == null || name.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List<CarStyle> objs = hibernateDao.find("CarStyle", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (CarStyle) objs.get(0);
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		hibernateDao.delete(CarStyle.class, id);
	}
}
