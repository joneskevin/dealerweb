package com.ava.gateway.domain.vo;

public class TBoxStatusDataRes extends BaseDataRes{
	/* mid为整数，1代表终端请求，3代表终端确认 */
	private int mid;
	/* mid为1时，tBoxStatus、tBoxToken、securityContext有值 */
	private String tBoxStatus;
	private String tBoxToken;
	private String securityContext;
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String gettBoxStatus() {
		return tBoxStatus;
	}
	public void settBoxStatus(String tBoxStatus) {
		this.tBoxStatus = tBoxStatus;
	}
	public String gettBoxToken() {
		return tBoxToken;
	}
	public void settBoxToken(String tBoxToken) {
		this.tBoxToken = tBoxToken;
	}
	public String getSecurityContext() {
		return securityContext;
	}
	public void setSecurityContext(String securityContext) {
		this.securityContext = securityContext;
	}
}
