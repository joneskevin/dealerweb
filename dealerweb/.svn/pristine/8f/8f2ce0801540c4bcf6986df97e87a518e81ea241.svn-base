package com.ava.gateway.gpsUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GpsUtil  implements Serializable
{
  public static void main(String[] args)
  {
    GPSPoint point = null;
    List points = null;
    String distance = null;
    String lngPosion = null;

    bufferJudgeCompress(point, points, distance, lngPosion);
    bufferJudgeNoCompress(point, points, distance, lngPosion);

    getRoundHalfUp(12, 20);
    getRoundHalfUp("12.57");
    distanceUnit(12.0D, 20.0D);
  }

  public static boolean bufferJudgeCompress(GPSPoint point, List<GPSPoint> points, String distance, String lngPosion)
  {
    Douglas dou = new Douglas();
    dou.algorithm(points, Integer.parseInt(distance), getRoundHalfUp(lngPosion));
    GpsBuffer buffer = new GpsBuffer();
    return buffer.bufferJudge(point, points, Integer.parseInt(distance), getRoundHalfUp(lngPosion));
  }

  public static boolean bufferJudgeNoCompress(GPSPoint point, List<GPSPoint> points, String distance, String lngPosion)
  {
    GpsBuffer buffer = new GpsBuffer();
    return buffer.bufferJudge(point, points, Integer.parseInt(distance), getRoundHalfUp(lngPosion));
  }

  public static int getRoundHalfUp(int divisor, int dividend) {
    BigDecimal loopCount = new BigDecimal(divisor).divide(new BigDecimal(dividend), 4);
    return loopCount.intValue();
  }

  public static int getRoundHalfUp(String number) {
    BigDecimal loopCount = new BigDecimal(number).setScale(0, 4);
    return loopCount.intValue();
  }

  protected static double distanceUnit(double a, double b) {
    double c = a * 0.001D / 111.0D * Math.cos(b);
    double d = (c + 0.0009D) / 2.0D;
    return d;
  }
}