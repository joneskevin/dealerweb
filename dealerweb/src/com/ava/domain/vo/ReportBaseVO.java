package com.ava.domain.vo;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.ava.util.TypeConverter;

public class ReportBaseVO {
	
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
	
	/**经销商ID*/
	private java.lang.Integer companyId;
	
	/**市名称*/
	private java.lang.String cityName;
	
	/**经销商名称*/
	private java.lang.String companyName;
	
	/**网络代码*/
	private java.lang.String dealerCode;
	
	/**上级经销商ID*/
	private java.lang.Integer parentCompanyId;
	
	/**上级经销商名称*/
	private java.lang.String parentCompanyName;
	
	/**上级网络代码*/
	private java.lang.String parentDealerCode;
	
	/**经销商状态名称*/
	private java.lang.String isKeyCityNick;
	
	/**网络形态名称*/
	private java.lang.String dealerTypeNick;

	/**车型名称*/
	private java.lang.String carStyleName;
	
	/**实际配置*/
	private java.lang.Integer realityConfigCount;

	public java.lang.String getSaleCenterName() {
		return saleCenterName;
	}

	public void setSaleCenterName(java.lang.String saleCenterName) {
		this.saleCenterName = saleCenterName;
	}

	public java.lang.String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}

	public java.lang.String getCityName() {
		return cityName;
	}

	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
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

	public java.lang.String getIsKeyCityNick() {
		return isKeyCityNick;
	}

	public void setIsKeyCityNick(java.lang.String isKeyCityNick) {
		this.isKeyCityNick = isKeyCityNick;
	}

	public java.lang.String getDealerTypeNick() {
		return dealerTypeNick;
	}

	public void setDealerTypeNick(java.lang.String dealerTypeNick) {
		this.dealerTypeNick = dealerTypeNick;
	}

	public java.lang.String getCarStyleName() {
		return carStyleName;
	}

	public void setCarStyleName(java.lang.String carStyleName) {
		this.carStyleName = carStyleName;
	}

	public java.lang.Integer getRealityConfigCount() {
		return realityConfigCount;
	}

	public void setRealityConfigCount(BigDecimal realityConfigCount) {
		this.realityConfigCount = TypeConverter.toInteger(realityConfigCount);
	}

	public Integer getSaleCenterId() {
		return saleCenterId;
	}

	public void setSaleCenterId(Integer saleCenterId) {
		this.saleCenterId = saleCenterId;
	}

	public java.lang.Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(java.lang.Integer provinceId) {
		this.provinceId = provinceId;
	}

	public java.lang.Integer getCityId() {
		return cityId;
	}

	public void setCityId(java.lang.Integer cityId) {
		this.cityId = cityId;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
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
	
	

}
