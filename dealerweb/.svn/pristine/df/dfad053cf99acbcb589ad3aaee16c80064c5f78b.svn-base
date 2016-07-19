package com.ava.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IVehicleConfigDao;

@Repository("dealer.vehicleConfigDao")
public class VehicleConfigDao implements IVehicleConfigDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	public <T> List<T> executeSQLQueryList(final String sql){
		return hibernateDao.executeSQLQueryList(sql);
	}
	
	/**	纯sql的query语句的执行方法，返回指定映射类的List型值（查询的字段可以映射到entityClass类）	*/
	public <T> List<T> executeSQLQueryList(final String sql,Class<T> entityClass){
		return 	hibernateDao.executeSQLQueryList(sql, entityClass);
	}
	
	public int getSqlCount(final String sql){
		return hibernateDao.getSqlCount(sql);
	}
}
