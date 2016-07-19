package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ava.resource.GlobalConstant;

@Entity
@Table(name = "t_car_style")
public class CarStyle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	private java.lang.String name;//车型名称

	@Column(name = "BRAND_ID")
	private java.lang.Integer brandId;//品牌ID：1，斯柯达品牌;2，大众品牌

	@Column(name = "SERIES_ID")
	private java.lang.Integer seriesId;//车系ID

	@Column(name = "SERIES_NAME")
	private java.lang.String seriesName;//车系名称

	@Column(name = "ENGINE_ID")
	private java.lang.Integer engineId;//引擎ID

	@Column(name = "ON_MARKET_DATE")
	private java.util.Date onMarketDate;//上市日期

	@Column(name = "OFF_MARKET_DATE")
	private java.util.Date offMarketDate;//退市日期

	@Column(name = "IS_PRODUCTION")
	private java.lang.Integer isProduction;//是否在产

	@Column(name = "IS_SALE")
	private java.lang.Integer isSale;//是否在售

	@Column(name = "GUIDE_PRICE")
	private java.lang.Integer guidePrice;//指导价

	@Column(name = "YEAR_TYPE")
	private java.lang.String yearType;//年款

	@Column(name = "COLOR")
	private java.lang.String color;

	@Column(name = "WEIGHT")
	private java.lang.Integer weight;//重量

	@Column(name = "DISCHARGE")
	private java.lang.String discharge;//排量
	
	@Column(name = "MODEL_CODE")
	private java.lang.String modelCode;//车型代码
	
	@Column(name = "PARENT_ID")
	private java.lang.Integer parentId;//所属车型ID
	
	@Column(name = "LEVEL")
	private java.lang.Integer level;//层级
	
	public void init(){
		this.setBrandId(GlobalConstant.CAR_BRAND_VM);
		this.setEngineId(0);
		this.setIsProduction(1);
		this.setIsSale(1);
		this.setGuidePrice(0);
		this.setWeight(0);
		this.setModelCode("");
		this.setParentId(0);
		this.setLevel(0);
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(java.lang.Integer brandId) {
		this.brandId = brandId;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(java.lang.Integer seriesId) {
		this.seriesId = seriesId;
	}

	public java.lang.String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(java.lang.String seriesName) {
		this.seriesName = seriesName;
	}

	public java.lang.Integer getEngineId() {
		return engineId;
	}

	public void setEngineId(java.lang.Integer engineId) {
		this.engineId = engineId;
	}

	public java.util.Date getOnMarketDate() {
		return onMarketDate;
	}

	public void setOnMarketDate(java.util.Date onMarketDate) {
		this.onMarketDate = onMarketDate;
	}

	public java.util.Date getOffMarketDate() {
		return offMarketDate;
	}

	public void setOffMarketDate(java.util.Date offMarketDate) {
		this.offMarketDate = offMarketDate;
	}

	public java.lang.Integer getIsProduction() {
		return isProduction;
	}

	public void setIsProduction(java.lang.Integer isProduction) {
		this.isProduction = isProduction;
	}

	public java.lang.Integer getIsSale() {
		return isSale;
	}

	public void setIsSale(java.lang.Integer isSale) {
		this.isSale = isSale;
	}

	public java.lang.Integer getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(java.lang.Integer guidePrice) {
		this.guidePrice = guidePrice;
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

	public java.lang.Integer getWeight() {
		return weight;
	}

	public void setWeight(java.lang.Integer weight) {
		this.weight = weight;
	}

	public java.lang.String getDischarge() {
		return discharge;
	}

	public void setDischarge(java.lang.String discharge) {
		this.discharge = discharge;
	}

	public java.lang.String getModelCode() {
		return modelCode;
	}

	public void setModelCode(java.lang.String modelCode) {
		this.modelCode = modelCode;
	}

	public java.lang.Integer getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}

	public java.lang.Integer getLevel() {
		return level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}
	
	
}