package com.ava.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_survey_result")
public class SurveyResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	/** 问卷车型ID */
	@Column(name = "CAR_STYLE_ID")
	private java.lang.Integer carStyleId;
	
	/** 创建时间 */
	@Column(name = "CREATION_TIME")
	private java.util.Date creationTime;
	
	public SurveyResult() {
		
	}

	public SurveyResult(Integer companyId, Integer carStyleId, Date creationTime) {
		this.companyId = companyId;
		this.carStyleId = carStyleId;
		this.creationTime = creationTime;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getCarStyleId() {
		return carStyleId;
	}

	public void setCarStyleId(java.lang.Integer carStyleId) {
		this.carStyleId = carStyleId;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
	
	
	
}
