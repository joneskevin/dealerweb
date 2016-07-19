package com.ava.domain.vo;

import java.util.Date;


public class TestNoDriveWeekFindVO extends BaseFindVO {
	
	private java.lang.Integer id;
	
	private java.lang.Integer year;
	
	private java.lang.Integer month;
	
	private java.lang.Integer week;
	
	private Date startTime;
	
	private Date endTime;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getYear() {
		return year;
	}

	public void setYear(java.lang.Integer year) {
		this.year = year;
	}

	public java.lang.Integer getMonth() {
		return month;
	}

	public void setMonth(java.lang.Integer month) {
		this.month = month;
	}

	public java.lang.Integer getWeek() {
		return week;
	}

	public void setWeek(java.lang.Integer week) {
		this.week = week;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
}
