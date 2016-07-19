package com.ava.exception;

import com.ava.enums.ServiceResponseEnum;

public class ProtocolParseBusinessException extends RuntimeException{

	private static final long serialVersionUID = -845123799671030511L;
	private String resultCode;
	private String desc;
	private ServiceResponseEnum resultEnum;
	
	public ProtocolParseBusinessException(String resultCode,String desc){
		this.resultCode=resultCode;
		this.desc=desc;
	}
	
	public ProtocolParseBusinessException(ServiceResponseEnum resultEnum){
		this.resultEnum=resultEnum;
		setResultCode(resultEnum.getCode());
		setDesc(resultEnum.getDesc());
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ServiceResponseEnum getResultEnum() {
		return resultEnum;
	}

	public void setResultEnum(ServiceResponseEnum resultEnum) {
		this.resultEnum = resultEnum;
	}
	
	
}
