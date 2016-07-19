package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.domain.entity.DataDictionary;

public interface IDataDictionaryDao  {
	
	public List getAll();
	
	public List getAllWithoutNickInvoke();
		
	public List find(Map parameters);
	
	public DataDictionary get(Integer id);
	
    public void delete(Integer id);
	
    public void edit(DataDictionary data);
	
    public Integer save(DataDictionary data);
    
}
