package com.ava.admin.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;

@Entity
@Table(name = "t_task_message")
public class TaskMessage implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
    
	@Column(name = "TASK_NAME")	
	private String taskName;
	
	@Column(name = "VIN")	
	private String vin;
	
	@Column(name = "START_TIME")	
	private String startTime;
	
	@Column(name = "END_TIME")	
	private String endTime;
	
	@Column(name = "COMPANY_ID")	
	private String companyId;
	
	@Column(name = "STATUS")	
	private String status;
	
	@Column(name = "CREATE_TIME")	
	private Date createTime;
	
	@Column(name = "COMPLETE_TIME")	
	private Date completeTime;

	@Column(name = "COMPLETE_RATE")	
	private Double completeRate;
	
	@Column(name = "DURATION_TIME")	
	private Integer durationTime; //持续时间，单位为秒
	
	@Column(name = "COMPLETE_ESTIMATE")	
	private Integer completeEstimate;//预计完成时间
	
	@Column(name = "TASK_TYPE")	
	private Integer taskType;//任务类型：0为机器重跑，2为手工重跑

	@Column(name = "COUNTER")	
	private Integer counter; //执行次数
	
	
	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	
	
	public Integer getCompleteEstimate() {
		return completeEstimate;
	}

	public void setCompleteEstimate(Integer completeEstimate) {
		this.completeEstimate = completeEstimate;
	}

	public Integer getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}
	

	public Double getCompleteRate() {
		return completeRate;
	}

	public void setCompleteRate(Double completeRate) {
		this.completeRate = completeRate;
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
}
