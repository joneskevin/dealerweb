package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.IJdbcDao4mysql;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IReportBaseDao;
import com.ava.domain.entity.ReportBase;

@Repository
public class ReportBaseDao implements IReportBaseDao
{
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IJdbcDao4mysql jdbcDao4mysql;
	
	@Override
	public List<ReportBase> find(HashMap parameters, String additionalCondition) {
		List<ReportBase> objList =  hibernateDao.find("ReportBase", parameters, additionalCondition);
		return objList;
	}
	
	public List<ReportBase> findByPage(TransMsg transMsg, String additionalCondition) {
		List<ReportBase> objList = hibernateDao.findByPage("ReportBase", transMsg, additionalCondition);
		return objList;
	}
	
	
	public int save(ReportBase reportBase) {
		if (reportBase != null) {
			try {
				return (Integer) hibernateDao.save(reportBase);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(ReportBase reportBase) {
		if (reportBase != null) {
			hibernateDao.edit(reportBase);
		}
	}
	
	public ReportBase get(Integer reportBaseId) {
		if (reportBaseId == null) {
			return null;
		}
		ReportBase reportBase = (ReportBase) hibernateDao.get(ReportBase.class, reportBaseId);
		return reportBase;
	}
	
	public void delete(Integer id) {
		hibernateDao.delete(ReportBase.class, id);
	}

	@Override
	public void delete(ReportBase reportBase) {
		hibernateDao.delete(reportBase);
		
	}
	
	@Override
	public List<ReportBase> findByPage(String nativeSql, TransMsg transMsg) {
		List<ReportBase> reportBaseList = null;
		List<Map<String, Object>> mapObjs = jdbcDao4mysql.queryForPage(nativeSql, transMsg);
		if(mapObjs != null && mapObjs.size() > 0){
			reportBaseList = new ArrayList();
			ReportBase reportBase = new ReportBase();
			for(Map properties : mapObjs){
				try {
					//MyBeanUtils.populate(reportBase, properties);
					reportBase.setSaleCenterName(properties.get("saleCenterName").toString());
					reportBase.setProvinceName(properties.get("provinceName").toString());
					reportBase.setCityName(properties.get("cityName").toString());
					reportBase.setDealerCode(properties.get("dealerCode").toString());
					reportBase.setCompanyName(properties.get("companyName").toString());
					reportBase.setIsKeyCityNick(properties.get("isKeyCityNick").toString());
					reportBase.setDealerTypeNick(properties.get("dealerTypeNick").toString());
					reportBase.setCarStyleName(properties.get("carStyleName").toString());
					reportBase.setRealityConfigCount(Integer.valueOf(properties.get("realityConfigCount").toString()));
				} catch (Exception e) {
					e.printStackTrace();
				} 
				reportBaseList.add(reportBase);
			}
		}
		return reportBaseList;
	}
	
}
