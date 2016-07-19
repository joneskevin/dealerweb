package com.ava.domain.vo;

import java.util.Date;

import javax.persistence.Transient;

import com.ava.base.domain.UserRight;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.Vehicle;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

public class VehicleVO {

	private java.lang.Integer id;
	
	private java.lang.String vin;

	private java.lang.Integer companyId;

	private java.lang.Integer carStyleId;

	private java.lang.String plateNumber;

	private java.lang.Integer isTestDrive;//是否为试驾车辆
	
	private Date licensingTime;//上牌时间
	
	private String licensingTime_Nick;//上牌时间,normalDateTime格式
	
	private String licensingTimeStr;//上牌时间
	
	private java.lang.String licensingExecutorName;//上牌人姓名
	
	private java.lang.Integer deletionFlag;
	
	private Date deletionTime;
	
	private java.lang.Integer configureType;//配置类型：0：不安装；1：必配、2：选配
	
	private java.lang.Integer configureStatus;//配置状态：1:初始、10:新装待审核、20:待安装、30:已安装、40:已拆除（也叫锁定，经销商可查看锁定半年之内的车辆）、90:不安装(重点、非重点)

	/** 公司名称 */
	private java.lang.String companyName;
	
	/** 车型 */
	private java.lang.String carStyleId_Nick;
	
	/** 配置类型 */
	private java.lang.String configureType_Nick;
	
	/** 配置状态 */
	private java.lang.String configureStatus_Nick;
	
	/** 是否可拆除*/
	private java.lang.Integer isDemolition;
	
	private java.lang.String isDemolition_Nick;
	
	/** 是否查看历史车辆*/
	private java.lang.Integer isHistroy;
	
	/** 拆除类型 */
	private java.lang.Integer demolitionType;
	
	private Date installationTime;
	
	private Date demolitionTime;
	
	private java.lang.String obdNo;
	
	private java.lang.Integer obdFlag;
	
	private java.lang.String remark;
	
	private DealerVO dealer;

	private BoxVO box;
	
	private BoxOperationVO boxOperationVO = new BoxOperationVO();
	
	public VehicleVO() {

	}
	public VehicleVO(Vehicle vehicle) {
		if (vehicle != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, vehicle);
			this.setLicensingTime_Nick(DateTime.toNormalDateTime(vehicle.getLicensingTime()));
		}
	}
	public VehicleVO(TestDriveFindVO testDriveFindVO) {
		if (testDriveFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, testDriveFindVO);
			this.nick();
		}
	}
	public VehicleVO(NoTestDriveFindVO noTestDriveFindVO) {
		if (noTestDriveFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, noTestDriveFindVO);
			this.nick();
		}
	}
	public VehicleVO(VehicleFindVO vehicleFindVO) {
		if (vehicleFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, vehicleFindVO);
			this.nick();
		}
	}
	
	public VehicleVO(DemolitionFindVO demolitionFindVO) {
		if (demolitionFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, demolitionFindVO);
			this.nick();
		}
	}
	
	public VehicleVO(ProposalFindVO proposalFindVO) {
		if (proposalFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, proposalFindVO);
			this.nick();
		}
	}
	
	public void nick() {
		carStyleId_Nick = CacheCarManager.getCarStyleNameById(getCarStyleId());
		configureStatus_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.configureStatusArray, getConfigureStatus());

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
	public DealerVO getDealer() {
		return dealer;
	}
	public void setDealer(DealerVO dealer) {
		this.dealer = dealer;
	}
	public BoxVO getBox() {
		return box;
	}
	public void setBox(BoxVO box) {
		this.box = box;
	}
	public String getLicensingTimeStr() {
		return licensingTimeStr;
	}
	public void setLicensingTimeStr(String licensingTimeStr) {
		this.licensingTimeStr = licensingTimeStr;
	}
	public String getLicensingTime_Nick() {
		return licensingTime_Nick;
	}
	public void setLicensingTime_Nick(String licensingTime_Nick) {
		this.licensingTime_Nick = licensingTime_Nick;
	}
	public BoxOperationVO getBoxOperationVO() {
		return boxOperationVO;
	}
	public void setBoxOperationVO(BoxOperationVO boxOperationVO) {
		this.boxOperationVO = boxOperationVO;
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
	public java.lang.Integer getDemolitionType() {
		return demolitionType;
	}
	public void setDemolitionType(java.lang.Integer demolitionType) {
		this.demolitionType = demolitionType;
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
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}	
	
	
	
}
