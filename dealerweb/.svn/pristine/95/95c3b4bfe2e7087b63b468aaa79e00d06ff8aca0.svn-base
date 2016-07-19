package com.ava.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IReportDao;

@Repository
public class ReportDao implements IReportDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	/*
	 * 配置报表 
	 */
	
	public List queryByYear(String excuteSql) {
		return hibernateDao.executeSQLQueryList(excuteSql);
//		return hibernateDao.executeSQLQueryList(excuteSql);
							
	}

}
