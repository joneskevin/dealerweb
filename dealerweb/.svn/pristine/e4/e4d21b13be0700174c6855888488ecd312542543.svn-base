package com.ava.gateway.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Long id;
	
	@Column(name="VIN")
	private String vin;
	
	@Column(name="SERIAL_NUMBER")
	private String serialNumber;
	
	@Column(name="IMSI")
	private String imsi;
		
	@Column(name="MESSAGE_CODE")
	private Integer messageCode;
	
	@Column(name="MESSAGE_DATE")
	private Date messageDate;
	
	@Column(name="CREATE_TIME")
	private Date createTime =new Date();

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Integer getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(Integer messageCode) {
		this.messageCode = messageCode;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}
	
	public Date getCreateTime() {
		createTime = new Date();
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = new Date();
	}
}
