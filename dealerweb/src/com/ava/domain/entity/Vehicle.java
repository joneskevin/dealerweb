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

import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;

@Entity
@Table(name = "t_vehicle")
public class Vehicle  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	private java.lang.String vin;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;

	@Column(name = "CAR_STYLE_ID")
	private java.lang.Integer carStyleId;

	@Column(name = "PLATE_NUMBER")
	private java.lang.String plateNumber;

	@Column(name = "IS_TEST_DRIVE")
	private java.lang.Integer isTestDrive;//是否为试驾车辆
	
	@Column(name = "LICENSING_TIME")
	private Date licensingTime;//上牌时间
	
	@Column(name = "LICENSING_EXECUTOR_NAME")
	private java.lang.String licensingExecutorName;//上牌人姓名
	
	@Column(name = "DELETION_FLAG")
	private java.lang.Integer deletionFlag;
	
	@Column(name = "DELETION_TIME")
	private Date deletionTime;
	
	@Column(name = "CONFIGURE_TYPE")
	private java.lang.Integer configureType;//配置类型：0：不安装；1：必配、2：选配
	
	@Column(name = "CONFIGURE_STATUS")
	private java.lang.Integer configureStatus;//配置状态：1:初始、10:新装待审核、20:待安装、30:已安装、40:已拆除（也叫锁定，经销商可查看锁定半年之内的车辆）、90:不安装(重点、非重点)
	
	@Column(name = "INSTALLATION_TIME")
	private Date installationTime;
	
	@Column(name = "DEMOLITION_TIME")
	private Date demolitionTime;
	
	@Column(name = "OBD_NO")
	private java.lang.String obdNo;
	
	@Column(name = "OBD_FLAG")
	private java.lang.Integer obdFlag;
	
	@Column(name = "REPLACE_PLAN_ID")
	private java.lang.Integer replacePlanId;
	
	@Column(name = "REMARK")
	private java.lang.String remark;

	/** 是否查看历史车辆*/
	@Transient
	private java.lang.Integer isHistroy;
	
	/** 公司名称 */
	@Transient
	private java.lang.String companyName;
	/** 车型 */
	@Transient
	private java.lang.String carStyleId_Nick;
	/** 配置类型 */
	@Transient
	private java.lang.String configureType_Nick;
	/** 配置状态 */
	@Transient
	private java.lang.String configureStatus_Nick;
	
	/** 是否可拆除*/
	@Transient
	private java.lang.Integer isDemolition;
	
	@Transient
	private java.lang.String isDemolition_Nick;
	
	public void nick() {
		//公司名称
		companyName = CacheOrgManager.getOrgName(companyId);
		//车型
		carStyleId_Nick = CacheCarManager.getCarStyleNameById(getCarStyleId());
		//配置类型
		configureType_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.configureTypeArray, getConfigureType());
		//配置状态
		configureStatus_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.configureStatusArray, getConfigureStatus());
		//是否可拆除
		isDemolition_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.yesOrNo, getIsDemolition());
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
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

	public java.lang.Integer getConfigureType() {
		return configureType;
	}

	public void setConfigureType(java.lang.Integer configureType) {
		this.configureType = configureType;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.Integer getConfigureStatus() {
		return configureStatus;
	}

	public void setConfigureStatus(java.lang.Integer configureStatus) {
		this.configureStatus = configureStatus;
	}

	public java.lang.String getCarStyleId_Nick() {
		return carStyleId_Nick;
	}

	public void setCarStyleId_Nick(java.lang.String carStyleId_Nick) {
		this.carStyleId_Nick = carStyleId_Nick;
	}

	public java.lang.String getConfigureType_Nick() {
		return configureType_Nick;
	}

	public void setConfigureType_Nick(java.lang.String configureType_Nick) {
		this.configureType_Nick = configureType_Nick;
	}

	public java.lang.String getConfigureStatus_Nick() {
		return configureStatus_Nick;
	}

	public void setConfigureStatus_Nick(java.lang.String configureStatus_Nick) {
		this.configureStatus_Nick = configureStatus_Nick;
	}

	public java.lang.Integer getIsDemolition() {
		return isDemolition;
	}

	public void setIsDemolition(java.lang.Integer isDemolition) {
		this.isDemolition = isDemolition;
	}

	public java.lang.String getIsDemolition_Nick() {
		return isDemolition_Nick;
	}

	public void setIsDemolition_Nick(java.lang.String isDemolition_Nick) {
		this.isDemolition_Nick = isDemolition_Nick;
	}

	public java.lang.Integer getIsHistroy() {
		return isHistroy;
	}

	public void setIsHistroy(java.lang.Integer isHistroy) {
		this.isHistroy = isHistroy;
	}

	public Date getInstallationTime() {
		return installationTime;
	}

	public void setInstallationTime(Date installationTime) {
		this.installationTime = installationTime;
	}

	public Date getDemolitionTime() {
		return demolitionTime;
	}

	public void setDemolitionTime(Date demolitionTime) {
		this.demolitionTime = demolitionTime;
	}

	public java.lang.Integer getObdFlag() {
		return obdFlag;
	}

	public void setObdFlag(java.lang.Integer obdFlag) {
		this.obdFlag = obdFlag;
	}

	public java.lang.String getObdNo() {
		return obdNo;
	}

	public void setObdNo(java.lang.String obdNo) {
		this.obdNo = obdNo;
	}

	public java.lang.Integer getReplacePlanId() {
		return replacePlanId;
	}

	public void setReplacePlanId(java.lang.Integer replacePlanId) {
		this.replacePlanId = replacePlanId;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	
	
}
