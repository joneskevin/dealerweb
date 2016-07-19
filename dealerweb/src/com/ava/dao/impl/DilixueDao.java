package com.ava.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IDilixueDao;
import com.ava.domain.entity.Dilixue;


@Repository
public class DilixueDao implements IDilixueDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public Integer save(Dilixue dilixue) {
		Integer result = (Integer) hibernateDao.save(dilixue);
		return result;
	}

}
