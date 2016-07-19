package com.ava.domain.vo;


public class VehicleFindVO extends BaseFindVO {
	
	/** 是否可拆除*/
	private java.lang.String isDemolitionNick;
	
	/** 设备号 */
	private java.lang.String uniqueId;
	
	/** 通讯号 */
	private java.lang.String simMobile;
	
	/** 安装时间*/
	private java.util.Date operationTime;
	
	/** 新入网经销商 1：是 0：不是*/
	private java.lang.String isNetWorkDealerNick;

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

	public java.lang.String getIsDemolitionNick() {
		return isDemolitionNick;
	}

	public void setIsDemolitionNick(java.lang.String isDemolitionNick) {
		this.isDemolitionNick = isDemolitionNick;
	}

	public java.lang.String getIsNetWorkDealerNick() {
		return isNetWorkDealerNick;
	}

	public void setIsNetWorkDealerNick(java.lang.String isNetWorkDealerNick) {
		this.isNetWorkDealerNick = isNetWorkDealerNick;
	}



	

}
