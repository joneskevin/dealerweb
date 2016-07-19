package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_bigArea_report_item")
public class BigAreaReportItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
    
	/** 大区名 */
	@Column(name = "BIG_AREA")
	private java.lang.String bigArea;
	
	/** 统计项 */
	@Column(name = "ITEM")
	private java.lang.String item;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getBigArea() {
		return bigArea;
	}

	public void setBigArea(java.lang.String bigArea) {
		this.bigArea = bigArea;
	}

	public java.lang.String getItem() {
		return item;
	}

	public void setItem(java.lang.String item) {
		this.item = item;
	}
	
}
