package com.ava.resource.cache;

import java.util.HashMap;

import com.ava.dao.IFenceDao;
import com.ava.domain.entity.Fence;
import com.ava.resource.GlobalConfig;

public class CacheFenceManager {
	/** 围栏ID和围栏对象的缓存 */
	private static HashMap<Integer, Fence> idAndFenceCache = new HashMap<Integer, Fence>(
			10000);

	private static IFenceDao fenceDao = (IFenceDao) GlobalConfig
			.getBean("fenceDao");

	private CacheFenceManager() {
	}
	
	public static void removeFenceById(Integer fenceId){
		if (fenceId == null) {
			return;
		}

		idAndFenceCache.remove(fenceId);
	}
	
	/** 从缓存中根据fenceId取得围栏对象，如果没有则从数据库获取并放入缓存 */
	public static Fence getFenceById(Integer fenceId) {
		if (fenceId == null) {
			return null;
		}

		Fence fence = idAndFenceCache.get(fenceId);
		if (fence == null) {
			fence = fenceDao.get(fenceId);
			idAndFenceCache.put(fenceId, fence);
		}
		return fence;
	}
}
