package com.ava.domain.entity;

import java.io.Serializable;
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
@Table(name = "t_test_drive")
public class TestDrive implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "COMPANY_ID",nullable=true)
	private java.lang.Integer companyId;

	@Column(name = "DEPARTMENT_ID",nullable=false)
	private java.lang.Integer departmentId;

	@Column(name = "VEHICLE_ID",nullable=true)
	private java.lang.Integer vehicleId;

	/**状态(0:无效驾驶;1:有效;2:违规 3加油线路 4 4S店内试跑 5报备审批后删除的状态)*/
	@Column(name = "STATUS",nullable=true)
	private java.lang.Integer status;

	@Column(name = "INTERVAL_TIME",nullable=true)
	private Long intevalTime;

	@Column(name = "TEST_DRIVE_DATE",nullable=true)
	private Date testDriveDate;

	@Column(name = "START_TIME",nullable=true)
	private Date startTime;

	@Column(name = "END_TIME",nullable=true)
	private Date endTime;

	@Column(name = "LINE_ID",nullable=true)
	private java.lang.Integer lineId;

	@Column(name = "LOOP_COUNT",nullable=true)
	private java.lang.Integer loopCount;

	@Column(name = "MILEAGE",nullable=true)
	private java.lang.Integer mileage;
	
	@Column(name = "SAVE_DATE",nullable=true)
	private Date saveDate;
	
	@Column(name = "POINT_FLAG")
	private Integer pointFlag;/**0 起点  1半圈点*/
	
	/** 试驾状态(0试驾中 1试驾结束 4非工作时间试驾) */
	@Column(name = "CURRENT_STATUS",nullable=true)
	private Integer currentStatus;
	
	/** 路线nick */
	@Transient
	private java.lang.String lineId_Nick;

	/** 试驾花费的时间 */
	@Transient
	private java.lang.Long costMinutes;
	
	/** 越界计数器 */
	@Transient
	private java.lang.Integer outLineCount;
	
	public void nick() {
		 lineId_Nick = CacheDriveLineManager.getDriveLineNameById(getLineId());
	}

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

	public java.lang.Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(java.lang.Integer departmentId) {
		this.departmentId = departmentId;
	}

	public java.lang.Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(java.lang.Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public Long getIntevalTime() {
		return intevalTime;
	}

	public void setIntevalTime(Long intevalTime) {
		this.intevalTime = intevalTime;
	}

	public Date getTestDriveDate() {
		return testDriveDate;
	}

	public void setTestDriveDate(Date testDriveDate) {
		this.testDriveDate = testDriveDate;
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

	public java.lang.Integer getLineId() {
		return lineId;
	}

	public void setLineId(java.lang.Integer lineId) {
		this.lineId = lineId;
	}

	public java.lang.Integer getLoopCount() {
		return loopCount;
	}

	public void setLoopCount(java.lang.Integer loopCount) {
		this.loopCount = loopCount;
	}

	public java.lang.Integer getMileage() {
		return mileage;
	}

	public void setMileage(java.lang.Integer mileage) {
		this.mileage = mileage;
	}

	public java.lang.String getLineId_Nick() {
		return lineId_Nick;
	}

	public void setLineId_Nick(java.lang.String lineId_Nick) {
		this.lineId_Nick = lineId_Nick;
	}

	public java.lang.Long getCostMinutes() {
		return costMinutes;
	}

	public void setCostMinutes(java.lang.Long costMinutes) {
		this.costMinutes = costMinutes;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	public Integer getPointFlag() {
		return pointFlag;
	}

	public void setPointFlag(Integer pointFlag) {
		this.pointFlag = pointFlag;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public java.lang.Integer getOutLineCount() {
		return outLineCount;
	}

	public void setOutLineCount(java.lang.Integer outLineCount) {
		this.outLineCount = outLineCount;
	}
	
	
}
