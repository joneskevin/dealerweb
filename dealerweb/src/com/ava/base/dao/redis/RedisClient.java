package com.ava.base.dao.redis;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ava.resource.RedisCacheKeys;





import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
	
    private JedisPool pool = null;
	Logger logger=Logger.getLogger(RedisClient.class);

    public RedisClient(){

    }
    public static void main(String args[]){
    	RedisClient client = new RedisClient();
    	client.getPool();
    	client.del(RedisCacheKeys.idAndVehicleCache);
    	client.del(RedisCacheKeys.vehicleIdAndBoxCache);
    	System.out.println(client.hlen(RedisCacheKeys.idAndVehicleCache));
    	//client.getPool();
//    	for(int i=0;i<4;i++){
//    		Thread th = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					for(int i=0;i<1000;i++){
//						System.out.println("线程"+Thread.currentThread().getId()+":begin:"+new Date().toLocaleString());
//						client.sadd("test".getBytes(), ("test"+i).getBytes());
//						System.out.println("线程"+Thread.currentThread().getId()+":runing"+new Date().toLocaleString());
//					}
//				}
//			});
//    		th.start();
//    	}
//    	Vehicle vehicle = new Vehicle();
//    	vehicle.setCompanyId(1001);
//    	System.out.println(client.hlen(RedisCacheKeys.idAndVehicleCache));
//    	List<String> list = Rerun.readLog("E:\\reparse\\cl.txt");
//    	for(String id : list){
//    		client.hdel(RedisCacheKeys.idAndVehicleCache,id);
//    		client.hdel(RedisCacheKeys.vehicleIdAndBoxCache,id);
//    	}
//    	Vehicle vehicle = SerializeUtil.unserialize(client.hget(RedisCacheKeys.idAndVehicleCache,"15601"));
//    	System.out.println(vehicle.getCompanyId());
    }
    /**
     * 构建redis连接池
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
    private JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxActive(5);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWait(1000*20);
            
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            //config.setTestOnBorrow(true);
//            pool = new JedisPool(config, "10.11.24.14", 6379,30000,"bdc",1);
            pool = new JedisPool(config, "10.2.100.21", 6379,30000,"bdc",1);

        }
        return pool;
    }
    
    /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
    
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public String get(String key){
        String value = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }
    
    /**
     * 保存数据
     * 
     * @param key
     * @return
     */
    public void set(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key,value);
            //设置默认过期时间
            setDefaultExpire(jedis, key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 向集合中保存数据
     * 
     * @param key
     * @return
     */
    public void sadd(byte[] key,byte[] value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
            //设置默认过期时间
            setDefaultExpire(jedis, key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 获取集合列表
     * TODO：replace with the method comments here!!!
     *
     * @author tangqingsong
     * @version 
     * @param key
     * @return
     */
    public Set<byte[]> smembers(byte[] key){
        Jedis jedis = null; 
        try {
            jedis = pool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }
    
    /**
     * 向集合中保存数据
     * 
     * @param key
     * @return
     */
    public void sadd(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
            //设置默认过期时间
            setDefaultExpire(jedis, key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
  
    /**
     * 获取集合列表
     *
     * @author tangqingsong
     * @version 
     * @param key
     * @return
     */
    public Set<String> smembers(String key){
        Jedis jedis = null; 
        try {
            jedis = pool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }
    
    /**
     * 删除集合
     * @param key
     * @return
     */
    public void del(byte[] key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 删除键
     * @param key
     * @return
     */
    public void del(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 保存二进制数据
     * 
     * @param key
     * @return
     */
    public void set(byte[] key,byte[] value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
            //设置默认过期时间
            setDefaultExpire(jedis, key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 获取二进制数据
     * @param key
     * @return
     */
    public byte[] get(byte[] key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }
    
    
    /**
     * 获取整个hash对象
     * @param key
     * @return
     */
    public Map<byte[],byte[]> hgetAll(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hgetAll(key.getBytes());
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }
    
    /**
     * 获取整个hash的长度
     * @param key
     * @return
     */
    public Long hlen(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hlen(key.getBytes());
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return 0l;
    }
    
    /**
     * 保存整个hash对象
     * @param key
     * @return
     */
    public void hmset(byte[] key,Map<byte[],byte[]> value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hmset(key, value);
            //设置默认过期时间
            setDefaultExpire(jedis, key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 向hash中添加对象
     * @param key
     * @return
     */
    public void hset(String key,String field,byte[] value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hset(key.getBytes(), field.getBytes(), value);
            //设置默认过期时间
            setDefaultExpire(jedis, key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 从hash中取出对象
     * @param key
     * @return
     */
    public byte[] hget(String key,String field){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hget(key.getBytes(), field.getBytes());
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }
    
    /**
     * 从hash中删除对象
     * @param key
     * @return
     */
    public void hdel(String key,String field){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hdel(key.getBytes(), field.getBytes());
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
	public void setPool(JedisPool pool) {
		this.pool = pool;
	}
    
	/**
	 * 
	 * 设置默认过期时间
	 * @author tangqingsong
	 * @version 
	 * @param jedis
	 * @param key
	 */
	private <T>void setDefaultExpire(Jedis jedis,T key){
//		if(key instanceof String){
//			jedis.expire((String)key,GlobalConstant.CACHE_EXPIRE_TIME);
//		}else if(key instanceof byte[]){
//			jedis.expire((byte[])key,GlobalConstant.CACHE_EXPIRE_TIME);
//		}
	}
    
}