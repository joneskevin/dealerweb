package com.ava.resource.security;

/**令牌类*/
public class UserToken{
	
	public UserToken(Integer userAccountId){
		this.setUserAccountId(userAccountId);
		this.setLastRefreshTime(System.currentTimeMillis());
	}
	
	private String token;
	
	private Integer userAccountId;
	
	private long lastRefreshTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

	public long getLastRefreshTime() {
		return lastRefreshTime;
	}

	public void setLastRefreshTime(long lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}
}
