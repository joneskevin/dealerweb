package com.ava.domain.vo;


public class BoxFindVO extends BaseFindVO {
	
	private java.lang.Integer id;
    
	/** 唯一号 */
	private java.lang.String uniqueId;
	
	/** SIM卡ID */
	private java.lang.String simId;
	
	/** 通讯号 */
	private java.lang.String simMobile;
	
	/** 当前版本 */
	private java.lang.String currentVersion;
	
	/** 状态（1：激活 ；0：未激活） */
	private java.lang.String statusNick;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(java.lang.String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public java.lang.String getSimId() {
		return simId;
	}

	public void setSimId(java.lang.String simId) {
		this.simId = simId;
	}

	public java.lang.String getSimMobile() {
		return simMobile;
	}

	public void setSimMobile(java.lang.String simMobile) {
		this.simMobile = simMobile;
	}

	public java.lang.String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(java.lang.String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public java.lang.String getStatusNick() {
		return statusNick;
	}

	public void setStatusNick(java.lang.String statusNick) {
		this.statusNick = statusNick;
	}
	
	

}
