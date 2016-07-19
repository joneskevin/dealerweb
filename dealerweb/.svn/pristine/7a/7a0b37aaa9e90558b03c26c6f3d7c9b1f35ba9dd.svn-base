package com.ava.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dictionary_geology")
public class Dilixue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private Integer id;
	
	@Column(name = "chinese")
	private String chinese;
	
	@Column(name = "english")
	private java.lang.String english;
	
	@Column(name = "content")
	private java.lang.String content;
	
	@Column(name = "time")
	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getChinese() {
		return chinese;
	}

	public void setChinese(java.lang.String chinese) {
		this.chinese = chinese;
	}

	public java.lang.String getEnglish() {
		return english;
	}

	public void setEnglish(java.lang.String english1) {
		english = english1;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}


}