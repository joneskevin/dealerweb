package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Department;

public interface IDepartmentDao {
	
	public Integer save(Department department);
	
	public void delete(Integer id);
	
	public void deleteByCompanyId(Integer companyId);
	
	public void edit(Department department);
	
	public Department get(Integer departmentId);

	public Department getByName(String Name);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public List find(Map parameters, String additionalCondition);
			
	
}
