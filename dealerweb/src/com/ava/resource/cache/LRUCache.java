package com.ava.resource.cache;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**  
 *   
 * 类说明：当缓存数目不多时，才用缓存计数的传统LRU算法  
 * @param <K>  
 * @param <V>  
 */   
public class LRUCache<K, V> implements Serializable {   
   
	protected LRULinkedHashMap<K, ValueEntry> map;   
    
    /**	Map的最大数量，默认100	*/
    private int maxCapacity = 100;   
   
    /**	Map中ValueEntry的最大生存时间，单位毫秒，默认2*60分钟	*/
    private long maxLifeTime = 1000 * 60 * 60 * 2;
   
    public LRUCache() {   
        this(100);
    }   
   
    public LRUCache(int capacity) {   
        if (capacity <= 0)   
            throw new RuntimeException("缓存容量不得小于等于0");   
        this.maxCapacity = capacity;   
        this.map = new LRULinkedHashMap<K, ValueEntry>(maxCapacity);   
    }   
   
    public boolean ContainsKey(K key) {  
        return this.map.containsKey(key);  
    }   
   
    public V put(K key, V value) {   
        ValueEntry valueEntry = map.put(key, new ValueEntry(value));   
        if (valueEntry != null)   
            return valueEntry.value;   
        else   
            return null;   
    }   
      
    public V get(K key) {   
        V value = null;   
        ValueEntry valueEntry = map.get(key);   
        if (valueEntry != null) {   
        	long lifeTime = System.currentTimeMillis() - valueEntry.putTime.get();
        	if ( lifeTime > maxLifeTime ){
        		//ValueEntry超过最大生存时间，则移除，返回null
        		map.remove(key);
        		return null;
        	}else{
        		value = valueEntry.value;  
        	}
        }   
        return value;   
    }   

    public V remove(K key) {   
        ValueEntry valueEntry = map.remove(key);   
        if (valueEntry != null)   
            return valueEntry.value;   
        else   
            return null;   
    }  
    
    public int size() {   
    	return map.size();
    }
    
    public void clear() { 
    	map.clear();
    }

	public long getMaxLifeTime() {
		return maxLifeTime;
	}

	public void setMaxLifeTime(long maxLifeTime) {
		this.maxLifeTime = maxLifeTime;
	}   
   
	
    class ValueEntry implements Serializable {   

		private V value;   
   
        private AtomicLong putTime;   
   
        public ValueEntry(V value) {   
            this.value = value;   
            putTime = new AtomicLong(System.currentTimeMillis());   
        }   
              
    }

}   

