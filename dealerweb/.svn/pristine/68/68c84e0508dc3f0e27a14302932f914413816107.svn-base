package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ICompanyTypeDao;
import com.ava.domain.entity.CompanyType;

@Repository
public class CompanyTypeDao implements ICompanyTypeDao {
	
	@Autowired
	private IHibernateDao hibernateDao;

	public List<CompanyType> find(HashMap<Object, Object> parameters, String additionalCondition) {
		List<CompanyType> companySalesList = hibernateDao.find("CompanyType", parameters, additionalCondition);
		return companySalesList;
	}

}
