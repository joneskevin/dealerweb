package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Fence;

public interface IFenceDao {
	
	public List find(HashMap parameters,String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public Fence getByName(String name);
	
	public int save(Fence fence);
	
	public void edit(Fence fence);		
	
	public Fence get(Integer fenceId);
	
	public void delete(Integer id);
	
}
