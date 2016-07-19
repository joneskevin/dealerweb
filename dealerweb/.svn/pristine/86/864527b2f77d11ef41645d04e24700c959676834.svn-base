package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.CompanyCarStyleRelation;

public interface ICompanyCarStyleRelationDao {
	
	public CompanyCarStyleRelation get(Integer id);
	
	public CompanyCarStyleRelation getByCarStyleId(Integer dealerId, Integer carStyleId);
    
	public int save(CompanyCarStyleRelation companyCarStyleRelation);
	
	public List<CompanyCarStyleRelation> find(Map parameters, String additionalCondition);
	
	public List<CompanyCarStyleRelation> findByPage(TransMsg msg, String additionalCondition);
	
	public void delete(Integer id);
}
