package com.ava.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_box_operation")
public class BoxOperation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;
    
	/** 公司ID */
	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;
	
	/** BOX_ID */
	@Column(name = "BOX_ID")
	private java.lang.Integer boxId;
	
	/** 车辆ID */
	@Column(name = "VEHICLE_ID")
	private java.lang.Integer vehicleId;
	
	/** 操作用户 */
	@Column(name = "OPERATION_NAME")
	private java.lang.String operationName;
	
	/** 操作时间*/
	@Column(name = "OPERATION_TIME")
	private java.util.Date operationTime;
	
	/** 状态*/
	@Column(name = "STATUS")
	private java.lang.Integer status;
	
	/** 类型（新装、拆除、解绑）*/
	@Column(name = "TYPE")
	private java.lang.Integer type;
	
	/** 备注*/
	@Column(name = "REMARK")
	private java.lang.String remark;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getBoxId() {
		return boxId;
	}

	public void setBoxId(java.lang.Integer boxId) {
		this.boxId = boxId;
	}

	public java.lang.Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(java.lang.Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public java.lang.String getOperationName() {
		return operationName;
	}

	public void setOperationName(java.lang.String operationName) {
		this.operationName = operationName;
	}

	public java.util.Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(java.util.Date operationTime) {
		this.operationTime = operationTime;
	}



	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	
	
}
