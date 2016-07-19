package com.ava.gateway.domain.vo;

public class TBoxStatusDataReq extends BaseDataReq{
	private int mid;
	private String activeAck;
	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getActiveAck() {
		return activeAck;
	}

	public void setActiveAck(String activeAck) {
		this.activeAck = activeAck;
	}
	
}
