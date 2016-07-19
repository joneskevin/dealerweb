package com.ava.api.vo;

import java.io.Serializable;

import com.ava.domain.entity.ProposalAttachment;
import com.ava.resource.cache.CacheUserManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

public class ProposalAttachmentApiVo implements Serializable {
	private java.lang.Integer id;

	private java.lang.Integer proposalId;
	
	//创建者ID，可以是申请人，也可以是任一个审批人
	private java.lang.Integer creatorId;

	private java.lang.String creationTime;

	private java.lang.Integer status;

	private java.lang.Integer type;

	private java.lang.String displayName;

	private java.lang.String description;

	private java.lang.String fullPath;

	private java.lang.Float size;

	public ProposalAttachmentApiVo(ProposalAttachment proposalAttachment) {
		if(proposalAttachment == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, proposalAttachment);
		this.setCreationTime(DateTime.toNormalDateTime(proposalAttachment.getCreateTime()));
	}
	//创建人姓名
	private java.lang.String creatorId_Nick;
	
	public void nick(){
		this.setCreatorId_Nick(CacheUserManager.getNickName(this.getCreatorId()));
	}

	public java.lang.String getCreatorId_Nick() {
		return creatorId_Nick;
	}

	public void setCreatorId_Nick(java.lang.String creatorIdNick) {
		creatorId_Nick = creatorIdNick;
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

	public java.lang.Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(java.lang.Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(java.lang.String displayName) {
		this.displayName = displayName;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getFullPath() {
		return fullPath;
	}

	public void setFullPath(java.lang.String fullPath) {
		this.fullPath = fullPath;
	}

	public java.lang.Float getSize() {
		return size;
	}

	public void setSize(java.lang.Float size) {
		this.size = size;
	}

}