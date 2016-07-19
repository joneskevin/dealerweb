package com.ava.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.SelectOptionResource;

@Entity
@Table(name = "t_fence")
public class Fence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	private java.lang.String name;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;

	private java.lang.Integer type;//类型(0：圆形；1：矩形；2：多边形)

	@Column(name = "JSON_DATA")
	private java.lang.String jsonData;

	@Column(name = "BAN_TYPE")
	private java.lang.Integer banType;//禁止类型（1：禁止驶入；0：禁止驶出）
	
	@Column(name = "CREATION_TIME")
	private Date creationTime;//创建时间

	@Transient
	private java.lang.String banType_Nick;
	
	
	public void nick(){
		banType_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.banTypes, getBanType());
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

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.String getJsonData() {
		return jsonData;
	}

	public void setJsonData(java.lang.String jsonData) {
		this.jsonData = jsonData;
	}

	public java.lang.Integer getBanType() {
		return banType;
	}

	public void setBanType(java.lang.Integer banType) {
		this.banType = banType;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.String getBanType_Nick() {
		return banType_Nick;
	}

	public void setBanType_Nick(java.lang.String banType_Nick) {
		this.banType_Nick = banType_Nick;
	}
}
