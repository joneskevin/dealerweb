package com.ava.dao;

import java.util.List;

import com.ava.domain.vo.VehicleConfigCount;


public interface IVehicleConfigDao {
	
	public <T> List<T> executeSQLQueryList(final String sql);
	
	public <T> List<T> executeSQLQueryList(final String sql,Class<T> entityClass);
	
	public int getSqlCount(final String sql);
}
