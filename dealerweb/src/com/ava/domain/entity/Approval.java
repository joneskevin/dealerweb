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

@Entity
@Table(name="t_approval")
public class Approval implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "PROPOSAL_ID")
	private java.lang.Integer proposalId;

	@Column(name = "USER_ID")
	private java.lang.Integer userId;

	@Column(name = "USER_NICK")
	private java.lang.String userNick;

	@Column(name = "ADVICE")
	private java.lang.Integer approvalStatus;
	
	@Column(name = "ADVICE_DESCRIPTION")
	private java.lang.String remark;

	@Column(name = "APPROVAL_TIME")
	private java.util.Date approvalTime;

	@Transient
	private java.lang.String approvalStatus_Nick;

	public void nick(){
		/**审批意见：通过、不通过、驳回	*/
		this.setApprovalStatus_Nick(SelectOptionResource.getOptionTextByValue(SelectOptionResource.proposalItemStatus, this.getApprovalStatus()));
	}

	public java.lang.String getApprovalStatus_Nick() {
		return approvalStatus_Nick;
	}

	public void setApprovalStatus_Nick(java.lang.String approvalStatus_Nick) {
		this.approvalStatus_Nick = approvalStatus_Nick;
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(java.lang.Integer proposalId) {
		this.proposalId = proposalId;
	}

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.String getUserNick() {
		return userNick;
	}

	public void setUserNick(java.lang.String userNick) {
		this.userNick = userNick;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.util.Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(java.util.Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public java.lang.Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(java.lang.Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
}
