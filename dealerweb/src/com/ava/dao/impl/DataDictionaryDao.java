package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IDataDictionaryDao;
import com.ava.domain.entity.DataDictionary;
import com.ava.resource.DbCacheResource;

@Repository
public class DataDictionaryDao implements IDataDictionaryDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	public List getAll(){		
		Map parameters = new HashMap();
		List objList =  hibernateDao.find("DataDictionary", parameters, " order by parentId asc, rank asc,id asc");
		return objList;
	}
	
	public List getAllWithoutNickInvoke(){
		return hibernateDao.getAllWithoutNickInvoke(DataDictionary.class);
	}
	
	/**	注意：此方法返回的集合必须严格按照等级排序，否则如果把子项排在父项之前，构建树型时候会出错	*/
	public List find(Map parameters){
		List objList = hibernateDao.find("DataDictionary",parameters, " order by parentId asc, rank asc,id asc");
		//Collections.sort(objList);
		return objList;
	}
	
	public DataDictionary get(Integer id){
		return (DataDictionary)hibernateDao.get(DataDictionary.class,id);
	}
	
	public void delete(Integer id){
		hibernateDao.delete(DataDictionary.class,id);
		DbCacheResource.setWholeDataDictionary(getAll());
	}
	
    public void edit(DataDictionary data){
    	hibernateDao.edit(data);
		DbCacheResource.setWholeDataDictionary(getAll());
    }
	
    public Integer save(DataDictionary data){
    	Integer result = (Integer) hibernateDao.save(data);
		DbCacheResource.setWholeDataDictionary(getAll());
		return result;
    	
    }
}
