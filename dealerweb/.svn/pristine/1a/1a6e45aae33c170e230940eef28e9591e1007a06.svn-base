package com.ava.domain.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.ava.util.MyStringUtils;

public abstract class AbstractLog implements ILog, Serializable {
	
	public static class OperactionObjProperty{
		public OperactionObjProperty(String name, String nick){
			this.name = name;
			this.nick = nick;
		}

		private String name;

		private String nick;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}
	}
	
	public static OperactionObjProperty[] getPropertiesByActionId(String actionId) {
		EOperationObject eoo = getEOperationObjectByActionId(actionId);
		if(eoo == null){
			return null;
		}
		String operationObjId = eoo.getId();
		EOperationObject[] values = EOperationObject.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equalsIgnoreCase(operationObjId)) {
				return values[i].getDisplayableProperties();
			}
		}
		return null;
	}
	
	private static OperactionObjProperty[] getPropertiesByClassName(Class clazz) {
		EOperationObject[] values = EOperationObject.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getClazz().isAssignableFrom(clazz)) {
				return values[i].getDisplayableProperties();
			}
		}
		return null;
	}

	/**
	 * 通过actionId得到操作对象
	 * @param actionId
	 * @return 
	 */
	public static EOperationObject getEOperationObjectByActionId(String actionId) {
		if(actionId == null){
			return null;
		}
		
		String simpleName = actionId.substring(actionId.indexOf("_")+1);
		EOperationObject[] values = EOperationObject.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getClazz().getSimpleName().equalsIgnoreCase(simpleName)) {
				return values[i];
			}
		}
		return null;
	}
	/**
	 * 通过actionId得到操作对象的Class
	 * @param actionId
	 * @return 
	 */
	public static Class getClassByActionId(String actionId) {
		if(actionId != null){
			return null;
		}
		
		String simpleName = actionId.substring(actionId.indexOf("_")+1);
		EOperationObject[] values = EOperationObject.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getClazz().getSimpleName().equalsIgnoreCase(simpleName)) {
				return values[i].getClazz();
			}
		}
		return null;
	}
	/**
	 * 得到可显示的属性集合
	 * 
	 * @param targetObj
	 * @return
	 */
	public static Map getDisplayableProperties(Object targetObj) {
		if (targetObj == null) {
			return null;
		}
		Map propertyMap = new HashMap();
		OperactionObjProperty[] properties = getPropertiesByClassName(targetObj.getClass());
		if(properties != null){
			for (int i=0; i < properties.length; i++) {
				try {
					String key = properties[i].getNick();
					String value = BeanUtils.getProperty(targetObj, properties[i].getName());
					propertyMap.put(key, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return propertyMap;
	}
	/**
	 * 得到可显示的属性集合
	 * 
	 * @param oldObj
	 * @param newObj
	 * @return
	 */
	public static Map getDisplayableProperties(Object oldObj, Object newObj) {
		if (oldObj == null || newObj == null) {
			return null;
		}
		Map propertyMap = new HashMap();
		OperactionObjProperty[] properties = getPropertiesByClassName(oldObj.getClass());
		if(properties != null){
			for (int i=0; i < properties.length; i++) {
				try {
					String key = properties[i].getNick();
					String value1 = BeanUtils.getProperty(oldObj, properties[i].getName());
					String value2 = BeanUtils.getProperty(newObj, properties[i].getName());
					String value = "";
					if(! MyStringUtils.isBlank(value1)){
						value = value1;
					}
					if(! MyStringUtils.isBlank(value1) && ! MyStringUtils.isBlank(value2)){
						value = value + "-->";
					}
					if(! MyStringUtils.isBlank(value2)){
						value = value + value2;
					}
					propertyMap.put(key, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return propertyMap;
	}
}
