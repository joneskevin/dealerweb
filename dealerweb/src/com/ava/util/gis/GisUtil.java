package com.ava.util.gis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.ava.enums.ServiceResponseEnum;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.gateway.gpsUtil.GPSPoint;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * 判断经纬度是否在多边形内
 * 提供数据如下格式:
 * 419164412,143703543;419164481,143702737;419164494,143702527;419164412,143703543
 * @author heng_guohong
 *
 */
public class GisUtil {	
	/**
	 * 点是否在圆内(在边上也认为在圆内)
	 * @param cPoint
	 * @param cRadius
	 * @param point
	 * @return
	 */
    public static boolean isRadius(String lng,String lat, double cRadius, String companyLng,String companyLat){
    	BigDecimal bigDecimalX=new BigDecimal("");
    	BigDecimal bigDecimalY=new BigDecimal("");
    	bigDecimalX=new BigDecimal(lng).subtract(new BigDecimal(companyLng));
    	bigDecimalY=new BigDecimal(lat).subtract(new BigDecimal(companyLat));
        double distance = Math.sqrt(Math.pow(Math.abs(bigDecimalX.doubleValue()), 2) + Math.pow(Math.abs(bigDecimalY.doubleValue()), 2));
        return distance <= cRadius;
    }
    
    /**
     * 求两点间球面距离
     * @param currentLng
     * @param currentLat
     * @param cRadius
     * @param referenceLng
     * @param referenceLat
     * @return
     */
    public static double getDistance(String currentLng,String currentLat, String referenceLng,String referenceLat){
    	BigDecimal firstLat=new BigDecimal("0");
    	BigDecimal firstLng=new BigDecimal("0");
    	BigDecimal secondLat=new BigDecimal("0");
    	BigDecimal secondLng=new BigDecimal("0");
    	
    	BigDecimal differenceLat=new BigDecimal("0");
    	BigDecimal differenceLng=new BigDecimal("0");
    	
    	firstLat=new BigDecimal(currentLat).multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), 6, BigDecimal.ROUND_HALF_UP);
    	firstLng=new BigDecimal(currentLng).multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), 6, BigDecimal.ROUND_HALF_UP);
    	secondLat=new BigDecimal(referenceLat).multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), 6, BigDecimal.ROUND_HALF_UP);
    	secondLng=new BigDecimal(referenceLng).multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), 6, BigDecimal.ROUND_HALF_UP);

    	differenceLat=firstLat.subtract(secondLat);
    	differenceLng=firstLng.subtract(secondLng);
    	
    	double distince=2 * Math.asin(Math.sqrt(Math.pow(Math.sin(differenceLat.doubleValue() / 2), 2)
				+ Math.cos(firstLat.doubleValue()) * Math.cos(secondLat.doubleValue())
				* Math.pow(Math.sin(differenceLng.doubleValue() / 2), 2)));
    	
		distince = distince * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
		distince = Math.round(distince * 10000) / 10000;
		return distince;
    }
    
    /**
     * 两点的经纬度坐标即可计算，计算出的单位是米
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2){
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lon1 * Math.PI / 180 - lon2 * Math.PI / 180;
		double distince = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		distince = distince * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
		distince = Math.round(distince * 10000) / 10000;
		return distince;
    }
	
	/**
	 * 判断点是否在多边形内
	 * @param point
	 * @param polygon
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInner(String point,String polygon) throws ParseException{
		//要求是一个闭环，最后一个与第一个必须相同
		WKTReader wkt = new WKTReader();
		Geometry polygonGeojudge;
		Geometry pointGeojudge;
		polygonGeojudge = wkt.read(polygon);
		pointGeojudge = wkt.read(point);
		if(polygonGeojudge.intersects(pointGeojudge)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断点是否在多边形内
	 * @param point
	 * @param polygonGeojudge
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInner(String point,Geometry polygonGeojudge) throws ParseException{
		//要求是一个闭环，最后一个与第一个必须相同
		WKTReader wkt = new WKTReader();
		Geometry pointGeojudge;
		pointGeojudge = wkt.read(point);
		if(polygonGeojudge.intersects(pointGeojudge)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断点是否在多边形内
	 * @param pointGeojudge
	 * @param polygonGeojudge
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInner(Geometry pointGeojudge,Geometry polygonGeojudge) throws ParseException{
		//要求是一个闭环，最后一个与第一个必须相同
		if(polygonGeojudge.intersects(pointGeojudge)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断点是否在多边形内
	 * @param lng
	 * @param lat
	 * @param polygonGeojudge
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInner(String lng,String lat,Geometry polygonGeojudge) throws ParseException{
		//要求是一个闭环，最后一个与第一个必须相同
		Geometry pointGeojudge=getPonit(lng,lat);
		if(polygonGeojudge.intersects(pointGeojudge)) {
			return true;
		}
		return false;
	}
	
	public static Geometry getPonit(String lng,String lat){
		WKTReader wkt = new WKTReader();
		Geometry pointGeojudge;
		try {
			pointGeojudge = wkt.read(getPointByLngLat(lng,lat));
		} catch (ParseException e) {
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10006);
		}
		return pointGeojudge;
	}
	
	/**
	 * 构造围栏
	 * @param lngLatOriginal
	 * @return
	 */
	public static Geometry getPolygon(String lngLatOriginal){
		WKTReader wkt = new WKTReader();
		Geometry polygonGeojudge;
		try {
			polygonGeojudge = wkt.read(lngLatOriginal);
		} catch (ParseException e) {
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10006);
		}
		return polygonGeojudge;
	}
	
	public static Geometry PolygonByFen(String lngLatOriginal){
		WKTReader wkt = new WKTReader();
		Geometry polygonGeojudge;
		String polygonMsg =getPolygonDataByFen(lngLatOriginal);
		try {
			polygonGeojudge = wkt.read(polygonMsg);
		} catch (ParseException e) {
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10006);
		}
		return polygonGeojudge;
	}
		
	public static String getPointByLngLat(String lng,String lat){
		
		StringBuffer latLngBuffer=new StringBuffer("");
		latLngBuffer.append("POINT(");
		latLngBuffer.append(lng).append(" ").append(lat);
		latLngBuffer.append("))");
		
		return latLngBuffer.toString();
	}
	
	/**
	 * 根据围栏信息解析
	 * @param lngLatOriginal
	 * @return
	 */
	public static String getPolygonDataByFen(String lngLatOriginal){
		StringBuffer latLngBuffer=new StringBuffer("");
		if(null!=lngLatOriginal && !"".equals(lngLatOriginal)){
			JSONArray jsonArray = JSONArray.fromObject(lngLatOriginal);
			int jsonArrayLength=jsonArray.size();
			if(null!=jsonArray && jsonArrayLength>=1){
				latLngBuffer.append("POLYGON ((");
				for (int i = 0; i < jsonArrayLength; i++) {
		           JSONObject jsonObj = (JSONObject) jsonArray.get(i);
		           latLngBuffer.append(jsonObj.get("lng").toString());
		           latLngBuffer.append(" ");
		           latLngBuffer.append(jsonObj.get("lat").toString());
		           latLngBuffer.append(",");
		        }
		        latLngBuffer.deleteCharAt(latLngBuffer.length()-1);
		        latLngBuffer.append("))");
		        return latLngBuffer.toString();
			}
		}
		return null;
	}
	
	/**
	 * 根据逗号加空格构造的多边形围栏数据
	 * @param lngLatOriginal
	 * @return
	 */
	public static String getPolygonDataByOther(String lngLatOriginal){
		
		StringBuffer latLngBuffer=new StringBuffer("");
		if(null!=lngLatOriginal && !"".equals(lngLatOriginal)){
			StringTokenizer token=new StringTokenizer(lngLatOriginal,";");
			String latLng;
			if(lngLatOriginal.indexOf(";")>=1){
				latLngBuffer.append("POLYGON ((");
		        while(token.hasMoreElements()){
		        	latLng=token.nextToken();
		        	if(null!=latLng && !"".equals(latLng.trim())){
		            	latLngBuffer.append(latLng.substring(0, latLng.indexOf(","))+" "+latLng.substring(latLng.indexOf(",")+1, latLng.length())).append(",");
		        	}
		        }
		        latLngBuffer.deleteCharAt(latLngBuffer.length()-1);
		        latLngBuffer.append("))");
		        return latLngBuffer.toString();
			}
		}
		return null;
	}
	
	public static String getLatLng(String latLngOriginal){
		StringBuffer latLngBuffer=new StringBuffer("");
		if(null!=latLngOriginal && !"".equals(latLngOriginal)){
			StringTokenizer token=new StringTokenizer(latLngOriginal,";");
			String latLng;
			if(latLngOriginal.indexOf(";")>=1){
				latLngBuffer.append("POLYGON ((");
		        while(token.hasMoreElements()){
		        	latLng=token.nextToken();
		        	if(null!=latLng && !"".equals(latLng.trim())){
		            	latLngBuffer.append(latLng.substring(0, latLng.indexOf(","))+" "+latLng.substring(latLng.indexOf(",")+1, latLng.length())).append(",");
		        	}
		        }
		        latLngBuffer.deleteCharAt(latLngBuffer.length()-1);
		        latLngBuffer.append("))");
			}else{
				latLngBuffer.append("POINT(");
				latLngBuffer.append(latLngOriginal.substring(0, latLngOriginal.indexOf(","))+" "+latLngOriginal.substring(latLngOriginal.indexOf(",")+1, latLngOriginal.length()));
				latLngBuffer.append("))");
			}
		}
		return latLngBuffer.toString();
	}
	
	/**
	 * 参考线转换为gps坐标
	 * @param lngLatOriginal
	 * @return
	 */
	public static List<GPSPoint> refencePolygonByFen(String lngLatOriginal){
		List<GPSPoint> gpsPoints=new ArrayList<GPSPoint>();
		if(null!=lngLatOriginal && !"".equals(lngLatOriginal)){
			JSONArray jsonArray = JSONArray.fromObject(lngLatOriginal);
			int jsonArrayLength=jsonArray.size();
			if(null!=jsonArray && jsonArrayLength>=1){
				for (int i = 0; i < jsonArrayLength; i++) {
		           JSONObject jsonObj = (JSONObject) jsonArray.get(i);
		           GPSPoint gpsPoint=new GPSPoint(Double.parseDouble(jsonObj.get("lng").toString()),Double.parseDouble(jsonObj.get("lat").toString()));
		           gpsPoints.add(gpsPoint);
		        }
			}
		}
		return gpsPoints;
	}
	
	/**
	 * 
	 * 把点扩展成正方形
	 * @author tangqingsong
	 * @version 
	 * @param point 经纬度
	 * @param expandSize 向外扩展多少米
	 * @return
	 */
	public static Polygon createPolygon(String point,double expandSize){
			String[] temPoints = point.split(",");
			double x = Double.parseDouble(temPoints[0]);
			double y = Double.parseDouble(temPoints[1]);
		   int pointSize = 4;
		   Coordinate coords[] = new Coordinate[pointSize+1];
		   double distance = expandSize * 0.00001;//扩大50米
		   Coordinate westCoor = new Coordinate(x-distance, y); 
		   coords[0] = westCoor;
		   Coordinate northCoor = new Coordinate(x, y+distance); 
		   coords[1] = northCoor;
		   Coordinate eastCoor = new Coordinate(x+distance, y); 
		   coords[2] = eastCoor;
		   Coordinate southCoor = new Coordinate(x, y-distance);
		   coords[3] = southCoor;
		   //默认结尾
		   coords[pointSize] = coords[0];
		   GeometryFactory geometryFactory = new GeometryFactory();
		   LinearRing ring = geometryFactory.createLinearRing( coords );
		   Polygon polygon = geometryFactory.createPolygon( ring, null );
		   return polygon;
		}
	
	/**
	 * 根据GPS点创建多边形
	 *
	 * @author tangqingsong
	 * @version 
	 * @param gpsPoints
	 * @return
	 */
	public static Polygon createPolygon(List<GPSPoint> gpsPoints){
		if(gpsPoints==null){
			return null;
		}
		 GeometryFactory geometryFactory = new GeometryFactory();
		 Coordinate[] coordinates = new Coordinate[gpsPoints.size()];
		for(int i=0;i<gpsPoints.size();i++){
			GPSPoint point = gpsPoints.get(i);
			Coordinate coor = new Coordinate(point.getLng(),point.getLat());
			coordinates[i] = coor;
		}
		return geometryFactory.createPolygon(coordinates);
	}
	
    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI= 6.28318530712; // 2*PI
    static double DEF_PI180= 0.01745329252; // PI/180.0
    static double DEF_R =6370693.5; // radius of earth
            //适用于近距离
    public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
        dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
        dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
            //适用于远距离
    public static double GetLongDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
             distance = 1.0;
        else if (distance < -1.0)
              distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }
}
