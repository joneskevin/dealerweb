package com.ava.base.domain;

import java.io.Serializable;

public class ReturnCode implements Serializable {

	private static final long serialVersionUID = 3361721085596128425L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private ReturnCode(){
	}
	
	public ReturnCode(int code){
		this();
		this.setCode(code);
	}

	private int code;
	
	private java.lang.String message;
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public java.lang.String getMessage() {
		return message;
	}

	public void setMessage(java.lang.String message) {
		this.message = message;
	}

}
