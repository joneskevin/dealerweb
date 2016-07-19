package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IOrgDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Department;
import com.ava.domain.entity.Org;
import com.ava.resource.GlobalConstant;

@Repository
public class OrgDao implements IOrgDao {	
	@Autowired
	private IHibernateDao hibernateDao;

	public void edit(Org org){
		if (org == null) {
			return;
		}
		if(org instanceof Company){
			hibernateDao.edit((Company) org);
		}
		if(org instanceof Department){
			hibernateDao.edit((Department) org);
		}
	}
	
	public List getAllWithoutNickInvoke(){
		List<Org> orgList = hibernateDao.getAllWithoutNickInvoke(Org.class);
		List<Org> currentList = new ArrayList<Org>();
		if (orgList != null) {
			for (Org org : orgList) {
				if (org.getDeletionFlag() == GlobalConstant.FALSE) {
					currentList.add(org);
				}
			}
		}
		
		return currentList;
	}

	public Org get(Integer orgId){
		if (orgId == null) {
			return null;
		}
		Org org = (Org) hibernateDao.get(Org.class,orgId);
		return org;
	}

	public Org getByName(String name){
		if (name == null || name.length()<1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", name);
		List orgs = find(parameters, "");
		if (orgs != null && orgs.size() > 0) {
			return (Org) orgs.get(0);
		}
		return null;
	}
		
	public List findByPage(TransMsg msg, String additionalCondition) {
		msg.put("deletionFlag", 0);
		List<Object> objList = (List<Object>) hibernateDao.findByPage("Org", msg, additionalCondition);
		return objList;
	}
	
		
	public List find(Map parameters, String additionalCondition) {
		parameters.put("deletionFlag", 0);
		List<Object> objList = (List<Object>) hibernateDao.find("Org", parameters, additionalCondition);
		return objList;
	}
	
	public ResponseData organizationCompetence(int departmentId){
		ResponseData rd = new ResponseData(0);
		Map parameters=new HashMap();
		parameters.put("id",departmentId);
		List orgList = this.find(parameters, "");
		if (orgList != null && orgList.size() > 0) {
			Org orgDepartment = (Org) orgList.get(0);

			// 当组织类型为公司并且上级部门为集团或者该部门的上级部门不是集团下的【岗位/部门/用户】不允许添加
			if ((orgDepartment.getOrgType().equals(
					GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY) && orgDepartment
					.getParentId() == GlobalConstant.DEFAULT_GROUP_ORG_ID)
					|| orgDepartment.getCompanyId() != GlobalConstant.DEFAULT_GROUP_ORG_ID) {
				rd.setCode(-10);
				return rd;
			}
		} else {
			rd.setCode(-11);
			rd.setMessage("找不到对应部门！");
			return rd;
		}
		
		return rd;
	}
	
		
}
