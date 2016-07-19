package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;

@Entity
@Table(name = "t_survey_car_style")
public class SurveyCarStyle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	/** 问卷车型名称 */
	@Column(name = "CAR_STYLE_NAME")
	private java.lang.String carStyleName;
	
	/** 经销商类型：4S店销量>20004S(1)、4S店销量<2000(2)、直营店(3)、直管直营店(4)、城市展厅(5) */
	@Column(name = "DEALER_TYPE")
	private java.lang.Integer dealerType;
	
	/** 组别*/
	@Column(name = "GROUP_TYPE")
	private java.lang.Integer groupType;
	
	/** 不配置车型标志 （1、是;0、不是）*/
	@Column(name = "FLAG")
	private java.lang.Integer flag;
	

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getCarStyleName() {
		return carStyleName;
	}

	public void setCarStyleName(java.lang.String carStyleName) {
		this.carStyleName = carStyleName;
	}

	public java.lang.Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(java.lang.Integer dealerType) {
		this.dealerType = dealerType;
	}

	public java.lang.Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(java.lang.Integer groupType) {
		this.groupType = groupType;
	}

	public java.lang.Integer getFlag() {
		return flag;
	}

	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}
	
	
}
