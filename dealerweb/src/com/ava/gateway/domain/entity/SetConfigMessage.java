package com.ava.gateway.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//报备申请
@Entity
@Table(name="t_message_set_config")
public class SetConfigMessage extends BaseMessage implements Serializable {
	private static final long serialVersionUID = 5298584540975317706L;
	@Column(name="IS_DEFAULT")
	private Integer isDefault;

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}