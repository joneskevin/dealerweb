package com.ava.resource.cache;

import java.util.HashMap;

import com.ava.dao.IBoxDao;
import com.ava.domain.entity.Box;
import com.ava.resource.GlobalConfig;

public class CacheBoxManager {
	/** 设备ID和设备对象的缓存 */
	private static HashMap<Integer, Box> idAndBoxCache = new HashMap<Integer, Box>(
			10000);

	private static IBoxDao boxDao = (IBoxDao) GlobalConfig
			.getBean("boxDao");

	private CacheBoxManager() {
	}
	
	public static void removeBoxById(Integer boxId){
		if (boxId == null) {
			return;
		}

		idAndBoxCache.remove(boxId);
	}

	/** 从缓存中根据boxId取得设备对象，如果没有则从数据库获取并放入缓存 */
	public static Box getBoxById(Integer boxId) {
		if (boxId == null) {
			return null;
		}

		Box box = idAndBoxCache.get(boxId);
		if (box == null) {
			box = boxDao.get(boxId);
			idAndBoxCache.put(boxId, box);
		}
		return box;
	}
}
