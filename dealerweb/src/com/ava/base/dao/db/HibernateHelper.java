package com.ava.base.dao.db;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;

import com.ava.base.dao.hibernate.QueryParameter;

/**
 * 非数据访问的方法
 * @author wangxiaopeng
 * @CreateDate 2012-03-14
 */
@SuppressWarnings("unchecked")
public class HibernateHelper {

	protected static final Log log = LogFactory.getLog(HibernateHelper.class);

	protected static final String DEFAULT_PO_ALIAS = "po";

	static public void preHandle(List<String> fields, List<String> poNames, Map<Object, Object> parametersMap) {

		if (log.isInfoEnabled()) {
			log.debug("::::::::::::::::::::::::Begin preHandle ::::::::::::::::::::::::::");
		}
		if (poNames == null || poNames.size() == 0) {
			throw new IllegalArgumentException(":::::::::::::::[preHandle]poNames is null or size is 0");
		}

		if (poNames.size() == 1) { // only one po
			if (log.isInfoEnabled()) {
				log.debug(":::::::::::::::::::: find[poNames.size()=1]:::::::::::::::::::::");
			}
			String poName = (String) poNames.iterator().next();
			if (poName.indexOf(' ') == -1) { // if po has no alias,we think
				// that fields has no alias too.

				poNames.clear();
				poNames.add(poName + " " + DEFAULT_PO_ALIAS); // PO alias

				// parameter ---------------------------------
				Map<Object, Object> tmpParametersMap = new HashMap<Object, Object>();
				if (parametersMap != null) {
					for (Iterator it = parametersMap.entrySet().iterator(); it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();

						Object oKey = entry.getKey();

						if (!(oKey instanceof String)) {
							continue;
						}
						String key = String.valueOf(oKey);
						Object value = entry.getValue();

						if (value != null) {
							if (value instanceof QueryParameter) {
								QueryParameter q = (QueryParameter) value;
								q.setPropertyName(DEFAULT_PO_ALIAS + "." + q.getPropertyName());
								tmpParametersMap.put(q.getParameterName(), q);
							} else {
								if (key.startsWith("po.")) {
									tmpParametersMap.put(key, value);
								} else {
									tmpParametersMap.put(DEFAULT_PO_ALIAS + "." + key, value);
								}
							}
						} else {
							log.debug(":::::::::::::::: key :" + key + ",value is null");
						}
					}

					parametersMap.clear();
					parametersMap.putAll(tmpParametersMap);
				} else {
					log.debug("::::::::::::parametersMap is null,ignore:::::::::::::::");
				}

				// field ---------------------------------------------
				List<String> tmpFields = new LinkedList<String>();
				if (fields != null) {
					for (Iterator it = fields.iterator(); it.hasNext();) {
						String fieldName = (String) it.next();
						if (fieldName.equalsIgnoreCase("count(*)")) {
							tmpFields.add(fieldName);
						} else {
							tmpFields.add(fieldName);
							// tmpFields.add(DEFAULT_PO_ALIAS + "." +
							// fieldName);
						}
					}
					fields.clear();
					fields.addAll(tmpFields);
				} else {
					log.debug("::::::::::::fields is null,ignore:::::::::::::::");
				}
			}
		} // size =1
		else {
			// size>1,no need to adjust.
		}
	}

	/**
	 * @param fields
	 *            - nullable,all fields,should like :
	 *            po1.id,po2.customerName,po3.companyName
	 * @param poNames
	 *            -List<String> should like : "CustomerPO customerPO" or
	 *            "CompanyPO companyPO" ...
	 * @param parametersMap
	 *            - query parameter Map<key,value>
	 * @param orderFields
	 *            - should like : "id etianxia" or "customerName desc"
	 * @param additionalCondition
	 *            -nullable
	 * @return
	 */
	static public String genQueryStringX(List fields, List poNames, Map parametersMap, List orderFields, String additionalCondition) {

		if (log.isInfoEnabled()) {
			log.debug(":::::::::::::::::::: begin genQueryStringX :::::::::::::::::::::");
		}

		StringBuffer buf = new StringBuffer();
		// process field list
		if (poNames == null || poNames.size() == 0) {
			throw new IllegalArgumentException("[genQueryStringX]poName is null or size is 0");
		}

		if (fields == null || fields.size() == 0) {

		} else {
			buf.append("select ");
			buf.append(join(fields, ",", null, null));
		}

		buf.append(" from ");

		// inner join /left outer join /right outer join
		buf.append(join(poNames, " left outer join ", null, null));
		buf.append(" where 1=1");

		// main part ----------------------------------------
		buf.append(genBasicCondition(parametersMap));

		if (additionalCondition != null) {
			// buf.append(" and (" + additionalCondition + ")");
			buf.append(" " + additionalCondition + "");
		}

		genOrderClause(orderFields);

		if (log.isInfoEnabled()) {
			log.debug(":::::::::::::::::QueryString is :" + buf.toString() + ":::::::::::::::::::::");
		}

		return buf.toString();

	}

	static private StringBuffer genOrderClause(List orderFields) {
		return genOrderClause(orderFields, DEFAULT_PO_ALIAS + ".", null);
	}

	/**
	 * generate order clause.
	 * 
	 * @param orderFields
	 * @return eg: order by po.id etianxia
	 */
	static private StringBuffer genOrderClause(List orderFields, String prefix, String suffix) {

		StringBuffer orderClause = join(orderFields, ",", prefix, suffix);

		if (orderClause.length() > 0) {
			orderClause.insert(0, " order by ");
		}

		if (log.isInfoEnabled()) {
			log.debug("[GenOrderClause]orderClause:" + orderClause.toString());
		}

		return orderClause;
	}

	/**
	 * return basic condition of query,used in GenQueryString & GenQueryStringX
	 * 
	 * @param parametersMap
	 *            <String key,Object value> or <String key,QueryParameter q>
	 * @return eg. key1=value1 and key2=value2 and key3 in (1,2,3) and key3 not
	 *         in (4)
	 */
	static private StringBuffer genBasicCondition(Map parametersMap) {

		StringBuffer debugBuf = new StringBuffer();

		if (log.isDebugEnabled()) {
			debugBuf.append("\n-----------------------------------------------------------");
			debugBuf.append("\tparameter\t\tcolumn\t\toperator\n");
			debugBuf.append("-----------------------------------------------------------");
		}

		if (log.isInfoEnabled()) {
			log.debug(":::::::::::::::::::: begin genBasicCondition ::::::::::::::::::::");
		}

		StringBuffer buf = new StringBuffer(1000);
		if (parametersMap == null) {
			log.debug("::::::::::::::::::parametersMap is null ,parameter ignored.:::::::::::::::::");
		} else {
			for (Iterator it = parametersMap.keySet().iterator(); it.hasNext();) {

				String propertyName = null;
				String operator = null;
				String parameterName = null;

				String key = (String) it.next();
				Object value = parametersMap.get(key);
				boolean hasParamter = true;
				boolean valueIsCollection = false;
				boolean valueIsIsBlankCollection = false;

				if (value != null && !"".equals(value)) {
					if (value instanceof QueryParameter) {

						QueryParameter q = (QueryParameter) value;
						value = q.getValue();

						if (value != null && !"".equals(value)) {
							propertyName = q.getPropertyName();
							operator = q.getOperator();
							parameterName = q.getParameterName().replace(".", "_");
						} else {
							hasParamter = false;
						}
					} else { // value is not null
						propertyName = key;
						operator = "=";
						// parameterName = key;
						// 把要赋值的参数名，从po.xxx的样式转为po_xxx的样式
						parameterName = key.replace(".", "_");
					}

					if (hasParamter) { // if value is instance of
						// QueryParameter

						if (QueryParameter.OPERATOR_IN.equals(operator) || QueryParameter.OPERATOR_NOT_IN.equals(operator)) {
							buf.append(" and " + propertyName + " " + operator + " " + "(:" + parameterName + ")");
						} else {
							if (value instanceof Collection) {
								valueIsCollection = true;
								if (((Collection) value).size() == 0) {
									valueIsIsBlankCollection = true;
								}
							} else if (value instanceof Object[]) {
								valueIsCollection = true;
								if (((Object[]) value).length == 0) {
									valueIsIsBlankCollection = true;
								}
							}
							if (valueIsIsBlankCollection) {
								buf.append(" and 1=0");
							} else {
								if (valueIsCollection) {
									buf.append(" and " + propertyName + " " + QueryParameter.OPERATOR_IN + " " + "(:" + parameterName + ")");
								} else {
									buf.append(" and " + propertyName + " " + operator + " " + ":" + parameterName);
								}
							}
						}
					}

					if (log.isDebugEnabled()) {
						debugBuf.append("\t" + parameterName + "\t\t" + propertyName + "\t\t" + operator + "\n");
					}

				}
			}
		}

		if (log.isInfoEnabled()) {
			log.debug(debugBuf.toString());
		}
		return buf;
	}

	/**
	 * *************************************************************************
	 * *******************
	 * 
	 * setQueryParameter
	 * 
	 * *************************************************************************
	 * *******************
	 * 
	 * @version 1.0
	 * @param q
	 * @param parametersMap
	 * @throws HibernateException
	 */
	public static void setQueryParameter(Query q, Map parametersMap) throws HibernateException {

		StringBuffer debugBuf = new StringBuffer();
		if (log.isInfoEnabled()) {
			log.debug(":::::::::::::::::::: begin genBasicCondition ::::::::::::::::::::");
			debugBuf.append("\n-----------------------------------------------------------");
			debugBuf.append("\tparameter\t\t\t\tvalue\n");
			debugBuf.append("-----------------------------------------------------------");

		}

		if (parametersMap == null) {
			log.debug("::::::::::::::::::parametersMap is null ,parameter ignored.:::::::::::::::::");
			return;
		}
		for (Iterator it = parametersMap.keySet().iterator(); it.hasNext();) {
			String pName = (String) it.next();
			// 把赋值的参数名，从po.xxx的样式转为po_xxx的样式
			String pValueName = pName.replace(".", "_");
			Object pValue = parametersMap.get(pName);
			if (pValue == null || "".equals(pValue)) {
				log.debug("::::::::::::::parameterKey->" + pName + " value is null,ignore!:::::::::::::::::");
			} else {

				if (pValue instanceof QueryParameter) {
					pName = ((QueryParameter) pValue).getParameterName();
					pValue = ((QueryParameter) pValue).getValue();
				}

				if (pValue == null || "".equals(pValue)) {
					log.debug("::::::::::::::parameterkey->" + pName + " value is null,ignore!:::::::::::::::::");
				} else {
					Type pType = getHibernateType(pValue);

					if (pType == null) {
						if (pValue instanceof Collection) {

							if (((Collection) pValue).size() > 0) {
								q.setParameterList(pValueName, (Collection) pValue);
							}
							if (log.isDebugEnabled()) {
								debugBuf.append("\t" + pName + "\t\t\t\t" + pValue + "\n");
							}

						} else if (pValue instanceof Object[]) {

							if (((Object[]) pValue).length > 0) {
								List<Object> list = new LinkedList<Object>();

								Object[] os = (Object[]) pValue;
								for (int i = 0; i < os.length; i++) {
									list.add(os[i]);
								}
								q.setParameterList(pValueName, list);

								if (log.isDebugEnabled()) {
									debugBuf.append("\t" + pName + "\t\t\t\t" + list + "\n");
								}
							} else {
								log.debug(":::::::::::::::" + pName + "<Object[]> size is 0,ignore!:::::::::::::::");
							}
						}
					} else { // pType != null
						q.setParameter(pValueName, pValue);

						if (log.isDebugEnabled()) {
							debugBuf.append("\t" + pName + "\t\t\t\t" + pValue + "\n");
						}

					}
				}

			}
		}
	}

	/**单个对象的nick方法*/
	public static void invokeNickFunction(Object obj) {
		if (obj != null) {
			List<Object> objList = new ArrayList<Object>();
			if (obj != null && obj instanceof Collection) {
				objList.addAll((Collection<? extends Object>) obj);
			} else {
				objList.add(obj);
			}
			invokeNickFunction(objList);
		}
	}

	/**
	 * 如果只调用本查询对象的nick方法可以在这个类中实现，但还要调用其级联对象的nick方法的时候，要在具体相应域对象中实现。
	 * 
	 * @param objList
	 */
	static protected void invokeNickFunction(Collection objList) {
		try {
			if (objList != null) {
				Iterator iterator = objList.iterator();

				Class[] cargs = new Class[1];
				cargs[0] = (new Integer(1)).getClass();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					if (obj != null) {
						try {
							obj.getClass().getMethod("nick").invoke(obj);
						} catch (NoSuchMethodException noSuchMethodException) {
							continue;
						}
					}
				}
			}
		} catch (InvocationTargetException e) {
			log.error("invokeNickFunction errors:" + e.getTargetException());
			//e.getTargetException().printStackTrace();
		} catch (Exception e) {
			log.error("invokeNickFunction errors:" + e);
			//e.printStackTrace();
		}
	}

	/**
	 * return basic hibernate type,if object is collection ...,will return null
	 * 
	 * @version 1.0
	 * @param object
	 * @return
	 */
	static private Type getHibernateType(Object object) {

		if (object != null) {
			return TypeFactory.basic(object.getClass().getName());
		}
		return null;
	}

	/**
	 * @author join element of collection with delimiter. modified by 2006-3-7
	 *         18:04 ,add prefix & suffix parameter
	 * @param collection
	 * @param delimiter
	 * @param prefix
	 *            -- if not null,it will be inserted at the begining of each
	 *            element.
	 * @param suffix
	 *            -- if not null,it will be appended at the end of each element
	 * @return
	 */
	static private StringBuffer join(Collection c, String delimiter, String prefix, String suffix) {

		StringBuffer buf = new StringBuffer();
		if (c != null && c.size() > 0) {
			Iterator it = c.iterator();
			if (it.hasNext()) {
				if (prefix != null)
					buf.append(prefix);
				buf.append(String.valueOf(it.next()));
				if (suffix != null) {
					buf.append(suffix);
				}
			}
			while (it.hasNext()) {
				Object value = it.next();
				buf.append(delimiter);
				if (prefix != null) {
					buf.append(prefix);
				}
				if (value != null) {
					buf.append(String.valueOf(value));
				} else {
					buf.append(String.valueOf(""));
				}
				if (suffix != null) {
					buf.append(suffix);
				}
			}
		}

		return buf;
	}
}
