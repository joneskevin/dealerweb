package com.ava.admin.domain.vo;

import java.io.Serializable;
import java.util.Date;

public class TaskMessageLogVo implements Serializable{
	
	private java.lang.Integer id;
	private Date curtime;
	private String logCode;
	private String vin;
	private Date startDate;
	private Date endDate;
	private Integer status;
	private String startDateNick;
	private String endDateNick;
	private String serialNumber;
	private Integer counter;
	
	
	public Integer getCounter() {
		return counter;
	}
	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getStartDateNick() {
		return startDateNick;
	}
	public void setStartDateNick(String startDateNick) {
		this.startDateNick = startDateNick;
	}
	public String getEndDateNick() {
		return endDateNick;
	}
	public void setEndDateNick(String endDateNick) {
		this.endDateNick = endDateNick;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public Date getCurtime() {
		return curtime;
	}
	public void setCurtime(Date curtime) {
		this.curtime = curtime;
	}
	public String getLogCode() {
		return logCode;
	}
	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
