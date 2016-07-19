package com.ava.resource.security.param;



public class Md5AuthenticationParam extends AuthenticationParam{
	
	private Long millisTime;//时间，格式为：2012-09-19 17:44:20
	
	private static String md5Token;//服务器和终端约定的一个口令，md5加密过的。
	
	public Md5AuthenticationParam(Long millisTime, String md5Token){
		this.millisTime = millisTime;
		this.md5Token = md5Token;
	}

	public Long getMillisTime() {
		return millisTime;
	}

	public void setMillisTime(Long millisTime) {
		this.millisTime = millisTime;
	}

	public static String getMd5Token() {
		return md5Token;
	}

	public static void setMd5Token(String md5Token) {
		Md5AuthenticationParam.md5Token = md5Token;
	}

}
