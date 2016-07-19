package com.ava.gateway.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//报备申请
@Entity
@Table(name="t_message_get_common")
public class GetCommonMessage extends BaseMessage implements Serializable {

	private static final long serialVersionUID = -2187201624645785958L;		
	@Column(name="TBOX_TOKEN")
	private String tboxToken;
	
	@Column(name="FIRE_STATE")
	private Integer fireState;

	
	public String getTboxToken() {
		return tboxToken;
	}

	public void setTboxToken(String tboxToken) {
		this.tboxToken = tboxToken;
	}

	public Integer getFireState() {
		return fireState;
	}

	public void setFireState(Integer fireState) {
		this.fireState = fireState;
	}
}