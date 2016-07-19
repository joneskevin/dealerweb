package com.ava.resource.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ava.dao.ICarStyleDao;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.TestDrive;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;

public class CacheCarManager {
	/** 品牌ID和车型对象ID集合的缓存 */
	private static HashMap<Integer, List<Integer>> brandIdAndCarStyleIdsCache = new HashMap<Integer, List<Integer>>(
			10000);
	/** 车型ID和车型对象的缓存 */
	private static HashMap<Integer, CarStyle> idAndCarStyleCache = new HashMap<Integer, CarStyle>(
			10000);

	private static ICarStyleDao carStyleDao = (ICarStyleDao) GlobalConfig
			.getBean("carStyleDao");

	private CacheCarManager() {
	}

	
	public static void removeCarStyleById(Integer carStyelId){
		if (carStyelId == null) {
			return;
		}

		idAndCarStyleCache.remove(carStyelId);
	}
	
	public static void putCarStyleCache(Integer carStyleId, CarStyle carStyle){
		idAndCarStyleCache.put(carStyleId, carStyle);
	}
	
	public static void removeCarStyleIdsCache(){
		brandIdAndCarStyleIdsCache.clear();
	}

	/** 从缓存中根据brandId取得该品牌所有的车型，如果没有则从数据库获取并放入缓存 */
	public static List<CarStyle> getAllCarStyleByBrandId(Integer brandId) {
		if (brandId == null) {
			return null;
		}

		List<Integer> carStyleIds = brandIdAndCarStyleIdsCache.get(brandId);
		if (carStyleIds == null) {
			carStyleIds = carStyleDao.findIdsByBrandId(brandId, GlobalConstant.FALSE);
			brandIdAndCarStyleIdsCache.put(brandId, carStyleIds);
		}
		
		List carStyleList = new ArrayList();
		if(carStyleIds != null && carStyleIds.size() > 0){
			for(Integer id : carStyleIds){
				CarStyle carStyle = getCarStyleById(id);
				carStyleList.add(carStyle);
			}
		}
		return carStyleList;
	}
	/** 从缓存中根据carStyleId取得车型对象，如果没有则从数据库获取并放入缓存 */
	public static CarStyle getCarStyleById(Integer carStyleId) {
		if (carStyleId == null) {
			return null;
		}

		CarStyle carStyle = idAndCarStyleCache.get(carStyleId);
		if (carStyle == null) {
			carStyle = carStyleDao.get(carStyleId);
			if (carStyle != null) {
				idAndCarStyleCache.put(carStyleId, carStyle);
			} else {
				return null;
			}
			
		}
		return carStyle;
	}

	/** 从缓存中根据carStyleId取得车型名称 */
	public static String getCarStyleNameById(Integer carStyleId) {
		CarStyle carStyle = getCarStyleById(carStyleId);
		if (carStyle != null) {
			return carStyle.getName() + " "+ carStyle.getYearType();
		}
		return "";
	}
}
