package com.ava.admin.dao.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.admin.dao.ICurrentGpsMessageDao;
import com.ava.admin.domain.vo.CurrentGpsMessageVo;
import com.ava.base.dao.IHibernateDao;
import com.ava.gateway.domain.entity.CurrentGpsMessage;
import com.ava.util.DateTime;

@Repository("currentGpsMessageDao")
public class CurrentGpsMessageDaoImpl implements ICurrentGpsMessageDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Override
	public List<CurrentGpsMessageVo> findGpsMessage(Map<String, Object> argMap) {
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
				" t.KO3_KILOMETER,"+
				" t.CREATE_TIME"+
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
			List<CurrentGpsMessageVo> result = new LinkedList();
			for(int i=0;i<list.size();i++){
				Object[] cols = (Object[]) list.get(i);
				CurrentGpsMessageVo gps = new CurrentGpsMessageVo();
				gps.setVin((String) cols[0]);
				gps.setSerialNumber((String) cols[1]);
				gps.setImsi((String) cols[2]);
				gps.setMessageCode((Integer) cols[3]);
				Date messageDate = (Date) cols[4];
				gps.setMessageDate(DateTime.getDate(messageDate, DateTime.PATTERN_DATETIME));
				gps.setFireState((Integer) (cols[5]==null?0:cols[5]));
				gps.setLng((String) cols[6]);
				gps.setLat((String) cols[7]);
				gps.setBaiduLng((String) cols[8]);
				gps.setBaiduLat((String) cols[9]);
				gps.setHeading((int) (cols[10]==null?0:cols[10]));
				gps.setSpeed((Double)cols[11]);
				gps.setKo3Kilometer((Double)cols[12]);
				Date createDate = (Date) cols[13];
				gps.setCreateTime(DateTime.getDate(createDate, DateTime.PATTERN_DATETIME));
				result.add(gps);
			}
			return result;
		}
		return null;
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
//				System.out.println(sb.toString());
			}
			return result;
		}
		return null;
	}
	
	
	public List<CurrentGpsMessage> findCurrentGpsMessage(Map<String, Object> argMap){
		StringBuffer condtion = new StringBuffer();
		if(argMap.containsKey("startDate")){
			condtion.append(" and messageDate >='"+argMap.get("startDate")+"'");
		}
		if(argMap.containsKey("endDate")){
			condtion.append(" and messageDate <='"+argMap.get("endDate")+"'");
		}
		if(argMap.containsKey("vin")){
			condtion.append(" and vin='"+argMap.get("vin")+"'");
		}
		condtion.append(" order by messageDate");
		List<CurrentGpsMessage> objList = hibernateDao.
				find("CurrentGpsMessage", null, condtion.toString());
		return objList;
	}
	
	public void saveCurrentGpsMessage(CurrentGpsMessage gpsMessage){
		hibernateDao.save(gpsMessage);
	}
	
	public IHibernateDao getHibernateDao() {
		return hibernateDao;
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

}