package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * 报警实体
 * */
@Entity
@Table(name="t_alert")
public class Alert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	@Column(name = "TYPE")
	private java.lang.Integer type;
	
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;
	
	@Column(name = "TO_USER_ID")
	private java.lang.Integer toUserId;
	
	@Column(name = "RANK")
	private java.lang.Integer rank;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "IS_READED")
	private Integer readed;
	
	@Column(name = "CREATION_TIME")
	private Long creationTime;
	
	@Column(name = "EVENT_ID")
	private java.lang.Integer eventId;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(java.lang.Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public java.lang.Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(java.lang.Integer toUserId) {
		this.toUserId = toUserId;
	}

	public java.lang.Integer getRank() {
		return rank;
	}

	public void setRank(java.lang.Integer rank) {
		this.rank = rank;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReaded() {
		return readed;
	}

	public void setReaded(Integer readed) {
		this.readed = readed;
	}

	public Long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.Integer getEventId() {
		return eventId;
	}

	public void setEventId(java.lang.Integer eventId) {
		this.eventId = eventId;
	}
}
