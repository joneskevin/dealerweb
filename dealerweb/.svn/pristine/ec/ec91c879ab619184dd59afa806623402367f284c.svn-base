package com.ava.domain.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_operator_log")
public class OperatorLog implements Serializable {

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



	@Column(name = "OPERATOR_ID")
	private java.lang.Integer operatorId;

	@Column(name = "URL_AND_PARAMETERS")
	private java.lang.String urlAndParameters;

	@Column(name = "REMARK")
	private java.lang.String remark;

	@Transient
	private java.lang.String companyName;
	
	@Transient
	private java.lang.String operatorName;
	public void nick() {
		//setCompanyName(CacheOrgManager.getOrgName(getCompanyId()));
		//setOperatorName(CacheUserManager.getNickName(getOperatorId()));
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

	public java.lang.Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(java.lang.Integer operatorId) {
		this.operatorId = operatorId;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(java.lang.String operatorName) {
		this.operatorName = operatorName;
	}


}
