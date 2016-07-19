package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheUserManager;

@Entity
@Table(name = "t_user_log")
public class UserLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "ACTION_ID")
	private java.lang.String actionId;

	@Column(name = "ACTION_NAME")
	private java.lang.String actionName;

	@Column(name = "ACTION_TIME")
	private java.util.Date actionTime;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;

	@Column(name = "USER_ID")
	private java.lang.Integer userId;

	@Column(name = "URL_AND_PARAMETERS")
	private java.lang.String urlAndParameters;

	@Column(name = "REMARK")
	private java.lang.String remark;

	@Transient
	private java.lang.String companyName;
	
	@Transient
	private java.lang.String userName;
	public void nick() {
		setCompanyName(CacheOrgManager.getOrgName(getCompanyId()));
		setUserName(CacheUserManager.getNickName(getUserId()));
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getActionId() {
		return actionId;
	}

	public void setActionId(java.lang.String actionId) {
		this.actionId = actionId;
	}

	public java.lang.String getActionName() {
		return actionName;
	}

	public void setActionName(java.lang.String actionName) {
		this.actionName = actionName;
	}

	public java.util.Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(java.util.Date actionTime) {
		this.actionTime = actionTime;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.String getUrlAndParameters() {
		return urlAndParameters;
	}

	public void setUrlAndParameters(java.lang.String urlAndParameters) {
		this.urlAndParameters = urlAndParameters;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

}
