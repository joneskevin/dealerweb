package com.ava.domain.vo;


public class TestDriveTimeVO {
	
	/** 车辆编号*/
	private Integer vehicleId;
	
	/**年*/
	private Integer year;
	
	/**季度*/
	private Integer season;
	
	/** 季度日期 */
	private String quarterDate;
	
	/** 开始日期 */
	private String startDate;
	
	/** 结束日期 */
	private String endDate;
    
	/** 是否试驾 */
	private Integer isTestDrive;
	
	/**无试驾次数 */
	private Integer noTestDriveCount;
	
	public TestDriveTimeVO() {
		
	}

	public String getQuarterDate() {
		return quarterDate;
	}

	public void setQuarterDate(String quarterDate) {
		this.quarterDate = quarterDate;
	}

	public Integer getIsTestDrive() {
		return isTestDrive;
	}

	public void setIsTestDrive(Integer isTestDrive) {
		this.isTestDrive = isTestDrive;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public Integer getNoTestDriveCount() {
		return noTestDriveCount;
	}

	public void setNoTestDriveCount(Integer noTestDriveCount) {
		this.noTestDriveCount = noTestDriveCount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	
}
