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

//报备申请
@Entity
@Table(name="t_filing_proposal")
public class FilingProposal implements Serializable {
	private static final long serialVersionUID = 1889783496574847838L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	// 报备类型
	@Column(name="type")
	private java.lang.Integer type;
	
	// 报备状态
	@Column(name = "status")
	private java.lang.Integer status;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	//申请人
	@Column(name = "PROPOSER_ID")
	private java.lang.Integer proposerId;

	//申请时间
	@Column(name = "PROPOSAL_TIME")
	private java.util.Date proposalTime;

	//目的地
	@Column(name = "DESTINATION")
	private java.lang.String description;

	//申请原因
	@Column(name = "REASON")
	private java.lang.String reason;
	
	//开始日期
	@Column(name = "START_TIME")
	private java.util.Date startTime;
	
	//结束日期
	@Column(name = "END_TIME")
	private java.util.Date endTime;
	
	//审批人
	@Column(name = "APPROVAL_ID")
	private java.lang.Integer approvalId;
	
	//审批意见
	@Column(name = "APPROVAL_MESSAGE")
	private String approvalMessage;
	
	//审批时间
	@Column(name = "APPROVAL_TIME")
	private java.util.Date approvalTime;
	
	//创建时间
	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;
	
	//更新时间
	@Column(name = "UPDATE_TIME")
	private java.util.Date updateTime;
	
	@Transient
	private String statusNick;
	
	@Transient
	private String typeNick;
	
	@Transient
	private String proposerName;
	
	@Transient
	private String approvalName;
	
	public void nick(){
		typeNick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.filingProposalTypeArray,getType());
		statusNick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.filingProposalStatusArray, getStatus());
		proposerName=null==CacheUserManager.getNickName(proposerId) ? "":CacheUserManager.getNickName(proposerId);
		approvalName=null==CacheUserManager.getNickName(null==approvalId ? -100:approvalId) ? "" :CacheUserManager.getNickName(null==approvalId ? -100:approvalId);
		if(null==approvalName)
			approvalName="";
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

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getReason() {
		return reason;
	}

	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public String getStatusNick() {
		return statusNick;
	}

	public void setStatusNick(String statusNick) {
		this.statusNick = statusNick;
	}

	public String getTypeNick() {
		return typeNick;
	}

	public void setTypeNick(String typeNick) {
		this.typeNick = typeNick;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public java.lang.Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(java.lang.Integer approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalName() {
		return approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	public String getApprovalMessage() {
		return approvalMessage;
	}

	public void setApprovalMessage(String approvalMessage) {
		this.approvalMessage = approvalMessage;
	}

	public java.util.Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(java.util.Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
}