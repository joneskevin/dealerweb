package com.ava.resource.security;

import java.util.Date;

import com.ava.resource.GlobalConfig;
import com.ava.resource.cache.ConcurrentHashMapCache;
import com.ava.resource.security.param.AuthenticationParam;
import com.ava.util.DateTime;

/**认证管理类*/
public class AuthenticationManager{
	/** 注册ip和最后一次注册时间(格式：yyyyMMddHHmmss)的缓存	*/
    private static ConcurrentHashMapCache<String, Long> lastRegisterTime = new ConcurrentHashMapCache<String, Long>();
	/** 注册ip和最近一小时注册数的缓存	*/
    private static ConcurrentHashMapCache<String, Integer> registerCount = new ConcurrentHashMapCache<String, Integer>();
    
    static{
    	//最大生存时间，单位毫秒
    	lastRegisterTime.setMaxLifeTime(1000 * 60 * 10);
    	//最大生存时间，单位毫秒
    	registerCount.setMaxLifeTime(1000 * 60 * 60 * 1);
    }
    
	/**
	 * @param 
	 * @return token string
	 */
	static public UserToken authenticate(IAuthenticationProvider provider, AuthenticationParam param){
		UserToken token = provider.authenticate(param);
		return token;
	}

    /**	注册成功后，更新某个ip的注册条件，包括：最后一次注册时间，最近一个小时注册次数等*/
	public static void updateRegisterCondition(String clientIp) {
    	updateLastRegisterTime(clientIp);
    	updateRegisterCountPerHour(clientIp);
    }
    
    /**	更新某个ip的最后一次注册时间*/
	private static void updateLastRegisterTime(String clientIp) {
    	if ( clientIp == null){
    	}else{
    		lastRegisterTime.put(clientIp, DateTime.toShortDateTimeL(new Date()));
    	}
    }

    /**	更新某个ip的最近一个小时注册次数*/
    private static void updateRegisterCountPerHour(String clientIp) {
    	if ( clientIp == null){
    	}else{
    		Integer count = registerCount.get(clientIp);
    		if(count != null){
    			registerCount.put(clientIp, count + 1);
    		}else{
    			registerCount.put(clientIp, 1);
    		}
    	}
    }
    
    /**	根据ip判断时间间隔是否符合要求*/
    public static boolean isValidInterval(String clientIp) {
    	if ( clientIp == null){
			return false;//ip为空认为是无效的间隔
		}
		Long registerTime = lastRegisterTime.get(clientIp);
    	if ( registerTime == null){
    		return true;//最后一次注册时间为空，认为是有效
    	}else{
    		long interval = DateTime.getSecondDifference(DateTime.toDate(registerTime), new Date());
    		if(interval >= GlobalConfig.getInt("register.intervalMin", 30)){
        		return true;
    		}
    		return false;
    	}
    }
    
    /**	根据ip判断最近一个小时内，该ip注册次数是否超过限制*/
    public static boolean isValidRegisterCountPerHour(String clientIp) {
    	if ( clientIp == null){
			return false;//ip为空认为是无效的注册次数
		}
		Integer count = registerCount.get(clientIp);
    	if ( count == null){
    		return true;//最近一个小时注册次数为空，认为是有效
    	}else{
    		if(count <= GlobalConfig.getInt("register.countMax", 10)){
        		return true;
    		}
    		return false;
    	}
    }
}
