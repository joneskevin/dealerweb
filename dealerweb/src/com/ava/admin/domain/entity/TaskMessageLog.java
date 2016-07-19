package com.ava.admin.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_task_message_log")
public class TaskMessageLog implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	@Column(name = "CURTIME")	
	private Date curtime;
	
	@Column(name = "LOG_CODE")	
	private String logCode;
	
	@Column(name = "VIN")	
	private String vin;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")	
	private Date endDate;
	
	@Column(name = "STATUS")	
	private Integer status;
	
	@Column(name = "SERIAL_NUMBER")	
	private String serialNumber;
	
	@Column(name = "COUNTER")	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
