package com.ava.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_hopeful_custumer")
public class HopefulCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "COMPANY_ID")
	private java.lang.String companyId;
	
	/** 录入客户数的日期  格式2013-06-01*/
	@Column(name = "INPUT_DATE")
	private Date inputDate;

	/** 录入客户数的当前月  格式2013-06-01*/
	@Column(name = "TIME")
	private Date time;

	/** 有望客户数 */
	@Column(name = "NUMBER")
	private java.lang.Integer number;
	
	/** 有望客户数 */
	@Column(name = "RECORD_DATE")
	private Date recordDate;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.String companyId) {
		this.companyId = companyId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public java.lang.Integer getNumber() {
		return number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	
}