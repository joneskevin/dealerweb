package com.ava.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 返利表
 * Title: Class Rebate.java
 * Copyright: Copyright(c)2015
 * Company: BDC
 * @author liuq
 * @version 0.1
 */
@Entity
@Table(name = "t_rebate")
public class Rebate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "COMPANY_ID", nullable=true)
	private java.lang.Integer companyId;

	@Column(name = "VEHICLE_ID", nullable=true)
	private java.lang.Integer vehicleId;
	
	/** 返利季度 */
	@Column(name = "SEASON", nullable=true)
	private java.lang.Integer season;

	/** 返利日期 */
	@Column(name = "REBATE_DATE", nullable=true)
	private Date rebateDate;
	
	/** 是否返利 */
	@Column(name = "IS_REBATE", nullable=true)
	private java.lang.Integer isRebate;

	/** 创建时间 */
	@Column(name = "CREATION_TIME", nullable=true)
	private Date creationTime;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
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

	public java.lang.Integer getSeason() {
		return season;
	}

	public void setSeason(java.lang.Integer season) {
		this.season = season;
	}

	public Date getRebateDate() {
		return rebateDate;
	}

	public void setRebateDate(Date rebateDate) {
		this.rebateDate = rebateDate;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.Integer getIsRebate() {
		return isRebate;
	}

	public void setIsRebate(java.lang.Integer isRebate) {
		this.isRebate = isRebate;
	}
	
	
}
