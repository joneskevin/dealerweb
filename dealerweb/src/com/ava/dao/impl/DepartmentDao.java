package com.ava.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IDepartmentDao;
import com.ava.domain.entity.Department;
import com.ava.resource.cache.CacheOrgManager;

@Repository
public class DepartmentDao implements IDepartmentDao {	
	@Autowired
	private IHibernateDao hibernateDao;
	
	public void delete( Integer id) {
		hibernateDao.delete(Department.class,id);
		
		CacheOrgManager.clear();
	}

	public void deleteByCompanyId(Integer companyId){
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		List objList = hibernateDao.find("Department", parameters, "");
		if (objList != null && objList.size() > 0) {
			Iterator itor = objList.iterator();
			while(itor.hasNext()){
				Department department = (Department) itor.next();
				delete(department.getId());
			}
		}
	}
	
	public Integer save(Department department){
		Integer objId = null;
		if (department!=null){
			if (department.getDeletionFlag()==null ){
				department.setDeletionFlag(0);
			}
			try {
				objId = (Integer) hibernateDao.save(department);
				
				CacheOrgManager.clear();
			} catch (Exception e) {
			}
		}
		
		return objId;
	}
	
	public void edit(Department department){
		if (department!=null){
			if ( department.getDeletionFlag()==null ){
				department.setDeletionFlag(0);
			}
			if ( department.getOrderNumber()==null ){
				department.setOrderNumber(0);
			}
			hibernateDao.edit(department);

			CacheOrgManager.clear();
		}
	}

	public Department get(Integer departmentId){
		if (departmentId == null) {
			return null;
		}
		Department department = (Department) hibernateDao.get(Department.class,departmentId);
		return department;
	}

	public Department getByName(String name){
		if (name == null || name.length()<1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List departments = find(parameters, "");
		if (departments != null && departments.size() > 0) {
			return (Department) departments.get(0);
		}
		return null;
	}
		
	public List findByPage(TransMsg msg, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.findByPage("Department", msg, additionalCondition);
		return objList;
	}
	
		
	public List find(Map parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Department", parameters, additionalCondition);
		return objList;
	}
}
