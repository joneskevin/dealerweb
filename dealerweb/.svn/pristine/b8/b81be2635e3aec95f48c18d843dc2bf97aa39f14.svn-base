package com.ava.resource.cache;


import com.ava.base.dao.redis.RedisClient;
import com.ava.dao.ICompanyDao;
import com.ava.domain.entity.Company;
import com.ava.resource.GlobalConfig;
import com.ava.resource.RedisCacheKeys;
import com.ava.util.SerializeUtil;

/**公司对象缓存类*/
public class CacheCompanyManager {

    /** Cache of companyId&obName. */
   // private static HashMap<Integer, String> companyIdAndObNameCache = new HashMap<Integer, String>(10000);
    /** Cache of obName&companyId */
   // private static HashMap<String, Integer> obNameAndCompanyIdCache = new HashMap<String, Integer>(10000);
    /** Cache of orgs. */
   //private static LRUCache<Integer, Company> companyCache = new LRUCache<Integer, Company>(1000);

    private static ICompanyDao companyDao = (ICompanyDao) GlobalConfig.getBean("companyDao");
    
	private static RedisClient redisClient =  (RedisClient)GlobalConfig
			.getBean("redisClient");

    static{
    	//组织对象缓存的最大生存时间，单位毫秒，1*60分钟
    	//userCache.setMaxLifeTime(1000 * 60 * 60 * 1);
    }
    
    private CacheCompanyManager() {
    }

    public static String getName(Integer companyId) {
        if (companyId == null) {
            return null;
        }
        
        String companyName =  null;
        Company company = getCompany(companyId);
        if (company != null) {   
        	companyName = company.getName();
        }
        return companyName;
    }
    
    /** 从缓存中根据companyId取得ObName，如果没有则从数据库获取并放入缓存；
     * 由于companyId和obName的唯一性和不可更改性，此方法的数据能保证实时性和正确性，可以放心调用	*/
    public static String getObName(Integer companyId) {
    	//System.out.println("userCache.size()=" + userCache.size());
        if (companyId == null) {
            return null;
        }
        
        String obName = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.companyIdAndObNameCache,companyId.toString()));
        if (obName == null) {   
        	Company org = companyDao.get(companyId);
            if (org != null){
            	obName = org.getObName();
            	redisClient.hset(RedisCacheKeys.companyIdAndObNameCache,companyId.toString(),obName.getBytes());
            }
        }
        return obName;
    }
    /** 从缓存中根据ObName取得companyId，如果没有则从数据库获取并放入缓存；
     * 由于companyId和obName的唯一性和不可更改性，此方法的数据能保证实时性和正确性，可以放心调用	*/
    public static Integer getCompanyId(String obName) {
    	//System.out.println("userCache.size()=" + userCache.size());
        if (obName == null) {
            return null;
        }else{
        	obName = obName.trim();
        }
        
        Integer companyId = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.obNameAndCompanyIdCache,obName));
        if (companyId == null) {   
        	Company org = companyDao.getByObName(obName);
            if (org != null){
            	companyId = org.getId();
            	redisClient.hset(RedisCacheKeys.obNameAndCompanyIdCache,org.getObName().trim(),SerializeUtil.serialize(companyId));
            }
        }
        return companyId;
    }
    
    /**	根据companyId从缓存中取得组织对象，如果没有则从数据库获取。注意：该方法肯定能获得组织对象，但组织对象信息并不和数据库完全同步！
     * 本方法一般用来组织门户、访问记录等提取组织信息用；在要求严格数据的场合下，不能采用此方法！	*/
    public static Company getCompany(Integer companyId) {
        if (companyId == null) {
            return null;
        }
        Company org = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.companyCache,companyId.toString()));
        if (org == null) {   
        	org = companyDao.get(companyId);
            if (org != null){
            	putCompany(companyId, org);
            }
        }
        return org;
    }
    
    /**
     * 根据网路代码获得经销商信息
     * @author liuq 
     * @version 0.1 
     * @param dealerCode
     * @return
     */
    public static Company getByDealerCode(String dealerCode) {
    	 if (dealerCode == null) {
             return null;
         }
    	 Company company = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.companyByDealerCodeCache, dealerCode));
         if (company == null) {   
         	company = companyDao.getByDealerCode(dealerCode);
             if (company != null){
             	putCompanyByDealerCode(dealerCode, company);
             }
         }
         return company;
    }
    
    public static Company putCompanyByDealerCode(String dealerCode, Company company) {
    	if (dealerCode != null && company != null ){
        	redisClient.hset(RedisCacheKeys.companyByDealerCodeCache, dealerCode, SerializeUtil.serialize(company));
    		return company;
    	}else{
    		return null;
    	}
    }
    
    /**	根据obName从缓存中取得组织对象，如果没有则从数据库获取。注意：该方法肯定能获得组织对象，但组织对象信息并不和数据库完全同步！
     * 本方法一般用来组织门户、访问记录等提取组织信息用；在要求严格数据的场合下，不能采用此方法！	*/
    public static Company getCompany(String obName) {
        Integer companyId = getCompanyId(obName);
        return getCompany(companyId);
    }
        
    public static Company putCompany(Integer companyId, Company org) {
    	if ( companyId != null && org != null ){
        	redisClient.hset(RedisCacheKeys.companyCache,companyId.toString(),SerializeUtil.serialize(org));
    		return org;
    	}else{
    		return null;
    	}
    }
    
    public static Company removeCompany(Integer companyId) {
        if (companyId == null) {
            return null;
        }
        Company org = SerializeUtil.unserialize(redisClient.hget(RedisCacheKeys.companyCache,companyId.toString()));
        redisClient.hdel(RedisCacheKeys.companyCache, companyId.toString());
        return org;
    }
    
    public static void clear() {
        redisClient.del(RedisCacheKeys.companyIdAndObNameCache);
        redisClient.del(RedisCacheKeys.obNameAndCompanyIdCache);
        redisClient.del(RedisCacheKeys.companyCache);
        redisClient.del(RedisCacheKeys.companyByDealerCodeCache);
	}
}
