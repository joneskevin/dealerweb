package com.ava.base.dao.db;

import com.ava.util.MyStringUtils;

/**
 * @author
 * 创建时间 May 18, 2010 1:24:02 PM
 * @version 1.0.31
 */
public class MySQLHelper implements IDBHelper {
	
	public String wrapToPageSql(String sql, int startIndex, int pageSize) {
		return sql + " limit " + startIndex + "," + pageSize;
	}
	
	public String wrapToTotalSql(String sql) {
		if (sql == null || sql.trim().length() <= 0){
			return null;
		}
		String upperCaseSql = sql.toUpperCase();
		int lastLimit = MyStringUtils.lastIndexOf(upperCaseSql, " LIMIT ");
		int endIndex;
		if (lastLimit > -1){
			//如果存在limit，则说明有分页函数，需要去除(注意，由于mysql允许limit 50这样的写法，所以不能通过逗号来判断limit是否存在)
			endIndex = lastLimit;
		}else{
			endIndex = sql.length();
		}
		String result = "select count(1) from (" + MyStringUtils.substring(sql, 0, endIndex) + ") as t;";
		result = result.trim();
		return result;
	}
	
	public static void main(String[] args) {
		MySQLHelper dbHelper = new MySQLHelper();
		System.out.println(dbHelper.wrapToTotalSql("select * from user;"));
		System.out.println(dbHelper.wrapToTotalSql("select * from user where userId=3434 Limit 0,50;"));
		System.out.println(dbHelper.wrapToTotalSql("select * From user where userId=3434 and slimit=3 limit 20;"));
	}
	
}
