package com.ava.domain.vo;

import javax.persistence.Transient;

import com.ava.domain.entity.Company;
import com.ava.resource.DbCacheResource;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.MyBeanUtils;

public class DealerVO extends OrgVO {

	/** 中文名（组织名称） */
	private java.lang.String cnName;

	/** 英文名 */
	private java.lang.String enName;

	/** 组织账号 */
	private java.lang.String obName;

	/** 组织当前人数 */
	private java.lang.Integer userNum;
	/** 组织空间硬盘已使用量，单位：字节 */
	private java.lang.Long orgUsage;

	/** 地区-国家编号 */
	private java.lang.Integer regionCountryId;
	/** 地区-省编号 */
	private java.lang.Integer regionProvinceId;
	/** 地区-市编号 */
	private java.lang.Integer regionCityId;
	/** 行业一级编号 */
	private java.lang.Integer industryLevel1Id;
	/** 行业二级编号 */
	private java.lang.Integer industryLevel2Id;

	/** 业务范围 */
	private java.lang.String description;

	private java.lang.String contactName;
	/** 邮编 */
	private java.lang.String zip;
	/** 联系电话 */
	private java.lang.String contactTel;
	/** 传真 */
	private java.lang.String fax;
	/** 联系地址 */
	private java.lang.String contactAddress;
	/** 电子邮件 */
	private java.lang.String email;

	/** 组织上次任一成员登录日期 */
	private java.util.Date lastLoginTime;

	/** 国家 */
	private java.lang.String country;

	/** 分销中心 */
	private java.lang.String distributionSaleCenterName;

	/** 网络形态 */
	private java.lang.String dealerCode;

	/** 是否重点城市(经销商状态) */
	private java.lang.Integer isKeyCity;

	/** 网络形态：对于汽车经销商公司才可使用，也叫“网络形态”（4S店、城市展厅、直管直营店、非直管直营店） */
	private java.lang.Integer dealerType;

	/**一级网点ID*/
	private Integer parentCompanyId;
	
	/** 一级网点名称 */
	private String parentCompanyName;
	
	/** 一级网点代码 */
	private java.lang.String parentDealerCode;
	
	/** 组织类型 */
	private String orgType_Nick;

	private String regionProvinceId_Nick;

	private String regionCityId_Nick;

	private String region_Nick;

	private String industryLevel1Id_Nick;

	private String industryLevel2Id_Nick;

	private String industry_Nick;

	/** 网络形态中文名 */
	private String isKeyCity_Nick;

	/** 经销商状态中文名 */
	private String dealerType_Nick;
	
	/** 经销商位置经度 */
	private java.lang.Double baiduLng;
	
	/** 经销商位置纬度 */
	private java.lang.Double baiduLat;
	
	/** 是否限购城市 */
	private Integer isRestrictionCity;

	public DealerVO() {
	}

	public DealerVO(Company company) {
		if (company != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, company);
			this.nick();
		}
	}
	
	public DealerVO(TestDriveFindVO testDriveFindVO) {
		if (testDriveFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, testDriveFindVO);
			this.nick();
		}
	}
	
	public DealerVO(NoTestDriveFindVO noTestDriveFindVO) {
		if (noTestDriveFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, noTestDriveFindVO);
			this.nick();
		}
	}
	
	public DealerVO(VehicleFindVO vehicleFindVO) {
		if (vehicleFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, vehicleFindVO);
			this.nick();
		}
	}


	/**
	 * 这个方法调用本域对象及其通过hibernate自动级联的域对象的及其...（以此类推到必要层次，不同的域对象层次不同）的nick方法
	 */
	public void nick() {
		super.nick();
		
		Integer parentId = getParentId();
		if (parentId != null && parentId == GlobalConstant.FALSE) {
			parentId = 1;
		}
		distributionSaleCenterName = CacheOrgManager.getOrgName(parentId);
		// 经销商状态
		isKeyCity_Nick = SelectOptionResource.getOptionTextByValue(
				SelectOptionResource.isKeyCitys, getIsKeyCity());
		// 网络形态
		dealerType_Nick = SelectOptionResource.getOptionTextByValue(
				SelectOptionResource.dealerTypes, getDealerType());

		// 地区
		regionProvinceId_Nick = DbCacheResource.getDataDictionaryNameById(this
				.getRegionProvinceId());
		regionCityId_Nick = DbCacheResource.getDataDictionaryNameById(this
				.getRegionCityId());
		region_Nick = regionProvinceId_Nick + "/" + regionCityId_Nick;

		// 行业
		if (getIndustryLevel1Id() == null
				|| getIndustryLevel1Id().intValue() <= 0) {
			setIndustryLevel1Id(473);
		}
		if (getIndustryLevel2Id() == null
				|| getIndustryLevel2Id().intValue() <= 0) {
			setIndustryLevel2Id(474);
		}

		industryLevel1Id_Nick = DbCacheResource.getDataDictionaryNameById(this
				.getIndustryLevel1Id());
		industryLevel2Id_Nick = DbCacheResource.getDataDictionaryNameById(this
				.getIndustryLevel2Id());
		industry_Nick = industryLevel1Id_Nick + "/" + industryLevel2Id_Nick;

		// 组织全称
		if (getCnName() == null || "".equals(getCnName())) {
			setCnName(getName());
		}
	}

	public java.lang.String getCnName() {
		return cnName;
	}

	public void setCnName(java.lang.String cnName) {
		this.cnName = cnName;
	}

	public java.lang.String getEnName() {
		return enName;
	}

	public void setEnName(java.lang.String enName) {
		this.enName = enName;
	}

	public java.lang.String getObName() {
		return obName;
	}

	public void setObName(java.lang.String obName) {
		this.obName = obName;
	}

	public java.lang.Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(java.lang.Integer userNum) {
		this.userNum = userNum;
	}

	public java.lang.Long getOrgUsage() {
		return orgUsage;
	}

	public void setOrgUsage(java.lang.Long orgUsage) {
		this.orgUsage = orgUsage;
	}

	public java.lang.Integer getRegionCountryId() {
		return regionCountryId;
	}

	public void setRegionCountryId(java.lang.Integer regionCountryId) {
		this.regionCountryId = regionCountryId;
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

	public java.lang.Integer getIndustryLevel1Id() {
		return industryLevel1Id;
	}

	public void setIndustryLevel1Id(java.lang.Integer industryLevel1Id) {
		this.industryLevel1Id = industryLevel1Id;
	}

	public java.lang.Integer getIndustryLevel2Id() {
		return industryLevel2Id;
	}

	public void setIndustryLevel2Id(java.lang.Integer industryLevel2Id) {
		this.industryLevel2Id = industryLevel2Id;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getContactName() {
		return contactName;
	}

	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}

	public java.lang.String getZip() {
		return zip;
	}

	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}

	public java.lang.String getContactTel() {
		return contactTel;
	}

	public void setContactTel(java.lang.String contactTel) {
		this.contactTel = contactTel;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(java.lang.String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public java.lang.String getCountry() {
		return country;
	}

	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	public java.lang.String getDistributionSaleCenterName() {
		return distributionSaleCenterName;
	}

	public void setDistributionSaleCenterName(
			java.lang.String distributionSaleCenterName) {
		this.distributionSaleCenterName = distributionSaleCenterName;
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

	public void setIsKeyCity(java.lang.Integer isKeyCity) {
		this.isKeyCity = isKeyCity;
	}

	public java.lang.Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(java.lang.Integer dealerType) {
		this.dealerType = dealerType;
	}

	public String getOrgType_Nick() {
		return orgType_Nick;
	}

	public void setOrgType_Nick(String orgType_Nick) {
		this.orgType_Nick = orgType_Nick;
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

	public String getRegion_Nick() {
		return region_Nick;
	}

	public void setRegion_Nick(String region_Nick) {
		this.region_Nick = region_Nick;
	}

	public String getIndustryLevel1Id_Nick() {
		return industryLevel1Id_Nick;
	}

	public void setIndustryLevel1Id_Nick(String industryLevel1Id_Nick) {
		this.industryLevel1Id_Nick = industryLevel1Id_Nick;
	}

	public String getIndustryLevel2Id_Nick() {
		return industryLevel2Id_Nick;
	}

	public void setIndustryLevel2Id_Nick(String industryLevel2Id_Nick) {
		this.industryLevel2Id_Nick = industryLevel2Id_Nick;
	}

	public String getIndustry_Nick() {
		return industry_Nick;
	}

	public void setIndustry_Nick(String industry_Nick) {
		this.industry_Nick = industry_Nick;
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

	public java.lang.Double getBaiduLng() {
		return baiduLng;
	}

	public void setBaiduLng(java.lang.Double baiduLng) {
		this.baiduLng = baiduLng;
	}

	public java.lang.Double getBaiduLat() {
		return baiduLat;
	}

	public void setBaiduLat(java.lang.Double baiduLat) {
		this.baiduLat = baiduLat;
	}

	public Integer getParentCompanyId() {
		return parentCompanyId;
	}

	public void setParentCompanyId(Integer parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}

	public String getParentCompanyName() {
		return parentCompanyName;
	}

	public void setParentCompanyName(String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}

	public java.lang.String getParentDealerCode() {
		return parentDealerCode;
	}

	public void setParentDealerCode(java.lang.String parentDealerCode) {
		this.parentDealerCode = parentDealerCode;
	}

	public Integer getIsRestrictionCity() {
		return isRestrictionCity;
	}

	public void setIsRestrictionCity(Integer isRestrictionCity) {
		this.isRestrictionCity = isRestrictionCity;
	}
	
	
	
}