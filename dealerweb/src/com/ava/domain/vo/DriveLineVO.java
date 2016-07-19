package com.ava.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.ava.domain.entity.DriveLine;
import com.ava.gateway.gpsUtil.GPSPoint;
import com.ava.util.MyBeanUtils;
import com.vividsolutions.jts.geom.Geometry;

public class DriveLineVO implements Serializable {

	// 线路ID
	private java.lang.Integer id;
	
	private java.lang.String name;

	private java.lang.Integer companyId;
	
	private java.lang.String companyName;
	
	//里程数
	private java.lang.Integer mileage;
	
	//类型（1：加油；2：试驾）
	private java.lang.Integer type;

	//类型（1：单围栏；2：双围栏）
	private java.lang.Integer fenceType;

	// 双围栏的情况下，指向外圈的围栏对象；单围栏情况下，指向围栏对象。
	private Geometry outPolygon;

	// 双围栏的情况下，指向内圈圈的围栏对象；单围栏情况下为空。
	private Geometry innerPolygon;
	
	//半圈经纬度坐标点
	private String semicyclePoint;
	
	private java.lang.String type_Nick;
	
	private java.lang.String fenceType_Nick;
	
	/**线路填充色*/
	private java.lang.String fillColor;
	
	/**线路边框色*/
	private java.lang.String strokeColor;
	
	private java.lang.String jsonContent;
	
	private java.lang.String jsonReferenceContent;
	
	private java.lang.String jsonPolygon1;

	private java.lang.String jsonPolygon2;
	
	private java.lang.String jsonReferencePolygon;
	
	private List<GPSPoint> referenceLPoints;
	
	private Date creationTime;//创建时间
	
	/** 更新时间 */
	private Date modifyTime;

	private DealerVO dealerVO;
	
	public DriveLineVO() {
	}
	
	public DriveLineVO(DriveLine driveLine) {
		if (driveLine != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, driveLine);
		}
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
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

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getFenceType() {
		return fenceType;
	}

	public void setFenceType(java.lang.Integer fenceType) {
		this.fenceType = fenceType;
	}

	public Geometry getOutPolygon() {
		return outPolygon;
	}

	public void setOutPolygon(Geometry outPolygon) {
		this.outPolygon = outPolygon;
	}

	public Geometry getInnerPolygon() {
		return innerPolygon;
	}

	public void setInnerPolygon(Geometry innerPolygon) {
		this.innerPolygon = innerPolygon;
	}

	public String getSemicyclePoint() {
		return semicyclePoint;
	}

	public void setSemicyclePoint(String semicyclePoint) {
		this.semicyclePoint = semicyclePoint;
	}

	public java.lang.String getFillColor() {
		return fillColor;
	}

	public void setFillColor(java.lang.String fillColor) {
		this.fillColor = fillColor;
	}

	public java.lang.String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(java.lang.String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public DealerVO getDealerVO() {
		return dealerVO;
	}

	public void setDealerVO(DealerVO dealerVO) {
		this.dealerVO = dealerVO;
	}

	public java.lang.String getJsonPolygon1() {
		return jsonPolygon1;
	}

	public void setJsonPolygon1(java.lang.String jsonPolygon1) {
		this.jsonPolygon1 = jsonPolygon1;
	}

	public java.lang.String getJsonPolygon2() {
		return jsonPolygon2;
	}

	public void setJsonPolygon2(java.lang.String jsonPolygon2) {
		this.jsonPolygon2 = jsonPolygon2;
	}

	public java.lang.String getJsonReferencePolygon() {
		return jsonReferencePolygon;
	}

	public void setJsonReferencePolygon(java.lang.String jsonReferencePolygon) {
		this.jsonReferencePolygon = jsonReferencePolygon;
	}

	public java.lang.String getJsonReferenceContent() {
		return jsonReferenceContent;
	}

	public void setJsonReferenceContent(java.lang.String jsonReferenceContent) {
		this.jsonReferenceContent = jsonReferenceContent;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
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

	public java.lang.String getType_Nick() {
		return type_Nick;
	}

	public void setType_Nick(java.lang.String type_Nick) {
		this.type_Nick = type_Nick;
	}

	public java.lang.String getFenceType_Nick() {
		return fenceType_Nick;
	}

	public void setFenceType_Nick(java.lang.String fenceType_Nick) {
		this.fenceType_Nick = fenceType_Nick;
	}

	public java.lang.String getJsonContent() {
		return jsonContent;
	}

	public void setJsonContent(java.lang.String jsonContent) {
		this.jsonContent = jsonContent;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public List<GPSPoint> getReferenceLPoints() {
		return referenceLPoints;
	}

	public void setReferenceLPoints(List<GPSPoint> referenceLPoints) {
		this.referenceLPoints = referenceLPoints;
	}

}
