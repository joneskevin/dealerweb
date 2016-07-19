package com.ava.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Company;


public interface ICompanyDao {
	
	public Integer save(Company company);
	
	public void delete(Integer id);
	
	public void edit(Company company);
	
	public List findNatively(Map parameters);
	
	public Company get(Integer id);
	
	public Company getByObName(String obName);
	
	public Company getByDealerCode(String dealerCode);
	
	public List find(HashMap parameters, String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public List findExist(HashMap parameters, String additionalCondition);
	
	public List<String> getByDealerCodeList(String dealerCode);
}
