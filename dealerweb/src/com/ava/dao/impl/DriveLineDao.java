package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IDriveLineDao;
import com.ava.domain.entity.DriveLine;
import com.ava.resource.cache.CacheDriveLineManager;
import com.ava.util.MyStringUtils;

@Repository
public class DriveLineDao implements IDriveLineDao {
	@Autowired
	private IHibernateDao hibernateDao;

	/**这是一个和数据表结构有关的一个工具方法*/
	public JSONArray toJSONArray(String jsonContent, String name) {
		if (!MyStringUtils.isBlank(jsonContent)) {
			JSONObject jsonObject = JSONObject.fromObject(jsonContent);
			if (jsonObject != null) {
				JSONArray jsonArray = (JSONArray) jsonObject.get(name);
				return jsonArray;
			}
		}

		return null;
	}

	public Integer save(DriveLine driveLine) {
		Integer objId = null;
		if (driveLine != null) {
			try {
				objId = (Integer) hibernateDao.save(driveLine);
				CacheDriveLineManager.removeDriveLineById(objId);
			} catch (Exception e) {
			}
		}

		return objId;
	}

	public void delete(Integer id) {
		hibernateDao.delete(DriveLine.class, id);
		CacheDriveLineManager.removeDriveLineById(id);
	}

	public void update(DriveLine driveLine) {
		hibernateDao.update(driveLine);
		CacheDriveLineManager.removeDriveLineById(driveLine.getId());
	}
	
	public DriveLine get(Integer id) {
		if (id == null) {
			return null;
		}
		DriveLine org = (DriveLine) hibernateDao.get(DriveLine.class, id);
		return org;
	}
	
	public List<DriveLine> getByName(String name,Integer companyId){
		if (name == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		parameters.put("companyId", companyId);
		List objs = hibernateDao.find("DriveLine", parameters, "");
		if(objs != null && objs.size() > 0){
			return (List<DriveLine>) objs;
		}
		return null;
	}

	public List findIdsByCompanyId(Integer companyId) {
		if (companyId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);

		List<String> fields = new ArrayList<String>();
		fields.add("id");
		
		List objs = hibernateDao.find(fields, "DriveLine", parameters, "order by id asc");
		return objs;
	}

	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("DriveLine",
				parameters, additionalCondition);
		return objList;
	}

	public List findByPage(TransMsg msg, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.findByPage("DriveLine",
				msg, additionalCondition);
		return objList;
	}
	
	public List<DriveLine> getDriveLineByDealerId(Integer dealerId){
		
		if (dealerId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", dealerId);
		parameters.put("companyId", dealerId);
		
		List<DriveLine> objs = hibernateDao.find("DriveLine", parameters);
		return objs;
	}
	
	public List<DriveLine> getDriveLineByDealerIdNew(Integer dealerId){
		
		if (dealerId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", dealerId);
		List<DriveLine> objs = hibernateDao.find("DriveLine", parameters, " order by name");
		return objs;
	}
}
