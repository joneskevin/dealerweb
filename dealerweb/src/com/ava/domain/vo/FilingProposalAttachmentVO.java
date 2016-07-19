package com.ava.domain.vo;


//报备申请附件表
public class FilingProposalAttachmentVO{
	
	private java.lang.Integer id;
	
	// 报备类型
	private java.lang.Integer filingProposalId;
	
	// 申请状态
	private java.lang.Integer status;
	
	//附件类型
	private java.lang.String type;

	//附件原始名称
	private java.lang.String originalName;
	
	//附件显示名
	private java.lang.String displayName;
	
	//描述
	private java.lang.String description;

	private String fullPath;

	// 文件大小
	private String size;

	//上传人
	private java.lang.Integer createId;
	
	//上传时间
	private java.util.Date createTime;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getFilingProposalId() {
		return filingProposalId;
	}

	public void setFilingProposalId(java.lang.Integer filingProposalId) {
		this.filingProposalId = filingProposalId;
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