package com.ava.domain.vo;

import com.ava.domain.entity.BoxOperation;
import com.ava.util.MyBeanUtils;


public class BoxOperationVO {
	
	private java.lang.Integer id;
    
	/** 公司ID */
	private java.lang.Integer companyId;
	
	/** BOX_ID */
	private java.lang.Integer boxId;
	
	/** 车辆ID */
	private java.lang.Integer vehicleId;
	
	/** 操作用户 */
	private java.lang.String operationName;
	
	/** 操作时间*/
	private java.util.Date operationTime;
	
	/** 退出时间*/
	private java.util.Date exitTime;
	
	/** 状态*/
	private java.lang.Integer STATUS;
	
	/** 类型（新装、拆除、解绑）*/
	private java.lang.Integer type;
	
	/** 备注*/
	private java.lang.String remark;
	
	private java.lang.String vin;
	
	private BoxVO box = new BoxVO();
	
	public BoxOperationVO() {
		
	}
	
	public BoxOperationVO(BoxOperation boxOperation) {
		if (boxOperation != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, boxOperation);
		}
	}
	
	public BoxOperationVO(VehicleFindVO vehicleFindVO) {
		if (vehicleFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, vehicleFindVO);
		}
	}
	
	public BoxOperationVO(DemolitionFindVO demolitionFindVO) {
		if (demolitionFindVO != null) {
			MyBeanUtils.copyPropertiesContainsDate(this, demolitionFindVO);
		}
	}

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

	public java.lang.Integer getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(java.lang.Integer sTATUS) {
		STATUS = sTATUS;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.util.Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(java.util.Date exitTime) {
		this.exitTime = exitTime;
	}

	public BoxVO getBox() {
		return box;
	}

	public void setBox(BoxVO box) {
		this.box = box;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getVin() {
		return vin;
	}

	public void setVin(java.lang.String vin) {
		this.vin = vin;
	}
	
	
	
}
