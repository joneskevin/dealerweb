package com.ava.util.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *  将二维数组对象和class实体类对应上
 */
public class ArrayDataToClass {

	// 获得对应的字段 截取属性前面的前缀
	public static String sublength(String temp) {
		return temp.substring(temp.lastIndexOf(".") + 1);
	}

	// 获得对应的字段 截取属性前面的前缀
	public static String sublengthShuxing(String temp) {
		String tempStr = temp.substring(temp.indexOf("java"),
				temp.indexOf("com") - 1);
		return tempStr;
	}
	
	//类型转换  供rovoke 函数的参数使用 
	public static Object changeTpye(String value,Class clasz) throws ParseException {
		Object toValue = new Object();
		if (clasz.equals(Integer.class)) {
			toValue = Integer.parseInt(value);
		} else if (clasz.equals(String.class)) {
			toValue = value;
		} else if (clasz.equals(Long.class)) {
			toValue = Long.parseLong(value);
		} else if (clasz.equals(Date.class)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
			Date date = sdf.parse(value);
			toValue = date;
		}
		// TODO boolean类型处理
//		else if (clasz.equals(boolean.class)) {
//			toValue = Long.parseLong(value);
//		} 
		return toValue;
	}

	public <T> List<T> getEntityList(String[][] result, String entityName) throws ParseException {
		Class<?> clazz;
		T t = null;
		try {
			clazz = Class.forName(entityName);
			t = (T) clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();

			List<T> listDilixue = new ArrayList<T>();
			//存储的属性值 如name
			ArrayList<String> cunchuField = new ArrayList<String>();
			// 对应cunchunField所对应的类型 如Field为name 类型为String
			ArrayList<String> shuxingField = new ArrayList<String>();
			Method s = null;

			for (int i = 0; i < result[0].length; i++) {
				for (Field field : fields) {
					if (sublength(field.toGenericString()).equals(result[0][i])) {
						cunchuField.add(result[0][i]);
						shuxingField.add(sublengthShuxing(field
								.toGenericString()));
					}
				}
			}

			// 获得excel中字段对应的set方法集合
			ArrayList<Method> methodList = new ArrayList<Method>();
			for (int i = 0; i <= cunchuField.size() - 1; i++) {
				try {
					// 属性要加上
					Class<?> clasz = Class.forName(shuxingField.get(i));
					s = clazz.getDeclaredMethod(StringHelper
							.asserSetMethodName(StringHelper
									.toJavaAttributeName(cunchuField.get(i))),
							clasz);
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
				try {
					//Method方法集合
					methodList.add(s);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}

			// 对二维数组解析 调用对应方法创建对象 对应到相应的entity
			for (int i = 1; i <= result.length - 1; i++) {
				// 要-2才可以, result会多出一列 空值
				for (int j = 0; j <= result[i].length - 2; j++) {
					try {
						// 方法调用 invoke
						String value = result[i][j];
						Class<?> clasz = Class.forName(shuxingField.get(j));

						((methodList.get(j))).invoke(t, changeTpye(value,clasz));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				listDilixue.add(t);
				t = (T) clazz.newInstance();
			}


			return listDilixue;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;

	}

//	public static <T> void main(String[] args) throws FileNotFoundException,
//			IOException, ParseException {
//	
//		// // String[][] result = { {"id" ,"chinese", "English" }, {"1", "中文",
//		// "zhongwen" },
//		// // {"2", "英语", "yingyu" }, {"3", "历史", "lishi" } };
//	
//		File file = new File("D:\\new2.xlsx");
//		String[][] result = ExcelOperate.getData(file, 0);
//
//		String entityName = "com.ava.util.excel.Dilixue";
//
//		ArrayDataToClass tmp = new ArrayDataToClass();
//		List<T> resultlist = tmp.getEntityList(result, entityName);
//
//		for (T d : resultlist) {
//			System.out.println(((Dilixue) d).getId() + "  "
//					+ ((Dilixue) d).getChinese() + "  "
//					+ ((Dilixue) d).getEnglish() + "  "
//					+ ((Dilixue) d).getContent()+ "  "
//					+ ((Dilixue) d).getTime());
//		}
//	}
}
