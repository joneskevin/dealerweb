package com.ava.base.dao.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.QueryParameter;
import com.ava.resource.SelectOptionResource;
import com.ava.util.TypeConverter;

public class ParameterUtil {
	/**	
	 * @首先从request.getParameter中获取参数值
	 * @如果没有则从request.getAttribute中获取
	 */
	public static Integer getIntegerParameter(HttpServletRequest request,
			String parameterKey){
		String value = request.getParameter(parameterKey);
		Integer result = null;
		try {
			result = TypeConverter.toInteger(value);
		} catch (Exception e) {
			result = null;
		}	

		if(result == null){
			Object valueObj = request.getAttribute(parameterKey);
			result = TypeConverter.toInteger(valueObj);
		}
		/**
		if(result == null){
			Object valueObj = request.getSession().getAttribute(parameterKey);
			result = TypeConverter.toInteger(valueObj);
		}	*/
		return result;
	}
	
	/**	
	 * @首先从request.getParameter中获取参数值
	 * @如果没有则从request.getAttribute中获取
	 */
	public static String getStringParameter(HttpServletRequest request,
			String parameterKey){
		String value = request.getParameter(parameterKey);
		if(value == null || value.length() == 0){
			Object valueObj = request.getAttribute(parameterKey);
			if (valueObj!=null){
				value = TypeConverter.toString(valueObj);
			}
		}
		return value==null ? "" : value.trim();
	}
	/**	得到ajax方式发送的字符串，处理了":"改成"%"，用来传递中文字符	*/
	public static String getAjaxDecodedStringParameter(HttpServletRequest request, String parameterKey){
		String value = null;
		String decodedValue = null;
		try {
			value = (String) request.getParameter(parameterKey);
			if (value != null && !"null".equalsIgnoreCase(value)) {
				decodedValue = value.replaceAll(":", "%");
				try {
					decodedValue = URLDecoder.decode(decodedValue, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else{
				decodedValue = "";
			}
		} catch (Exception e) {
			
		}
		return decodedValue;
	}
	
	/**	给搜索条件对象增加指定id的搜索范围(如果多次针对同一个字段设置集合值，则对集合进行“与”操作)*/
	public static void appendInQueryCondition(Map<Object,Object> parameters,String fieldName,Collection fieldValue){
		if (fieldValue != null && fieldValue.size() > 0) {
			Object valueObj = null;
			QueryParameter oldQp = (QueryParameter)parameters.get(fieldName);
			if ((fieldValue instanceof Collection) && oldQp != null) {
				Collection oldPks = (Collection)oldQp.getValue();
				valueObj = TypeConverter.pksAndpks(oldPks, fieldValue);
			}else{
				valueObj = fieldValue;
			}
			QueryParameter para = new QueryParameter(fieldName,QueryParameter.OPERATOR_IN,valueObj);
			parameters.put(fieldName, para);
		}
	}
	/**	给搜索条件对象增加不等于条件的方法	*/
	public static void appendNotEqualQueryCondition(Map<Object,Object> parameters,String fieldName,Integer fieldValue){
		if (fieldValue != null && fieldValue.intValue() > 0) {
			QueryParameter para = new QueryParameter(fieldName,QueryParameter.OPERATOR_NEQ,fieldValue);
			parameters.put(fieldName, para);
		}
	}
	public static void appendNotEqualQueryCondition(Map<Object,Object> parameters,String fieldName,String fieldValue){
		if (fieldValue != null && fieldValue.length() > 0) {
			QueryParameter para = new QueryParameter(fieldName,QueryParameter.OPERATOR_NEQ,fieldValue);
			parameters.put(fieldName, para);
		}
	}

	/**	给搜索条件对象增加大于等于条件的方法	*/
	public static void appendGreaterEqualQueryCondition(Map<Object,Object> parameters,String fieldName,Integer fieldValue){
		if (fieldValue != null && fieldValue.intValue() > 0) {
			QueryParameter para = new QueryParameter(fieldName,QueryParameter.OPERATOR_GE,fieldValue);
			parameters.put(fieldName, para);
		}
	}
	
	/**	给搜索条件对象增加小于条件的方法	*/
	public static void appendLessQueryCondition(Map<Object,Object> parameters,String fieldName,Integer fieldValue){
		if (fieldValue != null && fieldValue.intValue() > 0) {
			QueryParameter para = new QueryParameter(fieldName,QueryParameter.OPERATOR_LT,fieldValue);
			parameters.put(fieldName, para);
		}
	}
	
	/**	给搜索条件对象增加模糊搜索(like)条件的方法	*/
	public static void appendLikeQueryCondition(Map<Object,Object> parameters,String fieldName,Object fieldValue){
		if (fieldValue != null && fieldValue.toString().length() > 0) {
			String fieldValueToString = fieldValue.toString().trim();
			QueryParameter para = new QueryParameter(fieldName,QueryParameter.OPERATOR_LIKE,"%"+fieldValueToString+"%");
			parameters.put(fieldName, para);
		}
	}
	
	public static void appendTimeConfineQueryCondition(Map<Object,Object> parameters,String fieldName,String startTime,String endTime){
		if (parameters != null && fieldName != null) {
			if (startTime != null && startTime.length() == 10) {
				Timestamp startTimestamp = TypeConverter.toTimestamp(startTime+" 00:00:00");
				QueryParameter para20 = new QueryParameter(fieldName,
						"startTimestamp", QueryParameter.OPERATOR_GE,
						startTimestamp);
				parameters.put("startTimestamp", para20);
			}
			
			if (endTime != null && endTime.length() == 10) {
				Timestamp endTimestamp = TypeConverter.toTimestamp(endTime+" 23:59:59");
				QueryParameter para21 = new QueryParameter(fieldName,
						"endTimestamp", QueryParameter.OPERATOR_LE,
						endTimestamp);
				parameters.put("endTimestamp", para21);
			}			
			
			if (startTime != null && startTime.length() == 19) {
				Timestamp startTimestamp = TypeConverter.toTimestamp(startTime);
				QueryParameter para20 = new QueryParameter(fieldName,
						"startTimestamp", QueryParameter.OPERATOR_GE,
						startTimestamp);
				parameters.put("startTimestamp", para20);
			}
			
			if (endTime != null && endTime.length() == 19) {
				Timestamp endTimestamp = TypeConverter.toTimestamp(endTime);
				QueryParameter para21 = new QueryParameter(fieldName,
						"endTimestamp", QueryParameter.OPERATOR_LE,
						endTimestamp);
				parameters.put("endTimestamp", para21);
			}			
		}
	}
	
		
	public static void appendConfineQueryCondition(Map<Object,Object> parameters,String fieldName,Integer startValue,Integer endValue){
		if (parameters != null && fieldName != null) {
			if (startValue != null) {
				QueryParameter para20 = new QueryParameter(fieldName,
						"startValue"+fieldName, QueryParameter.OPERATOR_GE,
						startValue);
				parameters.put("startValue"+fieldName, para20);
			}
			
			if (endValue != null ) {
				QueryParameter para21 = new QueryParameter(fieldName,
						"endValue"+fieldName, QueryParameter.OPERATOR_LE,
						endValue);
				parameters.put("endValue"+fieldName, para21);
			}			
		}
	}
		
	public static void appendConfineQueryCondition(Map<Object,Object> parameters,String fieldName,Long startValue,Long endValue){
		if (parameters != null && fieldName != null) {
			if (startValue != null) {
				QueryParameter para20 = new QueryParameter(fieldName,
						"startValue"+fieldName, QueryParameter.OPERATOR_GE,
						startValue);
				parameters.put("startValue"+fieldName, para20);
			}
			
			if (endValue != null ) {
				QueryParameter para21 = new QueryParameter(fieldName,
						"endValue"+fieldName, QueryParameter.OPERATOR_LE,
						endValue);
				parameters.put("endValue"+fieldName, para21);
			}			
		}
	}
		
	public static void appendConfineQueryCondition(Map<Object,Object> parameters,String fieldName,String[] confineArray,Integer confineId){
		if(confineArray == null || confineId == null || confineId == 0){
			return;
		}
		String[] pair = SelectOptionResource.getOptionTextPairByValue(confineArray,confineId);
		if (pair != null && pair.length > 1) {
			Integer startValue = TypeConverter.toInteger(pair[0]);
			Integer endValue = TypeConverter.toInteger(pair[1]);
			appendConfineQueryCondition(parameters,fieldName,startValue,endValue);
		}
	}
}
