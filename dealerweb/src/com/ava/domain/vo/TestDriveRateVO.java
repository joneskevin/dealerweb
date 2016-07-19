package com.ava.domain.vo;

import com.ava.domain.entity.TestDrive;
import com.ava.domain.entity.TestDriveRate;
import com.ava.util.MyBeanUtils;


public class TestDriveRateVO {
	
	private java.lang.Integer id;

	private java.lang.Integer companyId;
	
	private java.lang.Integer customersCount;
	
	private java.lang.Integer testDriveCount;
	
	private java.lang.Integer year;
	
	private java.lang.Integer month;
	
	private java.lang.Integer monthTestDriveRate;
	
	private java.lang.Integer lastMonthTestDriveRate;
	
	private java.lang.Integer centralRatio;
	
	private java.lang.Integer saleCenterId;
	
	private DealerVO dealerVO = new DealerVO();
	
	public TestDriveRateVO() {
	}
	
	public TestDriveRateVO(TestDriveRate testDriveRate) {
		if (testDriveRate != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, testDriveRate);
		}
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getCustomersCount() {
		return customersCount;
	}

	public void setCustomersCount(java.lang.Integer customersCount) {
		this.customersCount = customersCount;
	}

	public java.lang.Integer getYear() {
		return year;
	}

	public void setYear(java.lang.Integer year) {
		this.year = year;
	}

	public java.lang.Integer getMonth() {
		return month;
	}

	public void setMonth(java.lang.Integer month) {
		this.month = month;
	}

	public java.lang.Integer getMonthTestDriveRate() {
		return monthTestDriveRate;
	}

	public void setMonthTestDriveRate(java.lang.Integer monthTestDriveRate) {
		this.monthTestDriveRate = monthTestDriveRate;
	}

	public java.lang.Integer getLastMonthTestDriveRate() {
		return lastMonthTestDriveRate;
	}

	public void setLastMonthTestDriveRate(java.lang.Integer lastMonthTestDriveRate) {
		this.lastMonthTestDriveRate = lastMonthTestDriveRate;
	}

	public java.lang.Integer getCentralRatio() {
		return centralRatio;
	}

	public void setCentralRatio(java.lang.Integer centralRatio) {
		this.centralRatio = centralRatio;
	}

	public java.lang.Integer getTestDriveCount() {
		return testDriveCount;
	}

	public void setTestDriveCount(java.lang.Integer testDriveCount) {
		this.testDriveCount = testDriveCount;
	}

	public DealerVO getDealerVO() {
		return dealerVO;
	}

	public void setDealerVO(DealerVO dealerVO) {
		this.dealerVO = dealerVO;
	}

	public java.lang.Integer getSaleCenterId() {
		return saleCenterId;
	}

	public void setSaleCenterId(java.lang.Integer saleCenterId) {
		this.saleCenterId = saleCenterId;
	}


}
