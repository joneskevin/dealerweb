package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;

@Entity
@Table(name = "t_box")
public class Box implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
    
	/** 唯一号 */
	@Column(name = "UNIQUE_ID")
	private java.lang.String uniqueId;
	
	/** 公司ID */
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	/** 车辆ID */
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;
	
	/** 安装到车辆的时间 */
	@Column(name = "INSTALL_TIME")
	private java.util.Date installTime;
	
	@Transient
	private java.lang.String installTime_Nick;
	
	/** SIM卡ID */
	@Column(name = "SIM_ID")
	private java.lang.String simId;
	
	/** 通讯号 */
	@Column(name = "SIM_MOBILE")
	private java.lang.String simMobile;
	
	/** 当前版本 */
	@Column(name = "CURRENT_VERSION")
	private java.lang.String currentVersion;
	
	/** 状态（1：激活 ；0：未激活） */
	@Column(name = "STATUS")
	private java.lang.Integer status;
	
	/** 删除标志 */
	@Column(name = "DELETION_FLAG")
	private java.lang.Integer deletionFlag;
	
	/** 公司名称 */
	@Transient
	private java.lang.String companyName;
	
	/** 状态中文名 */
	@Transient
	private java.lang.String status_nick;
	
	public void nick() {
		//公司名称
		companyName = CacheOrgManager.getOrgName(companyId);
		status_nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.boxStatusArray, getStatus());
		installTime_Nick = DateTime.toNormalDateTime(getInstallTime());
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
	
}
