package com.ava.gateway.domain.vo;

public class GpsMessageDataRes extends BaseDataRes{
	
	private int paramVer;
	private int existCmd;
	public int getParamVer() {
		return paramVer;
	}
	public void setParamVer(int paramVer) {
		this.paramVer = paramVer;
	}
	public int getExistCmd() {
		return existCmd;
	}
	public void setExistCmd(int existCmd) {
		this.existCmd = existCmd;
	}
}
