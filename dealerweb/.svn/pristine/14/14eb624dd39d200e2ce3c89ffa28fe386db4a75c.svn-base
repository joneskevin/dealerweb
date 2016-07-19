package com.ava.domain.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_test_no_drive_week")
public class TestNoDriveWeek implements java.io.Serializable{

	private static final long serialVersionUID = -6670222797322574860L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;

	@Column(name = "YEAR")
	private java.lang.Integer year;
	
	@Column(name = "MONTH")
	private java.lang.Integer month;
	
	@Column(name = "WEEK")
	private java.lang.Integer week;

	@Column(name = "CREATE_TIME")
	private Date createTime;

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

	public java.lang.Integer getYear() {
		return year;
	}

	public void setYear(java.lang.Integer year) {
		this.year = year;
	}

	public java.lang.Integer getMonth() {
		return month;
	}

	public void setMonth(java.lang.Integer month) {
		this.month = month;
	}

	public java.lang.Integer getWeek() {
		return week;
	}

	public void setWeek(java.lang.Integer week) {
		this.week = week;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
