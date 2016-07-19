package com.ava.base.domain;


/**岗位权限类*/
public class PositionRight {

	
	/**
	 * --------------------------行政人事 start-----------------------------------------
	 */
	
	/** 我的审批-审批申请单 */
	private boolean rightApproApprove = false;	
	/** 我的审批-查看所有申请 */
	private boolean rightApproViewAll = false;
	/** 我的审批-打印申请单 */
	private boolean rightApproPrint = false;
	/** 我的审批-申请单附件上传 */
	private boolean rightApproAttachment = false;
	/** 我的审批-分配申请单 */
	private boolean rightApproAssign = false;
	/**
	 * --------------------------行政人事 end-----------------------------------------
	 */

	/** 权限转换处理，根据rightsCode字段得到对应的权限 */
	public void processRightCode(String rightsCode) {
		if (rightsCode == null) {
			rightsCode = "";
		}
		if (rightsCode.length() >= 0) {

			/************************************** 行政人事-开始 **************************************/
			/** 我的审批-审批申请单 */
			if (rightsCode.indexOf("4101") > -1) {
				setRightApproApprove(true);
			}
			/** 我的审批-查看所有申请 */
			if (rightsCode.indexOf("4102") > -1) {
				setRightApproViewAll(true);
			}
			/** 我的审批-打印申请单 */
			if (rightsCode.indexOf("4103") > -1) {
				setRightApproPrint(true);
			}
			/** 我的审批-申请单附件上传 */
			if (rightsCode.indexOf("4104") > -1) {
				setRightApproAttachment(true);
			}
			/** 我的审批-分配申请单 */
			if (rightsCode.indexOf("4105") > -1) {
				setRightApproAssign(true);
			}

			/************************************** 行政人事-结束 **************************************/

		}
	}

	public boolean isRightApproApprove() {
		return rightApproApprove;
	}

	public void setRightApproApprove(boolean rightApproApprove) {
		this.rightApproApprove = rightApproApprove;
	}

	public boolean isRightApproViewAll() {
		return rightApproViewAll;
	}

	public void setRightApproViewAll(boolean rightApproViewAll) {
		this.rightApproViewAll = rightApproViewAll;
	}

	public boolean isRightApproPrint() {
		return rightApproPrint;
	}

	public void setRightApproPrint(boolean rightApproPrint) {
		this.rightApproPrint = rightApproPrint;
	}

	public boolean isRightApproAttachment() {
		return rightApproAttachment;
	}

	public void setRightApproAttachment(boolean rightApproAttachment) {
		this.rightApproAttachment = rightApproAttachment;
	}

	public boolean isRightApproAssign() {
		return rightApproAssign;
	}

	public void setRightApproAssign(boolean rightApproAssign) {
		this.rightApproAssign = rightApproAssign;
	}
}