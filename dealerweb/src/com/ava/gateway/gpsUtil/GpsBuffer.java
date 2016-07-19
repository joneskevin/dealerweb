package com.ava.gateway.gpsUtil;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import java.io.Serializable;
import java.util.List;

class GpsBuffer implements Serializable
{
  protected boolean bufferJudge(GPSPoint point, List<GPSPoint> points, double distance, double lngPosion)
  {
    GeometryFactory geometryFactory = new GeometryFactory();
    LineString line = creatLine(points);
    Geometry polygon = line.buffer(GpsUtil.distanceUnit(distance, lngPosion), 2);
    Coordinate jCoordinate = new Coordinate(point.getLng(), point.getLat());
    Point judgePoint = geometryFactory.createPoint(jCoordinate);
    boolean isContain = polygon.contains(judgePoint);
    return isContain;
  }

  private LineString creatLine(List<GPSPoint> points) {
    int num = points.size();
    Coordinate[] coords = new Coordinate[num];
    for (int i = 0; i < num; ++i) {
      coords[i] = new Coordinate(((GPSPoint)points.get(i)).getLng(), ((GPSPoint)points.get(i)).getLat());
    }
    GeometryFactory geometryFactory = new GeometryFactory();
    LineString line = geometryFactory.createLineString(coords);
    return line;
  }
}