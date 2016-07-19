package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.ReportBase;
import com.ava.domain.vo.TestDrive4StatVO;

public interface IReportBaseDao {
	
	public List<ReportBase> findByPage(String nativeSql, TransMsg transMsg) ;
	
	public List<ReportBase> find(HashMap<String, ?> parameters, String additionalCondition);
	
	public List<ReportBase> findByPage(TransMsg msg, String additionalCondition);
	
	public int save(ReportBase reportBase);
	
	public void edit(ReportBase reportBase);		
	
	public ReportBase get(Integer reportBase);
	
	public void delete(Integer id);
	
	public void delete(ReportBase reportBase);
	
}
