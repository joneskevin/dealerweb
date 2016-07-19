package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//报备申请
@Entity
@Table(name="t_filing_proposal_detail")
public class FilingProposalDetail implements Serializable {

	private static final long serialVersionUID = -4334537116682116962L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;

	//报备车辆ID
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;

	//报备编号
	@Column(name = "PROPOSAL_ID")
	private Integer proposalId;
	
	//状态（0 删除 1有效）
	@Column(name = "STATUS")
	private Integer status;
		
	//月份
	@Column(name = "MONTHS")
	private Integer months;
	

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

	public java.lang.Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(java.lang.Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}
}