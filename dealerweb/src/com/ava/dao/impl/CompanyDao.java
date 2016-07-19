package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.dao.ICompanyDao;
import com.ava.domain.entity.Company;
import com.ava.resource.cache.CacheOrgManager;

@Repository
public class CompanyDao implements ICompanyDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	public Integer save(Company company){
		Integer objId = null;
		if (company!=null){
			if (company.getDeletionFlag()==null ){
				company.setDeletionFlag(0);
			}
			try {
				objId = (Integer) hibernateDao.save(company);
				
				CacheOrgManager.clear();
			} catch (Exception e) {
			}
		}
		
		return objId;
	}
	
	public void delete(Integer id){
		hibernateDao.delete(Company.class, id);
		
		CacheOrgManager.clear();
	}
	public void edit(Company company){
		hibernateDao.edit(company);
		
		CacheOrgManager.clear();
	}
	
	public List findNatively(Map parameters) {
		List<Object> objList = (List<Object>) hibernateDao.find("Company", parameters);
		return objList;
	}

	public Company get(Integer id) {
		if (id == null) {
			return null;
		}
		Company org = (Company) hibernateDao.get(Company.class, id);
		return org;
	}
	
	public Company getByObName(String obName){
		if (obName == null || obName.length()<1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("obName", obName);
		List orgs =  hibernateDao.find("Company", parameters, "");
		if (orgs != null && orgs.size() > 0) {
			return (Company) orgs.get(0);
		}
		return null;
	}
	public List<String> getByDealerCodeList(String dealerCode){
		if (dealerCode == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		ParameterUtil.appendLikeQueryCondition(parameters, "dealerCode",
				dealerCode.trim());
		List orgs =  hibernateDao.find("Company", parameters, "");
		List ids = new ArrayList();
		if (orgs != null && orgs.size() > 0) {
			for(int i= 0;i<orgs.size();i++)
			{
				ids.add(((Company)orgs.get(i)).getId());
			}
			return ids;
		}
		return null;
	}
	
	public Company getByDealerCode(String dealerCode){
		if (dealerCode == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("dealerCode", dealerCode);
		List orgs =  hibernateDao.find("Company", parameters, "");
		if (orgs != null && orgs.size() > 0) {
			return (Company) orgs.get(0);
		}
		return null;
	}
	
	public List findByPage(TransMsg msg, String additionalCondition) {
//		msg.put("deletionFlag", 0);
		List<Object> objList = (List<Object>) hibernateDao.findByPage("Company", msg, additionalCondition);
		return objList;
	}
	
	public List find(HashMap parameters, String additionalCondition) {
		parameters.put("deletionFlag", 0);
		List<Object> objList = (List<Object>) hibernateDao.find("Company", parameters, additionalCondition);
		return objList;
	}
	
	public List findExist(HashMap parameters, String additionalCondition) {
//		parameters.put("deletionFlag", 0);
		List<Object> objList = (List<Object>) hibernateDao.find("Company", parameters, additionalCondition);
		return objList;
	}
}
