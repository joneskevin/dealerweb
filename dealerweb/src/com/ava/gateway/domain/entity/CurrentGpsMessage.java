package com.ava.gateway.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_message_current_gps")
public class CurrentGpsMessage extends BaseMessage implements Serializable {
	//private static final long serialVersionUID = 8129855283580521664L;

	@Column(name="FIRE_STATE")
	private Integer fireState;
	
	@Column(name="ALARM_TYPE")
	private Integer alarmType;
	
	@Column(name="REMIND_TYPE_SS")
	private Integer remindTypeSs;
	
	@Column(name="REMIND_TYPE_HBXO")
	private Integer remindTypeHbxo;
	
	@Column(name="REMIND_TYPE_HBXWS")
	private Integer remindTypeHbxws;

	
	@Column(name="REMIND_TYPE_CCWG")
	private Integer remindTypeCcwg;
	
	@Column(name="REMIND_TYPE_DKCC")
	private Integer remindTypeDkcc;
	
	@Column(name="REMIND_TYPE_DDWG")
	private Integer remindTypeDdwg;
	
	@Column(name="REMIND_TYPE_HCHWG")
	private Integer remindTypeHchwg;
	
	@Column(name="REMIND_TYPE_OVERSPEED")
	private Integer remindTypeOverspeed;
	
	@Column(name="REMIND_TYPE_XHTX")
	private Integer remindTypeXhtx;
	
	@Column(name="REMIND_TYPE_YLGD")
	private Integer remindTypeYlgd;
	
	@Column(name="LOCATION")
	private Integer location;
	
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
		
	//车况数据
	@Column(name="OIL_VALUE_ONCE")
	private Double oilValueOnce;
	
	@Column(name="MILEAGE_ONCE")
	private Double mileageOnce;
	
	@Column(name="KO1_TANKINHALT")
	private Double kO1Tankinhalt;
	
	@Column(name="MFN_SENSORWARN")
	private Integer mfnSensorwarn;
	
	@Column(name="FT1_TUER_GEOEFFNET")
	private Integer ft1TuerGeoeffnet;
	
	@Column(name="FT1_VERRIEGELT")
	private Integer ft1Verriegelt;
	
	@Column(name="BT1_TUER_GEOEFFNET")
	private Integer bt1TuerGeoeffnet;
	
	@Column(name="BT1_VERRIEGELT")
	private Integer bt1Verriegelt;
	
	@Column(name="HL1_TUER_GEOEFFNET")
	private Integer hl1TuerGeoeffnet;
	
	@Column(name="HL1_VERRIEGELT")
	private Integer hl1Verriegelt;
	
	@Column(name="HR1_TUER_GEOEFFNET")
	private Integer hr1TuerGeoeffnet;
	
	@Column(name="HR1_VERRIEGELT")
	private Integer hr1Verriegelt;
	
	@Column(name="BT1_FH_OEFFNUNG")
	private Integer bt1FhOeffnung;
	
	@Column(name="FT1_FH_OEFFNUNG")
	private Integer ft1FhOeffnung;
	
	@Column(name="HL1_FH_OEFFNUNG")
	private Integer hl1FhOeffnung;
	
	@Column(name="HR1_FH_OEFFNUNG")
	private Integer hr1FhOeffnung;
	
	@Column(name="FT1_FH_HL_FREIGABE")
	private Integer ft1FhHlFreigabe;
	
	@Column(name="FT1_FH_HR_FREIGABE")
	private Integer ft1FhHrFreigabe;
	
	@Column(name="KO1_HANDBREMSE")
	private Integer ko1Handbremse;
	
	@Column(name="ZK4_HECKEINZELENTRIEG")
	private Integer zk4Heckeinzelentrieg;
	
	@Column(name="BSK_HD_HAUPTRASTE")
	private Integer bskHdHauptraste;
	
	@Column(name="KO1_KUEHLMITTEL")
	private Integer ko1Kuehlmittel;
	
	@Column(name="MO5_HEISSL")
	private Integer mo5Heissl;
	
	@Column(name="KO1_WASCH_WASSER")
	private Integer ko1WaschWasser;
	
	@Column(name="GW5_POSITION_PSD")
	private Integer gw5PositionPsd;
	
	@Column(name="BSK_DEF_LAMPE")
	private Integer bskDefLampe;
	
	@Column(name="BSK_ABBLENDLICHT")
	private Integer bskAbblendlicht;
	
	@Column(name="BSK_NEBELLICHT")
	private Integer bskNebellicht;
	
	@Column(name="BSK_NEBELSCHLUSSLICHT")
	private Integer bskNebelschlusslicht;
	
	@Column(name="BSK_FERNLICHT")
	private Integer bskFernlicht;
	
	@Column(name="KO1_STA_OELDR")
	private Integer ko1StaOeldr;
	
	@Column(name="STORIGE_BATT")
	private Double storigeBatt;
	
	@Column(name="KO1_ANGEZ_KMH")
	private Double ko1AngezKmh;
	
	@Column(name="KO3_KILOMETER")
	private Double ko3Kilometer;
	
	@Column(name="MO1_DREHZAHL")
	private Double mo1Drehzahl;

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

	public Integer getRemindTypeSs() {
		return remindTypeSs;
	}

	public void setRemindTypeSs(Integer remindTypeSs) {
		this.remindTypeSs = remindTypeSs;
	}

	public Integer getRemindTypeHbxo() {
		return remindTypeHbxo;
	}

	public void setRemindTypeHbxo(Integer remindTypeHbxo) {
		this.remindTypeHbxo = remindTypeHbxo;
	}

	public Integer getRemindTypeHbxws() {
		return remindTypeHbxws;
	}

	public void setRemindTypeHbxws(Integer remindTypeHbxws) {
		this.remindTypeHbxws = remindTypeHbxws;
	}

	public Integer getRemindTypeCcwg() {
		return remindTypeCcwg;
	}

	public void setRemindTypeCcwg(Integer remindTypeCcwg) {
		this.remindTypeCcwg = remindTypeCcwg;
	}

	public Integer getRemindTypeDkcc() {
		return remindTypeDkcc;
	}

	public void setRemindTypeDkcc(Integer remindTypeDkcc) {
		this.remindTypeDkcc = remindTypeDkcc;
	}

	public Integer getRemindTypeDdwg() {
		return remindTypeDdwg;
	}

	public void setRemindTypeDdwg(Integer remindTypeDdwg) {
		this.remindTypeDdwg = remindTypeDdwg;
	}

	public Integer getRemindTypeHchwg() {
		return remindTypeHchwg;
	}

	public void setRemindTypeHchwg(Integer remindTypeHchwg) {
		this.remindTypeHchwg = remindTypeHchwg;
	}

	public Integer getRemindTypeOverspeed() {
		return remindTypeOverspeed;
	}

	public void setRemindTypeOverspeed(Integer remindTypeOverspeed) {
		this.remindTypeOverspeed = remindTypeOverspeed;
	}

	public Integer getRemindTypeXhtx() {
		return remindTypeXhtx;
	}

	public void setRemindTypeXhtx(Integer remindTypeXhtx) {
		this.remindTypeXhtx = remindTypeXhtx;
	}

	public Integer getRemindTypeYlgd() {
		return remindTypeYlgd;
	}

	public void setRemindTypeYlgd(Integer remindTypeYlgd) {
		this.remindTypeYlgd = remindTypeYlgd;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
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

	public Double getOilValueOnce() {
		return oilValueOnce;
	}

	public void setOilValueOnce(Double oilValueOnce) {
		this.oilValueOnce = oilValueOnce;
	}

	public Double getMileageOnce() {
		return mileageOnce;
	}

	public void setMileageOnce(Double mileageOnce) {
		this.mileageOnce = mileageOnce;
	}

	public Double getkO1Tankinhalt() {
		return kO1Tankinhalt;
	}

	public void setkO1Tankinhalt(Double kO1Tankinhalt) {
		this.kO1Tankinhalt = kO1Tankinhalt;
	}

	public Integer getMfnSensorwarn() {
		return mfnSensorwarn;
	}

	public void setMfnSensorwarn(Integer mfnSensorwarn) {
		this.mfnSensorwarn = mfnSensorwarn;
	}

	public Integer getFt1TuerGeoeffnet() {
		return ft1TuerGeoeffnet;
	}

	public void setFt1TuerGeoeffnet(Integer ft1TuerGeoeffnet) {
		this.ft1TuerGeoeffnet = ft1TuerGeoeffnet;
	}

	public Integer getFt1Verriegelt() {
		return ft1Verriegelt;
	}

	public void setFt1Verriegelt(Integer ft1Verriegelt) {
		this.ft1Verriegelt = ft1Verriegelt;
	}

	public Integer getBt1TuerGeoeffnet() {
		return bt1TuerGeoeffnet;
	}

	public void setBt1TuerGeoeffnet(Integer bt1TuerGeoeffnet) {
		this.bt1TuerGeoeffnet = bt1TuerGeoeffnet;
	}

	public Integer getBt1Verriegelt() {
		return bt1Verriegelt;
	}

	public void setBt1Verriegelt(Integer bt1Verriegelt) {
		this.bt1Verriegelt = bt1Verriegelt;
	}

	public Integer getHl1TuerGeoeffnet() {
		return hl1TuerGeoeffnet;
	}

	public void setHl1TuerGeoeffnet(Integer hl1TuerGeoeffnet) {
		this.hl1TuerGeoeffnet = hl1TuerGeoeffnet;
	}

	public Integer getHl1Verriegelt() {
		return hl1Verriegelt;
	}

	public void setHl1Verriegelt(Integer hl1Verriegelt) {
		this.hl1Verriegelt = hl1Verriegelt;
	}

	public Integer getHr1TuerGeoeffnet() {
		return hr1TuerGeoeffnet;
	}

	public void setHr1TuerGeoeffnet(Integer hr1TuerGeoeffnet) {
		this.hr1TuerGeoeffnet = hr1TuerGeoeffnet;
	}

	public Integer getHr1Verriegelt() {
		return hr1Verriegelt;
	}

	public void setHr1Verriegelt(Integer hr1Verriegelt) {
		this.hr1Verriegelt = hr1Verriegelt;
	}

	public Integer getBt1FhOeffnung() {
		return bt1FhOeffnung;
	}

	public void setBt1FhOeffnung(Integer bt1FhOeffnung) {
		this.bt1FhOeffnung = bt1FhOeffnung;
	}

	public Integer getFt1FhOeffnung() {
		return ft1FhOeffnung;
	}

	public void setFt1FhOeffnung(Integer ft1FhOeffnung) {
		this.ft1FhOeffnung = ft1FhOeffnung;
	}

	public Integer getHl1FhOeffnung() {
		return hl1FhOeffnung;
	}

	public void setHl1FhOeffnung(Integer hl1FhOeffnung) {
		this.hl1FhOeffnung = hl1FhOeffnung;
	}

	public Integer getHr1FhOeffnung() {
		return hr1FhOeffnung;
	}

	public void setHr1FhOeffnung(Integer hr1FhOeffnung) {
		this.hr1FhOeffnung = hr1FhOeffnung;
	}

	public Integer getFt1FhHlFreigabe() {
		return ft1FhHlFreigabe;
	}

	public void setFt1FhHlFreigabe(Integer ft1FhHlFreigabe) {
		this.ft1FhHlFreigabe = ft1FhHlFreigabe;
	}

	public Integer getFt1FhHrFreigabe() {
		return ft1FhHrFreigabe;
	}

	public void setFt1FhHrFreigabe(Integer ft1FhHrFreigabe) {
		this.ft1FhHrFreigabe = ft1FhHrFreigabe;
	}

	public Integer getKo1Handbremse() {
		return ko1Handbremse;
	}

	public void setKo1Handbremse(Integer ko1Handbremse) {
		this.ko1Handbremse = ko1Handbremse;
	}

	public Integer getZk4Heckeinzelentrieg() {
		return zk4Heckeinzelentrieg;
	}

	public void setZk4Heckeinzelentrieg(Integer zk4Heckeinzelentrieg) {
		this.zk4Heckeinzelentrieg = zk4Heckeinzelentrieg;
	}

	public Integer getBskHdHauptraste() {
		return bskHdHauptraste;
	}

	public void setBskHdHauptraste(Integer bskHdHauptraste) {
		this.bskHdHauptraste = bskHdHauptraste;
	}

	public Integer getKo1Kuehlmittel() {
		return ko1Kuehlmittel;
	}

	public void setKo1Kuehlmittel(Integer ko1Kuehlmittel) {
		this.ko1Kuehlmittel = ko1Kuehlmittel;
	}

	public Integer getMo5Heissl() {
		return mo5Heissl;
	}

	public void setMo5Heissl(Integer mo5Heissl) {
		this.mo5Heissl = mo5Heissl;
	}

	public Integer getKo1WaschWasser() {
		return ko1WaschWasser;
	}

	public void setKo1WaschWasser(Integer ko1WaschWasser) {
		this.ko1WaschWasser = ko1WaschWasser;
	}

	public Integer getGw5PositionPsd() {
		return gw5PositionPsd;
	}

	public void setGw5PositionPsd(Integer gw5PositionPsd) {
		this.gw5PositionPsd = gw5PositionPsd;
	}

	public Integer getBskDefLampe() {
		return bskDefLampe;
	}

	public void setBskDefLampe(Integer bskDefLampe) {
		this.bskDefLampe = bskDefLampe;
	}

	public Integer getBskAbblendlicht() {
		return bskAbblendlicht;
	}

	public void setBskAbblendlicht(Integer bskAbblendlicht) {
		this.bskAbblendlicht = bskAbblendlicht;
	}

	public Integer getBskNebellicht() {
		return bskNebellicht;
	}

	public void setBskNebellicht(Integer bskNebellicht) {
		this.bskNebellicht = bskNebellicht;
	}

	public Integer getBskNebelschlusslicht() {
		return bskNebelschlusslicht;
	}

	public void setBskNebelschlusslicht(Integer bskNebelschlusslicht) {
		this.bskNebelschlusslicht = bskNebelschlusslicht;
	}

	public Integer getBskFernlicht() {
		return bskFernlicht;
	}

	public void setBskFernlicht(Integer bskFernlicht) {
		this.bskFernlicht = bskFernlicht;
	}

	public Integer getKo1StaOeldr() {
		return ko1StaOeldr;
	}

	public void setKo1StaOeldr(Integer ko1StaOeldr) {
		this.ko1StaOeldr = ko1StaOeldr;
	}

	public Double getStorigeBatt() {
		return storigeBatt;
	}

	public void setStorigeBatt(Double storigeBatt) {
		this.storigeBatt = storigeBatt;
	}

	public Double getKo1AngezKmh() {
		return ko1AngezKmh;
	}

	public void setKo1AngezKmh(Double ko1AngezKmh) {
		this.ko1AngezKmh = ko1AngezKmh;
	}

	public Double getKo3Kilometer() {
		return ko3Kilometer;
	}

	public void setKo3Kilometer(Double ko3Kilometer) {
		this.ko3Kilometer = ko3Kilometer;
	}

	public Double getMo1Drehzahl() {
		return mo1Drehzahl;
	}

	public void setMo1Drehzahl(Double mo1Drehzahl) {
		this.mo1Drehzahl = mo1Drehzahl;
	}
}