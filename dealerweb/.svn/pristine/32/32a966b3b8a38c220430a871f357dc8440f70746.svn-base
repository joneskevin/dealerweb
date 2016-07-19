package com.ava.base.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.db.HibernateHelper;

/**
 * 基本CURD操作Hibernate实现
 * 
 * @author wangxiaopeng
 * @CreateDate 2012-03-14
 * @version 1.0.01
 */
@Repository
public class HibernateDao extends HibernateDaoSupport implements IHibernateDao {

	public Serializable save(Object o) {
		return getHibernateTemplate().save(o);
	}

	public void saveOrUpdateAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public <T> Object merge(T entity) {
		return getHibernateTemplate().merge(entity);
	}

	public <T> void delete(Class<T> clazz, Serializable id) {
		Object obj = load(clazz, id);
		if (obj != null) {
			delete(obj);
		}
	}

	public <T> void delete(Class<T> clazz, List<T> ids) {
		if (ids == null) {
			return;
		}
		Iterator iter = ids.iterator();
		while (iter.hasNext()) {
			getHibernateTemplate().delete(get(clazz, (Serializable) iter.next()));
		}
	}

	public <T> void delete(Class<T> clazz, Integer[] ids) {
		if (ids == null) {
			return;
		}
		for (int i = 0; i < ids.length; i++) {
			getHibernateTemplate().delete(get(clazz, ids[i]));
		}
	}

	public <T> void delete(final String namedQuery, final List<T> ids) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.getNamedQuery(namedQuery);
				query.setParameterList("ids", ids);
				Iterator iter = query.list().iterator();
				while (iter.hasNext()) {
					session.delete(iter.next());
				}
				return null;
			}
		});
	}

	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	public void update(Object o) {
		getHibernateTemplate().update(o);
	}

	public int bulkUpdate(String queryString, Object... objs) {
		return getHibernateTemplate().bulkUpdate(queryString, objs);
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		try {
			getHibernateTemplate().clear();
		} catch (DataAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public void evict(Object t) {
		getHibernateTemplate().evict(t);
	}

	public void edit(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	public void edit(List objects) {
		if (objects == null) {
			return;
		}
		Iterator iter = objects.iterator();
		while (iter.hasNext()) {
			edit(iter.next());
		}
	}

	public void setCachable(boolean cacheQueries) {
		getHibernateTemplate().setCacheQueries(cacheQueries);
	}

	public void setCheckWriteOperations(boolean checkWriteOperations) {
		getHibernateTemplate().setCheckWriteOperations(checkWriteOperations);
	}

	public void setQueryCacheRegion(String queryCacheRegion) {
		getHibernateTemplate().setQueryCacheRegion(queryCacheRegion);
	}

	public <T> T get(Class<T> clazz, Serializable id) {
		T t = (T) getHibernateTemplate().get(clazz, id);
		HibernateHelper.invokeNickFunction(t);
		return t;
	}

	public <T> T get(String poName, HashMap parameters) {
		return (T) get(poName, parameters, null);
	}

	public <T> T get(String poName, HashMap parameters, String additionalCondition) {
		List objList = find(poName, parameters, additionalCondition, 0, Integer.MAX_VALUE);
		if (objList != null) {
			Object o = objList.get(0);
			try {
				HibernateHelper.invokeNickFunction(o);
				return (T) o;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public <T> T load(Class<T> clazz, Serializable id) {
		return (T) getHibernateTemplate().load(clazz, id);
	}

	public <T> List<T> getAll(Class<T> clazz) {
		List<T> list = getHibernateTemplate().loadAll(clazz);
		HibernateHelper.invokeNickFunction(list);
		return list;
	}

	public <T> List<T> getAllWithoutNickInvoke(Class<T> clazz) {
		List<T> list = getHibernateTemplate().loadAll(clazz);
		return list;
	}

	public <T> T getUnique(String hql, Object... params) {
		List<T> list = getHibernateTemplate().find(hql, params);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	public <T> T getUniqueByNamedParams(String queryString, String[] paramNames, Object[] values) {
		List<T> list = getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	public <T> T getUniqueByNamedParam(String queryString, String name, Object value) {
		List<T> list = getHibernateTemplate().findByNamedParam(queryString, name, value);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	public <T> Iterator<T> iterate(String queryString, Object... values) {
		return getHibernateTemplate().iterate(queryString, values);
	}

	public <T> List<T> list(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	public <T> List<T> find(String queryString, Object... params) {
		return getHibernateTemplate().find(queryString, params);
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return getHibernateTemplate().find("from " + entityClass.getCanonicalName() + " o");
	}

	public <T> List<T> find(String poName, Map parameters) {
		return find(poName, parameters, null, 0, Integer.MAX_VALUE);
	}

	public <T> List<T> find(String poName, Map parameters, String additionalCondition) {
		return find(poName, parameters, additionalCondition, 0, Integer.MAX_VALUE);
	}

	public <T> List<T> find(String poName, Map parameters, Integer fetchSize) {
		return find(poName, parameters, null, 0, fetchSize);
	}

	public <T> List<T> find(String poName, Map parameters, String additionalCondition, Integer fetchSize) {
		return find(poName, parameters, additionalCondition, 0, fetchSize);
	}

	public <T> List<T> find(String poName, Map parameters, String additionalCondition, Integer startIndex, Integer fetchSize) {
		List<String> fields = new LinkedList<String>();
		return find(fields, poName, parameters, additionalCondition, 0, fetchSize);
	}

	public <T> List<T> find(List<String> fields, String poName, Map parameters, String additionalCondition) {
		return find(fields, poName, parameters, additionalCondition, 0, Integer.MAX_VALUE);
	}

	public <T> List<T> find(List<String> fields, String poName, Map parameters, String additionalCondition, Integer startIndex, Integer fetchSize) {
		if (fetchSize == null || fetchSize == 0) {
			return null;
		}
		List<T> col = null;

		List poNames = new ArrayList<T>();
		poNames.add(poName);
		HibernateHelper.preHandle(fields, poNames, parameters);

		String qs = HibernateHelper.genQueryStringX(fields, poNames, parameters, null, additionalCondition);
		col = getCollectionByQuerystring(qs, parameters, startIndex, fetchSize);

		HibernateHelper.invokeNickFunction(col);
		List objList = null;
		if (col != null) {
			objList = (List) col;
		}
		return objList;
	}

	/**
	 * 根据当前页来获取对象集的方法，不进行session读写操作
	 * 
	 * @param poName
	 * @param transMsg
	 * @return
	 */
	public final <T> List<T> findByPage(String poName, TransMsg transMsg) {
		return this.findByPage(poName, transMsg, null);
	}

	public final <T> List<T> findByPage(String poName, TransMsg transMsg, String additionalCondition) {
		List<String> poNames = new ArrayList<String>();
		poNames.add(poName);
		return this.findByPage(poNames, transMsg, additionalCondition);
	}

	public final <T> List<T> findByPage(List<String> fields, String poName, TransMsg transMsg, String additionalCondition) {
		List<String> poNames = new ArrayList<String>();
		poNames.add(poName);
		return this.findByPage(fields, poNames, transMsg, additionalCondition);
	}

	private final <T> List<T> findByPage(List<String> poNames, TransMsg transMsg, String additionalCondition) {
		List<String> fields = new LinkedList<String>();
		return this.findByPage(fields, poNames, transMsg, additionalCondition);
	}

	private final <T> List<T> findByPage(List<String> fields, List<String> poNames, TransMsg transMsg, String additionalCondition) {
		List<T> col = null;
		if (poNames == null || transMsg == null) {
			return null;
		}
		try {
			Integer pageSize = transMsg.getPageSize();
			if(pageSize == null || pageSize.intValue() < 0){
				pageSize = 11;
			}
			Integer startIndex = transMsg.getStartIndex();
			if(startIndex == null || startIndex.intValue() < 0){
				startIndex = 0;
			}

			if (fields == null) {
				fields = new LinkedList<String>();
			}
			if (poNames == null) {
				poNames = new ArrayList<String>();
			}

			HibernateHelper.preHandle(fields, poNames, transMsg.getParameters());

			String qs = HibernateHelper.genQueryStringX(fields, poNames, transMsg.getParameters(), null, additionalCondition);
			col = getCollectionByQuerystring(qs, transMsg.getParameters(), startIndex, pageSize);

			HibernateHelper.invokeNickFunction(col);
			// 把记录总数存放在request中
			transMsg.setTotalRecords(findTotalSize(poNames, transMsg.getParameters(), additionalCondition));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return col;
	}

	public <T> List<T> findByPage(Class clazz, TransMsg transMsg, String sql, String countSql) {
		if (transMsg == null) {
			return null;
		}
		if (!transMsg.getParameters().isEmpty()) {
			throw new IllegalArgumentException("TransMsg中不能带查询参数，查询参数请拼成hql语句");
		}

		// 记录总数
		int totalRecords = getSqlCount(countSql);
		transMsg.setTotalRecords((long) totalRecords);

		// 查询数据
		List<T> list = findEntityPageBySQL(clazz, sql, transMsg.getStartIndex(), transMsg.getPageSize());
		HibernateHelper.invokeNickFunction(list);

		return list;
	}

	private Long findTotalSize(List<String> poNames, Map<Object, Object> parameters, String additionalCondition) {
		List<String> fields = new LinkedList<String>();
		fields.add("count(*)");
		if (poNames == null)
			poNames = new ArrayList<String>();
		HibernateHelper.preHandle(fields, poNames, parameters);

		Long tSize = new Long(0);
		String qs = HibernateHelper.genQueryStringX(fields, poNames, parameters, null, additionalCondition);
		try {
			tSize = getTotalSizeByQuerystring(qs, parameters);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return tSize;
	}

	private Long getTotalSizeByQuerystring(final String qs, final Map parameters) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery(qs);
				HibernateHelper.setQueryParameter(q, parameters);
				Long size;
				try {
					size = (Long) q.iterate().next();
				} catch (NoSuchElementException e) {
					size = new Long(0);
				}
				return size.longValue();
			}
		});
	}

	private <T> List<T> getCollectionByQuerystring(final String qs, final Map parameters, final int startIndex, final int pageSize) {
		Object tmpObject = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery(qs);
				HibernateHelper.setQueryParameter(q, parameters);
				q.setFirstResult(startIndex);
				q.setMaxResults(pageSize);
				return q.list();
			}
		});
		Collection tmpCollection;
		List<T> col;
		if (tmpObject instanceof Collection) {
			if (((Collection) tmpObject).size() > 0) {
				tmpCollection = (Collection) tmpObject;
				Iterator itor = tmpCollection.iterator();
				col = new ArrayList<T>();
				while (itor.hasNext()) {
					Object item = (Object) itor.next();
					col.add((T) item);
				}
				return col;
			}
		}
		return null;
	}

	public <T> List<T> excuteQuerystring(String hql) {
		return excuteQuerystring(hql, (Map) null);
	}

	public <T> List<T> excuteQuerystring(final String hql, final Map parameters) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery(hql);
				HibernateHelper.setQueryParameter(q, parameters);
				return q.list();
			}
		});
	}

	/** update语句的执行方法，返回受影响的记录数 */
	public int executeUpdate(final String hql) {
		Integer rows = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Integer updatedEntities = session.createQuery(hql).executeUpdate();
				return updatedEntities;
			}
		});

		if (rows == null) {
			rows = 0;
		}
		return rows.intValue();
	}

	/** 返回int型值，一般用于获得查询记录总数 */
	public int executeQueryInt(final String hql) {
		Long result = (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				return session.createQuery(hql).uniqueResult();
			}
		});

		return (int) result.longValue();
	}

	public String executeQueryString(final String hql) {
		List objList = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery(hql);
				return q.list();
			}
		});

		if (objList != null && objList.size() > 0) {
			Iterator itor = objList.iterator();
			while (itor.hasNext()) {
				return itor.next().toString();
			}
		}
		return "";
	}

	public List executeQueryList(final String hql) {
		List objList = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query q = session.createQuery(hql);
				return q.list();
			}
		});
		return objList;
	}

	/** 纯sql的query语句的执行方法，返回指定映射类的List型值 */
	public <T> List<T> executeSQLQueryList(final String sql) {
		List objList = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery q = session.createSQLQuery(sql);
				return q.list();
			}
		});
		return objList;
	}

	/** 纯sql的query语句的执行方法，返回指定映射类的List型值（查询的字段可以映射到entityClass类） */
	public <T> List<T> executeSQLQueryList(final String sql, final Class<T> entityClass) {
		List objList = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery q = session.createSQLQuery(sql);
				if (entityClass != null) {
					q.addEntity(entityClass);
				}
				return q.list();
			}
		});
		return objList;
	}

	/** 纯sql的query语句的执行方法*/
	public int executeSQLUpdate(final String sql) {
		Integer resultFlag = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery q = session.createSQLQuery(sql);
			    return q.executeUpdate();  
			}
		});
		return resultFlag == null ? -99 : resultFlag;
	}

	/**
	 *带参数的sql操作
	 * @author tangqingsong
	 * @version 
	 * @param sql
	 * @param argMap
	 * @return
	 */
	public int executeSQLUpdate(final String sql,final Map<String,?> argMap) {
		Integer resultFlag = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setProperties(argMap);
			    return q.executeUpdate();  
			}
		});
		return resultFlag == null ? -99 : resultFlag;
	}
	// ---------------------------------------------------------------------------

	public <T> List<T> findByCollection(final Class<T> t, final String propertyName, final Collection coll, final String... joinProperties) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(t);
				criteria.setCacheable(getHibernateTemplate().isCacheQueries());
				if (joinProperties != null) {
					for (int i = 0; i < joinProperties.length; i++) {
						criteria.setFetchMode(joinProperties[i], FetchMode.JOIN);
					}
				}
				if (propertyName.indexOf("id") != -1) {
					return criteria.add(Restrictions.in(propertyName, coll)).list();
				} else {
					return criteria.setFetchMode(propertyName, FetchMode.JOIN).createCriteria(propertyName).add(Restrictions.in("id", coll)).list();
				}
			}

		});
	}

	public <T> List<T> getSplitPage(final String hql, final int start, final int limit, final Object... values) {
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				if (null != values)
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				query.setCacheable(getHibernateTemplate().isCacheQueries());
				return query.setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public <T> List<T> getSplitPage(final Class<T> clazz, final int start, final int limit) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("select o from " + clazz.getSimpleName() + " o ");
				query.setCacheable(getHibernateTemplate().isCacheQueries());
				return query.setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public <T> List<T> getSplitPage(final Class<T> clazz, final int start, final int limit, final String sort, final String direc) {
		return getSplitPage(clazz, start, limit, sort, direc, null);
	}

	public <T> List<T> getSplitPage(final Class<T> clazz, final int start, final int limit, final String sort, final String direc, final String[] paramNames, final Object... values) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException {
				String sortField = null;
				String direct = null;
				if (null != sort) {
					sortField = sort;
				}
				if (null != direc) {
					direct = direc;
				}
				StringBuffer hql = new StringBuffer("select o from " + clazz.getSimpleName() + " o where 1=1");
				if (null != paramNames) {
					for (int i = 0; i < paramNames.length; i++) {
						hql.append(" and o." + paramNames[i] + " = ?");
					}
				}
				if (sortField != null)
					hql.append(" order by o." + sortField + " " + direct);
				Query query = session.createQuery(hql.toString());
				if (null != values)
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				query.setCacheable(getHibernateTemplate().isCacheQueries());
				return query.setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public long getTotal(Class clazz) {
		List list = getHibernateTemplate().find("select count(*) from " + clazz.getSimpleName() + " o");
		if (null != list && list.size() > 0)
			return (Long) list.get(0);
		return 0;
	}

	public long getTotal(String hql, Object... values) {
		List list = getHibernateTemplate().find(hql, values);
		if (null != list && list.size() > 0)
			return (Long) list.get(0);
		return 0;
	}

	public <T> T getBySQL(final String sql, final Object... values) {
		return (T) getBySQL(sql, values, null, null);
	}

	public <T> T getBySQL(final String sql, final Object[] values, final String[] aliases, final Class[] entityClasses) {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql);
				try {
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}
					}
					if (entityClasses != null) {
						for (int i = 0; i < aliases.length; i++) {
							query.addEntity(aliases[i], entityClasses[i]);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				query.setCacheable(getHibernateTemplate().isCacheQueries());
				return query.uniqueResult();
			}
		});
	}

	public <T> List<T> listBySQL(final String sql, final Object... values) {
		return listBySQL(sql, values, null, null);
	}

	public <T> List<T> listBySQL(final String sql, final Object[] values, final String[] aliases, final Class[] entityClasses) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql);
				try {
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}
					}
					if (entityClasses != null) {
						for (int i = 0; i < aliases.length; i++) {
							query.addEntity(aliases[i], entityClasses[i]);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				query.setCacheable(getHibernateTemplate().isCacheQueries());
				return query.list();
			}
		});
	}

	public <T> List<T> getSplitPageBySQL(final String sql, final int start, final int limit, final Object... values) {
		return getSplitPageBySQL(sql, start, limit, values, null, null);
	}

	public <T> List<T> getSplitPageBySQL(final String sql, final int start, final int limit, final Object[] values, final String[] aliases, final Class[] entityClasses) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql);
				try {
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}
					}
					if (entityClasses != null) {
						for (int i = 0; i < aliases.length; i++) {
							query.addEntity(aliases[i], entityClasses[i]);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				query.setFirstResult(start);
				query.setMaxResults(limit);
				query.setCacheable(getHibernateTemplate().isCacheQueries());
				return query.list();
			}
		});
	}

	public <T> List<T> findEntityPageBySQL(final Class<T> clazz, final String sql, final int start, final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql);
				if (clazz != null) {
					query.addEntity(clazz);
				}
				query.setFirstResult(start);
				query.setMaxResults(limit);
				query.setCacheable(getHibernateTemplate().isCacheQueries());
				return query.list();
			}
		});
	}

	public int getSqlCount(final String sql) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				return ((Number) session.createSQLQuery(sql).uniqueResult()).intValue();
			}
		});
	}

	public int executeSQL(final String sql, final Object... values) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Integer doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql);

				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}

	public int[] batchExecuteSQL(final String sql, final List<? extends Object> values) {
		return (int[]) getHibernateTemplate().execute(new HibernateCallback() {
			public int[] doInHibernate(Session session) {
				SQLQuery query = session.createSQLQuery(sql);

				if (values != null) {
					int[] resultInts = new int[values.size()];
					for (int i = 0; i < values.size(); i++) {
						Object value = values.get(i);
						if (value.getClass().isArray()) {
							Object[] params = (Object[]) value;
							for (int j = 0; j < params.length; j++) {
								query.setParameter(j, params[j]);
							}
						} else {
							query.setParameter(0, value);
						}
						resultInts[i] = query.executeUpdate();
					}
					return resultInts;
				} else {
					return new int[] { query.executeUpdate() };
				}
			}
		});
	}
	
	/**
	 * 
	* Description: 原生SQL语句的执行方法，返回指定映射类的List型值
	* 查询结果可以映射到VO【字段数量保持一致 且count、sum方法要映射到bigDecimal类型上】
	* @author henggh 
	* @version 0.1 
	* @param sql
	* @param entityClass
	* @return
	 */
	public <T> List<T> executeSQLQueryVoList(final String sql, final Class<T> entityClass) {
		List<T> resultList = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setResultTransformer(Transformers.aliasToBean(entityClass));
				return q.list();
			}
		});
		return resultList;
	}
	
	/**
	 * 
	* Description: 原生SQL语句的执行方法，返回指定映射类的List型值
	* 查询结果可以映射到VO【字段数量保持一致 且count、sum方法要映射到bigDecimal类型上】
	 * @author tangqingsong
	 * @version 
	 * @param sql
	 * @param entityClass
	 * @param argMap
	 * @return
	 */
	public <T> List<T> executeSQLQueryVoList(final String sql, final Class<T> entityClass,final Map<String,Object> argMap) {
		List<T> resultList = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery q = session.createSQLQuery(sql);
				q.setProperties(argMap);
				q.setResultTransformer(Transformers.aliasToBean(entityClass));
				return q.list();
			}
		});
		return resultList;
	}
}
