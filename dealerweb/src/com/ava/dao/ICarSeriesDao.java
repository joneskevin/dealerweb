package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.CarSeries;

public interface ICarSeriesDao {
	
	public CarSeries get(Integer id);
	
	public CarSeries getCarSeriesName(String name);
    
	public int save(CarSeries carSeries);
	
	public void update(CarSeries carSeries);
	
	public void delete(Integer id);
	
	public List<CarSeries> find(Map<Object, Object> parameters, String additionalCondition);
	
	public List<CarSeries> findByPage(TransMsg msg, String additionalCondition);
}
