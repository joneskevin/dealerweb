package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.DriveLine;


public interface IDriveLineDao {
	public JSONArray toJSONArray(String jsonContent, String name);
	
	public Integer save(DriveLine driveLine);
	
	public void delete(Integer id);
	
	public void update(DriveLine driveLine);
	
	public DriveLine get(Integer id);

	public List<DriveLine> getByName(String name, Integer companyId);
	
	public List findIdsByCompanyId(Integer companyId);
	
	public List find(HashMap parameters, String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public List<DriveLine> getDriveLineByDealerId(Integer dealerId);
	
	public List<DriveLine> getDriveLineByDealerIdNew(Integer dealerId);
}
