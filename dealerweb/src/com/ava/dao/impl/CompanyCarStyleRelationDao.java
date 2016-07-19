package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.ICompanyCarStyleRelationDao;
import com.ava.domain.entity.CompanyCarStyleRelation;

@Repository
public class CompanyCarStyleRelationDao implements ICompanyCarStyleRelationDao {
	
	@Autowired
	private IHibernateDao hibernateDao;

	public CompanyCarStyleRelation get(Integer id) {
		if (id == null) {
			return null;
		}
		CompanyCarStyleRelation obj = (CompanyCarStyleRelation) hibernateDao.get(CompanyCarStyleRelation.class, id);
		return obj;
	}
	
	public CompanyCarStyleRelation getByCarStyleId(Integer dealerId, Integer carStyleId){
		if (dealerId == null || dealerId.intValue() < 1) {
			return null;
		}
		if (carStyleId == null || carStyleId.intValue() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", dealerId);
		parameters.put("carStyleId", carStyleId);
		List<CompanyCarStyleRelation> objList = find(parameters, " order by id desc");
		if (objList != null && objList.size() > 0) {
			return (CompanyCarStyleRelation) objList.get(0);
		}
		return null;
	}

	@Override
	public int save(CompanyCarStyleRelation companyCarStyleRelation) {
		if (companyCarStyleRelation == null) {
			return 0;
		}
		return (Integer) hibernateDao.save(companyCarStyleRelation);
	}

	@Override
	public List<CompanyCarStyleRelation> findByPage(TransMsg msg, String additionalCondition) {
		List<CompanyCarStyleRelation> objList = (List) hibernateDao.findByPage("CompanyCarStyleRelation",
				msg, additionalCondition);
		return objList;

	}

	@Override
	public List<CompanyCarStyleRelation> find(Map parameters, String additionalCondition) {
		List<CompanyCarStyleRelation> objList = (List) hibernateDao.find("CompanyCarStyleRelation", parameters,
				additionalCondition);
		return objList;
	}
	
	@Override
	public void delete(Integer id) {
		hibernateDao.delete(CompanyCarStyleRelation.class, id);
	}
}
