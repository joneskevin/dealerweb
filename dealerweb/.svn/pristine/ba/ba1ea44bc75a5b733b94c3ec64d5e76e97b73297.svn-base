package com.ava.domain.vo;

import com.ava.resource.DbCacheResource;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;

/*
 * company为主的VO  7.1.7.9.	外出报备/维修报备 查询报表 用到
 */
public class CompanyFilingProposalVO {
	
	//报备类型
	private java.lang.Integer type;
	//报备类型 名称
	private String type_nick;
	
	private java.lang.String id;// 经销商ID

	private java.lang.String name;//经销商名称
	
	
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
	
	/** 网络形态 TODO 网络代码  */
	private java.lang.String dealerCode;
	
	private java.lang.String fenxiao_center;
	
	private java.lang.String fenxiao_center_nick;
	
	private java.lang.Integer companyId;
	
	
	//查询用到的开始时间 结束时间
	private java.util.Date  startTime;

	private java.util.Date  endTime;
	
	//报备开始时间 结束时间
	private java.util.Date  start_Time;

	private java.util.Date  end_Time;

	//车牌
	private java.lang.String plateNumber;
	
	//车型
	private java.lang.Integer carStyleId;
	
	/** 车型 */
	private java.lang.String carStyleId_Nick;
	
	//vin
	private java.lang.String vin;
	

	public void nick() {
		type_nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.filingProposalTypeArray,getType());
		
		//车型
		carStyleId_Nick = CacheCarManager.getCarStyleNameById(getCarStyleId()); //TODO
		//经销商状态
		isKeyCity_Nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.isKeyCitys, getIsKeyCity());
		//网络形态
		dealerType_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dealerTypes, getDealerType());
		
		//地区
		regionProvinceId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionProvinceId());
		regionCityId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionCityId());
		
	}

	
	public java.lang.Integer getType() {
		return type;
	}


	public void setType(java.lang.Integer type) {
		this.type = type;
	}


	public String getType_nick() {
		return type_nick;
	}


	public void setType_nick(String type_nick) {
		this.type_nick = type_nick;
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



	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}





	public java.util.Date getStart_Time() {
		return start_Time;
	}





	public void setStart_Time(java.util.Date start_Time) {
		this.start_Time = start_Time;
	}





	public java.util.Date getEnd_Time() {
		return end_Time;
	}





	public void setEnd_Time(java.util.Date end_Time) {
		this.end_Time = end_Time;
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





	public java.lang.String getCarStyleId_Nick() {
		return carStyleId_Nick;
	}





	public void setCarStyleId_Nick(java.lang.String carStyleId_Nick) {
		this.carStyleId_Nick = carStyleId_Nick;
	}





	public java.lang.String getVin() {
		return vin;
	}





	public void setVin(java.lang.String vin) {
		this.vin = vin;
	}

	


}
