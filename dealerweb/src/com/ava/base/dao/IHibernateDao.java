/**
 * 
 */
package com.ava.base.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
/**
 * @author public
 */
public interface IHibernateDao {
    /**	HibernateDao的方法	*/ 
    public <T> Serializable save(T o);
    
    /**	HibernateDao的方法	
     * @param <T>*/
    public <T> void delete(Class<T> clazz, Serializable id);
    /**	HibernateDao的方法	*/
    public <T> void delete(Class<T> clazz, List<T> ids);
    /**	HibernateDao的方法	*/
    public <T> void delete(Class<T> clazz, Integer[] ids);
    /**	HibernateDao的方法	*/
    public <T> void delete(T o);
    
    /**	HibernateDao的方法	*/
    public <T> void update(T o);
    /**	HibernateDao的方法	*/
    public <T> void edit(T o);
    /**	HibernateDao的方法	*/
    public <T> void edit(List<T> l);
    
    public <T> Object merge(T entity);

    /**	HibernateDao的方法	*/
    public <T> List<T> getAll(Class<T> clazz);
    /**	HibernateDao的方法	*/
    public <T> List<T> getAllWithoutNickInvoke(Class<T> clazz);
    /**	HibernateDao的方法	*/
	public <T> T get(Class<T> clazz, Serializable id);
    /**	HibernateDao的方法	*/
	public <T> T get(String poName, HashMap parameters);
    /**	HibernateDao的方法	*/
	public <T> T get(String poName, HashMap parameters, String additionalCondition);

    /**	HibernateDao的方法	*/
    public <T> List<T> find(String poName, Map parameters);
    /**	HibernateDao的方法	*/
    public <T> List<T> find(String poName, Map parameters, String additionalCondition);
    /**	HibernateDao的方法	*/
	public <T> List<T> find(String poName,Map parameters,Integer fetchSize);
    /**	HibernateDao的方法	*/
	public <T> List<T> find(String poName,Map parameters,String additionalCondition,Integer fetchSize);
	
	public <T> List<T> find(String poName, Map parameters ,String additionalCondition ,Integer startIndex ,Integer fetchSize);
	
	public <T> List<T> find(List<String> fields, String poName, Map parameters ,String additionalCondition);
	
    /**	HibernateDao的方法	*/
    public <T> List<T> findByPage(String poName, TransMsg transMsg);
    /**	HibernateDao的方法	*/
    public <T> List<T> findByPage(String poName, TransMsg transMsg, String additionalCondition);
    /**	HibernateDao的方法	*/
    public <T> List<T> findByPage(List<String> fields, String poName, TransMsg transMsg, String additionalCondition);
    /** 根据hql分页查询，主要解决多表联合分页查询以及排序的问题
     * <br>注意：TransMsg只是用来传递通用的分页参数用，不能带其他查询参数，查询参数需要拼成hql语句！！！
     *  */
    public <T> List<T> findByPage(Class clazz, TransMsg transMsg, String hql, String countHql);

    /**	执行hql查询的方法	*/
    public <T> List<T> excuteQuerystring(final String hql);    
    /**	执行hql查询的方法	*/
    public <T> List<T> excuteQuerystring(final String hql, Map parameters);
    /**	执行hql更新的方法	*/
	public int executeUpdate(final String hql);
    /**	执行hql查询的方法	*/
	public int executeQueryInt(final String hql);
    /**	执行hql查询的方法	*/
	public String executeQueryString(final String hql);
    /**	执行hql查询的方法	*/
	public List executeQueryList(final String hql);

	/**	纯sql的query语句的执行方法	*/
	public <T> List<T> executeSQLQueryList(final String sql);	
	/**	纯sql的query语句的执行方法，返回指定映射类的List型值（查询的字段可以映射到entityClass类）	*/
	public <T> List<T> executeSQLQueryList(final String sql,Class<T> entityClass);
	/**	纯sql的query语句的执行方法	*/
	public int executeSQLUpdate(final String sql);
	
	public int getSqlCount(final String sql);
	
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
	public <T> List<T> executeSQLQueryVoList(final String sql, final Class<T> entityClass);
	
	/**
	 * 批量更新或insert使用
	 *
	 * @author wangchao
	 * @version 
	 * @param queryString
	 * @param objs
	 * @return
	 */
	public int bulkUpdate(String queryString, Object... objs);
	
	/**
	 *带参数的sql操作
	 * @author tangqingsong
	 * @version 
	 * @param sql
	 * @param argMap
	 * @return
	 */
	public int executeSQLUpdate(final String sql,final Map<String,?> argMap) ;
	
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
	public <T> List<T> executeSQLQueryVoList(final String sql, final Class<T> entityClass,final Map<String,Object> argMap);

}
