package com.ava.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheOrgManager;

@Entity
@Table(name = "t_drive_line")
public class DriveLine  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	private java.lang.String name;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;

	@Column(name = "MILEAGE")
	private java.lang.Integer mileage;
	
	private java.lang.Integer type;//类型（1：加油；2：试驾）

	@Column(name = "FENCE_TYPE")
	private java.lang.Integer fenceType;//类型（1：单围栏；2：双围栏）

	@Column(name = "JSON_CONTENT")
	private java.lang.String jsonContent;
	
	@Column(name = "JSON_REFERENCE_CONTENT")
	private java.lang.String jsonReferenceContent;
	
	@Column(name = "SEMICYCLE_POINT")
	private java.lang.String semicyclePoint;
	
	@Column(name = "CREATION_TIME")
	private Date creationTime;
	
	/** 更新时间 */
	@Column(name = "MODIFY_TIME")
	private Date modifyTime;
	
	/**线路填充色*/
	@Column(name = "FILL_COLOR")
	private java.lang.String fillColor;
	
	/**线路边框*/
	@Column(name = "STROKE_COLOR")
	private java.lang.String strokeColor;

	/** 公司名称 */
	@Transient
	private java.lang.String companyName;
	
	@Transient
	private java.lang.String type_Nick;
	
	@Transient
	private java.lang.String fenceType_Nick;

	@Transient
	private java.lang.String jsonPolygon1;

	@Transient
	private java.lang.String jsonPolygon2;
	
	@Transient
	private java.lang.String jsonReferencePolygon;
	
	public void nick() {
		//公司名称
		companyName = CacheOrgManager.getOrgName(companyId);//配置状态
		type_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.driveLineTypeArray, getType());
		fenceType_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.driveLineFenceTypeArray, getFenceType());
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

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
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

	public java.lang.String getSemicyclePoint() {
		return semicyclePoint;
	}

	public void setSemicyclePoint(java.lang.String semicyclePoint) {
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

	public java.lang.String getJsonReferenceContent() {
		return jsonReferenceContent;
	}

	public void setJsonReferenceContent(java.lang.String jsonReferenceContent) {
		this.jsonReferenceContent = jsonReferenceContent;
	}

	public java.lang.String getJsonReferencePolygon() {
		return jsonReferencePolygon;
	}

	public void setJsonReferencePolygon(java.lang.String jsonReferencePolygon) {
		this.jsonReferencePolygon = jsonReferencePolygon;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	
}
