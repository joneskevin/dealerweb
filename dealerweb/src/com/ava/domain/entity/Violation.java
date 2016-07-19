package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.domain.vo.ViolationFindVO;
import com.ava.resource.DbCacheResource;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

/** 
 * 违规实体
 * */
@Entity
@Table(name="t_violation")
public class Violation  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	@Column(name = "DEPARTMENT_ID")
	private java.lang.Integer departmentId;
	
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;
	
	@Column(name = "TYPE")
	private java.lang.Integer typeId;
	
	@Column(name = "COUNT")
	private java.lang.Integer countId;
	
	@Column(name = "CREATION_TIME")
	private java.util.Date creationTime;
	
	@Column(name = "END_TIME")
	private java.util.Date end_Time;
	
	@Column(name = "START_TIME")
	private java.util.Date start_Time;
	
	@Column(name = "DRIVE_LINE_ID")
	private java.lang.Integer driveLineId;
	
	@Column(name = "CURRENT_STATUS",nullable=true)
	private Integer currentStatus;
	
	/** 删除标志 */
	@Column(name = "DELETION_FLAG")
	private java.lang.Integer deletionFlag;
	
	//车牌
	@Transient
	private java.lang.String plateNumber;
	
	@Transient
	private java.lang.String fenxiao_center;
	
	//违规类型name
	@Transient
	private java.lang.String typeId_nick;

	//车型
	@Transient 
	private java.lang.Integer carStyleId;
	
	/** 车型 */
	@Transient
	private java.lang.String carStyleId_Nick;
	
	//vin
	@Transient 
	private java.lang.String vin;
	
	//----------------------- company 表
	
	/** 是否重点城市(经销商状态) */
	@Transient 
	private java.lang.Integer isKeyCity;
	
	/**网络形态   网络形态：对于汽车经销商公司才可使用，也叫“”（4S店、城市展厅、直管直营店、非直管直营店） */
	@Transient
	private java.lang.Integer dealerType;
	
	/** 网络形态 TODO 网络代码 ? */
	@Transient 
	private java.lang.String dealerCode;
	
	/** 地区-省编号 */
	@Transient
	private java.lang.Integer regionProvinceId;	
	/** 地区-市编号 */
	@Transient
	private java.lang.Integer regionCityId;
	
	/**	网络形态中文名*/
	@Transient
	private String isKeyCity_Nick;
	
	/**	经销商状态中文名*/
	@Transient
	private String dealerType_Nick;
	
	/**	经销商中文名*/
	@Transient
	private String companyName;
	
	@Transient
	private String regionProvinceId_Nick;

	@Transient
	private String regionCityId_Nick;
	
	@Transient
	private java.util.Date  startTime;


	@Transient
	private java.util.Date  endTime;
	
	
	//违规月份
	@Transient
	private Integer  year;
	//违规季度
	@Transient
	private Integer  season;

	//违规月份
	@Transient
	private String  month;
	//违规周
	@Transient
	private String  week;
	
	public Violation() {
	}
	
	public Violation(ViolationFindVO violationFindVO) {
		if (violationFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, violationFindVO);
			this.nick();
		}
	}
	
	public void nick() {
		//车型
		carStyleId_Nick = CacheCarManager.getCarStyleNameById(getCarStyleId());
		//经销商状态
		isKeyCity_Nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.isKeyCitys, getIsKeyCity());
		//网络形态
		dealerType_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dealerTypes, getDealerType());
		//地区
		regionProvinceId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionProvinceId());
		
		regionCityId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionCityId());
		
		typeId_nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.violationTypes, getTypeId());
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public java.lang.String getFenxiao_center() {
		return fenxiao_center;
	}

	//分销中心
	public void setFenxiao_center(java.lang.String fenxiao_center) {
		this.fenxiao_center = fenxiao_center;
	}
	public java.lang.String getTypeId_nick() {
		return typeId_nick;
	}


	public void setTypeId_nick(java.lang.String typeId_nick) {
		this.typeId_nick = typeId_nick;
	}
	
	public java.util.Date getEnd_Time() {
		return end_Time;
	}

	public void setEnd_Time(java.util.Date end_Time) {
		this.end_Time = end_Time;
	}

	public java.util.Date getStart_Time() {
		return start_Time;
	}

	public void setStart_Time(java.util.Date start_Time) {
		this.start_Time = start_Time;
	}

	public java.lang.String getCarStyleId_Nick() {
		return carStyleId_Nick;
	}


	public void setCarStyleId_Nick(java.lang.String carStyleId_Nick) {
		this.carStyleId_Nick = carStyleId_Nick;
	}
	
	
	public java.lang.String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(java.lang.String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public java.lang.Integer getCarStyleId() {
		return carStyleId;
	}

	public void setCarStyleId(java.lang.Integer carStyleId) {
		this.carStyleId = carStyleId;
	}

	public java.lang.String getVin() {
		return vin;
	}

	public void setVin(java.lang.String vin) {
		this.vin = vin;
	}

	public java.lang.Integer getIsKeyCity() {
		return isKeyCity;
	}

	public void setIsKeyCity(java.lang.Integer isKeyCity) {
		this.isKeyCity = isKeyCity;
	}

	public java.lang.Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(java.lang.Integer dealerType) {
		this.dealerType = dealerType;
	}

	public java.lang.String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(java.lang.String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public java.lang.Integer getRegionProvinceId() {
		return regionProvinceId;
	}

	public void setRegionProvinceId(java.lang.Integer regionProvinceId) {
		this.regionProvinceId = regionProvinceId;
	}

	public java.lang.Integer getRegionCityId() {
		return regionCityId;
	}

	public void setRegionCityId(java.lang.Integer regionCityId) {
		this.regionCityId = regionCityId;
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

	public java.lang.Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(java.lang.Integer tpyeId) {
		this.typeId = tpyeId;
	}

	public java.lang.Integer getCountId() {
		return countId;
	}

	public void setCountId(java.lang.Integer countId) {
		this.countId = countId;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.Integer getDriveLineId() {
		return driveLineId;
	}

	public void setDriveLineId(java.lang.Integer driveLineId) {
		this.driveLineId = driveLineId;
	}
	
	public String getIsKeyCity_Nick() {
		return isKeyCity_Nick;
	}


	public void setIsKeyCity_Nick(String isKeyCity_Nick) {
		this.isKeyCity_Nick = isKeyCity_Nick;
	}


	public String getDealerType_Nick() {
		return dealerType_Nick;
	}


	public void setDealerType_Nick(String dealerType_Nick) {
		this.dealerType_Nick = dealerType_Nick;
	}


	public String getRegionProvinceId_Nick() {
		return regionProvinceId_Nick;
	}


	public void setRegionProvinceId_Nick(String regionProvinceId_Nick) {
		this.regionProvinceId_Nick = regionProvinceId_Nick;
	}


	public String getRegionCityId_Nick() {
		return regionCityId_Nick;
	}


	public void setRegionCityId_Nick(String regionCityId_Nick) {
		this.regionCityId_Nick = regionCityId_Nick;
	}
	
	
	public java.util.Date getStartTime() {
		return startTime;
	}


	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}


	public java.util.Date getEndTime() {
		return endTime;
	}


	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public java.lang.Integer getDeletionFlag() {
		return deletionFlag;
	}

	public void setDeletionFlag(java.lang.Integer deletionFlag) {
		this.deletionFlag = deletionFlag;
	}
	
	
}
