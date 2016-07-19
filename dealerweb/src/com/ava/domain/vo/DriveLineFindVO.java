package com.ava.domain.vo;

import java.util.Date;

import com.ava.util.TypeConverter;


public class DriveLineFindVO extends BaseFindVO {
	
	/** 线路ID */
	private java.lang.Integer id;
	
	/** 线路名称 */
	private java.lang.String name;

	/** 里程 */
	private java.lang.Integer mileage;
	
	/** 线路类型 */
	private java.lang.Integer type;
	
	/** 线路类型名称 */
	private java.lang.String typeNick;

	/** 围栏类型 */
	private java.lang.Integer fenceType;
	
	/** 围栏类型名称 */
	private java.lang.String fenceTypeNick;
	
	/** 创建时间 */
	private Date creationTime;
	
	/** 更新时间 */
	private Date modifyTime;
	
	/** 分销中心ID */
	private Integer saleCenterId;
	
	/**分销中心名称*/
	private java.lang.String saleCenterName;
	
	/** 地区-省编号 */
	private java.lang.Integer provinceId;	
	
	/**省名称*/
	private java.lang.String provinceName;
	
	/** 地区-市编号 */
	private java.lang.Integer cityId;
	
	/**市名称*/
	private java.lang.String cityName;
	
	/**一级网点ID*/
	private java.lang.Integer parentCompanyId;
	
	/** 一级网点名称 */
	private java.lang.String parentCompanyName;
	
	/** 一级网点代码 */
	private java.lang.String parentDealerCode;
	
	/**经销商ID*/
	private java.lang.Integer companyId;
	
	/** 经销商名称 */
	private java.lang.String companyName;
	
	/** 网络代码 */
	private java.lang.String dealerCode;
	
	/** 是否重点城市(经销商状态) */
	private java.lang.Integer isKeyCity;
	
	/**经销商状态名称*/
	private java.lang.String isKeyCityNick;
	
	/** 网络形态：对于汽车经销商公司才可使用，也叫“网络形态”（4S店、城市展厅、直管直营店、非直管直营店） */
	private java.lang.Integer dealerType;
	
	/**网络形态名称*/
	private java.lang.String dealerTypeNick;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getMileage() {
		return mileage;
	}

	public void setMileage(java.lang.Integer mileage) {
		this.mileage = mileage;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Byte type) {
		this.type = TypeConverter.toInteger(type);
	}

	public java.lang.Integer getFenceType() {
		return fenceType;
	}

	public void setFenceType(java.lang.Byte fenceType) {
		this.fenceType = TypeConverter.toInteger(fenceType);
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getSaleCenterId() {
		return saleCenterId;
	}

	public void setSaleCenterId(Integer saleCenterId) {
		this.saleCenterId = saleCenterId;
	}

	public java.lang.String getSaleCenterName() {
		return saleCenterName;
	}

	public void setSaleCenterName(java.lang.String saleCenterName) {
		this.saleCenterName = saleCenterName;
	}

	public java.lang.Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(java.lang.Integer provinceId) {
		this.provinceId = provinceId;
	}

	public java.lang.String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}

	public java.lang.Integer getCityId() {
		return cityId;
	}

	public void setCityId(java.lang.Integer cityId) {
		this.cityId = cityId;
	}

	public java.lang.String getCityName() {
		return cityName;
	}

	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}

	public java.lang.Integer getParentCompanyId() {
		return parentCompanyId;
	}

	public void setParentCompanyId(java.lang.Integer parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}

	public java.lang.String getParentCompanyName() {
		return parentCompanyName;
	}

	public void setParentCompanyName(java.lang.String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}

	public java.lang.String getParentDealerCode() {
		return parentDealerCode;
	}

	public void setParentDealerCode(java.lang.String parentDealerCode) {
		this.parentDealerCode = parentDealerCode;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
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

	public java.lang.String getIsKeyCityNick() {
		return isKeyCityNick;
	}

	public void setIsKeyCityNick(java.lang.String isKeyCityNick) {
		this.isKeyCityNick = isKeyCityNick;
	}

	public java.lang.Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(java.lang.Integer dealerType) {
		this.dealerType = dealerType;
	}

	public java.lang.String getDealerTypeNick() {
		return dealerTypeNick;
	}

	public void setDealerTypeNick(java.lang.String dealerTypeNick) {
		this.dealerTypeNick = dealerTypeNick;
	}

	public java.lang.String getTypeNick() {
		return typeNick;
	}

	public void setTypeNick(java.lang.String typeNick) {
		this.typeNick = typeNick;
	}

	public java.lang.String getFenceTypeNick() {
		return fenceTypeNick;
	}

	public void setFenceTypeNick(java.lang.String fenceTypeNick) {
		this.fenceTypeNick = fenceTypeNick;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	

}
