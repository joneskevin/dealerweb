package com.ava.gateway.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_message_obd_dismantle_alert")
public class ObdDismantleAlertMessage extends BaseMessage implements Serializable {

	private static final long serialVersionUID = -6839075544981999926L;

	@Column(name="FIRE_STATE")
	private Integer fireState;
	
	@Column(name="ALARM_TYPE")
	private Integer alarmType;
		
	@Column(name="LNG")
	private String lng;       //经度
	
	@Column(name="LAT")
	private String lat;       //纬度
	
	@Column(name="BAIDU_LNG")
	private String baiduLng;  //百度地图经度
	
	@Column(name="BAIDU_LAT")
	private String baiduLat;  //百度地图纬度
	
	@Column(name="HEADING")
	private Integer heading;      //车头方向
	
	@Column(name="SPEED")
	private double speed;

	public Integer getFireState() {
		return fireState;
	}

	public void setFireState(Integer fireState) {
		this.fireState = fireState;
	}

	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
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

	public Integer getHeading() {
		return heading;
	}

	public void setHeading(Integer heading) {
		this.heading = heading;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}