package com.ava.domain.vo;

import java.util.List;

import com.ava.resource.DbCacheResource;
import com.ava.resource.SelectOptionResource;

/*
 * company为主的VO  配置查询,违规查询，试驾率报表 都 用到
 */
public class CompanyCarStyleVO {
	
	private java.lang.Integer id;// 经销商ID

	private java.lang.String name;//经销商名称
	
	// 经销商 ,他对应的车型集合 是动态的  可以变化
	private List carStyleList;
	
	//单个经销商配置的车辆
	private java.lang.Integer configureNum;
	
	private java.lang.Integer year;
	
	private java.lang.String week;
	
	private java.lang.String season;
	
	private java.lang.String yearType;//年款

	private java.lang.String color;
	
	/** 地区-省编号 */
	private java.lang.Integer regionProvinceId;	
	/** 地区-市编号 */
	private java.lang.Integer regionCityId;
	
	/**	网络形态中文名*/
	private String isKeyCity_Nick;
	
	/**	经销商状态中文名*/
	private String dealerType_Nick;
	
	
	/** 是否重点城市(经销商状态) */
	private java.lang.Integer isKeyCity;

	/**网络形态   网络形态：对于汽车经销商公司才可使用，也叫“”（4S店、城市展厅、直管直营店、非直管直营店） */
	private java.lang.Integer dealerType;
	
	private String regionProvinceId_Nick;

	private String regionCityId_Nick;
	
	/** 网络形态 TODO 网络代码 */
	private java.lang.String dealerCode;
	
	private java.lang.String fenxiao_center;
	
	private java.lang.String fenxiao_center_nick;
	
	private java.lang.Integer companyId;
	
	private java.util.Date creationTime;
	
	//违规开始时间 结束时间
	private java.util.Date  startTime;

	private java.util.Date  endTime;
	
	//违规数量 某经销商下面的某一阶段的总数量
	private java.lang.Integer  violationNum;
	//试驾次数 某经销商下面某一阶段的总数量
	private java.lang.Integer  testDriveNum;
	
	//试驾率查询 用到 月份 条件
	private java.lang.String month;
	
	//试驾率查询 单个经销商下面总的有望客户数
	private java.lang.Integer hopefulCustomer ;
	
	//试驾率查询 单个经销商下面总的试驾率
	private java.lang.String drivePercent ;
	
	// 大区报表用到9个list
	//配置要求
	private List  configureRequireList;
	//已配置
	private List  hasConfigureList;
	//未配置
	private List  hasNotConfigureList;
	
	//配置率
	private List  configurePercentList;
	//全国配置率
	private List  chinaConfigurePercentList;
	
	//试驾次数
	private List  testDriveTimesList;
	//有望客户数
	private List  hopeFulCustomersList;
	//试驾率list
	private List  testDrivePercentList;
	//全国试驾率 
	private List  chinaTestDrivePercentList;
	
	private DealerVO dealer;
	
	public void nick() {
		//经销商状态
		isKeyCity_Nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.isKeyCitys, getIsKeyCity());
		//网络形态
		dealerType_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dealerTypes, getDealerType());
		
		//地区
		regionProvinceId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionProvinceId());
		regionCityId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionCityId());
		
	}
	
	public java.lang.String getSeason() {
		return season;
	}

	public void setSeason(java.lang.String season) {
		this.season = season;
	}

	public List getHasNotConfigureList() {
		return hasNotConfigureList;
	}

	public void setHasNotConfigureList(List hasNotConfigureList) {
		this.hasNotConfigureList = hasNotConfigureList;
	}

	public List getConfigurePercentList() {
		return configurePercentList;
	}

	public void setConfigurePercentList(List configurePercentList) {
		this.configurePercentList = configurePercentList;
	}

	public List getChinaConfigurePercentList() {
		return chinaConfigurePercentList;
	}

	public void setChinaConfigurePercentList(List chinaConfigurePercentList) {
		this.chinaConfigurePercentList = chinaConfigurePercentList;
	}

	public java.lang.Integer getHopefulCustomer() {
		return hopefulCustomer;
	}

	public void setHopefulCustomer(java.lang.Integer hopefulCustomer) {
		this.hopefulCustomer = hopefulCustomer;
	}

	public java.lang.String getDrivePercent() {
		return drivePercent;
	}

	public void setDrivePercent(java.lang.String drivePercent) {
		this.drivePercent = drivePercent;
	}

	public java.lang.String getMonth() {
		return month;
	}

	public void setMonth(java.lang.String month) {
		this.month = month;
	}

	public java.lang.Integer getTestDriveNum() {
		return testDriveNum;
	}

	public void setTestDriveNum(java.lang.Integer testDriveNum) {
		this.testDriveNum = testDriveNum;
	}


	public java.lang.Integer getViolationNum() {
		return violationNum;
	}


	public void setViolationNum(java.lang.Integer violationNum) {
		this.violationNum = violationNum;
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


	public java.util.Date getCreationTime() {
		return creationTime;
	}


	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}


	public java.lang.String getFenxiao_center_nick() {
		return fenxiao_center_nick;
	}


	public void setFenxiao_center_nick(java.lang.String fenxiao_center_nick) {
		this.fenxiao_center_nick = fenxiao_center_nick;
	}



	public java.lang.Integer getCompanyId() {
		return companyId;
	}



	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}



	public java.lang.String getDealerCode() {
		return dealerCode;
	}


	public void setDealerCode(java.lang.String dealerCode) {
		this.dealerCode = dealerCode;
	}


	public java.lang.String getFenxiao_center() {
		return fenxiao_center;
	}


	public void setFenxiao_center(java.lang.String fenxiao_center) {
		this.fenxiao_center = fenxiao_center;
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


	public java.lang.Integer getConfigureNum() {
		return configureNum;
	}

	public void setConfigureNum(java.lang.Integer configureNum) {
		this.configureNum = configureNum;
	}	
	
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

	public List getCarStyleList() {
		return carStyleList;
	}

	public void setCarStyleList(List carStyleList) {
		this.carStyleList = carStyleList;
	}

	public java.lang.String getYearType() {
		return yearType;
	}

	public void setYearType(java.lang.String yearType) {
		this.yearType = yearType;
	}

	public java.lang.String getColor() {
		return color;
	}

	public void setColor(java.lang.String color) {
		this.color = color;
	}


	public java.lang.Integer getYear() {
		return year;
	}

	public void setYear(java.lang.Integer year) {
		this.year = year;
	}

	public List getConfigureRequireList() {
		return configureRequireList;
	}

	public void setConfigureRequireList(List configureRequireList) {
		this.configureRequireList = configureRequireList;
	}

	public List getHasConfigureList() {
		return hasConfigureList;
	}

	public void setHasConfigureList(List hasConfigureList) {
		this.hasConfigureList = hasConfigureList;
	}

	public List getTestDriveTimesList() {
		return testDriveTimesList;
	}

	public void setTestDriveTimesList(List testDriveTimesList) {
		this.testDriveTimesList = testDriveTimesList;
	}

	public List getHopeFulCustomersList() {
		return hopeFulCustomersList;
	}

	public void setHopeFulCustomersList(List hopeFulCustomersList) {
		this.hopeFulCustomersList = hopeFulCustomersList;
	}

	public List getTestDrivePercentList() {
		return testDrivePercentList;
	}

	public void setTestDrivePercentList(List testDrivePercentList) {
		this.testDrivePercentList = testDrivePercentList;
	}

	public List getChinaTestDrivePercentList() {
		return chinaTestDrivePercentList;
	}

	public void setChinaTestDrivePercentList(List chinaTestDrivePercentList) {
		this.chinaTestDrivePercentList = chinaTestDrivePercentList;
	}

	public java.lang.String getWeek() {
		return week;
	}

	public void setWeek(java.lang.String week) {
		this.week = week;
	}

	public DealerVO getDealer() {
		return dealer;
	}

	public void setDealer(DealerVO dealer) {
		this.dealer = dealer;
	}

	
}
