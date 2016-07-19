package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name="t_department")
@PrimaryKeyJoinColumn(name="ORG_ID")
public class Department extends Org  implements Serializable{

	private java.lang.String remark;
	
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
}