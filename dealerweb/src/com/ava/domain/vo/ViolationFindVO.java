package com.ava.domain.vo;

import com.ava.util.TypeConverter;

public class ViolationFindVO {
	
	/** 违规ID */
	private java.lang.Integer violationId;

	/** 分销中心ID */
	private Integer parentId;
	
	/** 地区-省编号 */
	private java.lang.Integer regionProvinceId;	
	
	/** 地区-市编号 */
	private java.lang.Integer regionCityId;
	
	/** 经销商名称 */
	private java.lang.String cnName;
	
	/** 网络代码 */
	private java.lang.String dealerCode;
	
	/** 是否重点城市(经销商状态) */
	private java.lang.Integer isKeyCity;
	
	/** 网络形态：对于汽车经销商公司才可使用，也叫“网络形态”（4S店、城市展厅、直管直营店、非直管直营店） */
	private java.lang.Integer dealerType;
	
	/** 车牌 */
	private java.lang.String plateNumber;
	
	/** 车型 */
	private java.lang.Integer carStyleId;
	
	/** VIN */
	private java.lang.String vin;
	
	/** 违规类型 */
	private java.lang.Integer typeId;
	
	/** 违规次数 */
	private java.lang.Integer countId;
	
	/** 违规时间 */
	private java.util.Date creationTime;
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public java.lang.String getCnName() {
		return cnName;
	}

	public void setCnName(java.lang.String cnName) {
		this.cnName = cnName;
	}

	public java.lang.String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(java.lang.String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public java.lang.Integer getIsKeyCity() {
		return isKeyCity;
	}

	public void setIsKeyCity(java.lang.Byte isKeyCity) {
		this.isKeyCity = TypeConverter.toInteger(isKeyCity);
	}

	public java.lang.Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(java.lang.Integer dealerType) {
		this.dealerType = dealerType;
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

	public java.lang.Integer getViolationId() {
		return violationId;
	}

	public void setViolationId(java.lang.Integer violationId) {
		this.violationId = violationId;
	}

	public java.lang.Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(java.lang.Integer typeId) {
		this.typeId = typeId;
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

}
