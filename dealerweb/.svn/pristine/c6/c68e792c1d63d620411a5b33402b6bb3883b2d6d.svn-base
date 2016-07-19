package com.ava.base.dao.jdbc;

import com.ava.base.dao.db.IDBHelper;
import com.ava.base.dao.db.MySQLHelper;


public class JdbcDao4mysql extends AbstractJdbcDao {	
	private IDBHelper dbHelper = new MySQLHelper();
	
	@Override
	protected String wrapToPageSql(String sql, int startIndex, int pageSize) {
		return dbHelper.wrapToPageSql(sql, startIndex, pageSize);
	}

	@Override
	protected String wrapToTotalSql(String sql) {
		return dbHelper.wrapToTotalSql(sql);
	}

}
