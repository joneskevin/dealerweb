package com.ava.base.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**面向整个系统的service类的返回结果对象*/
public class ResponseData implements Serializable {

	private static final long serialVersionUID = -7963207521488312037L;

	protected final static Logger logger = Logger.getLogger(ResponseData.class);

	public ResponseData() {
	}

	public ResponseData(int code) {
		this.setCode(code);
	}

	private ReturnCode returnCode = new ReturnCode(0);

	private java.util.Map data = null;

	private Object firstItem;

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public void put(Object key, Object item) {
		if(data == null){
			data = new HashMap();
		}
		data.put(key, item);
	}

	public Object get(Object key) {
		if(data == null){
			return null;
		}
		return data.get(key);
	}
	
	public Object getFirstItem() {
		return firstItem;
	}

	public void setFirstItem(Object firstItem) {
		this.firstItem = firstItem;
	}

	public int getCode() {
		return this.getReturnCode().getCode();
	}

	public void setCode(int code) {
		this.getReturnCode().setCode(code);
	}

	public String getMessage() {
		return this.getReturnCode().getMessage();
	}

	public void setMessage(java.lang.String message) {
		this.getReturnCode().setMessage(message);
	}

	/**
	 * 得到ReturnCode对象<br>
	 * @return ReturnCode，如果returnCode对象为null，则新建一个code=0的对象并返回
	 */
	public ReturnCode getReturnCode() {
		if (returnCode == null) {
			returnCode = new ReturnCode(0);
		}
		return returnCode;
	}

	public void setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode;
	}
}