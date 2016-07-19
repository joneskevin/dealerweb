package com.ava.resource.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.dao.redis.RedisClient;
import com.ava.dao.ILocationDao;
import com.ava.dao.IMonitorLocationDao;
import com.ava.dao.ITestDriveDao;
import com.ava.dao.impl.LocationDao;
import com.ava.dao.impl.MonitorLocationDao;
import com.ava.dealer.service.IDriveLineService;
import com.ava.dealer.service.IViolationService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Location;
import com.ava.domain.entity.MonitorLocation;
import com.ava.domain.entity.TestDrive;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.entity.Violation;
import com.ava.domain.vo.DriveLineWithPolygonVO;
import com.ava.gateway.gpsUtil.GPSPoint;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.RedisCacheKeys;
import com.ava.util.SerializeUtil;

public class CacheTestDriveManager {
	/** 试驾信息(如果一次试驾结束，从缓存删除该试驾信息) */
	//private static HashMap<String, TestDrive> testDriveCache = new HashMap<String, TestDrive>(10000);
	
	//private static HashMap<String, Location> locationCache = new HashMap<String, Location>(10000);
	
	/** 车辆实时监控记录 */
	//private static HashMap<Integer, MonitorLocation> monitorLocationCache = new HashMap<Integer, MonitorLocation>(10000);
	
	//private static HashMap<Integer, DriveLineWithPolygonVO> driveLineWithPolygonCache = new HashMap<Integer, DriveLineWithPolygonVO>(10000);
	
	/**一次试驾违规信息*/
	//private static HashMap<String, Violation> testViolationCache = new HashMap<String, Violation>(10000);
	/***/
	//private static HashMap<String, Violation> timeViolationCache = new HashMap<String, Violation>(10000);
	//private static HashMap<String, Violation> lineViolationCache = new HashMap<String, Violation>(10000);
	
	private static HashMap<String, List<GPSPoint>> testDriveGpsPointCache = new HashMap<String, List<GPSPoint>>(10000);

	private static ITestDriveDao testDriveDao = (ITestDriveDao) GlobalConfig
			.getBean("testDriveDao");
	
	private static IViolationService violationService = (IViolationService) GlobalConfig
			.getBean("violationService");
	
	private static ILocationDao locationDao=(LocationDao)GlobalConfig.getBean("locationDao");
	
	private static IMonitorLocationDao monitorLocationDao=(MonitorLocationDao)GlobalConfig.getBean("monitorLocationDao");
	
	private static IDriveLineService driveLineService=(IDriveLineService)GlobalConfig.getBean("driveLineService");
	
	//List<DriveLineWithPolygonVO> driveLineVOList = new ArrayList<DriveLineWithPolygonVO>();

	private static RedisClient redisClient =  (RedisClient)GlobalConfig
			.getBean("redisClient");
	
	private CacheTestDriveManager() {
		
	}
	
	public static TestDrive getTestDrive(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		TestDrive testDrive = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.testDriveCache,key));
		return testDrive;
	}
	
	public static Location getLocation(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		Location location = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.locationCache,key));
		return location;
	}
	
	public static MonitorLocation getMonitorLocation(Integer vehicleId){
		MonitorLocation monitorLocation = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.locationCache,vehicleId.toString()));
		return monitorLocation;
	}
	
	public static Violation getTestViolation(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		Violation violation = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.testViolationCache,key));
		return violation;
	}
	
	public static Violation getTimeViolation(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		Violation violation = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.timeViolationCache,key));
		return violation;
	}
	
	public static Violation getLineViolation(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		Violation violation =  SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.lineViolationCache,key));
		return violation;
	}
	
	public static List<GPSPoint> getTestDriveGpsPoint(Integer vehicleId,String typeCode){
			//重跑使用本地缓存
			return testDriveGpsPointCache.get(getKey(vehicleId,typeCode));
//			String key = getKey(RedisCacheKeys.testDriveGpsPointCache,vehicleId,typeCode);
//			Set<String> gpsSet = redisClient.smembers(key);
//			List<GPSPoint> gpsList = new LinkedList<GPSPoint>();
//			for(String point : gpsSet){
//				//GPSPoint gpsPoint = (GPSPoint) SerializeUtil.unserialize(gpsBytes);
//				String[] tempPoint = point.split(";");
//				GPSPoint gpsPoint=new GPSPoint(Double.parseDouble(tempPoint[0]),Double.parseDouble(tempPoint[1]));
//				gpsList.add(gpsPoint);
//			}
//			return gpsList;
	}
	
	public static DriveLineWithPolygonVO getDriveLineWithPolygon(Integer dealerId){
		String key = dealerId.toString();
		DriveLineWithPolygonVO driveLineWithPolygonVO = (DriveLineWithPolygonVO)SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.driveLineWithPolygonCache,key));
		return driveLineWithPolygonVO;
	}
	
	public static void removeTestDriveById(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		redisClient.hdel(RedisCacheKeys.testDriveCache,key);
	}
	
	public static void removeLocation(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		redisClient.hdel(RedisCacheKeys.locationCache,key);
	}
	
	public static void removeMonitorLocationCache(Integer vehicleId,String typeCode){
		if (null!=vehicleId) {
			String key = vehicleId.toString();
			redisClient.hdel(RedisCacheKeys.monitorLocationCache,key);
		}
	}
	
	public static void removeTestViolation(Integer vehicleId,String typeCode){
		if (null!=vehicleId && null!=getTestViolation(vehicleId,typeCode)) {
			String key = getKey(vehicleId,typeCode);
			redisClient.hdel(RedisCacheKeys.testViolationCache,key);
		}
	}
	
	public static void removeTimeViolation(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		redisClient.hdel(RedisCacheKeys.timeViolationCache,key);
	}
	
	public static void removeLineViolation(Integer vehicleId,String typeCode){
		String key = getKey(vehicleId,typeCode);
		redisClient.hdel(RedisCacheKeys.lineViolationCache,key);
	}
	
	public static void removeTestDriveGpsPoint(Integer vehicleId,String typeCode){
			String key = getKey(vehicleId,typeCode);
			if (testDriveGpsPointCache.containsKey(key)) {
				testDriveGpsPointCache.remove(key);
			}
//			String key = getKey(RedisCacheKeys.testDriveGpsPointCache,vehicleId,typeCode);
//			redisClient.del(key);

	}
	
	public static void removeDriveLineWithPolygon(Integer dealerId){
		if (null!=dealerId) {
			String key =dealerId.toString();
			redisClient.hdel(RedisCacheKeys.driveLineWithPolygonCache,key);
		}
	}

	/** 从缓存中根据车辆ID取得试驾信息，如果没有则从数据库获取并放入缓存 */
	public static TestDrive getTestDriveByVehicleId(Integer vehicleId,String typeCode,String startDate) {
		if (vehicleId == null) {
			return null;
		}
		TestDrive testDrive = getTestDrive(vehicleId,typeCode);
		if (testDrive == null) {
			if(typeCode!=null && !typeCode.isEmpty()){
				testDrive = testDriveDao.getTestDriveByVehicle(vehicleId,startDate);				
			}else{
				testDrive = testDriveDao.getTestDriveByVehicle(vehicleId);
			}
			if(testDrive != null){
				putTestDrive(vehicleId,testDrive,typeCode);
			}
		}
		return testDrive;
	}

	/** 从缓存中根据车辆ID取得违规信息，如果没有则从数据库获取并放入缓存 */
	public static Violation getTestViolationByVehicleId(Integer vehicleId,String typeCode) {
		if (vehicleId == null) {
			return null;
		}
		Violation violation = getTestViolation(vehicleId,typeCode);
		if (violation == null) {
			violation = violationService.getViolationByVehicle(vehicleId,null);
			if(violation != null){
				String key = getKey(vehicleId,typeCode);
				redisClient.hset(RedisCacheKeys.testViolationCache,key,SerializeUtil.serialize(violation));
			}
		}
		return violation;
	}
	
	/** 从缓存中根据车辆ID取得时间违规对象，如果没有则从数据库获取并放入缓存 */
	public static Violation getTimeViolationByVehicleId(Integer vehicleId,String typeCode) {
		if (vehicleId == null) {
			return null;
		}
		Violation violation = getTimeViolation(vehicleId,typeCode);
		if (violation == null) {
			violation = violationService.getViolationByVehicle(vehicleId,GlobalConstant.TIME_VIOLATION_TYPE);
			if(violation != null){
				String key = getKey(vehicleId,typeCode);
				redisClient.hset(RedisCacheKeys.timeViolationCache, key, SerializeUtil.serialize(violation));
			}
		}
		return violation;
	}
	
	/** 从缓存中根据车辆ID取得线路违规对象，如果没有则从数据库获取并放入缓存 */
	public static Violation getLineViolationByVehicleId(Integer vehicleId,String typeCode) {
		if (vehicleId == null) {
			return null;
		}
		Violation violation = getLineViolation(vehicleId,typeCode);
		if (violation == null) {
			violation = violationService.getViolationByVehicle(vehicleId,GlobalConstant.LINE_VIOLATION_TYPE);
			if(violation != null){
				String key = getKey(vehicleId,typeCode);
				redisClient.hset(RedisCacheKeys.lineViolationCache,key,SerializeUtil.serialize(violation));
			}
		}
		return violation;
	}
	
	/** 从缓存中根据车辆ID取得线路违规对象，如果没有则从数据库获取并放入缓存 */
	public static Violation getLineViolationByVehicleId(Integer vehicleId,Date startTime,String typeCode) {
		if (vehicleId == null) {
			return null;
		}
		Violation violation = getLineViolation(vehicleId,typeCode);
		if (violation == null) {
			violation = violationService.getViolationByVehicle(vehicleId,GlobalConstant.LINE_VIOLATION_TYPE,startTime);
			if(violation != null){
				String key = getKey(vehicleId,typeCode);
				redisClient.hset(RedisCacheKeys.lineViolationCache,key,SerializeUtil.serialize(violation));
			}
		}
		return violation;
	}
		
	/**
	 * 获取判断是否越界试驾路线
	 * @param dealerId
	 * @return
	 */
	public static DriveLineWithPolygonVO getDriveLineWithPolygonByDealerId(Integer dealerId) {
		if (dealerId == null) {
			return null;
		}
		DriveLineWithPolygonVO driveLineWithPolygon = getDriveLineWithPolygon(dealerId);
		if (driveLineWithPolygon == null) {
			driveLineWithPolygon = driveLineService.getDriveLineWithPolygonListByDealerId(dealerId);
			if(null!=driveLineWithPolygon){
				String key = dealerId.toString();
				redisClient.hset(RedisCacheKeys.driveLineWithPolygonCache,key,SerializeUtil.serialize(driveLineWithPolygon));		
			}
		}
		return driveLineWithPolygon;
	}
	
	/**
	 * 获取判断是否越界试驾路线
	 * @param dealerId
	 * @return
	 */
	public static DriveLineWithPolygonVO getDriveLineWithPolygonByDealerIdNew(Integer dealerId) {
		if (dealerId == null) {
			return null;
		}
		DriveLineWithPolygonVO driveLineWithPolygon = getDriveLineWithPolygon(dealerId);
		if (driveLineWithPolygon == null) {
			driveLineWithPolygon = driveLineService.getDriveLineWithPolygonListByDealerIdNew(dealerId);
			if(null!=driveLineWithPolygon){
				String key = dealerId.toString();
				redisClient.hset(RedisCacheKeys.driveLineWithPolygonCache,key,SerializeUtil.serialize(driveLineWithPolygon));		
			}
		}
		return driveLineWithPolygon;
	}
	
	public static Location getLocationByVehicleId(Integer vehicleId,String typeCode) {
		if (vehicleId == null) {
			return null;
		}
		Location location = getLocation(vehicleId,typeCode);
		if (location == null) {
			location = locationDao.getLastByVehicleId(vehicleId);
			if(location != null){
				putLocation(vehicleId, location,typeCode);
			}else{
				Vehicle vehicle=CacheVehicleManager.getVehicleById(vehicleId);
				if(null!=vehicle && null!=vehicle.getCompanyId()){
					Company company=CacheCompanyManager.getCompany(vehicle.getCompanyId());
					if(null!=company && null!=company.getBaiduLat() && null!=company.getBaiduLng()){
						location=new Location();
						location.setLatitude(String.valueOf(company.getBaiduLat()));
						location.setLongitude(String.valueOf(company.getBaiduLng()));
					}
				}
			}
		}
		return location;
	}
	
	/** 获得车辆最后位置点 */
	public static MonitorLocation getLastMonitorLocationByVehicleId(Integer vehicleId) {
		if (vehicleId == null) {
			return null;
		}
		MonitorLocation monitorLocation = getMonitorLocation(vehicleId);
		if (monitorLocation == null) {
			monitorLocation = monitorLocationDao.getLastByVehicleId(vehicleId);
			if(monitorLocation != null){
				String key = vehicleId.toString();
				redisClient.hset(RedisCacheKeys.monitorLocationCache,key,SerializeUtil.serialize(monitorLocation));
			}else{
				Vehicle vehicle=CacheVehicleManager.getVehicleById(vehicleId);
				if(null!=vehicle && null!=vehicle.getCompanyId()){
					Company company=CacheCompanyManager.getCompany(vehicle.getCompanyId());
					if(null!=company && null!=company.getBaiduLat() && null!=company.getBaiduLng()){
						monitorLocation=new MonitorLocation();
						monitorLocation.setLatitude(String.valueOf(company.getBaiduLat()));
						monitorLocation.setLongitude(String.valueOf(company.getBaiduLng()));
						monitorLocation.setSpeed(0);
					}
				}
			}
		}
		return monitorLocation;
	}

	public static void putTestDrive(Integer vehicleId, TestDrive testDrive,String typeCode){
		String key = getKey(vehicleId,typeCode);
		//对象序列化
		byte[] value = SerializeUtil.serialize(testDrive);
		redisClient.hset(RedisCacheKeys.testDriveCache,key, value);
	}
		
	public static void putViolation(Integer vehicleId, Violation violation,Integer violationType,String typeCode){
		if(GlobalConstant.TIME_VIOLATION_TYPE==violationType.intValue()){
			String key = getKey(vehicleId,typeCode);
			redisClient.hset(RedisCacheKeys.timeViolationCache,key,SerializeUtil.serialize(violation));
		}else if(GlobalConstant.LINE_VIOLATION_TYPE==violationType.intValue()){
			String key = getKey(vehicleId,typeCode);
			redisClient.hset(RedisCacheKeys.lineViolationCache,key,SerializeUtil.serialize(violation));
		}
	}
	
	public static void putTestDriveGpsPoint(Integer vehicleId, String lng,String lat,String typeCode){
			String key = getKey(vehicleId,typeCode);
			//重跑GPS使用本地缓存
			List<GPSPoint> gpsPoints= testDriveGpsPointCache.get(key);
			if(null==gpsPoints)
				gpsPoints=new ArrayList<GPSPoint>();
			GPSPoint gpsPoint=new GPSPoint(Double.parseDouble(lng),Double.parseDouble(lat));
			gpsPoints.add(gpsPoint);
			testDriveGpsPointCache.put(key, gpsPoints);
//			String key = getKey(RedisCacheKeys.testDriveGpsPointCache,vehicleId,typeCode);
//			String point = lng+";"+lat;
//			//GPSPoint gpsPoint=new GPSPoint(Double.parseDouble(lng),Double.parseDouble(lat));
//			redisClient.sadd(key, point);

	}
	
	public static void putLocation(Integer vehicleId, Location location,String typeCode){
		String key = getKey(vehicleId,typeCode);
		redisClient.hset(RedisCacheKeys.locationCache,key,SerializeUtil.serialize(location));
	}
	
	public static void putMonitorLocation(Integer vehicleId, MonitorLocation monitorLocation){
		String key = vehicleId.toString();
		redisClient.hset(RedisCacheKeys.monitorLocationCache,key,SerializeUtil.serialize(monitorLocation));
	}
	
	public static String getKey(Integer id,String typeCode){
		StringBuffer buffer = new StringBuffer();
		if(typeCode!=null && !typeCode.isEmpty()){
			buffer.append(typeCode).append(id);
			
		}else {
			buffer.append(id);
		}
		return buffer.toString();
	}
	
	public static String getKey(String redisCode,Integer id,String typeCode){
		StringBuffer buffer = new StringBuffer(redisCode);
		if(typeCode!=null && !typeCode.isEmpty()){
			buffer.append(typeCode).append(id);
			
		}else {
			buffer.append(id);
		}
		return buffer.toString();
	}
}
