package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_operation_log")
public class OperationLog implements Serializable{
	
	/**
	 * Created on 2015-10-12
	 * filename: OperationLog.java
	 * Description: 
	 * Copyright: Copyright(c)2015
	 * Company: BDC
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	@Column(name = "TYPE")
	private java.lang.Integer type;
	
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;
    
	@Column(name = "OPERATOR_ID")
	private java.lang.Integer operatorId;
	
	@Column(name = "OPERATOR_TIME")
	private java.util.Date operatorTime;
	
	@Column(name = "REMARK")
	private java.lang.String remark;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(java.lang.Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public java.lang.Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(java.lang.Integer operatorId) {
		this.operatorId = operatorId;
	}

	public java.util.Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(java.util.Date operatorTime) {
		this.operatorTime = operatorTime;
	}


	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	
	
	
}
