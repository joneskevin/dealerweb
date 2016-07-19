package com.ava.gateway.gpsUtil;

import java.io.Serializable;

public class GPSPoint  implements Serializable
{
  private double lng;
  private double lat;

  public GPSPoint(double lng, double lat)
  {
    this.lng = lng;
    this.lat = lat; }

  public double getLng() {
    return this.lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getLat() {
    return this.lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }
}