package com.ava.domain.vo;

import com.ava.util.DateTime;

public class OrgLoginRecord {
	
	private java.lang.Integer orgId;

	private java.lang.String loginDate;
	
	public OrgLoginRecord(Integer orgId){
		this.orgId = orgId;
		this.loginDate = DateTime.getNormalDate();		
	}
	
	public OrgLoginRecord(Integer orgId, String loginDate){
		this.orgId = orgId;
		this.loginDate = loginDate;
	}

	public java.lang.String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(java.lang.String loginDate) {
		this.loginDate = loginDate;
	}

	public java.lang.Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(java.lang.Integer orgId) {
		this.orgId = orgId;
	}
	

}
