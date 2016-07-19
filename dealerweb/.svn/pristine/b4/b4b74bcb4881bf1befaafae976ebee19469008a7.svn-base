package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_proposal_attachment")
public class ProposalAttachment implements Serializable {
	private static final long serialVersionUID = -2047330431214271013L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	// 报备类型
	@Column(name="PROPOSAL_ID")
	private java.lang.Integer proposalId;
	
	// 申请状态
	@Column(name = "STATUS")
	private java.lang.Integer status;
	
	//附件类型
	@Column(name = "TYPE")
	private java.lang.String type;

	//附件原始名称
	@Column(name = "ORIGINAL_NAME")
	private java.lang.String originalName;
	
	//附件显示名
	@Column(name = "DISPLAY_NAME")
	private java.lang.String displayName;
	
	//描述
	@Column(name = "DESCRIPTION")
	private java.lang.String description;

	@Column(name = "FULL_PATH")
	private String fullPath;

	// 子类型，比如：年休、病假、事假、其它
	@Column(name = "SIZE")
	private String size;

	//上传人
	@Column(name = "CREATE_ID")
	private java.lang.Integer createId;
	
	//上传时间
	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;

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

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(java.lang.String originalName) {
		this.originalName = originalName;
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

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public java.lang.Integer getCreateId() {
		return createId;
	}

	public void setCreateId(java.lang.Integer createId) {
		this.createId = createId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

}