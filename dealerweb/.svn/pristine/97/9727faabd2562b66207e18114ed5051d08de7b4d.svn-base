package com.ava.resource.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import java.util.List;

import com.ava.dao.impl.UserDao;
import com.ava.dao.impl.UserRoleRelationDao;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.util.TypeConverter;

/**用户缓存类*/
public class CacheUserManager {

	/**	用户登录名和ID的缓存	*/
    private static HashMap<String, Integer> loginNameAndUserIdCache = new HashMap<String, Integer>(10000);
    /** Cache of users. */
    private static LRUCache<Integer, User> userCache = new LRUCache<Integer, User>(1000);

	/**	用户ID和临时密码的缓存	*/
    private static LRUCache<Integer, String> userIdAndTempPasswordCache = new LRUCache<Integer, String>(10000);
    
	private static UserDao userDao = (UserDao) GlobalConfig.getBean("userDao");
	
	private static UserRoleRelationDao userRoleRelationDao=(UserRoleRelationDao) GlobalConfig.getBean("userRoleRelationDao");
	
	//默认3小时
    public static int  TEMP_PASSWORD_LIFE_TIME_MAX = 60 * 3;
    static{
    	//临时密码缓存的最大生存时间，单位毫秒，
    	userIdAndTempPasswordCache.setMaxLifeTime(1000 * 60 * TEMP_PASSWORD_LIFE_TIME_MAX);
    }
    
    private CacheUserManager() {
    }

    /** 从缓存中根据loginName取得userId，如果没有则从数据库获取并放入缓存；
     * 由于userId和loginName的唯一性和不可更改性，此方法的数据能保证实时性和正确性*/
    public static Integer getUserId(String loginName) {
    	//System.out.println("userCache.size()=" + userCache.size());
        if (loginName == null) {
            return null;
        }else{
        	loginName = loginName.trim();
        }
        Integer userId = loginNameAndUserIdCache.get(loginName);
        if (userId == null) {   
        	User user = userDao.getByLoginName(loginName);
            if (user != null){
            	userId = user.getId();
            	loginNameAndUserIdCache.put(user.getLoginName().trim(), userId);
            }
        }
        return userId;
    }
    
    public static String getLoginName(Integer userId) {
        if (userId == null) {
            return null;
        }
        User user = getUser(userId);
        if (user != null) {
        	return user.getLoginName();
        }
        return null;
    }
    
    public static String getNickName(Integer userId) {
        if (userId == null) {
            return null;
        }
        User user = getUser(userId);
        if (user != null) {
        	return user.getNickName();
        }
        return null;
    }
    
    /**	从缓存中取得用户对象，如果没有则从数据库获取。注意：该方法肯定能获得用户对象，但用户对象信息并不和数据库完全同步！
     * 本方法一般用来论坛帖子、站内消息中提取用户信息用；在要求严格数据的场合下，不能采用此方法！	*/
    public static User getUser(Integer userId) {
        if (userId == null) {
            return null;
        }
        User user =  userCache.get(userId);
        if (user == null) {   
            user = userDao.get(userId);
            //判断该用户的角色是否包含了运营管理员
            if (user != null){
            	if (userRoleRelationDao.isUserCompriseRole(userId, GlobalConstant.OPERATIONS_ADMIN_ROLE_ID)) {
            		user.setIsAdminRole(true);
    				user.setCompanyId(GlobalConstant.DEFAULT_GROUP_ORG_ID);
    			}
            	removeUser(userId);
            	userCache.put(userId, user);
            }
        }
        return user;
    }
    /**	从缓存中取得用户对象，如果没有则从数据库获取。注意：该方法肯定能获得用户对象，但用户对象信息并不和数据库完全同步！
     * 本方法一般用来论坛帖子、站内消息中提取用户信息用；在要求严格数据的场合下，不能采用此方法！	*/
    public static User getUser(String loginName) {
        Integer userId = getUserId(loginName);
        return getUser(userId);
    }
    /**	移除一个用户对象	*/
    @SuppressWarnings("rawtypes")
	public static void removeUser(Integer userId) {
    	if ( userId != null ){
    		userCache.remove(userId);
    		//不能调用getLoginName(Integer userId)获得loginName，否则死循环
    		Set entries = loginNameAndUserIdCache.entrySet();
    		if(entries != null && entries.size() > 0){
    			Iterator itor = entries.iterator();
    			while(itor.hasNext()){
    				Entry entry = (Entry) itor.next();
    				Integer aUserId = TypeConverter.toInteger(entry.getValue());
    				if(aUserId != null && aUserId.intValue() == userId.intValue()){
    	        		break;
    				}
    			}
    		}
    	}
    }
    

    /**
     * 根据orgId找到组织下某种类型用户
     * @param orgId
     * @param roleId 
     * -1 所有用户
     * @return
     */
	@SuppressWarnings("rawtypes")
	public static List<User> findUserByOrgId(Integer orgId, Integer roleId) {
		List<User> userList = new ArrayList<User>();
		Iterator iterator = null;
		//LRULinkedHashMap map=userCache.map;
		try{
			/**
			if (null != map && !map.entrySet().isEmpty()) {
				Set<Map.Entry<Integer,User>> sets= map.entrySet();
				User user = null;
				 Iterator<Entry<Integer, User>> entrys=sets.iterator();
				while (entrys.hasNext()) {
					Object obj=entrys.next();
					if(null!=obj){
						Entry<Integer, User> entry= (Entry<Integer, User>) obj;
						user=getUser(entry.getKey());
						if(null!=user){
							if (null == roleId || -1 == roleId.intValue()) {
								userList.add(user);
							} else {
								if (null != user && user.getCompanyId().intValue() == orgId) {
									if (userRoleRelationDao.isUserCompriseRole(user.getId(), roleId)) {
										userList.add(user);
									}
								}
							}
						}
					}
				}
			}
			*/
			if(null==userList || userList.isEmpty() ){
				userList = new ArrayList<User>();
				List<User> users = userDao.getByOrg(orgId);
				if (null != users) {
					iterator = users.iterator();
					User user = null;
					while (iterator.hasNext()) {
						user = (User) iterator.next();
						if (null == roleId || -1 == roleId.intValue()) {
							userList.add(user);
						} else {
							if (null != user && user.getCompanyId().intValue() == orgId) {
								if (userRoleRelationDao.isUserCompriseRole(user.getId(), roleId)) {
									userList.add(user);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException("系统异常");
		}
		return userList;
	}

    public static void putTempPassword(Integer userId, String tempPassword) {
    	if ( userId != null && tempPassword != null )
    		userIdAndTempPasswordCache.put(userId, tempPassword);
    }
    
    public static String getTempPassword(Integer userId) {
        return userIdAndTempPasswordCache.get(userId);
    }
    
    public static String getMacOfPc(Integer userId) {
    	User user = getUser(userId);
    	if(user != null){
    		return user.getMacOfPc();
    	}
    	return null;
    }
}
