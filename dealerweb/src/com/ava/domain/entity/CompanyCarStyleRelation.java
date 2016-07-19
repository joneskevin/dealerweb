package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_company_car_style_relation")
public class CompanyCarStyleRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;//经销商ID

	@Column(name = "CAR_STYLE_ID")
	private java.lang.Integer carStyleId;//车型ID

	@Column(name = "YEARS")
	private java.lang.Integer years;//年份
	
	@Column(name = "CONFIG_TYPE")
	private java.lang.Integer configType;//方式（必配、选配、无需配置）

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

	public java.lang.Integer getYears() {
		return years;
	}

	public void setYears(java.lang.Integer years) {
		this.years = years;
	}

	public java.lang.Integer getConfigType() {
		return configType;
	}

	public void setConfigType(java.lang.Integer configType) {
		this.configType = configType;
	}

	
}