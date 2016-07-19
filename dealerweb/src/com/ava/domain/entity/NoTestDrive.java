package com.ava.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheDriveLineManager;

@Entity
@Table(name = "t_no_test_drive")
public class NoTestDrive {

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

	@Column(name = "DURATION_DAY_COUNT")
	private java.lang.Integer durationDayCount;
	
	@Column(name = "SEASON")
	private java.lang.Integer season;

	@Column(name = "START_TIME")
	private Date startTime;

	@Column(name = "END_TIME")
	private Date endTime;

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

	public java.lang.Integer getSeason() {
		return season;
	}

	public void setSeason(java.lang.Integer season) {
		this.season = season;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public java.lang.Integer getDurationDayCount() {
		return durationDayCount;
	}

	public void setDurationDayCount(java.lang.Integer durationDayCount) {
		this.durationDayCount = durationDayCount;
	}

	
}
