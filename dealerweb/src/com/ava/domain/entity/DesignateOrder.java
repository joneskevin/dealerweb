package com.ava.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_designate_order")
public class DesignateOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
    
	/** 网络代码 */
	@Column(name = "DEALER_CODE")
	private java.lang.String dealerCode;
	
	/** 试驾车型ID */
	@Column(name = "TEST_DRIVE_CAR_STYLE_ID")
	private java.lang.Integer testDriveCarStyleId;
	
	/** 待换装车型ID */
	@Column(name = "DESS_CAR_STYLE_ID")
	private java.lang.Integer dessCarStyleId;
	
	@Column(name = "CREATION_TIME")
	private Date creationTime;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(java.lang.String dealerCode) {
		this.dealerCode = dealerCode;
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

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	
	
	
}
