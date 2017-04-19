package com.system.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.comm.utils.FrameSpringBeanUtil;

public class BaseCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseCache.class);

	private RedisClient redisClient;
	
	public RedisClient getRedisClient() {
		if(redisClient == null) {
			redisClient = FrameSpringBeanUtil.getBean(RedisClient.class);
		}
		return redisClient;
	}

	/**
	 * 拼装key
	 * @param strings
	 * @return
	 */
	public String key(String ...strings) {
		StringBuilder builder = new StringBuilder();
		if(strings != null) {
			for (String string : strings) {
				builder.append(string);
			}
		}
		return builder.toString();
	}
	private String getKey(String ...strings) {
		return getRedisClient().getKey(strings);
	}
	public <T> T get(String key) {
		try {
			//LOGGER.info("redis: get");
			return getRedisClient().get(getKey(key));
		} catch (Exception e) {
			LOGGER.error("redis get 异常: " + e.getMessage());
			return null;
		}
	}

	public void set(String key, Object value) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().set(getKey(key), value);
		} catch (Exception e) {
			LOGGER.error("redis set 异常: " + e.getMessage());
		}
	}

	/**
	 * 设置值
	 * @param key
	 * @param value
	 * @param timeout	过期时间值	单位s
	 */
	public void set(String key, Object value, int timeout) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().set(getKey(key), value, timeout);
		} catch (Exception e) {
			LOGGER.error("redis set 异常: " + e.getMessage());
		}
	}

	/**
	 * 删除数据
	 * @param key
	 */
	public void delete(String key) {
		try {
			getRedisClient().delete(getKey(key));
		} catch (Exception e) {
			LOGGER.error("redis delete 异常: " + e.getMessage());
		}
	}

	/**
	 * 根据key获取list的集合
	 * @param key
	 * @return
	 */
	public List<String> lrange(String key) {
		List<String> list = lrange(getKey(key), 0, -1);
		return list == null ? new ArrayList<String>() : list;
	}
	/**
	 * 根据key获取list中指定位置的集合
	 * @param key
	 * @param start	0表示第一条
	 * @param end	-1表示最后一条
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		try {
			//LOGGER.info("redis: set");
			return getRedisClient().lrange(getKey(key), start, end);
		} catch (Exception e) {
			LOGGER.error("redis list lrange 异常: " + e.getMessage());
		}
		return null;
	}
	/**
	 * 添加值到list中
	 * @param key
	 * @param value
	 */
	public void lpush(String key, String value) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().lpush(getKey(key), value);
		} catch (Exception e) {
			LOGGER.error("redis list lpush 异常: " + e.getMessage());
		}
	}

	/**
	 * 移除key中list中value的值
	 * @param key
	 * @param value
	 */
	public void lrem(String key, String value) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().lrem(getKey(key), value);
		} catch (Exception e) {
			LOGGER.error("redis list lrem 异常: " + e.getMessage());
		}
	}
	
	/**
	 * 根据key获取map的集合
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		try {
			//LOGGER.info("redis: set");
			return getRedisClient().hgetAll(getKey(key));
		} catch (Exception e) {
			LOGGER.error("redis map hgetAll 异常: " + e.getMessage());
		}
		return null;
	}
	/**
	 * 添加值到map中
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field, String value) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().hset(getKey(key), field, value);
		} catch (Exception e) {
			LOGGER.error("redis map hset 异常: " + e.getMessage());
		}
	}
	/**
	 * 添加值到map中
	 * @param key
	 * @param hash
	 */
	public void hmset(String key, Map<String, String> hash) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().hmset(getKey(key), hash);
		} catch (Exception e) {
			LOGGER.error("redis map hset 异常: " + e.getMessage());
		}
	}

	public int getCr(String key) {
		try {
			//LOGGER.info("redis: get");
			return getRedisClient().getCr(getKey(key));
		} catch (Exception e) {
			LOGGER.error("redis get 异常: " + e.getMessage());
			return 0;
		}
	}
	/**
	 * 移除key中map中key为field的值
	 * @param key
	 * @param value
	 */
	public void hdel(String key, String... fields) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().hdel(getKey(key), fields);
		} catch (Exception e) {
			LOGGER.error("redis map hdel 异常: " + e.getMessage());
		}
	}
	/**
	 * 计算器：+1
	 * @param key
	 */
	public void incr(String key) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().incr(getKey(key));
		} catch (Exception e) {
			LOGGER.error("redis incr 异常: " + e.getMessage());
		}
	}
	/**
	 * 计算器：-1
	 * @param key
	 */
	public void decr(String key) {
		try {
			//LOGGER.info("redis: set");
			getRedisClient().decr(getKey(key));
		} catch (Exception e) {
			LOGGER.error("redis decr 异常: " + e.getMessage());
		}
	}
}