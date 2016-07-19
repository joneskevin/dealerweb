package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IBoxDao;
import com.ava.domain.entity.Box;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheBoxManager;

@Repository
public class BoxDao implements IBoxDao {
	
	@Autowired
	private IHibernateDao hibernateDao;

	public Integer save(Box box) {
		Integer objId = null;
		if (box != null) {
			try {
				objId = (Integer) hibernateDao.save(box);
				CacheBoxManager.removeBoxById(objId);
			} catch (Exception e) {
			}
		}

		return objId;
	}

	public void delete(Integer id) {
		hibernateDao.delete(Box.class, id);
		CacheBoxManager.removeBoxById(id);
	}

	public void update(Box box) {
		hibernateDao.update(box);
		CacheBoxManager.removeBoxById(box.getId());
	}
	
	@Override
	public Integer merge(Box box) {
		box = (Box) hibernateDao.merge(box);
		Integer id = box.getId();
		CacheBoxManager.removeBoxById(id);
		return id;
	}
	
	public Box get(Integer id) {
		if (id == null) {
			return null;
		}
		Box org = (Box) hibernateDao.get(Box.class, id);
		return org;
	}
	
	public Box getByVehicleId(Integer vehicleId){
		if (vehicleId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vehicleId", vehicleId);
		List<Box> objs = hibernateDao.find("Box", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (Box) objs.get(0);
		}
		return null;
	}
	
	public Box getBySimId(String simId) {
		if (simId == null || simId.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("simId", simId);
		List objs = hibernateDao.find("Box", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (Box) objs.get(0);
		}
		return null;
	}
	
	public List<Box> findByCompanyId(Integer companyId) {
		if (companyId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		parameters.put("deletionFlag", GlobalConstant.FALSE);
		List<Box> objs = hibernateDao.find("Box", parameters, "");
		return objs;
	}

	public List<Box> find(HashMap<Object, Object> parameters, String additionalCondition) {
		parameters.put("deletionFlag", GlobalConstant.FALSE);
		List<Box> boxList = hibernateDao.find("Box",
				parameters, additionalCondition);
		return boxList;
	}

	public List<Box> findByPage(TransMsg msg, String additionalCondition) {
		msg.put("deletionFlag", GlobalConstant.FALSE);
		List<Box> boxList =  hibernateDao.findByPage("Box",
				msg, additionalCondition);
		return boxList;
	}

	@Override
	public Box getByUniqueId(String uniqueId) {
		if (uniqueId == null || uniqueId.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("uniqueId", uniqueId);
		List objs = hibernateDao.find("Box", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (Box) objs.get(0);
		}
		return null;
	}
}
