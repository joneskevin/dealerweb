package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.CarStyle;

public interface ICarStyleDao {
	
	public CarStyle get(Integer id);
	
	public CarStyle getCarStyleName(String name);
    
	public List<Integer> findIdsByBrandId(Integer brandId, Integer level);
	
	public int save(CarStyle carStyle);
	
	public void update(CarStyle carStyle);
	
	public void delete(Integer id);
	
	public List<CarStyle> find(Map parameters, String additionalCondition);
	
	public List<CarStyle> findByPage(TransMsg msg, String additionalCondition);
}
