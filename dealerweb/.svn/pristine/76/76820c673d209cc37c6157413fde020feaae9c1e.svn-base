package com.ava.domain.vo;

import com.ava.domain.entity.VehicleInstallationPlan;
import com.ava.resource.cache.CacheCarManager;
import com.ava.util.MyBeanUtils;


public class VehicleInstallationPlanVO {
	
	private java.lang.Integer id;
    
	/** 试驾车型ID */
	private java.lang.Integer testDriveCarStyleId;
	
	/** 待换装车型ID */
	private java.lang.Integer dessCarStyleId;
	
	/** 车辆ID(换装1、直接拆除 2) */
	private java.lang.Integer type;
	
	private java.lang.String testDriveCarStyleNick;
	
	private java.lang.String dessCarStyleNick;
	
	/** 状态：启用：0，停用：1 */
	private java.lang.Integer flag;
	
	public VehicleInstallationPlanVO() {
	}
	
	public VehicleInstallationPlanVO(VehicleInstallationPlan vehicleInstallationPlan){
		if(vehicleInstallationPlan == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, vehicleInstallationPlan);
	}
	
	public VehicleInstallationPlanVO(DemolitionFindVO demolitionFindVO){
		if(demolitionFindVO == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, demolitionFindVO);
		nick();
	}
	
	public void nick() {
		testDriveCarStyleNick = CacheCarManager.getCarStyleNameById(getTestDriveCarStyleId());
		dessCarStyleNick = CacheCarManager.getCarStyleNameById(getDessCarStyleId());
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}


	public java.lang.Integer getTestDriveCarStyleId() {
		return testDriveCarStyleId;
	}

	public void setTestDriveCarStyleId(java.lang.Integer testDriveCarStyleId) {
		this.testDriveCarStyleId = testDriveCarStyleId;
	}

	public java.lang.Integer getDessCarStyleId() {
		return dessCarStyleId;
	}

	public void setDessCarStyleId(java.lang.Integer dessCarStyleId) {
		this.dessCarStyleId = dessCarStyleId;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.String getDessCarStyleNick() {
		return dessCarStyleNick;
	}

	public void setDessCarStyleNick(java.lang.String dessCarStyleNick) {
		this.dessCarStyleNick = dessCarStyleNick;
	}

	public java.lang.String getTestDriveCarStyleNick() {
		return testDriveCarStyleNick;
	}

	public void setTestDriveCarStyleNick(java.lang.String testDriveCarStyleNick) {
		this.testDriveCarStyleNick = testDriveCarStyleNick;
	}

	public java.lang.Integer getFlag() {
		return flag;
	}

	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}
	
	
	
}
