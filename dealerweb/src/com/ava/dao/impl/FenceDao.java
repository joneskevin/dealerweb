package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IFenceDao;
import com.ava.domain.entity.Fence;
import com.ava.resource.cache.CacheFenceManager;

@Repository
public class FenceDao implements IFenceDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Fence", parameters, additionalCondition);
		return objList;
	}
	
	public List findByPage(TransMsg transMsg, String additionalCondition) {
		List objList = hibernateDao.findByPage("Fence", transMsg, additionalCondition);
		return objList;
	}
	
	public Fence getByName(String name) {
		if (name == null || name.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List fences = find(parameters, "");
		if (fences != null && fences.size() > 0) {
			return (Fence) fences.get(0);
		}
		return null;
	}
	
	
	public int save(Fence fence) {
		if (fence != null) {
			try {
				Integer objId = (Integer) hibernateDao.save(fence);
				CacheFenceManager.removeFenceById(objId);
				return objId;
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(Fence fence) {
		if (fence != null) {
			hibernateDao.edit(fence);
			CacheFenceManager.removeFenceById(fence.getId());
		}
	}
	
	public Fence get(Integer fenceId) {
		if (fenceId == null) {
			return null;
		}
		Fence fence = (Fence) hibernateDao.get(Fence.class, fenceId);
		return fence;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(Fence.class, id);
		CacheFenceManager.removeFenceById(id);
	}
	
}
