package com.ava.domain.vo;

import java.util.Date;

import com.ava.domain.entity.MonitorLocation;
import com.ava.domain.entity.Vehicle;
import com.ava.util.MyBeanUtils;

public class VehicleMonitorVO {

	private java.lang.Integer id;
	
	private java.lang.String nodeId;//为了保证作为树节点的ID的唯一性，等于"vehicle"+id

	private java.lang.String vin;

	private java.lang.Integer companyId;

	private java.lang.Integer carStyleId;

	private java.lang.String plateNumber;

	private java.lang.Integer isTestDrive;//是否为试驾车辆
	
	private Date licensingTime;//上牌时间
	
	private java.lang.String licensingExecutorName;//上牌人姓名
	
	private java.lang.Integer deletionFlag;
	
	private Date deletionTime;
	
	private java.lang.Integer configureStatus;//配置状态：1:初始、10:新装待审核、20:待安装、30:已安装、40:已拆除（也叫锁定，经销商可查看锁定半年之内的车辆）、90:不安装(重点、非重点)

	/** 公司名称 */
	private java.lang.String companyName;
	
	/** 车型 */
	private java.lang.String carStyleId_Nick;
	
	/** 配置状态 */
	private java.lang.String configureStatus_Nick;

	/** 当前位置 */
	private MonitorLocation currentLocation;
	
	public VehicleMonitorVO(Vehicle vehicle){
		if(vehicle == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, vehicle);
		this.setNodeId("vehicle" + vehicle.getId());
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getNodeId() {
		return nodeId;
	}

	public void setNodeId(java.lang.String nodeId) {
		this.nodeId = nodeId;
	}

	public java.lang.String getVin() {
		return vin;
	}

	public void setVin(java.lang.String vin) {
		this.vin = vin;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getCarStyleId() {
		return carStyleId;
	}

	public void setCarStyleId(java.lang.Integer carStyleId) {
		this.carStyleId = carStyleId;
	}

	public java.lang.String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(java.lang.String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public java.lang.Integer getIsTestDrive() {
		return isTestDrive;
	}

	public void setIsTestDrive(java.lang.Integer isTestDrive) {
		this.isTestDrive = isTestDrive;
	}

	public Date getLicensingTime() {
		return licensingTime;
	}

	public void setLicensingTime(Date licensingTime) {
		this.licensingTime = licensingTime;
	}

	public java.lang.String getLicensingExecutorName() {
		return licensingExecutorName;
	}

	public void setLicensingExecutorName(java.lang.String licensingExecutorName) {
		this.licensingExecutorName = licensingExecutorName;
	}

	public java.lang.Integer getDeletionFlag() {
		return deletionFlag;
	}

	public void setDeletionFlag(java.lang.Integer deletionFlag) {
		this.deletionFlag = deletionFlag;
	}

	public Date getDeletionTime() {
		return deletionTime;
	}

	public void setDeletionTime(Date deletionTime) {
		this.deletionTime = deletionTime;
	}

	public java.lang.Integer getConfigureStatus() {
		return configureStatus;
	}

	public void setConfigureStatus(java.lang.Integer configureStatus) {
		this.configureStatus = configureStatus;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.String getCarStyleId_Nick() {
		return carStyleId_Nick;
	}

	public void setCarStyleId_Nick(java.lang.String carStyleId_Nick) {
		this.carStyleId_Nick = carStyleId_Nick;
	}

	public java.lang.String getConfigureStatus_Nick() {
		return configureStatus_Nick;
	}

	public void setConfigureStatus_Nick(java.lang.String configureStatus_Nick) {
		this.configureStatus_Nick = configureStatus_Nick;
	}

	public MonitorLocation getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(MonitorLocation currentLocation) {
		this.currentLocation = currentLocation;
	}

	
}
