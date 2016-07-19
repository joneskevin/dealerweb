package com.ava.domain.vo;


public class FilingApprovalVO{

	private java.lang.Integer id;

	private java.lang.Integer filingProposalId;

	private java.lang.Integer userId;

	private java.lang.String advice;
	
	private java.lang.String adviceDescription;

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
