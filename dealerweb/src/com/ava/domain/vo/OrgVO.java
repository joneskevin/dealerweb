package com.ava.domain.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheOrgManager;

/**用来组织搜索用的组织VO对象*/
public class OrgVO implements Serializable{

	private java.lang.Integer id;

	/** 该对象是公司还是部门 */
	private java.lang.Integer orgType;

	/** 职能类型：业务部门、后勤部门、工厂 */
	private java.lang.Integer functionType;

	private java.lang.String name;

	private java.lang.Integer parentId;

	private java.lang.Integer companyId;

	private java.lang.Integer levelId;

	private java.lang.Integer isLeaf;

	private java.lang.Integer orderNumber;

	private Timestamp deletionTime;

	private java.lang.Integer deletionFlag;

	private java.lang.String remark;

	private java.lang.Integer creatorId;

	private java.lang.String creatorName;

	private java.util.Date creationTime;

	/**
	 * ---------------------------------Transient
	 * start------------------------------
	 */

	/** 上级组织名称 */
	private java.lang.String parentName;

	private java.lang.String companyName;

	/** ----------页面操作权限 start-------- */

	/** 组织管理-新增公司权限 */
	private java.lang.Integer addCompanyCompetence = GlobalConstant.TRUE;
	public void nick() {
		parentName = CacheOrgManager.getOrgName(parentId);
		
		companyName=CacheOrgManager.getOrgName(companyId);
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(java.lang.Integer orgType) {
		this.orgType = orgType;
	}

	public java.lang.Integer getFunctionType() {
		return functionType;
	}

	public void setFunctionType(java.lang.Integer functionType) {
		this.functionType = functionType;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(java.lang.Integer levelId) {
		this.levelId = levelId;
	}

	public java.lang.Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(java.lang.Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public java.lang.Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(java.lang.Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Timestamp getDeletionTime() {
		return deletionTime;
	}

	public void setDeletionTime(Timestamp deletionTime) {
		this.deletionTime = deletionTime;
	}

	public java.lang.Integer getDeletionFlag() {
		return deletionFlag;
	}

	public void setDeletionFlag(java.lang.Integer deletionFlag) {
		this.deletionFlag = deletionFlag;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(java.lang.Integer creatorId) {
		this.creatorId = creatorId;
	}

	public java.lang.String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(java.lang.String creatorName) {
		this.creatorName = creatorName;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.lang.Integer getAddCompanyCompetence() {
		return addCompanyCompetence;
	}

	public void setAddCompanyCompetence(java.lang.Integer addCompanyCompetence) {
		this.addCompanyCompetence = addCompanyCompetence;
	}

	public java.lang.String getParentName() {
		return parentName;
	}

	public void setParentName(java.lang.String parentName) {
		this.parentName = parentName;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

}