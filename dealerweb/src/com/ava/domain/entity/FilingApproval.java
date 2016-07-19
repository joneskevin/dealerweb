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
@Table(name="t_filing_approval")
public class FilingApproval implements Serializable{
	private static final long serialVersionUID = -976357938908689505L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "FILING_PROPOSAL_ID")
	private java.lang.Integer filingProposalId;

	@Column(name = "USER_ID")
	private java.lang.Integer userId;

	@Column(name = "ADVICE")
	private java.lang.String advice;
	
	@Column(name = "ADVICE_DESCRIPTION")
	private java.lang.String adviceDescription;

	@Column(name = "APPROVAL_TIME")
	private java.util.Date approvalTime;

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

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.String getAdvice() {
		return advice;
	}

	public void setAdvice(java.lang.String advice) {
		this.advice = advice;
	}

	public java.lang.String getAdviceDescription() {
		return adviceDescription;
	}

	public void setAdviceDescription(java.lang.String adviceDescription) {
		this.adviceDescription = adviceDescription;
	}

	public java.util.Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(java.util.Date approvalTime) {
		this.approvalTime = approvalTime;
	}
}
