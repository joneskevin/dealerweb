package com.ava.gateway.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.gateway.dao.IProtocolMessageDao;
import com.ava.gateway.domain.entity.BoxUpdateMessage;
import com.ava.gateway.domain.entity.CurrentGpsMessage;
import com.ava.gateway.domain.entity.GetCommonMessage;
import com.ava.gateway.domain.entity.ObdDismantleAlertMessage;
import com.ava.gateway.domain.entity.SetConfigMessage;
import com.ava.gateway.domain.entity.TBoxStatusMessage;


@Repository("testDriver.web2gatewayDao")
public class ProtocolMessageDao implements IProtocolMessageDao{

	@Autowired
	private IHibernateDao hibernateDao;
	@Override
	public void saveGetCommonMessage(GetCommonMessage getCommonMessage) {
		hibernateDao.save(getCommonMessage);
	}
	
	@Override
	public void saveTBoxStatusMessage(TBoxStatusMessage tBoxStatusMessage) {
		hibernateDao.save(tBoxStatusMessage);
	}
	
	@Override
	public void saveSetConfigMessage(SetConfigMessage setConfigMessage) {
		hibernateDao.save(setConfigMessage);
	}
	
	@Override
	public void saveCurrentGpsMessage(CurrentGpsMessage currentGpsMessage) {
		hibernateDao.save(currentGpsMessage);
	}
	
	@Override
	public void saveBoxUpdateMessage(BoxUpdateMessage boxUpdateMessage) {
		hibernateDao.save(boxUpdateMessage);
	}
	

	public void saveGpsLocation(BoxUpdateMessage boxUpdateMessage) {
		hibernateDao.save(boxUpdateMessage);
	}
	
	public void saveObdDismantleAlertMessage(ObdDismantleAlertMessage obdDismantleAlertMessage) {
		hibernateDao.save(obdDismantleAlertMessage);
	}

	@Override
	public Queue<String> findMessage(Map<String, Object> argMap,String taskId) {
		String sql = "SELECT "+
							" t.vin,"+
							" t.SERIAL_NUMBER,"+
							" t.IMSI,"+
							" t.MESSAGE_CODE,"+
							" t.MESSAGE_DATE,"+
							" t.FIRE_STATE,"+
							" t.LNG,"+
							" t.LAT,"+
							" t.BAIDU_LNG,"+
							" t.BAIDU_LAT,"+
							" t.HEADING,"+
							" t.SPEED,"+
							" t.KO3_KILOMETER"+
							" FROM"+
							" t_message_current_gps t"+
							" WHERE 1=1 ";
		if(argMap.containsKey("startDate")){
			sql+= " AND t.MESSAGE_DATE >="+
					" '"+argMap.get("startDate")+"' ";
		}
		if(argMap.containsKey("endDate")){
			sql+= " AND t.MESSAGE_DATE <= "+
					" '"+argMap.get("endDate")+"'";
		}
		if(argMap.containsKey("vin")){
			sql+= " AND t.VIN = '"+argMap.get("vin")+"'";
		}
		if(argMap.containsKey("serialNumber")){
			sql+= " AND t.SERIAL_NUMBER = '"+argMap.get("serialNumber")+"'";
		}
		sql+=" order by t.MESSAGE_DATE asc";
		List<Object[]> list = hibernateDao.executeSQLQueryList(sql);
		if(list!=null){
			Queue<String> result = new LinkedList();
			for(int i=0;i<list.size();i++){
				Object[] cols = (Object[]) list.get(i);
				//组织json
				StringBuffer sb = new StringBuffer();
				sb.append("{\"header\":{\"vin\":\"").append(cols[0]).append("\",\"serialNumber\":\"").append(cols[1]).append("\",").
				append("\"imsi\":\"").append(cols[2]).append("\",\"messageCode\":").append(cols[3]).append(",\"timestamp\":\"").append(cols[4]).append("\"").append(",\"typeCode\":").append("\""+taskId+"\"},"+
						"\"body\":{\"data\":{\"fireState\":").append(cols[5]==null?"0":cols[5]).append(",\"lng\":").append(cols[6]).append(",\"lat\":").append(cols[7]).append(","+
						"\"baiduLng\":\""+cols[8]).append("\",\"baiduLat\":\"").append(cols[9]).append("\",\"heading\":").append(cols[10]).append(","+
						"\"speed\":").append(cols[11]).append(",\"ko3Kilometer\":").append(cols[12]).append("}}}");
				result.add(sb.toString());	
			//	System.out.println(sb.toString());
			}
			return result;
		}
		return null;
	}
	
	public List<Object> getIgnitionMessageList (String vin, String startTime, String endTime) {
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vin", vin);
		List<Object> objList = (List<Object>) hibernateDao.
				find("CurrentGpsMessage", parameters, " and messageCode='1281' and messageDate >='"+startTime+"' and messageDate <='"+endTime+"' order by messageDate");
		return objList;
	}
	
	public CurrentGpsMessage getNearlyGpsPointMessage (String vin, String startTime, String endTime, String orderBy) {
		CurrentGpsMessage result = null;
		
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vin", vin);
		StringBuffer additionalCondition = new StringBuffer();
		additionalCondition.append(" and messageCode='1284' and messageDate >='");
		additionalCondition.append(startTime);
		additionalCondition.append("' and messageDate <='");
		additionalCondition.append(endTime);
		additionalCondition.append("' order by messageDate ");
		if(StringUtils.isNotEmpty(orderBy)){
			additionalCondition.append(orderBy);
		}
		List<Object> objList = (List<Object>) hibernateDao.
				find("CurrentGpsMessage", parameters, additionalCondition.toString(), new Integer(1));
		if(null!=objList && objList.size()>0){
			result = (CurrentGpsMessage)objList.get(0);
		}
		
		return result;
	}
	
	public String findLastGpsDate(Map<String, Object> argMap) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("max(t.MESSAGE_DATE) ");
		sql.append("FROM ");
		sql.append("t_message_current_gps t ");
		sql.append("WHERE ");
		sql.append(" 1=1 ");
		if(argMap.containsKey("startDate")){
			sql.append(" AND t.MESSAGE_DATE >= ");
			sql.append(" '"+argMap.get("startDate")+"'  ");
		}
		if(argMap.containsKey("endDate")){
			sql.append(" AND t.MESSAGE_DATE <  ");
			sql.append(" '"+argMap.get("endDate")+"' ");
		}
		if(argMap.containsKey("vin")){
			sql.append(" AND t.VIN = '"+argMap.get("vin")+"' ");
		}
		List<Object> list = hibernateDao.executeSQLQueryList(sql.toString());
		if(list!=null && !list.isEmpty()){
			if(list.get(0)!=null){
				return list.get(0).toString();
			}
		}
		return null;
	}
}
