package com.ava.domain.vo;

public class FlowPathItem4Rejection {
	
	private EItemType type;
	/** 节点类型：申请人节点、流程中的节点 */
	public enum EItemType{
		item4Proposer,//申请人节点
		itemOfFlow;//流程中的节点
	}
	
	private java.lang.Integer id;

	/** 岗位ID*/
	private java.lang.Integer positonId;

	/** 岗位名称 */
	private java.lang.String positionName;

	/** 公司ID */
	private java.lang.Integer companyId;
	
	/** 部门ID */
	private java.lang.Integer departmentId;
	
	/** 公司名称 */
	private java.lang.String companyName;
	
	/** 部门名称 */
	private java.lang.String departmentName;

	private FlowPathItem4Rejection(){
	}

	public FlowPathItem4Rejection(Integer id, EItemType type, String positonName){
		this.id = id;
		this.type = type;
		this.positionName = positonName;
	}

	public EItemType getType() {
		return type;
	}

	public void setType(EItemType type) {
		this.type = type;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getPositonId() {
		return positonId;
	}

	public void setPositonId(java.lang.Integer positonId) {
		this.positonId = positonId;
	}

	public java.lang.String getPositionName() {
		return positionName;
	}

	public void setPositionName(java.lang.String positionName) {
		this.positionName = positionName;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(java.lang.Integer departmentId) {
		this.departmentId = departmentId;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(java.lang.String departmentName) {
		this.departmentName = departmentName;
	}
}