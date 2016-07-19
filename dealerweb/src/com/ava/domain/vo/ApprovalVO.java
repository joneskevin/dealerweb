package com.ava.domain.vo;

import com.ava.domain.entity.Approval;
import com.ava.resource.SelectOptionResource;
import com.ava.util.MyBeanUtils;

public class ApprovalVO {
	
	private java.lang.Integer id;

	private java.lang.Integer proposalId;

	private java.lang.Integer userId;

	private java.lang.String userNick;

	private java.lang.Integer approvalStatus;
	
	private java.lang.String remark;

	private java.util.Date approvalTime;

	private java.lang.String approvalStatus_Nick;
	
	public ApprovalVO() {
		
	}
	
	public ApprovalVO(Approval approval){
		if(approval == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, approval);
	}

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
