package com.ava.base.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * 创建时间 Mar 18, 2010 3:27:02 PM
 * @version 1.0.01
 */
public abstract class ReflectUtils {
	/**
	 * 调用字段名对象的setter方法，也有可能只是以set*的普通方法
	 * @param fieldName 字段名，也可能只是一个普通的名字
	 * @param type setter方法类型
	 * @param instance 调用实例
	 * @param args setter方法参数
	 * @return setter方法调用后的返回值，通常为null
	 */
	public static Object callSetMethod(String fieldName, Class type, Object instance, Object... args){
		Object result = null;
		String mn = "set"+StringUtils.capitalize(fieldName);
		try{
			Method method = getMethod(instance.getClass(), mn, type);
			result = method.invoke(instance, args);
		}catch(IllegalArgumentException e){
			throw new RuntimeException("参数不正确[MethodName:"+mn+",Type:"+type.getCanonicalName()+",ArgumentType:"+args[0].getClass().getCanonicalName()+"]", e);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 根据字段名调用对象的getter方法，如果字段类型为boolean，则方法名可能为is开头，也有可能只是以setFieleName的普通方法
	 * @param fieldName
	 * @param instance
	 * @return getter方法调用后的返回值
	 */
	public static Object callGetMethod(String fieldName, Object instance){
		Object result = null;
		try{
			String mn = "get"+StringUtils.capitalize(fieldName);
			Method method = null;
			try{
				method = getMethod(instance.getClass(), mn);
			}catch(NoSuchMethodException nsme){
				mn = "is"+StringUtils.capitalize(fieldName);
				method = getMethod(instance.getClass(), mn);
			}
			result = method.invoke(instance, new Object[]{});
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 判断是否含有某方法
	 * @param fieleName
	 * @param instance
	 * @return true：含有某方法；fasle：不含有某方法
	 */
	public static boolean hasGetMethod(String fieleName, Object instance){
		return hasGetMethod(fieleName, instance, new Object[]{});
	}
	/**
	 * 判断是否含有某方法
	 * @param fieleName
	 * @param instance
	 * @param args 方法调用参数
	 * @return true：含有某方法；fasle：不含有某方法
	 */
	public static boolean hasGetMethod(String fieleName, Object instance, Object[] args){
		try{
			String mn = "get"+StringUtils.capitalize(fieleName);
			Method method = null;
			try{
				method = getMethod(instance.getClass(), mn);
			}catch(NoSuchMethodException nsme1){
				mn = "is"+StringUtils.capitalize(fieleName);
				try{
					method = getMethod(instance.getClass(), mn);
				}catch(NoSuchMethodException nsme2){
					return false;
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(), e);
		}
		return true;
	}

	/**
	 * 
	 * @param fieleName
	 * @param instance
	 * @param args 方法调用参数
	 * @return
	 */
	public static Object callGetMethod(String fieleName, Object instance, Object[] args){
		Object result = null;
		try{
			String mn = "get"+StringUtils.capitalize(fieleName);
			Method method = null;
			try{
				method = getMethod(instance.getClass(), mn);
			}catch(NoSuchMethodException nsme){
				mn = "is"+StringUtils.capitalize(fieleName);
				method = getMethod(instance.getClass(), mn);
			}
			result = method.invoke(instance, args);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 得到给定的类或其父类中声明的字段
	 * @param entityClass 类
	 * @param fieldname 字段名
	 * @param caseSensitive 是否大小写敏感
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getField(Class<?> entityClass, String fieldname, boolean caseSensitive) throws NoSuchFieldException {
		if(caseSensitive){
			Field f = entityClass.getDeclaredField(fieldname);
			if(f != null){
				return f;
			}
		}else{
			Field[] fs = entityClass.getDeclaredFields();
			for(int i=0; i<fs.length; i++){
				Field f = fs[i];
				if(fieldname.toLowerCase().equals( f.getName().toLowerCase() )){
					return f;
				}
			}
		}
		if(entityClass.getSuperclass() != null && entityClass.getSuperclass() != Object.class){
			return getField(entityClass.getSuperclass(), fieldname, caseSensitive);
		}
		return null;
	}
	/**
	 * 得到给写的类或其父类中声明的方法
	 * @param entityClass
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(Class<?> entityClass, String methodName, Class... type) throws NoSuchMethodException {
		try{
			Method m = entityClass.getDeclaredMethod(methodName, type);
			if(m != null){
				return m;
			}
		}catch(NoSuchMethodException ex){
			if(entityClass.getSuperclass() != null && entityClass.getSuperclass() != Object.class){
				return getMethod(entityClass.getSuperclass(), methodName, type);
			}else{
				throw ex;
			}
		}
		return null;
	}

	public static Field[] getFields(Class<?> entityClass, boolean containsStaticAndFinal){
		List<Field> fields = new ArrayList<Field>();
		
		Field[] temp = entityClass.getDeclaredFields();
		for(int i=0; i<temp.length; i++){
			Field f = temp[i];
			if(! containsStaticAndFinal){
				if(!Modifier.isStatic(f.getModifiers()) || !Modifier.isFinal(f.getModifiers())){
					fields.add(f);
				}
			}else{
				fields.add(f);
			}
		}
		if(entityClass.getSuperclass() != null && entityClass.getSuperclass() != Object.class){
			Field[] fs = getFields(entityClass.getSuperclass(), containsStaticAndFinal);
			for(int i=0; i<fs.length; i++){
				fields.add(fs[i]);
			}
		}
		return fields.toArray(new Field[fields.size()]);
	}

	public static void main(String[] args) {
	}

}
