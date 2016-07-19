package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.BigAreaReportItem;

public interface IBigAreaReportItemDao {
	public List<BigAreaReportItem> find(HashMap parameters, String additionalCondition);
	
	public List<BigAreaReportItem> findByPage(TransMsg msg, String additionalCondition);
	
	public int save(BigAreaReportItem bigAreaReportItem);
	
	
	public void delete(Integer id);
	
	public void delete(BigAreaReportItem bigAreaReportItem);
	
}
