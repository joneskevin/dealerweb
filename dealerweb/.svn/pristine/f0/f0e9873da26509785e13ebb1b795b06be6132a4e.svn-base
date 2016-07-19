/**
 * 公告实体
 * Created on 2015-1-4
 * filename: Notice.java
 * @author jiangfei
 * @version xxx
 */

package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 公告实体
 * */
@Entity
@Table(name = "t_notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
	
	/** 标题 */
	@Column(name = "TITLE")
	private java.lang.String title;
	
	/** 简介 */
	@Column(name = "SUMMARY")
	private java.lang.String summary;
	
	/** 类型 */
	@Column(name = "TYPE")
	private java.lang.Integer type;
	
	/** 启用时间 */
	@Column(name = "START_TIME")
	private java.util.Date startTime;
	
	/** 失效时间 */
	@Column(name = "INVALID_TIME")
	private java.util.Date invalidTime;
	
	/** 是否停用  0 启用 1 停用 */
	@Column(name = "STATUS")
	private java.lang.Integer status;

	/** 存储用户选择的经销商集合文本 */
	@Transient
	private java.lang.String contents;
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getSummary() {
		return summary;
	}

	public void setSummary(java.lang.String summary) {
		this.summary = summary;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(java.util.Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getContents() {
		return contents;
	}

	public void setContents(java.lang.String contents) {
		this.contents = contents;
	}
}
