package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheUserManager;

@Entity
@Table(name="t_proposal")
public class Proposal implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	/** 申请类型（新装、拆除）*/
	@Column(name = "TYPE")
	private java.lang.Integer type;
	
	/** 状态：审批中、已通过、未通）*/
	@Column(name = "STATUS")
	private java.lang.Integer status;
	
	/** 经销商编号*/
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	/** 部门编号*/
	@Column(name = "DEPARTMENT_ID")
	private java.lang.Integer departmentId;
	
	/** 车辆ID*/
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;
	
	/** 联系人名称*/
	@Column(name = "CONTACT_NAME")
	private java.lang.String contactName;
	
	/** 联系电话*/
	@Column(name = "CONTACT_PHONE")
	private java.lang.String contactPhone;
	
	/** 申请人ID	*/
	@Column(name = "PROPOSER_ID")
	private java.lang.Integer proposerId;
	
	/** 申请时间）*/
	@Column(name = "PROPOSAL_TIME")
	private java.util.Date proposalTime;
	
	/** 申请人中文名*/
	@Transient
	private String proposerName;
	
	/** 状态中文名 */
	@Transient
	private String status_nick;
	
	/** 拆除状态 */
	@Transient
	private String demolitionStatus_nick;
	
	/** 申请类型中文名 */
	@Transient
	private String type_nick;
	
	public void nick(){
		type_nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.proposalTypeArray,getType());
		status_nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.installationList, getStatus());
		demolitionStatus_nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.demolitionList, getStatus());
		proposerName = CacheUserManager.getNickName(proposerId);
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(java.lang.Integer departmentId) {
		this.departmentId = departmentId;
	}

	public java.lang.Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(java.lang.Integer vehicleId) {
		this.vehicleId = vehicleId;
	}


	public java.lang.String getContactName() {
		return contactName;
	}

	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}

	public java.lang.String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(java.lang.String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public java.lang.Integer getProposerId() {
		return proposerId;
	}

	public void setProposerId(java.lang.Integer proposerId) {
		this.proposerId = proposerId;
	}

	public java.util.Date getProposalTime() {
		return proposalTime;
	}

	public void setProposalTime(java.util.Date proposalTime) {
		this.proposalTime = proposalTime;
	}

	public String getStatus_nick() {
		return status_nick;
	}

	public void setStatus_nick(String status_nick) {
		this.status_nick = status_nick;
	}

	public String getType_nick() {
		return type_nick;
	}

	public void setType_nick(String type_nick) {
		this.type_nick = type_nick;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public String getDemolitionStatus_nick() {
		return demolitionStatus_nick;
	}

	public void setDemolitionStatus_nick(String demolitionStatus_nick) {
		this.demolitionStatus_nick = demolitionStatus_nick;
	}

}