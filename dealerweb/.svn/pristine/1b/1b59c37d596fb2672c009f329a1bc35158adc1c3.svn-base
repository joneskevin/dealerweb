package com.ava.domain.vo;

import java.util.Date;

import javax.persistence.Transient;

import com.ava.domain.entity.TestDrive;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheDriveLineManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;

public class TestDriveVO {
	
	private java.lang.Integer id;

	private java.lang.Integer companyId;

	private java.lang.Integer departmentId;

	private java.lang.Integer vehicleId;

	private java.lang.Integer status;
	
	private java.lang.String status_Nick;

	private Long secondsOfInterval;

	private Long minutesOfInterval;
	
	private String testDriveDate;
	
	private Date startTime;
	
	private String startTime_Nick;
	
	private Date endTime;
	
	private String endTime_Nick;
	
	private java.lang.Integer lineId;
	
	private java.lang.Integer loopCount;

	private java.lang.Integer mileage;

	private java.lang.Float mileageKm;
	
	private java.lang.String lineId_Nick;
	
	private java.lang.Long costMinutes;
	
	private java.lang.Integer season;
	
	private VehicleVO vehicle = new VehicleVO();
	
	private DealerVO dealer = new DealerVO();
	
	private TestDriveTimeVO testDriveTime = new TestDriveTimeVO();
	private Integer currentStatus;
	private Integer pointFlag;
	private Date saveDate;
	private Date testDriveDateTime;
	private Integer intevalTime;
	public TestDriveVO() {
	}
	
	public TestDriveVO(TestDrive testDrive) {
		if (testDrive != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, testDrive);
			this.nick();
		}
	}
	public TestDriveVO(TestDriveFindVO testDriveFindVO) {
		if (testDriveFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, testDriveFindVO);
			this.nick();
		}
	}
	
	public void nick() {
		status_Nick=  SelectOptionResource.getOptionTextByValue(SelectOptionResource.testDriveStatusArray, getStatus());
		minutesOfInterval = getSecondsOfInterval() == null ? null : (getSecondsOfInterval() / 60);
		mileageKm = getMileage() == null ? null : (TypeConverter.toFloat(getMileage()) / 1000);
		startTime_Nick = DateTime.toNormalDateTime(getStartTime());
		endTime_Nick = DateTime.toNormalDateTime(getEndTime());
		lineId_Nick = CacheDriveLineManager.getDriveLineNameById(getLineId());
	}
	
	public java.lang.String getStatus_Nick() {
		return status_Nick;
	}

	public void setStatus_Nick(java.lang.String status_Nick) {
		this.status_Nick = status_Nick;
	}

	public java.lang.Long getCostMinutes() {
		return costMinutes;
	}

	public void setCostMinutes(java.lang.Long costMinutes) {
		this.costMinutes = costMinutes;
	}

	public java.lang.String getLineId_Nick() {
		return lineId_Nick;
	}

	public void setLineId_Nick(java.lang.String lineId_Nick) {
		this.lineId_Nick = lineId_Nick;
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

	public Long getSecondsOfInterval() {
		return secondsOfInterval;
	}

	public void setSecondsOfInterval(Long secondsOfInterval) {
		this.secondsOfInterval = secondsOfInterval;
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
	public String getTestDriveDate() {
		return testDriveDate;
	}

	public void setTestDriveDate(String testDriveDate) {
		this.testDriveDate = testDriveDate;
	}

	public java.lang.Integer getSeason() {
		return season;
	}

	public void setSeason(java.lang.Integer season) {
		this.season = season;
	}

	public TestDriveTimeVO getTestDriveTime() {
		return testDriveTime;
	}

	public void setTestDriveTime(TestDriveTimeVO testDriveTime) {
		this.testDriveTime = testDriveTime;
	}

	public Long getMinutesOfInterval() {
		return minutesOfInterval;
	}

	public void setMinutesOfInterval(Long minutesOfInterval) {
		this.minutesOfInterval = minutesOfInterval;
	}

	public java.lang.Float getMileageKm() {
		return mileageKm;
	}

	public void setMileageKm(java.lang.Float mileageKm) {
		this.mileageKm = mileageKm;
	}

	public String getStartTime_Nick() {
		return startTime_Nick;
	}

	public void setStartTime_Nick(String startTime_Nick) {
		this.startTime_Nick = startTime_Nick;
	}

	public String getEndTime_Nick() {
		return endTime_Nick;
	}

	public void setEndTime_Nick(String endTime_Nick) {
		this.endTime_Nick = endTime_Nick;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Integer getPointFlag() {
		return pointFlag;
	}

	public void setPointFlag(Integer pointFlag) {
		this.pointFlag = pointFlag;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	public Date getTestDriveDateTime() {
		return testDriveDateTime;
	}

	public void setTestDriveDateTime(Date testDriveDateTime) {
		this.testDriveDateTime = testDriveDateTime;
	}

	public Integer getIntevalTime() {
		return intevalTime;
	}

	public void setIntevalTime(Integer intevalTime) {
		this.intevalTime = intevalTime;
	}
	
	
}
