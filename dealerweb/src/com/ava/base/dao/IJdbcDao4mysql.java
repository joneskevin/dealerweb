package com.ava.base.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;

public interface IJdbcDao4mysql {
	
	public List<Map<String,Object>> queryForPage(String sql, TransMsg transMsg);
	
	public Map<String, Object> queryOneForMultiFields(String sql);
	
	public Map<String, Object> queryOneForMultiFields(String sql, Object[] values);
	
	public List<Map<String, Object>> queryListForMultiFields(String sql);
	
	public List<Map<String, Object>> queryListForMultiFields(String sql, Object[] values);
	
	public Integer queryForInt(String sql);
	
	public Long queryForLong(String sql);
	
	public String queryForString(String sql);
	
	public int queryForInt(String sql, Object... value);
	
	public void execute(String sql);
	
	public void batchUpdate(String sqls);
	
}
