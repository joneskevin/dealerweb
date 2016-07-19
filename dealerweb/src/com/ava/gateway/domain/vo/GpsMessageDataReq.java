package com.ava.gateway.domain.vo;

public class GpsMessageDataReq extends BaseDataReq{
	
	private int fireState;
	private int alarmType;
	
	private boolean remindTypeSs;
	private boolean remindTypeHbxo;
	private boolean remindTypeHbxws;
	private boolean remindTypeCcwg;
	private boolean remindTypeDkcc;
	private boolean remindTypeDdwg;
	private boolean remindTypeHchwg;
	private boolean remindTypeOverspeed;
	private boolean remindTypeXhtx;
	private boolean remindTypeYlgd;
	
	private boolean location;
	private String lng;       //经度
	private String lat;       //纬度
	private String baiduLng;  //百度地图经度
	private String baiduLat;  //百度地图纬度
	private int heading;      //车头方向
	private double speed;
	
	private String timestamp;
	
	//车况数据
	private Double oilValueOnce;
	private Double mileageOnce;
	
	private Double kO1Tankinhalt;
	private Boolean mfnSensorwarn;
	
	private Boolean ft1TuerGeoeffnet;
	private Boolean ft1Verriegelt;
	private Boolean bt1TuerGeoeffnet;
	private Boolean bt1Verriegelt;
	private Boolean hl1TuerGeoeffnet;
	private Boolean hl1Verriegelt;
	private Boolean hr1TuerGeoeffnet;
	private Boolean hr1Verriegelt;
	
	private Integer bt1FhOeffnung;
	private Integer ft1FhOeffnung;
	private Integer hl1FhOeffnung;
	private Integer hr1FhOeffnung;
	
	private Boolean ft1FhHlFreigabe;
	private Boolean ft1FhHrFreigabe;
	private Boolean ko1Handbremse;
	private Boolean zk4Heckeinzelentrieg;
	private Boolean bskHdHauptraste;
	private Boolean ko1Kuehlmittel;
	private Boolean mo5Heissl;
	private Boolean ko1WaschWasser;
	
	private Integer gw5PositionPsd;
	private Boolean bskDefLampe;
	
	private Boolean bskAbblendlicht;
	private Boolean bskNebellicht;
	private Boolean bskNebelschlusslicht;
	private Boolean bskFernlicht;
	private Boolean ko1StaOeldr;
	
	private Double storigeBatt;
	private Double ko1AngezKmh;
	private Double ko3Kilometer;
	private Double mo1Drehzahl;
	public int getFireState() {
		return fireState;
	}
	public void setFireState(int fireState) {
		this.fireState = fireState;
	}
	public int getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}
	public boolean isRemindTypeSs() {
		return remindTypeSs;
	}
	public void setRemindTypeSs(boolean remindTypeSs) {
		this.remindTypeSs = remindTypeSs;
	}
	public boolean isRemindTypeHbxo() {
		return remindTypeHbxo;
	}
	public void setRemindTypeHbxo(boolean remindTypeHbxo) {
		this.remindTypeHbxo = remindTypeHbxo;
	}
	public boolean isRemindTypeHbxws() {
		return remindTypeHbxws;
	}
	public void setRemindTypeHbxws(boolean remindTypeHbxws) {
		this.remindTypeHbxws = remindTypeHbxws;
	}
	public boolean isRemindTypeCcwg() {
		return remindTypeCcwg;
	}
	public void setRemindTypeCcwg(boolean remindTypeCcwg) {
		this.remindTypeCcwg = remindTypeCcwg;
	}
	public boolean isRemindTypeDkcc() {
		return remindTypeDkcc;
	}
	public void setRemindTypeDkcc(boolean remindTypeDkcc) {
		this.remindTypeDkcc = remindTypeDkcc;
	}
	public boolean isRemindTypeDdwg() {
		return remindTypeDdwg;
	}
	public void setRemindTypeDdwg(boolean remindTypeDdwg) {
		this.remindTypeDdwg = remindTypeDdwg;
	}
	public boolean isRemindTypeHchwg() {
		return remindTypeHchwg;
	}
	public void setRemindTypeHchwg(boolean remindTypeHchwg) {
		this.remindTypeHchwg = remindTypeHchwg;
	}
	public boolean isRemindTypeOverspeed() {
		return remindTypeOverspeed;
	}
	public void setRemindTypeOverspeed(boolean remindTypeOverspeed) {
		this.remindTypeOverspeed = remindTypeOverspeed;
	}
	public boolean isRemindTypeXhtx() {
		return remindTypeXhtx;
	}
	public void setRemindTypeXhtx(boolean remindTypeXhtx) {
		this.remindTypeXhtx = remindTypeXhtx;
	}
	public boolean isRemindTypeYlgd() {
		return remindTypeYlgd;
	}
	public void setRemindTypeYlgd(boolean remindTypeYlgd) {
		this.remindTypeYlgd = remindTypeYlgd;
	}
	public boolean isLocation() {
		return location;
	}
	public void setLocation(boolean location) {
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
	public int getHeading() {
		return heading;
	}
	public void setHeading(int heading) {
		this.heading = heading;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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
	public Boolean getMfnSensorwarn() {
		return mfnSensorwarn;
	}
	public void setMfnSensorwarn(Boolean mfnSensorwarn) {
		this.mfnSensorwarn = mfnSensorwarn;
	}
	public Boolean getFt1TuerGeoeffnet() {
		return ft1TuerGeoeffnet;
	}
	public void setFt1TuerGeoeffnet(Boolean ft1TuerGeoeffnet) {
		this.ft1TuerGeoeffnet = ft1TuerGeoeffnet;
	}
	public Boolean getFt1Verriegelt() {
		return ft1Verriegelt;
	}
	public void setFt1Verriegelt(Boolean ft1Verriegelt) {
		this.ft1Verriegelt = ft1Verriegelt;
	}
	public Boolean getBt1TuerGeoeffnet() {
		return bt1TuerGeoeffnet;
	}
	public void setBt1TuerGeoeffnet(Boolean bt1TuerGeoeffnet) {
		this.bt1TuerGeoeffnet = bt1TuerGeoeffnet;
	}
	public Boolean getBt1Verriegelt() {
		return bt1Verriegelt;
	}
	public void setBt1Verriegelt(Boolean bt1Verriegelt) {
		this.bt1Verriegelt = bt1Verriegelt;
	}
	public Boolean getHl1TuerGeoeffnet() {
		return hl1TuerGeoeffnet;
	}
	public void setHl1TuerGeoeffnet(Boolean hl1TuerGeoeffnet) {
		this.hl1TuerGeoeffnet = hl1TuerGeoeffnet;
	}
	public Boolean getHl1Verriegelt() {
		return hl1Verriegelt;
	}
	public void setHl1Verriegelt(Boolean hl1Verriegelt) {
		this.hl1Verriegelt = hl1Verriegelt;
	}
	public Boolean getHr1TuerGeoeffnet() {
		return hr1TuerGeoeffnet;
	}
	public void setHr1TuerGeoeffnet(Boolean hr1TuerGeoeffnet) {
		this.hr1TuerGeoeffnet = hr1TuerGeoeffnet;
	}
	public Boolean getHr1Verriegelt() {
		return hr1Verriegelt;
	}
	public void setHr1Verriegelt(Boolean hr1Verriegelt) {
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
	public Boolean getFt1FhHlFreigabe() {
		return ft1FhHlFreigabe;
	}
	public void setFt1FhHlFreigabe(Boolean ft1FhHlFreigabe) {
		this.ft1FhHlFreigabe = ft1FhHlFreigabe;
	}
	public Boolean getFt1FhHrFreigabe() {
		return ft1FhHrFreigabe;
	}
	public void setFt1FhHrFreigabe(Boolean ft1FhHrFreigabe) {
		this.ft1FhHrFreigabe = ft1FhHrFreigabe;
	}
	public Boolean getKo1Handbremse() {
		return ko1Handbremse;
	}
	public void setKo1Handbremse(Boolean ko1Handbremse) {
		this.ko1Handbremse = ko1Handbremse;
	}
	public Boolean getZk4Heckeinzelentrieg() {
		return zk4Heckeinzelentrieg;
	}
	public void setZk4Heckeinzelentrieg(Boolean zk4Heckeinzelentrieg) {
		this.zk4Heckeinzelentrieg = zk4Heckeinzelentrieg;
	}
	public Boolean getBskHdHauptraste() {
		return bskHdHauptraste;
	}
	public void setBskHdHauptraste(Boolean bskHdHauptraste) {
		this.bskHdHauptraste = bskHdHauptraste;
	}
	public Boolean getKo1Kuehlmittel() {
		return ko1Kuehlmittel;
	}
	public void setKo1Kuehlmittel(Boolean ko1Kuehlmittel) {
		this.ko1Kuehlmittel = ko1Kuehlmittel;
	}
	public Boolean getMo5Heissl() {
		return mo5Heissl;
	}
	public void setMo5Heissl(Boolean mo5Heissl) {
		this.mo5Heissl = mo5Heissl;
	}
	public Boolean getKo1WaschWasser() {
		return ko1WaschWasser;
	}
	public void setKo1WaschWasser(Boolean ko1WaschWasser) {
		this.ko1WaschWasser = ko1WaschWasser;
	}
	public Integer getGw5PositionPsd() {
		return gw5PositionPsd;
	}
	public void setGw5PositionPsd(Integer gw5PositionPsd) {
		this.gw5PositionPsd = gw5PositionPsd;
	}
	public Boolean getBskDefLampe() {
		return bskDefLampe;
	}
	public void setBskDefLampe(Boolean bskDefLampe) {
		this.bskDefLampe = bskDefLampe;
	}
	public Boolean getBskAbblendlicht() {
		return bskAbblendlicht;
	}
	public void setBskAbblendlicht(Boolean bskAbblendlicht) {
		this.bskAbblendlicht = bskAbblendlicht;
	}
	public Boolean getBskNebellicht() {
		return bskNebellicht;
	}
	public void setBskNebellicht(Boolean bskNebellicht) {
		this.bskNebellicht = bskNebellicht;
	}
	public Boolean getBskNebelschlusslicht() {
		return bskNebelschlusslicht;
	}
	public void setBskNebelschlusslicht(Boolean bskNebelschlusslicht) {
		this.bskNebelschlusslicht = bskNebelschlusslicht;
	}
	public Boolean getBskFernlicht() {
		return bskFernlicht;
	}
	public void setBskFernlicht(Boolean bskFernlicht) {
		this.bskFernlicht = bskFernlicht;
	}
	public Boolean getKo1StaOeldr() {
		return ko1StaOeldr;
	}
	public void setKo1StaOeldr(Boolean ko1StaOeldr) {
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
