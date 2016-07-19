package com.ava.domain.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.ava.domain.entity.Proposal;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheUserManager;
import com.ava.util.MyBeanUtils;

public class ProposalVO {
	
private java.lang.Integer id;
	
	private java.lang.Integer type;
	
	private java.lang.Integer status;
	
	private java.lang.Integer companyId;
	
	private java.lang.Integer departmentId;
	
	private java.lang.Integer vehicleId;
	
	private java.lang.String contactName;
	
	private java.lang.String contactPhone;
	
	private java.lang.Integer proposerId;
	
	private java.util.Date proposalTime;
	
	private String status_nick;
	
	private String type_nick;
	
	private String proposerName;
	
	private java.lang.String adviceDescription;
	
	private String demolitionStatus_nick;
	
	/** 先申请后添加车牌标志 */
	private Integer isAddPlatenumberCity;
	
	/**待替换的车辆ID*/
	private Integer replaceVehicleId;

	
	public ProposalVO(){
		
	}
	
	public ProposalVO(ProposalFindVO proposalFindVO){
		if(proposalFindVO == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, proposalFindVO);
		this.nick();
	}
	
	public ProposalVO(DemolitionFindVO demolitionFindVO){
		if(demolitionFindVO == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, demolitionFindVO);
		this.nick();
	}
	
	public ProposalVO(Proposal proposal){
		if(proposal == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, proposal);
	}
	
	public void nick(){
		status_nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.installationList, getStatus());
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

	public java.lang.String getAdviceDescription() {
		return adviceDescription;
	}

	public void setAdviceDescription(java.lang.String adviceDescription) {
		this.adviceDescription = adviceDescription;
	}

	public String getDemolitionStatus_nick() {
		return demolitionStatus_nick;
	}

	public void setDemolitionStatus_nick(String demolitionStatus_nick) {
		this.demolitionStatus_nick = demolitionStatus_nick;
	}

	public Integer getReplaceVehicleId() {
		return replaceVehicleId;
	}

	public void setReplaceVehicleId(Integer replaceVehicleId) {
		this.replaceVehicleId = replaceVehicleId;
	}

	public Integer getIsAddPlatenumberCity() {
		return isAddPlatenumberCity;
	}

	public void setIsAddPlatenumberCity(Integer isAddPlatenumberCity) {
		this.isAddPlatenumberCity = isAddPlatenumberCity;
	}


	
}