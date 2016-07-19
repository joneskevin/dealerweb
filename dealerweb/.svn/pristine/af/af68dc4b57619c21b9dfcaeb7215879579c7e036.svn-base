package com.ava.resource.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ava.base.dao.redis.RedisClient;
import com.ava.dao.IBoxDao;
import com.ava.dao.IVehicleDao;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.Vehicle;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.RedisCacheKeys;
import com.ava.util.SerializeUtil;

public class CacheVehicleManager {
	protected final static Logger logger = Logger.getLogger(CacheVehicleManager.class);

	/** 车辆ID和车辆对象的缓存 */
	//public static HashMap<Integer, Vehicle> idAndVehicleCache = new HashMap<Integer, Vehicle>(
	//		10000);
	/** 车辆ID和其关联的设备对象的缓存 */
//	public static HashMap<Integer, Box> vehicleIdAndBoxCache = new HashMap<Integer, Box>(
//			10000);

	public static HashMap<String, Integer> vinMap = new HashMap<String, Integer>(10000);
	private static IVehicleDao vehicleDao = (IVehicleDao) GlobalConfig
			.getBean("vehicleDao");

	private static IBoxDao boxDao = (IBoxDao) GlobalConfig
			.getBean("boxDao");

	private static RedisClient redisClient =  (RedisClient)GlobalConfig
			.getBean("redisClient");
	

	private CacheVehicleManager() {
	}
	
	public static void putVehicle(Integer vehicleId, Vehicle vehicle){
		redisClient.hset(RedisCacheKeys.idAndVehicleCache,vehicleId.toString(),SerializeUtil.serialize(vehicle));
	}
	
	public static void putBox(Integer vehicleId, Box box){
		redisClient.hset(RedisCacheKeys.vehicleIdAndBoxCache,vehicleId.toString(),SerializeUtil.serialize(box));
	}
	
	public static void removeBox(Integer vehicleId){
		redisClient.hdel(RedisCacheKeys.vehicleIdAndBoxCache,vehicleId.toString());
	}
	
	public static void removeVehicleById(Integer vehicleId){
		if (vehicleId == null) {
			return;
		}
		redisClient.hdel(RedisCacheKeys.idAndVehicleCache,vehicleId.toString());
		redisClient.hdel(RedisCacheKeys.vehicleIdAndBoxCache,vehicleId.toString());
	}

	/** 从缓存中根据vehicleId取得车辆对象，如果没有则从数据库获取并放入缓存 */
	public static Vehicle getVehicleById(Integer vehicleId) {
		if (vehicleId == null) {
			return null;
		}
		Vehicle vehicle = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.idAndVehicleCache,vehicleId.toString()));
		if (vehicle == null) {
			vehicle = vehicleDao.get(vehicleId);
			redisClient.hset(RedisCacheKeys.idAndVehicleCache,vehicleId.toString(),SerializeUtil.serialize(vehicle));
		}
		return vehicle;
	}

	public static Box getBoxByVehicleId(Integer vehicleId) {
		if (vehicleId == null) {
			return null;
		}
		Box box = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.vehicleIdAndBoxCache,vehicleId.toString()));
		if (box == null) {
			box = boxDao.getByVehicleId(vehicleId);
			if(null==box)
				return null;
			redisClient.hset(RedisCacheKeys.vehicleIdAndBoxCache,vehicleId.toString(),SerializeUtil.serialize(box));
		}
		return box;
	}
	
	/** 从缓存中根据vehicleId取得车辆对象，如果没有则从数据库获取并放入缓存 */
	public static Vehicle getVehicleByVin(String vin) {
		if (null == vin || "".equals(vin.trim())) {
			return null;
		}
		Vehicle vehicle=null;
		Long cacheSize = redisClient.hlen(RedisCacheKeys.idAndVehicleCache);
		if(null==cacheSize || cacheSize.longValue()==0){
			vehicle=vehicleDao.getByVin(vin);
			if(null==vehicle) return null;
			vinMap.put(vin, vehicle.getId());//添加vin和车辆id的缓存关系 
			redisClient.hset(RedisCacheKeys.idAndVehicleCache,vehicle.getId().toString(),SerializeUtil.serialize(vehicle));
			return vehicle;
		}
		
		/*Set<Entry<Integer, Vehicle>> set = idAndVehicleCache.entrySet() ;
		if(null==set || set.isEmpty())
			return null;
		Iterator<Entry<Integer, Vehicle>> it = set.iterator();
		
		while(it.hasNext()){
			Entry<Integer, Vehicle> entry =it.next();
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			vehicle = entry.getValue();
			if(vehicle.getVin().equals(vin)){
				return vehicle;
			}else{
				continue;
			}
		} */
		
		//final List<Vehicle> tempVehicleList = new ArrayList<Vehicle>();

//		Map<byte[],byte[]> tempMap = redisClient.hgetAll(RedisCacheKeys.idAndVehicleCache);
//		//tempVehicleList.addAll(idAndVehicleCache.values());
//		
//		if( null != tempMap && tempMap.size()>0 ){
//			for(byte[] byteValue : tempMap.values()){
//				Vehicle temp = SerializeUtil.unserialize(byteValue);
//				if(null!=temp && temp.getVin().equals(vin)){
//					logger.info("试驾重跑-进度更新：getVehicleByVin 2....");
//					return temp;
//				}
//			}
//		}
		//替换每次循环vin带来的性能问题
		if(vinMap.containsKey(vin)){
			byte[] byteValue = redisClient.hget(RedisCacheKeys.idAndVehicleCache,vinMap.get(vin).toString());
			Vehicle temp = SerializeUtil.unserialize(byteValue);
			if(temp!=null){
				return temp;
			}
		}
		
		vehicle=vehicleDao.getByVin(vin);
		if(null==vehicle)	return null;
		vinMap.put(vin, vehicle.getId());//添加vin和车辆id的缓存关系 
		redisClient.hset(RedisCacheKeys.idAndVehicleCache, vehicle.getId().toString(), SerializeUtil.serialize(vehicle));
		return vehicle;
	}
	
	
	public static Box getBoxByUniqueId(String uniqueId) {
		if (null == uniqueId || "".equals(uniqueId.trim())) {
			return null;
		}

		Box box=null;
		Long cacheSize = redisClient.hlen(RedisCacheKeys.vehicleIdAndBoxCache);
		if(null==cacheSize || cacheSize.longValue()==0){
			box=boxDao.getByUniqueId(uniqueId);
			if(null==box) return null;
			if (box.getVehicleId() != null && box.getVehicleId() != GlobalConstant.FALSE) {
				redisClient.hset(RedisCacheKeys.vehicleIdAndBoxCache,box.getVehicleId().toString(),SerializeUtil.serialize(box));
			}
			return box;
		}
		
		/*Set<Entry<Integer, Box>> set = vehicleIdAndBoxCache.entrySet() ;
		if(null==set || set.isEmpty())
			return null;
		Iterator<Entry<Integer, Box>> it = set.iterator();
		
		while(it.hasNext()){
			Entry<Integer, Box> entry =it.next();
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			box = entry.getValue();
			if(null!=box && box.getUniqueId().equals(uniqueId)){
				return box;
			}else{
				continue;
			}
		}*/
		
		//final List<Box> tempBoxList = new ArrayList<Box>();
		Map<byte[],byte[]> tempMap = redisClient.hgetAll(RedisCacheKeys.vehicleIdAndBoxCache);
		//tempBoxList.addAll(vehicleIdAndBoxCache.values());
		
		if( null != tempMap && tempMap.size()>0 ){
			for(byte[] tempValue : tempMap.values()){
				Box temp = SerializeUtil.unserialize(tempValue);
				if(null!=temp && temp.getUniqueId().equals(uniqueId)){
					return temp;
				}
			}
		}
		
		box=boxDao.getByUniqueId(uniqueId);
		if(null==box) return null;
		
		if (box.getVehicleId() != null && box.getVehicleId() != GlobalConstant.FALSE) {
			redisClient.hset(RedisCacheKeys.vehicleIdAndBoxCache,box.getVehicleId().toString(),SerializeUtil.serialize(box));
		}
		return box;
	}
	
	/** 从缓存中根据vehicleId取得车辆对象，如果没有则从数据库获取并放入缓存 */
	public static Vehicle getVehicleBySerialNumber(String serialNumber) {
		return vehicleDao.getBySerialNumber(serialNumber);
	}

}
