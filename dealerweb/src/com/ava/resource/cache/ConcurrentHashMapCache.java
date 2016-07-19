package com.ava.resource.cache;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("serial")
public class ConcurrentHashMapCache<K, V> implements Serializable {

	private ConcurrentHashMap<K, ValueEntry> map;

	/** Map中ValueEntry的最大生存时间，单位毫秒，默认24小时，设置为<=0代表永不超时 */
	private long maxLifeTime = 1000 * 60 * 60 * 24;

	private final static int CLEAR_EXPIRE_COUNTER_THRESHOLD = 10000;

	private int clearExpireCounter = 0;
	private boolean clearExpire = false;

	public ConcurrentHashMapCache() {
		this(false);
	}

	public ConcurrentHashMapCache(boolean clearExpire) {
		this.clearExpire = clearExpire;
		this.map = new ConcurrentHashMap<K, ValueEntry>();
	}

	public boolean ContainsKey(K key) {
		return this.map.containsKey(key);
	}

	public V put(K key, V value) {
		// 是否需要清空过期数据
		if (clearExpire) {
			// 每put到CLEAR_EXPIRE_COUNTER_THRESHOLD次时，执行清空操作，然后把clearExpireCounter值归零
			if (clearExpireCounter >= CLEAR_EXPIRE_COUNTER_THRESHOLD) {
				clearExpire();
				clearExpireCounter = 0;
			}
			clearExpireCounter++;
		}

		ValueEntry valueEntry = map.put(key, new ValueEntry(value));
		if (valueEntry != null)
			return valueEntry.value;
		else
			return null;
	}

	/**
	 * 直接获取ValueEntry对象，一般用来防止系统重复读取数据库不存在数据的场景，比如车辆最后GPS位置、GPS纠偏数据等
	 * 如果数据过期会删掉该数据
	 */
	public ValueEntry getEntry(K key) {
		ValueEntry valueEntry = map.get(key);
		if (valueEntry != null) {
			if (maxLifeTime <= 0) {
				// 永不超时
			} else {
				long lifeTime = System.currentTimeMillis() - valueEntry.putTime.get();
				if (lifeTime > maxLifeTime) {
					// ValueEntry超过最大生存时间，则移除，返回null
					map.remove(key);
					valueEntry = null;
				}
			}
		}
		return valueEntry;
	}

	/** 如果数据过期会删掉该数据 */
	public V get(K key) {
		ValueEntry valueEntry = getEntry(key);
		if (valueEntry != null) {
			return valueEntry.value;
		} else {
			return null;
		}
	}

	public V remove(K key) {
		if (key == null){
			return null;
		}
		ValueEntry valueEntry = map.remove(key);
		if (valueEntry != null) {
			return valueEntry.value;
		} else {
			return null;
		}
	}

	public int size() {
		return map.size();
	}

	public void clear() {
		map.clear();
	}

	/**
	 * 清空缓存中过期的数据
	 */
	private void clearExpire() {
		Iterator<K> it = map.keySet().iterator();
		while (it.hasNext()) {
			K key = it.next();
			getEntry(key);
		}
	}

	public long getMaxLifeTime() {
		return maxLifeTime;
	}

	/** 设置Map中ValueEntry的最大生存时间，单位毫秒，默认24小时，设置为<=0时代表永不超时 **/
	public void setMaxLifeTime(long maxLifeTime) {
		this.maxLifeTime = maxLifeTime;
	}

	protected class ValueEntry implements Serializable {

		private V value;

		private AtomicLong putTime;

		public ValueEntry(V value) {
			this.value = value;
			putTime = new AtomicLong(System.currentTimeMillis());
		}

		public V getValue() {
			return value;
		}

	}
}
