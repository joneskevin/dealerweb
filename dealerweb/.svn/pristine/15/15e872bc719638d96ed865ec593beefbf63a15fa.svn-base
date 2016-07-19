package com.ava.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ava.dao.IUserDao;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.ProposalAttachment;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConfig;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

public class ProposalApiVo implements Serializable {
	private java.lang.Integer id;
	
	// 审批状态：通过、不通过、驳回
	private java.lang.Integer approvalStatus;

	private java.lang.Integer orgId;

	private java.lang.Integer departmentId;

	// 申请类型：请假、报销、领用物品、其它
	private java.lang.Integer type;

	// 子类型，比如：年休、病假、事假、其它
	private java.lang.Integer subType;

	//金额区间编号
	private java.lang.Integer sumInterval;

	//申请者
	private java.lang.Integer proposerUserId;

	//申请者昵称
	private java.lang.String proposerUserNick;
	
	private String proposalTime;

	//修改者
	private java.lang.Integer modificatorUserId;

	//修改者昵称
	private java.lang.String modificatorUserNick;
	
	private String modificationTime;
	
	private String creationTime;
	
	private java.lang.String description;

	//驳回时，把description复制到descriptionCopy里作为历史记录，多次驳回时，descriptionCopy中只记录最近一次
	private java.lang.String descriptionCopy;
	
	private java.lang.Integer currentFlowPathId;
	
	private java.lang.Integer currentFlowPathItemId;
	
	private java.lang.Integer rejectionFlowPathItemId;
	
	// 要申请的对象的编号
	private java.lang.Integer approvalObjectId;

	//--------------Transient-----------------------
	private java.lang.String flowPathName;

	//申请人所属公司名
	private java.lang.String proposerUserCompanyName;
	
	private java.lang.String type_Nick;

	private java.lang.String subType_Nick;

	private java.lang.String sumInterval_Nick;
	
	/**下一批审批者	*/
	private List<UserApiVo> approvers;

	private java.lang.String approvalStatus_Nick;

	private java.lang.String description_Nick;

	private java.lang.Integer canPublish;
	/**对当前登陆者，是否有发布权限	*/
	private List<ProposalAttachmentApiVo> attachments;
	/**对当前登陆者（当前审批者），是否可编辑自己的附件	*/
	private java.lang.Integer canEditOwnerAttachment;
	/**对当前登陆者（当前审批者），是否有审批权限	*/
	private java.lang.Integer canApprove;
	/**对当前登陆者（当前审批者），是否有打印权限	*/
	private java.lang.Integer canPrint;
	/**对当前登陆者，是否有标识申请询价完成的权限	*/
	private java.lang.Integer enquiryFinish;

	public ProposalApiVo(Proposal proposal) {
		if(proposal == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, proposal);
		this.setProposalTime(DateTime.toNormalDateTime(proposal.getProposalTime()));
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(java.lang.Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public java.lang.Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(java.lang.Integer orgId) {
		this.orgId = orgId;
	}

	public java.lang.Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(java.lang.Integer departmentId) {
		this.departmentId = departmentId;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getSubType() {
		return subType;
	}

	public void setSubType(java.lang.Integer subType) {
		this.subType = subType;
	}

	public java.lang.Integer getSumInterval() {
		return sumInterval;
	}

	public void setSumInterval(java.lang.Integer sumInterval) {
		this.sumInterval = sumInterval;
	}

	public java.lang.Integer getProposerUserId() {
		return proposerUserId;
	}

	public void setProposerUserId(java.lang.Integer proposerUserId) {
		this.proposerUserId = proposerUserId;
	}

	public java.lang.String getProposerUserNick() {
		return proposerUserNick;
	}

	public void setProposerUserNick(java.lang.String proposerUserNick) {
		this.proposerUserNick = proposerUserNick;
	}

	public String getProposalTime() {
		return proposalTime;
	}

	public void setProposalTime(String proposalTime) {
		this.proposalTime = proposalTime;
	}

	public java.lang.Integer getModificatorUserId() {
		return modificatorUserId;
	}

	public void setModificatorUserId(java.lang.Integer modificatorUserId) {
		this.modificatorUserId = modificatorUserId;
	}

	public java.lang.String getModificatorUserNick() {
		return modificatorUserNick;
	}

	public void setModificatorUserNick(java.lang.String modificatorUserNick) {
		this.modificatorUserNick = modificatorUserNick;
	}

	public String getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(String modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getDescriptionCopy() {
		return descriptionCopy;
	}

	public void setDescriptionCopy(java.lang.String descriptionCopy) {
		this.descriptionCopy = descriptionCopy;
	}

	public java.lang.Integer getCurrentFlowPathId() {
		return currentFlowPathId;
	}

	public void setCurrentFlowPathId(java.lang.Integer currentFlowPathId) {
		this.currentFlowPathId = currentFlowPathId;
	}

	public java.lang.Integer getCurrentFlowPathItemId() {
		return currentFlowPathItemId;
	}

	public void setCurrentFlowPathItemId(java.lang.Integer currentFlowPathItemId) {
		this.currentFlowPathItemId = currentFlowPathItemId;
	}

	public java.lang.Integer getRejectionFlowPathItemId() {
		return rejectionFlowPathItemId;
	}

	public void setRejectionFlowPathItemId(java.lang.Integer rejectionFlowPathItemId) {
		this.rejectionFlowPathItemId = rejectionFlowPathItemId;
	}

	public java.lang.Integer getApprovalObjectId() {
		return approvalObjectId;
	}

	public void setApprovalObjectId(java.lang.Integer approvalObjectId) {
		this.approvalObjectId = approvalObjectId;
	}

	public java.lang.String getProposerUserCompanyName() {
		return proposerUserCompanyName;
	}

	public void setProposerUserCompanyName(java.lang.String proposerUserCompanyName) {
		this.proposerUserCompanyName = proposerUserCompanyName;
	}

	public java.lang.String getFlowPathName() {
		return flowPathName;
	}

	public void setFlowPathName(java.lang.String flowPathName) {
		this.flowPathName = flowPathName;
	}

	public java.lang.String getType_Nick() {
		return type_Nick;
	}

	public void setType_Nick(java.lang.String typeNick) {
		type_Nick = typeNick;
	}

	public java.lang.String getSubType_Nick() {
		return subType_Nick;
	}

	public void setSubType_Nick(java.lang.String subTypeNick) {
		subType_Nick = subTypeNick;
	}

	public java.lang.String getSumInterval_Nick() {
		return sumInterval_Nick;
	}

	public void setSumInterval_Nick(java.lang.String sumIntervalNick) {
		sumInterval_Nick = sumIntervalNick;
	}

	public List<UserApiVo> getApprovers() {
		return approvers;
	}

	public void setApprovers(List<UserApiVo> approvers) {
		this.approvers = approvers;
	}

	public java.lang.String getApprovalStatus_Nick() {
		return approvalStatus_Nick;
	}

	public void setApprovalStatus_Nick(java.lang.String approvalStatusNick) {
		approvalStatus_Nick = approvalStatusNick;
	}

	public java.lang.String getDescription_Nick() {
		return description_Nick;
	}

	public void setDescription_Nick(java.lang.String descriptionNick) {
		description_Nick = descriptionNick;
	}

	public List<ProposalAttachmentApiVo> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<ProposalAttachmentApiVo> attachments) {
		this.attachments = attachments;
	}

	public java.lang.Integer getCanPublish() {
		return canPublish;
	}

	public void setCanPublish(java.lang.Integer canPublish) {
		this.canPublish = canPublish;
	}

	public java.lang.Integer getCanEditOwnerAttachment() {
		return canEditOwnerAttachment;
	}

	public void setCanEditOwnerAttachment(java.lang.Integer canEditOwnerAttachment) {
		this.canEditOwnerAttachment = canEditOwnerAttachment;
	}

	public java.lang.Integer getCanApprove() {
		return canApprove;
	}

	public void setCanApprove(java.lang.Integer canApprove) {
		this.canApprove = canApprove;
	}

	public java.lang.Integer getCanPrint() {
		return canPrint;
	}

	public void setCanPrint(java.lang.Integer canPrint) {
		this.canPrint = canPrint;
	}

	public java.lang.Integer getEnquiryFinish() {
		return enquiryFinish;
	}

	public void setEnquiryFinish(java.lang.Integer enquiryFinish) {
		this.enquiryFinish = enquiryFinish;
	}

}