package com.ava.domain.vo;

import java.util.Date;

public class TestDriveDetailFindVO extends BaseFindVO {
	
	/** 线路ID */
	private java.lang.Integer lineId;
	
	/** 线路名称*/
	private java.lang.String lineNick;

	/** 里程 */
	private java.lang.Integer mileage;
	
	/** 试驾日期 */
	private Date testDriveDate;
	
	/** 开始时间 */
	private Date startTime;

	/** 结束时间 */
	private Date endTime;
	
	/** 持续时间 */
	private java.lang.Integer intervalTime;
	
	/** 用时：格式（XX小时XX分） */
	private java.lang.String useTimeStr;

	public java.lang.Integer getLineId() {
		return lineId;
	}

	public void setLineId(java.lang.Integer lineId) {
		this.lineId = lineId;
	}


	public java.lang.Integer getMileage() {
		return mileage;
	}

	public void setMileage(java.lang.Integer mileage) {
		this.mileage = mileage;
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

	public Date getTestDriveDate() {
		return testDriveDate;
	}

	public void setTestDriveDate(Date testDriveDate) {
		this.testDriveDate = testDriveDate;
	}

	public java.lang.String getLineNick() {
		return lineNick;
	}

	public void setLineNick(java.lang.String lineNick) {
		this.lineNick = lineNick;
	}

	public java.lang.Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(java.lang.Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public java.lang.String getUseTimeStr() {
		return useTimeStr;
	}

	public void setUseTimeStr(java.lang.String useTimeStr) {
		this.useTimeStr = useTimeStr;
	}


}
