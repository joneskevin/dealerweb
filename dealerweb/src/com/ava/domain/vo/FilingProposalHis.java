package com.ava.domain.vo;


//报备申请
public class FilingProposalHis{

	private java.lang.Integer filingProposalId;
	
	private java.lang.Integer saleCenterId;
	
	// 分销中心
	private String saleCenterName;
	
	// 经销商状态
	private String isKeyCityNick;

	//网络形态
	private String dealerTypeNick;
	
	//车牌
	private String plateNumber;
	
	//车型
	private String vehicleStyle;

	//经销商名称
	private java.lang.String companyName;
	//经销商代码
	private String dealerCode;
	
	//一级经销商名称
	private java.lang.String parentCompanyName;
	
	//一级经销商代码
	private String parentDealerCode;

	//省
	private String provinceName;

	//市
	private java.lang.String cityName;

	//申请时间
	private java.util.Date proposalTime;
	
	//审核时间
	private java.util.Date approvalTime;
	
	//报备类型
	private Integer type;
	//报备类型
	private String filingType;
	
	//报备状态
	private Integer status;
	//报备状态
	private String filingStatus;
	
	//开始日期
	private java.util.Date startTime;
	
	//结束日期
	private java.util.Date endTime;
	
	private String approvalName;

	public java.lang.Integer getFilingProposalId() {
		return filingProposalId;
	}

	public void setFilingProposalId(java.lang.Integer filingProposalId) {
		this.filingProposalId = filingProposalId;
	}

	public java.lang.Integer getSaleCenterId() {
		return saleCenterId;
	}

	public void setSaleCenterId(java.lang.Integer saleCenterId) {
		this.saleCenterId = saleCenterId;
	}

	public String getSaleCenterName() {
		return saleCenterName;
	}

	public void setSaleCenterName(String saleCenterName) {
		this.saleCenterName = saleCenterName;
	}

	public String getIsKeyCityNick() {
		return isKeyCityNick;
	}

	public void setIsKeyCityNick(String isKeyCityNick) {
		this.isKeyCityNick = isKeyCityNick;
	}

	public String getDealerTypeNick() {
		return dealerTypeNick;
	}

	public void setDealerTypeNick(String dealerTypeNick) {
		this.dealerTypeNick = dealerTypeNick;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getVehicleStyle() {
		return vehicleStyle;
	}

	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public java.lang.String getParentCompanyName() {
		return parentCompanyName;
	}

	public void setParentCompanyName(java.lang.String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}

	public String getParentDealerCode() {
		return parentDealerCode;
	}

	public void setParentDealerCode(String parentDealerCode) {
		this.parentDealerCode = parentDealerCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public java.lang.String getCityName() {
		return cityName;
	}

	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}

	public java.util.Date getProposalTime() {
		return proposalTime;
	}

	public void setProposalTime(java.util.Date proposalTime) {
		this.proposalTime = proposalTime;
	}

	public java.util.Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(java.util.Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFilingType() {
		return filingType;
	}

	public void setFilingType(String filingType) {
		this.filingType = filingType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFilingStatus() {
		return filingStatus;
	}

	public void setFilingStatus(String filingStatus) {
		this.filingStatus = filingStatus;
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

	public String getApprovalName() {
		return approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}
	
	
}