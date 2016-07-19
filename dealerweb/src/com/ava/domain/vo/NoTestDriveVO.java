package com.ava.domain.vo;

import java.util.Date;

import com.ava.domain.entity.NoTestDrive;
import com.ava.util.MyBeanUtils;

public class NoTestDriveVO {
	
	private java.lang.Integer id;

	private java.lang.Integer companyId;

	private java.lang.Integer vehicleId;

	private java.lang.Integer year;

	private java.lang.Integer season;
	
	private java.lang.Integer durationDayCount;

	private Date startTime;

	private Date endTime;
	
	private VehicleVO vehicle = new VehicleVO();
	
	private DealerVO dealer = new DealerVO();
	
	private TestDriveTimeVO testDriveTime = new TestDriveTimeVO();
	
	public NoTestDriveVO() {
	}
	
	public NoTestDriveVO(NoTestDrive noTestDrive) {
		if (noTestDrive != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, noTestDrive);
		}
	}
	
	public NoTestDriveVO(NoTestDriveFindVO noTestDriveFindVO) {
		if (noTestDriveFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, noTestDriveFindVO);
		}
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

	public VehicleVO getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleVO vehicle) {
		this.vehicle = vehicle;
	}

	public DealerVO getDealer() {
		return dealer;
	}

	public void setDealer(DealerVO dealer) {
		this.dealer = dealer;
	}

	public TestDriveTimeVO getTestDriveTime() {
		return testDriveTime;
	}

	public void setTestDriveTime(TestDriveTimeVO testDriveTime) {
		this.testDriveTime = testDriveTime;
	}

	public java.lang.Integer getDurationDayCount() {
		return durationDayCount;
	}

	public void setDurationDayCount(java.lang.Integer durationDayCount) {
		this.durationDayCount = durationDayCount;
	}
	
	

}
