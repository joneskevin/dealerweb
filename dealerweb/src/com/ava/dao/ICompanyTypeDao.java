package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.domain.entity.CompanyType;


public interface ICompanyTypeDao {
	
	public List<CompanyType> find(HashMap<Object, Object> parameters, String additionalCondition);
	
}
