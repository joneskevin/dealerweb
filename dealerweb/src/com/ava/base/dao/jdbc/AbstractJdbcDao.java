package com.ava.base.dao.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ava.base.dao.IJdbcDao4mysql;
import com.ava.base.dao.hibernate.TransMsg;

/**
 * @author
 * 创建时间 Mar 18, 2010 3:00:49 PM
 * @version 1.0.01
 */
public abstract class AbstractJdbcDao extends JdbcDaoSupport implements IJdbcDao4mysql {

	protected final static Logger logger = Logger.getLogger(AbstractJdbcDao.class);
	
	public AbstractJdbcDao(){
		
	}
	
	public List<Map<String,Object>> queryForPage(String sql, TransMsg transMsg){
		// 把记录总数存放在transMsg中
		transMsg.setTotalRecords(queryForLong(wrapToTotalSql(sql)));
		
		int startIndex = 0;
		if(transMsg.getStartIndex() != null && transMsg.getStartIndex().intValue() > 0){
			startIndex = transMsg.getStartIndex().intValue();
		}
		int pageSize = 10;
		if(transMsg.getPageSize() != null && transMsg.getPageSize().intValue() > 0){
			pageSize = transMsg.getPageSize().intValue();
		}
		return super.getJdbcTemplate().queryForList(wrapToPageSql(sql, startIndex, pageSize));
	}

	abstract protected String wrapToPageSql(String sql, int startIndex, int pageSize);
	
	abstract protected String wrapToTotalSql(String sql);

	public Class getSupportDaoType() {
		return super.getClass();
	}

	public boolean execute(String sql, Object value) {
		return execute(sql, new Object[]{value});
	}

	public void execute(String sql) {
		getJdbcTemplate().execute(sql);
	}

	public void batchUpdate(String sqls) {
		String[] sqlArray = sqls.split(";");
		if (sqlArray.length > 0) {
			batchUpdate(sqlArray);
		}
	}

	public <T> List<T> list(Class<T> entityClass, String sql){
		if (logger.isDebugEnabled()){
			logger.debug("List-SQL:"+sql);
		}
		return super.getJdbcTemplate().query(sql, RowMapperFactory.getRowMapper(entityClass));
	}
	
	public <T> List<T> list(Class<T> entityClass, String sql, Object[] values){
		if (logger.isDebugEnabled()){
			logger.debug("List-SQL:"+sql);
		}
		return super.getJdbcTemplate().query(sql, values, RowMapperFactory.getRowMapper(entityClass));
	}

	public <T> List<T> queryForList(Class<T> type, String sql) {
		return getJdbcTemplate().queryForList(sql, type);
	}

	public <T> List<T> queryForList(Class<T> type, String sql,
			Object[] values) {
		return getJdbcTemplate().queryForList(sql, values, type);
	}

	public List<Map<String, Object>> queryListForMultiFields(String sql) {
		return queryListForMultiFields(sql, null);
	}

	public List<Map<String, Object>> queryListForMultiFields(String sql, Object[] values) {
		return getJdbcTemplate().queryForList(sql, values);
	}

	public Map<String, Object> queryOneForMultiFields(String sql) {
		return queryOneForMultiFields(sql, null);
	}

	public Map<String, Object> queryOneForMultiFields(String sql, Object[] values) {
		List<Map<String, Object>> mapList = queryListForMultiFields(sql, values);
		if(mapList != null && mapList.size() > 0){
			return mapList.get(0);
		}
		return null;
	}

	public int queryForInt(String sql, Object... value) {
		return getJdbcTemplate().queryForInt(sql, value);
	}

	public Integer queryForInt(String sql) {
		try{
			return getJdbcTemplate().queryForInt(sql);
		}catch (EmptyResultDataAccessException e1){
			logger.info(sql);
			return null;
		}
	}

	public long queryForLong(String sql, Object... value) {
		return getJdbcTemplate().queryForLong(sql, value);
	}

	public Long queryForLong(String sql) {
		try{
			return getJdbcTemplate().queryForLong(sql);
		}catch (EmptyResultDataAccessException e1){
			logger.info(sql);
			return null;
		}
	}

	public String queryForString(String sql) {
		try{
			return getJdbcTemplate().queryForObject(sql, String.class);
		}catch (EmptyResultDataAccessException e1){
			logger.info(sql);
			return null;
		}
	}

	public <T> T queryForObject(Class<T> type, String sql) {
		return getJdbcTemplate().queryForObject(sql, type);
	}
	
	public <T> T queryForObject(Class<T> type, String sql, Object[] values) {
		return getJdbcTemplate().queryForObject(sql, values, type);
	}
	
	public int[] batchUpdate(String[] sql){
		return getJdbcTemplate().batchUpdate(sql);
	}
	
	public void setMaxRows(int maxRows){
		getJdbcTemplate().setMaxRows(maxRows);
	}
	
	public int getMaxRows(){
		return getJdbcTemplate().getMaxRows();
	}
	
	public void setFetchSize(int fetchSize){
		getJdbcTemplate().setFetchSize(fetchSize);
	}
	
	public int getFetchSize(){
		return getJdbcTemplate().getFetchSize();
	}
}
