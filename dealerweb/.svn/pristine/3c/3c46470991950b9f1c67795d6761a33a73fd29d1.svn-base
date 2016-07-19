package com.ava.domain.vo;

import com.ava.domain.entity.CompanyCarStyleRelation;
import com.ava.util.MyBeanUtils;


public class CompanyCarStyleRelationVO {

	private java.lang.Integer id;

	private java.lang.Integer companyId;//经销商ID

	private java.lang.Integer carStyleId;//车型ID

	private java.lang.Integer years;//年份
	
	private java.lang.Integer type;//方式（必配、选配、无需配置）
	
	public CompanyCarStyleRelationVO(CompanyCarStyleRelation companyCarStyleRelation){
		if(companyCarStyleRelation == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, companyCarStyleRelation);
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

	public java.lang.Integer getYears() {
		return years;
	}

	public void setYears(java.lang.Integer years) {
		this.years = years;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}
}