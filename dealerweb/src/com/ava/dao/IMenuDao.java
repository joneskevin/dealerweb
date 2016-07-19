package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Menu;

public interface IMenuDao {
	
	public List<Menu> find(HashMap parameters, String additionalCondition);
	
	public List<Menu> findByPage(TransMsg msg, String additionalCondition);
	
	public Menu getByName(String name);
	
	public int save(Menu menu);
	
	public void edit(Menu menu);		
	
	public Menu get(Integer menuId);
	
	public void delete(Integer id);
	
}
