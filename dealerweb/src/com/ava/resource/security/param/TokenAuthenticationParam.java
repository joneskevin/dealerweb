package com.ava.resource.security.param;


public class TokenAuthenticationParam extends AuthenticationParam{
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
