package com.ava.resource.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.ava.base.dao.redis.RedisClient;
import com.ava.dao.IDriveLineDao;
import com.ava.dao.IFenceDao;
import com.ava.domain.entity.DriveLine;
import com.ava.domain.entity.Fence;
import com.ava.domain.vo.DriveLineWithFenceVO;
import com.ava.resource.GlobalConfig;
import com.ava.resource.RedisCacheKeys;
import com.ava.util.MyStringUtils;
import com.ava.util.SerializeUtil;

public class CacheDriveLineManager {
	/** 经销商ID和线路对象集合的缓存 */
	//private static HashMap<Integer, List<Integer>> dealerIdAndDriveLineIdsCache = new HashMap<Integer, List<Integer>>(
	//		10000);
	/** 线路ID和线路对象的缓存 */
	//private static HashMap<Integer, DriveLine> idAndDriveLineCache = new HashMap<Integer, DriveLine>(
	//		10000);

	private static RedisClient redisClient =  (RedisClient)GlobalConfig
			.getBean("redisClient");
	
	private static IDriveLineDao driveLineDao = (IDriveLineDao) GlobalConfig
			.getBean("driveLineDao");
	
	private static IFenceDao fenceDao = (IFenceDao) GlobalConfig
			.getBean("fenceDao");

	private CacheDriveLineManager() {
	}
	
	public static void removeDriveLineById(Integer driveLineId){
		if (driveLineId == null) {
			return;
		}
		redisClient.hdel(RedisCacheKeys.dealerIdAndDriveLineIdsCache, driveLineId.toString());
		redisClient.hdel(RedisCacheKeys.idAndDriveLineCache, driveLineId.toString());
	}

	/** 从缓存中根据线路ID取得车型对象，如果没有则从数据库获取并放入缓存 */
	public static DriveLine getDriveLineById(Integer driveLineId) {
		if (driveLineId == null) {
			return null;
		}

		DriveLine driveLine = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.idAndDriveLineCache,driveLineId.toString()));
		if (driveLine == null) {
			driveLine = driveLineDao.get(driveLineId);
			if(driveLine != null){
				JSONArray jsonArray = driveLineDao.toJSONArray(driveLine.getJsonContent(), "fenceIds");
				if (jsonArray != null) {
					if (jsonArray.size() > 0) {
						Integer fenceId = jsonArray.getInt(0);
						Fence fence = fenceDao.get(fenceId);
						driveLine.setJsonPolygon1(fence.getJsonData());
					}
					if (jsonArray.size() > 1) {
						Integer fenceId = jsonArray.getInt(1);
						Fence fence = fenceDao.get(fenceId);
						driveLine.setJsonPolygon2(fence.getJsonData());
					}
				}
			}
        	redisClient.hset(RedisCacheKeys.idAndDriveLineCache,driveLineId.toString(),SerializeUtil.serialize(driveLine));
		}
		return driveLine;
	}

	private static List<Integer> getDriveLineIdsByDealerId(Integer dealerId) {
		List<Integer> driveLineIds = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.dealerIdAndDriveLineIdsCache,dealerId.toString()));
		if(driveLineIds == null){
			driveLineIds = driveLineDao.findIdsByCompanyId(dealerId);
        	redisClient.hset(RedisCacheKeys.dealerIdAndDriveLineIdsCache,dealerId.toString(),SerializeUtil.serialize(driveLineIds));
		}
		
		return driveLineIds;
	}

	public static List<DriveLine> getDriveLinesByDealerId(
			Integer dealerId) {
		List<DriveLine> driveLineList = null;
		
		List<Integer> driveLineIds = getDriveLineIdsByDealerId(dealerId);
		if(driveLineIds != null){
			driveLineList = new ArrayList();
			for(Integer id : driveLineIds){
				driveLineList.add(getDriveLineById(id));				
			}
		}
		
		return driveLineList;
	}

	public static List<DriveLineWithFenceVO> getDriveLineVOsByDealerId(
			Integer dealerId) {
		List<DriveLineWithFenceVO> driveLineVOList = null;
		
		List<Integer> driveLineIds = getDriveLineIdsByDealerId(dealerId);
		if (driveLineIds != null && driveLineIds.size() > 0) {
			driveLineVOList = new ArrayList();
			for (Integer id : driveLineIds) {
				DriveLine aDriveLine = getDriveLineById(id);
				String jsonContent = aDriveLine.getJsonContent();
				if (aDriveLine != null && !MyStringUtils.isBlank(jsonContent)) {
					JSONArray jsonArray = driveLineDao.toJSONArray(jsonContent, "fenceIds");
					if (jsonArray != null) {
						DriveLineWithFenceVO vo = new DriveLineWithFenceVO(
								aDriveLine);
						if (jsonArray.size() > 0) {
							Integer fenceId = jsonArray.getInt(0);
							Fence fence = CacheFenceManager.getFenceById(fenceId);
							vo.setFence1(fence);
						}
						if (jsonArray.size() > 1) {
							Integer fenceId = jsonArray.getInt(1);
							Fence fence = CacheFenceManager.getFenceById(fenceId);
							vo.setFence2(fence);
						}
						driveLineVOList.add(vo);
					}
				}
			}
		}	

		return driveLineVOList;
	}
	
	public static String getDriveLineNameById(Integer driveLineId) {
		DriveLine driveLine = getDriveLineById(driveLineId);
		if(driveLine != null){
			return driveLine.getName();
		}
		return null;
	}
}
