package com.ava.domain.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_monitor_location")
public class MonitorLocation implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;
	
	@Column(name = "ORIGN_LONGITUDE")
	private String orignLongitude;
	
	@Column(name = "ORIGN_LATITUDE")
	private String orignLatitude;

	private java.lang.String longitude;
	
	private java.lang.String latitude;
	
	private java.lang.Integer speed;
	
	private java.lang.Integer direction;

	@Column(name = "CREATION_TIME")
	private java.lang.Long creationTime;
	
	@Column(name = "SAVE_DATE")
	private Date saveDate;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(java.lang.Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public java.lang.String getLongitude() {
		return longitude;
	}

	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}

	public java.lang.String getLatitude() {
		return latitude;
	}

	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}

	public java.lang.Integer getSpeed() {
		return speed;
	}

	public void setSpeed(java.lang.Integer speed) {
		this.speed = speed;
	}

	public java.lang.Long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.lang.Long creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.Integer getDirection() {
		return direction;
	}

	public void setDirection(java.lang.Integer direction) {
		this.direction = direction;
	}

	public String getOrignLongitude() {
		return orignLongitude;
	}

	public void setOrignLongitude(String orignLongitude) {
		this.orignLongitude = orignLongitude;
	}

	public String getOrignLatitude() {
		return orignLatitude;
	}

	public void setOrignLatitude(String orignLatitude) {
		this.orignLatitude = orignLatitude;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
}
