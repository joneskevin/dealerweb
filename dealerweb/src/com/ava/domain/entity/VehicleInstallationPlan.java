package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_vehicle_installation_plan")
public class VehicleInstallationPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
    
	/** 试驾车型ID */
	@Column(name = "TEST_DRIVE_CAR_STYLE_ID")
	private java.lang.Integer testDriveCarStyleId;
	
	/** 待换装车型ID */
	@Column(name = "DESS_CAR_STYLE_ID")
	private java.lang.Integer dessCarStyleId;
	
	/** 车辆ID(换装1、直接拆除 2) */
	@Column(name = "TYPE")
	private java.lang.Integer type;
	
	/** 状态：启用：0，停用：1 */
	@Column(name = "FLAG")
	private java.lang.Integer flag;
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}


	public java.lang.Integer getTestDriveCarStyleId() {
		return testDriveCarStyleId;
	}

	public void setTestDriveCarStyleId(java.lang.Integer testDriveCarStyleId) {
		this.testDriveCarStyleId = testDriveCarStyleId;
	}

	public java.lang.Integer getDessCarStyleId() {
		return dessCarStyleId;
	}

	public void setDessCarStyleId(java.lang.Integer dessCarStyleId) {
		this.dessCarStyleId = dessCarStyleId;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getFlag() {
		return flag;
	}

	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}
	
	
	
}
