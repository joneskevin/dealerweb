package com.ava.gateway.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

//报备申请
@Entity
@Table(name="t_message_box_update")
public class BoxUpdateMessage extends BaseMessage implements Serializable {
	private static final long serialVersionUID = -4329731307721319883L;
}