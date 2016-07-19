/**
 * Created on 2015-6-18
 * filename: City.java
 * Description: 
 * Copyright: Copyright(c)2014
 * Company: BD Cluster
 */
package com.ava.domain.vo;

import com.ava.util.excel.ExcelVOAttribute;


public class DesignateOrderExcelVO {
	
	@ExcelVOAttribute(name="网络代码", column="A")
	private String dealerCode;
	
	@ExcelVOAttribute(name="换装前车型ID", column="B")
	private Integer testDriveCarStyleId;
	
	@ExcelVOAttribute(name="换装后车型ID", column="C")
	private Integer dessCarStyleId;

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getTestDriveCarStyleId() {
		return testDriveCarStyleId;
	}

	public void setTestDriveCarStyleId(Integer testDriveCarStyleId) {
		this.testDriveCarStyleId = testDriveCarStyleId;
	}

	public Integer getDessCarStyleId() {
		return dessCarStyleId;
	}

	public void setDessCarStyleId(Integer dessCarStyleId) {
		this.dessCarStyleId = dessCarStyleId;
	}
	
	
	
}
