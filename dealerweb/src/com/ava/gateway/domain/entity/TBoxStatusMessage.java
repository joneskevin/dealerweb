package com.ava.gateway.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//报备申请
@Entity
@Table(name="t_message_box_status")
public class TBoxStatusMessage extends BaseMessage implements Serializable {

	private static final long serialVersionUID = -8371304640230952852L;

	@Column(name="MID")
	private Integer mid;
	
	@Column(name="ACTIVE_ACK")
	private String activeAck;

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getActiveAck() {
		return activeAck;
	}

	public void setActiveAck(String activeAck) {
		this.activeAck = activeAck;
	}
}