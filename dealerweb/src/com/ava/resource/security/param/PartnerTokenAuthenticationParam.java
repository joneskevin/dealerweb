package com.ava.resource.security.param;


public class PartnerTokenAuthenticationParam extends AuthenticationParam{
	
	private String accessToken;
	
	private Integer accountType;

	public PartnerTokenAuthenticationParam(String accessToken, Integer accountType){
		this.accessToken = accessToken;
		this.accountType = accountType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
}
