package com.ava.admin.domain.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public class CurrentGpsMessageVo implements Serializable {
	private static final long serialVersionUID = 8129855283580521664L;

	private Integer fireState;
	
	private String lng;       //经度
	
	private String lat;       //纬度
	
	private String baiduLng;  //百度地图经度
	
	private String baiduLat;  //百度地图纬度
	
	private Double speed;
		
	private Double ko3Kilometer;

	private String vin;
	
	private String serialNumber;
	
	private String imsi;
		
	private Integer messageCode;
	
	private String messageDate;
	
	private String createTime;
   
	private String startDate;
	private String endDate;
	private Integer vehicleId;
	private String orderType;
	private int heading;   
	private String taskId;
	
	public String getJson(){
		StringBuffer sb = new StringBuffer();
		sb.append("{\"header\":{\"vin\":\"").append(vin).append("\",\"serialNumber\":\"").append(serialNumber).append("\",").
		append("\"imsi\":\"").append(imsi).append("\",\"messageCode\":").append(messageCode).append(",\"timestamp\":\"").append(messageDate).append("\"");
		if(taskId!=null && !taskId.isEmpty()){
			sb.append(",\"typeCode\":").append("\""+taskId+"\"},");
		}
		sb.append("\"body\":{\"data\":{\"fireState\":").append(fireState).append(",\"lng\":").append(lng).append(",\"lat\":").append(lat).append(","+
				"\"baiduLng\":\""+baiduLng).append("\",\"baiduLat\":\"").append(baiduLat).append("\",\"heading\":").append(heading).append(","+
				"\"speed\":").append(speed).append(",\"ko3Kilometer\":").append(ko3Kilometer).append("}}}");
		return sb.toString();
	}
	
	
	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public int getHeading() {
		return heading;
	}
	public void setHeading(int heading) {
		this.heading = heading;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Integer getFireState() {
		return fireState;
	}
	public void setFireState(Integer fireState) {
		this.fireState = fireState;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getBaiduLng() {
		return baiduLng;
	}
	public void setBaiduLng(String baiduLng) {
		this.baiduLng = baiduLng;
	}
	public String getBaiduLat() {
		return baiduLat;
	}
	public void setBaiduLat(String baiduLat) {
		this.baiduLat = baiduLat;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Double getKo3Kilometer() {
		return ko3Kilometer;
	}
	public void setKo3Kilometer(Double ko3Kilometer) {
		this.ko3Kilometer = ko3Kilometer;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public Integer getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(Integer messageCode) {
		this.messageCode = messageCode;
	}
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	
	
	
}