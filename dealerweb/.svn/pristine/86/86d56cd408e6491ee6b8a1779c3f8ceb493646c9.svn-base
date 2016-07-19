package com.ava.resource.security;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ava.resource.cache.ConcurrentHashMapCache;
import com.ava.util.MyRandomUtils;

/**令牌管理类*/
public class UserTokenManager {
	
	private final static Logger logger = Logger.getLogger(UserTokenManager.class);

	/** userToken对象缓存类，key为token字符串 */
	private static ConcurrentHashMapCache<String, UserToken> tokenInfo = new ConcurrentHashMapCache<String, UserToken>(true);
	
	/**	userAccountId和Token的映射，用来同一个用户在同一时期的不同客户端登录时，返回同一个token值 */
	private static ConcurrentHashMap<Integer, String> userTokenMapping = new ConcurrentHashMap<Integer, String>();
	
	static{
		/**	UserToken的最大生存时间，单位毫秒，默认7*24小时 */
		tokenInfo.setMaxLifeTime(1000 * 60 * 60 * 24 * 7);
	}
	
	/**
	 * 根据userAccountId注册一个登录记录，返回UserToken对象中的token值，对于同一userAccountId在同一时期的不同客户端登录时都相同
	 * @param userAccountId
	 * @return UserToken
	 */
	public static UserToken regUserAccountInfo(Integer userAccountId){
		if (userAccountId == null){
			return null;
		}
		
		String oldTokenKey = userTokenMapping.get(userAccountId);
		UserToken userToken = getUserToken(oldTokenKey);
		if (userToken == null){
			//该用户没有UserToken对象，代表服务器重启后还未登录过或已过期，则重新生成token值
			userToken = new UserToken(userAccountId);
			userToken.setToken(MyRandomUtils.getUUID());
		}
		userToken.setLastRefreshTime(System.currentTimeMillis());
		tokenInfo.put(userToken.getToken(), userToken);
		userTokenMapping.put(userAccountId, userToken.getToken());	//写入映射表，用来下次登录时检查使用
		
		if (logger.isDebugEnabled()){
			logger.debug("regUserId()	token=" + userToken.getToken());
		}
		return userToken;
	}
	
	public static UserToken getUserToken(String tokenKey){
		if (tokenKey == null){
			return null;
		}
		return tokenInfo.get(tokenKey);
	}
	
}
