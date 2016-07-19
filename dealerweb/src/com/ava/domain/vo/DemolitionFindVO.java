package com.ava.domain.vo;

import com.ava.util.TypeConverter;


public class DemolitionFindVO extends BaseFindVO {
	
	/** 申请ID */
	private java.lang.Integer proposalId;
	
	/** 申请状态*/
	private java.lang.Integer status;
	
	/** 是否可拆除*/
	private java.lang.String isDemolition_Nick;
	
	/** 设备号 */
	private java.lang.String uniqueId;
	
	/** 通讯号 */
	private java.lang.String simMobile;
	
	/** 安装时间*/
	private java.util.Date operationTime;
	
	/** 退出时间*/
	private java.util.Date exitTime;
	
	/** 待换装车型ID */
	private java.lang.Integer dessCarStyleId;
	
	/** 待换装车型名称 */
	private java.lang.String dessCarStyleName;
	
	/** 限购城市ID */
	private Integer restrictionCityId;
	
	public java.lang.String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(java.lang.String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public java.lang.String getSimMobile() {
		return simMobile;
	}

	public void setSimMobile(java.lang.String simMobile) {
		this.simMobile = simMobile;
	}

	public java.util.Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(java.util.Date operationTime) {
		this.operationTime = operationTime;
	}

	public java.lang.String getIsDemolition_Nick() {
		return isDemolition_Nick;
	}

	public void setIsDemolition_Nick(java.lang.String isDemolition_Nick) {
		this.isDemolition_Nick = isDemolition_Nick;
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

	public void setStatus(java.lang.Byte status) {
		this.status = TypeConverter.toInteger(status);
	}

	public java.lang.Integer getDessCarStyleId() {
		return dessCarStyleId;
	}

	public void setDessCarStyleId(java.lang.Integer dessCarStyleId) {
		this.dessCarStyleId = dessCarStyleId;
	}

	public java.util.Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(java.util.Date exitTime) {
		this.exitTime = exitTime;
	}

	public java.lang.String getDessCarStyleName() {
		return dessCarStyleName;
	}

	public void setDessCarStyleName(java.lang.String dessCarStyleName) {
		this.dessCarStyleName = dessCarStyleName;
	}

	public Integer getRestrictionCityId() {
		return restrictionCityId;
	}

	public void setRestrictionCityId(Integer restrictionCityId) {
		this.restrictionCityId = restrictionCityId;
	}

}
