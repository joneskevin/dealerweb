package com.ava.domain.vo;

import com.ava.util.MyBeanUtils;

public class TestDrive4StatVO extends TestDriveVO {
	// 年
	private Integer year;
	// 月份
	private String month;
	// 周
	private String week;
	
	private Integer loopTotal;
	
	private Integer mileageTotal;
	
	private String dealerCode;
	
	public TestDrive4StatVO() {
		//Spring MVC需要
	}
	
	public TestDrive4StatVO(TestDriveVO testDrive) {
		if (testDrive != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, testDrive);
			
			this.nick();
		}
	}
	
	public void nick() {
		super.nick();
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Integer getLoopTotal() {
		return loopTotal;
	}

	public void setLoopTotal(Integer loopTotal) {
		this.loopTotal = loopTotal;
	}

	public Integer getMileageTotal() {
		return mileageTotal;
	}

	public void setMileageTotal(Integer mileageTotal) {
		this.mileageTotal = mileageTotal;
	}
}
