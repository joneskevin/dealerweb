package com.ava.domain.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.domain.entity.DriveLine;
import com.ava.domain.entity.Fence;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.MyBeanUtils;

public class DriveLineWithFenceVO {
	private java.lang.Integer id;

	private java.lang.String name;

	private java.lang.Integer companyId;

	private java.lang.Integer mileage;

	private java.lang.Integer type;// 类型（1：加油；2：试驾）

	private java.lang.Integer fenceType;// 类型（1：单围栏；2：双围栏）

	private java.lang.String jsonContent;

	private Date creationTime;// 创建时间

	@Transient
	private Fence fence1;// 双围栏的情况下，指向外圈的围栏对象；单围栏情况下，指向围栏对象。

	@Transient
	private Fence fence2;// 双围栏的情况下，指向内圈圈的围栏对象；单围栏情况下为空。

	public DriveLineWithFenceVO(DriveLine driveLine) {
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

	public Fence getFence1() {
		return fence1;
	}

	public void setFence1(Fence fence1) {
		this.fence1 = fence1;
	}

	public Fence getFence2() {
		return fence2;
	}

	public void setFence2(Fence fence2) {
		this.fence2 = fence2;
	}

}
