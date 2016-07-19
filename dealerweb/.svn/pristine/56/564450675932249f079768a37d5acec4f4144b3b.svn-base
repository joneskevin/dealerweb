package com.ava.gps;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.base.dao.IHibernateDao;
import com.ava.util.MyStringUtils;


/** 分表自动创建工具 */
public class CreateSubtableTool extends TestBase {
	@Autowired
	private IHibernateDao hibernateDao;
	
	//@Test
	public void createSubtable() {
		int GPS_SUB_TABLE_MAX = 1000*10;

		String origTableName = "gps_location_template";
		
		for(int i=60000; i<GPS_SUB_TABLE_MAX; i++){
			String destTableName = "gps_location_" + MyStringUtils.leftPad(String.valueOf(i+1), String.valueOf(GPS_SUB_TABLE_MAX).length(), "0");;
			String sql = "create table if not exists " + destTableName + " like " + origTableName;
			hibernateDao.executeSQLUpdate(sql);
		}
	}
	
	@Test
	public void insertData() {
		int GPS_DATA_MAX = 1;//一辆车每年大概1千万条
		
		for(int i=0; i<GPS_DATA_MAX; i++){
			//String destTableName = "gps_location_" + MyStringUtils.leftPad(String.valueOf(i+1), String.valueOf(GPS_SUB_TABLE_MAX).length(), "0");;
			String sql = "insert into gps_location_00009(id, lon, lat) values(" + (i+1) + ",123,31321);"; 
			//hibernateDao.executeSQLUpdate(sql);
		}
	}


}
