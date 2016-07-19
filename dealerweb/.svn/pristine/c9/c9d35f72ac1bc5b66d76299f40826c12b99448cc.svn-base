package com.ava.domain.vo;

import javax.persistence.Transient;

import com.ava.domain.entity.Box;
import com.ava.domain.entity.BoxOperation;
import com.ava.domain.entity.Vehicle;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.MyBeanUtils;

public class BoxInfoVO {
	
	private java.lang.Integer id;
	
	/** 唯一号 */
	private java.lang.String uniqueId;
	
	/** 公司ID */
	private java.lang.Integer companyId;
	
	/** 车辆ID */
	private java.lang.Integer vehicleId;
	
	/** 安装到车辆的时间 */
	private java.util.Date installTime;
	
	@Transient
	private java.lang.String installTime_Nick;
	
	/** SIM卡ID */
	private java.lang.String simId;
	
	/** 通讯号 */
	private java.lang.String simMobile;
	
	/** 当前版本 */
	private java.lang.String currentVersion;
	
	/** 状态（1：激活 ；0：未激活） */
	private java.lang.Integer status;
	
	/** 删除标志 */
	private java.lang.Integer deletionFlag;
	
	/** 公司名称 */
	private java.lang.String companyName;
	
	/** 状态中文名 */
	private java.lang.String status_nick;
	
	/** 解绑标志 （1：可解绑 ；0：不能解绑） */
	private java.lang.Integer unbindFlag;
	
	private Vehicle vehicle;
	
	private BoxOperation boxOperation;
	
	public void nick() {
		//公司名称
		companyName = CacheOrgManager.getOrgName(companyId);
		status_nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.boxStatusArray, getStatus());
	}
	
	public BoxInfoVO(){
	}
	
	public BoxInfoVO(Box box){
		if(box == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, box);
	}

	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(java.lang.String uniqueId) {
		this.uniqueId = uniqueId;
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

	public java.util.Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(java.util.Date installTime) {
		this.installTime = installTime;
	}

	public java.lang.String getInstallTime_Nick() {
		return installTime_Nick;
	}

	public void setInstallTime_Nick(java.lang.String installTime_Nick) {
		this.installTime_Nick = installTime_Nick;
	}

	public java.lang.String getSimId() {
		return simId;
	}

	public void setSimId(java.lang.String simId) {
		this.simId = simId;
	}

	public java.lang.String getSimMobile() {
		return simMobile;
	}

	public void setSimMobile(java.lang.String simMobile) {
		this.simMobile = simMobile;
	}

	public java.lang.String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(java.lang.String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getDeletionFlag() {
		return deletionFlag;
	}

	public void setDeletionFlag(java.lang.Integer deletionFlag) {
		this.deletionFlag = deletionFlag;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.String getStatus_nick() {
		return status_nick;
	}

	public void setStatus_nick(java.lang.String status_nick) {
		this.status_nick = status_nick;
	}

	public BoxOperation getBoxOperation() {
		return boxOperation;
	}

	public void setBoxOperation(BoxOperation boxOperation) {
		this.boxOperation = boxOperation;
	}

	public java.lang.Integer getUnbindFlag() {
		return unbindFlag;
	}

	public void setUnbindFlag(java.lang.Integer unbindFlag) {
		this.unbindFlag = unbindFlag;
	}

	
}
