package com.ava.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;

import com.ava.api.vo.ProposalApiVo;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.User;

public class MyBeanUtils extends BeanUtils {
	static {
		// 为目标对象的Date类型属性设置转换器
		ConvertUtils.register(new Converter() {
			public Object convert(Class arg0, Object arg1) {
				if (arg1 == null) {
					return null;
				}
				if (arg1 instanceof Date) {
					return arg1;
				} else if (arg1 instanceof Timestamp) {
					return arg1;
				} else {
					String dateStr = arg1.toString();
					if (dateStr.trim().length() == 0) {
						return null;
					}
					return DateTime.toDate(dateStr);
				}
			}
		}, java.util.Date.class);

		// 为目标对象的Timestamp类型属性设置转换器
		ConvertUtils.register(new Converter() {
			public Object convert(Class arg0, Object arg1) {
				if (arg1 == null) {
					return null;
				}
				if (arg1 instanceof Date) {
					return arg1;
				} else if (arg1 instanceof Timestamp) {
					return arg1;
				} else {
					String dateStr = arg1.toString();
					if (dateStr.trim().length() == 0) {
						return null;
					}
					return DateTime.toDate(dateStr);
				}
			}
		}, Timestamp.class);
	}

	/**
	 * 克隆属性，主要包括基本类型、字符串、日期等
	 */
	private static Map<String, Object> cloneNotCollectionProperties(Object obj) {
		Map<String, Object> properties = null;
		try {
			Map<String, Object> tempProperties = BeanUtils.describe(obj);
			if (tempProperties == null) {
				return null;
			}
			properties = new HashMap();
			Iterator itor = tempProperties.entrySet().iterator();
			while (itor.hasNext()) {
				Entry entry = (Entry) itor.next();
				String key = (String) entry.getKey();
				String value = String.valueOf(entry.getValue());
				properties.put(key, entry.getValue());
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			if (obj == null) {
				return null;
			}
			// 获取对象的类属性
			Class clazz = obj.getClass();
			List<Field> fieldList = new ArrayList<Field>();
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				fieldList.addAll(Arrays.asList(fields));
			}
			for (int i = 0; i < 5 && clazz.getSuperclass() != null; i++) {
				clazz = clazz.getSuperclass();

				Field[] superFields = clazz.getDeclaredFields();
				if (superFields != null && superFields.length > 0) {
					fieldList.addAll(Arrays.asList(superFields));
				}
			}

			for (Field f : fieldList) {
				Class t = f.getType();
				String name = f.getName();
				String value = BeanUtils.getProperty(obj, name);
				if (value == null) {// 值为null忽略
					continue;
				}
				Object valueObj = null;
				if (t.getName().equals("boolean")
						|| t.getName().equals("java.lang.Boolean")) {
					valueObj = Boolean.valueOf(String.valueOf(value));
				} else if (t.getName().equals("char")
						|| t.getName().equals("java.lang.Char")) {
					valueObj = value;
				} else if ( t.getName().equals("java.lang.String")) {
					valueObj = value;
				} else if (t.getName().equals("byte")
						|| t.getName().equals("java.lang.Byte")) {
					valueObj = Byte.valueOf(String.valueOf(value));
				} else if (t.getName().equals("short")
						|| t.getName().equals("java.lang.Short")) {
					valueObj = Short.valueOf(String.valueOf(value));
				} else if (t.getName().equals("int")
						|| t.getName().equals("java.lang.Integer")) {
					valueObj = Integer.valueOf(String.valueOf(value));
				} else if (t.getName().equals("long")
						|| t.getName().equals("java.lang.Long")) {
					valueObj = Long.valueOf(String.valueOf(value));
				} else if (t.getName().equals("float")
						|| t.getName().equals("java.lang.Float")) {
					valueObj = Float.valueOf(String.valueOf(value));
				} else if (t.getName().equals("double")
						|| t.getName().equals("java.lang.Double")) {
					valueObj = Double.valueOf(String.valueOf(value));
				} else if (t.getName().equals("java.util.Date")) {
					valueObj = DateTime.toDate(String.valueOf(value));
				} else if (t.getName().equals("java.sql.Timestamp")) {
					valueObj = DateTime.toTimestamp(DateTime.toDate(String.valueOf(value)));
				} else {
					properties.remove(name);
					continue;
				}

				properties.put(name, valueObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 把一个新对象的属性字段值拷贝到另一个对象的方法
	 * 注意1：对象包含基本类型会丢失，比如int（包装类型不会，比如Integer）
	 * 注意2：当源对象属性字段值为null 或值为String[]且尺寸为0，则不拷贝此属性值
	 */
	public static void copyPropertiesContainsDate(Object dest, Object orig) {
		try {
			if (dest == null || dest.getClass() == null) {
				return;
			}
			// 由于PropertyUtils.copyProperties会把为null的基本数字类型置为0，故先备份
			Map<String, Object> destProperties = cloneNotCollectionProperties(dest);
			// 利用其拷贝集合类型
			BeanUtils.copyProperties(dest, orig);
			// 给dest恢复基本属性的值
			if (destProperties != null && destProperties.size() > 0) {
				Iterator itor = destProperties.entrySet().iterator();
				while (itor.hasNext()) {
					Entry entry = (Entry) itor.next();
					String name = (String) entry.getKey();
					Object valueObj = entry.getValue();
					if (valueObj == null) {
						continue;
					}
					try {
						PropertyUtils.setProperty(dest, name, valueObj);
					} catch (NoSuchMethodException e) {
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

			// 拷贝orig的基本属性的值
			Map<String, Object> origProperties = cloneNotCollectionProperties(orig);
			if (origProperties != null && origProperties.size() > 0) {
				Iterator itor = origProperties.entrySet().iterator();
				while (itor.hasNext()) {
					Entry entry = (Entry) itor.next();
					String name = (String) entry.getKey();
					Object valueObj = entry.getValue();
					if (valueObj == null) {
						continue;
					}
					try {
						PropertyUtils.setProperty(dest, name, valueObj);
					} catch (Exception e) {
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map toMapByClass(Class clazz, Object obj) {
		if (clazz == null || obj == null) {
			return null;
		}
		Map objMap = new HashMap();
		if (obj instanceof Object[]) {
			Object[] properties = (Object[]) obj;
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				String fName = f.getName();
				objMap.put(fName, properties[i]);
			}
		} else {
			throw new ConversionException("Could not convert "
					+ clazz.getName());
		}

		return objMap;
	}

	/***
	 * 把属性组成的数组对象转换成clazz指定的类的对象
	 * 
	 * @param clazz
	 *            指定类
	 * @param dest
	 *            要装成的指定类的对象
	 * @param orig
	 *            需要转换的属性数组
	 * @return
	 */
	public static Object toObjByClass(Class clazz, Object dest, Object orig) {
		try {
			Map objMap = MyBeanUtils.toMapByClass(clazz, orig);
			if (objMap != null) {
				MyBeanUtils.populate(dest, objMap);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return dest;
	}
	
	public static String toJson(Object obj) {
		// 设置JSON-LIB的setCycleDetectionStrategy属性让其自己处理循环，注意：数据过于复杂的话会引起数据溢出或者效率低下。
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONArray jsonArray = JSONArray.fromObject(obj, jsonConfig);
		String jsonStr = jsonArray.toString();
		return jsonStr;
	}
	
	public static String toJson(Map map) {
		JSONObject jsonObject = JSONObject.fromObject(map);
		String jsonStr = jsonObject.toString();
		return jsonStr;
	}

	public static void main(String[] args) {
		System.out.print("ok");
	}
}
