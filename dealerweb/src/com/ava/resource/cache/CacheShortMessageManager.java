package com.ava.resource.cache;

import com.ava.dao.IShortMessageDao;
import com.ava.domain.entity.Company;
import com.ava.resource.GlobalConfig;

/**站内消息管理类*/
public class CacheShortMessageManager {
    /** Cache of newShortMessageCounts. */
    private static LRUCache<Integer, Integer> newShortMessageCountCache = new LRUCache<Integer, Integer>(1000);

	private static IShortMessageDao shortMessageDao = (IShortMessageDao) GlobalConfig.getBean("shortMessageDao");
    
    static{
    	//用户未读新邮件对象缓存的最大生存时间，单位毫秒
    	newShortMessageCountCache.setMaxLifeTime(1000 * 60 * 1);
    }
    
    private CacheShortMessageManager() {
    }
    
    /** 从缓存中根据userId取得用户新邮件数量，如果没有则从数据库获取并放入缓存	*/
    public static Integer getNewShortMessageCount(Integer userId) {
    	//System.out.println("userCache.size()=" + userCache.size());
        if (userId == null) {
            return null;
        }
        Integer newShortMessageCount = newShortMessageCountCache.get(userId);
        if (newShortMessageCount == null) {
    		String hql = "select count(id) from ShortMessage where isReaded=0 AND toUserId=" + userId;
    		newShortMessageCount = shortMessageDao.executeQueryInt(hql);
        	newShortMessageCountCache.put(userId, newShortMessageCount);
        }
        return newShortMessageCount;
    }

    public static void remove(Integer userId) {
    	newShortMessageCountCache.remove(userId);
    }
    public static void put(Integer userId, Integer newShortMessageCount) {
    	if ( userId != null  )
    	newShortMessageCountCache.put(userId, newShortMessageCount);
    }
    
    
}
