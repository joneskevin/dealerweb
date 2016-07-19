package com.ava.base.dao.hibernate;

import java.util.HashMap;

import com.ava.resource.GlobalConstant;

/**分页查询方法需要传递有关分页的属性组成的对象*/
public class TransMsg implements Cloneable {

	private HashMap<Object, Object> parameters = new HashMap<Object, Object>();

	private Integer pageSize;

	private Integer startIndex;//在使用SpringMVC时,由于配置的是IntegerConverter,故不能声明为"int"类型

	private Long totalRecords;

	public TransMsg() {
		this.startIndex = 0;
		this.pageSize = GlobalConstant.DEFAULT_TABLE_ROWS;
	}

	public TransMsg(Integer startIndex) {
		this.startIndex = startIndex;
		this.pageSize = GlobalConstant.DEFAULT_TABLE_ROWS;
	}

	public TransMsg(Integer startIndex, Integer pageSize) {
		this.startIndex = startIndex;
		this.pageSize = pageSize;
	}

	public HashMap<Object, Object> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<Object, Object> parameters) {
		this.parameters = parameters;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public void put(Object key, Object value) {
		parameters.put(key, value);
	}

	public Object get(Object key) {
		return parameters.get(key);
	}
	
}
