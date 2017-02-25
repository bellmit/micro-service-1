package com.system.session;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.system.cache.redis.BaseCache;
import com.system.cache.redis.RedisClient;
import com.system.comm.utils.FrameSpringBeanUtil;

@Component
public class SessionService extends BaseCache {


	/*private static SessionService instance = null;
	public static synchronized SessionService getInstance() {
		if (instance == null) {
			instance = new SessionService();
		}
		return instance;
	}*/
	/** 
	 * shiro-redis的session对象前缀 
	 */
	private final String REDIS_SESSION_PRE = "redis_ses:";

	/*
	 * Redis 过期时间，默认为 30分钟
	 */
	private long expire = 30 * 60 * 1000;

	public void setExpire(long expire) {
		this.expire = expire;
	}

	//private JedisPoolManager redisManager;
	private RedisClient redisClient;

	public RedisClient getRedisClient() {
		if(redisClient == null) {
			redisClient = FrameSpringBeanUtil.getBean(RedisClient.class);
		}
		return redisClient;
	}

	public static SessionService getInstance() {
		return (SessionService) FrameSpringBeanUtil.getBean("sessionService");
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getSession(String id) {
		String key = this.REDIS_SESSION_PRE + id;
		Map<Object, Object> session = (Map<Object, Object>) super.get(key);
		if (session == null) {
			/*Long redisExpire = expire / 1000;
			int timeout = redisExpire.intValue();*/
			session = new HashMap<Object, Object>();
			//memCacheUtil.addData(MemCachePrdfix.CP_SESSION + id, session);
			
			//getRedisClient().set(key, session, timeout);
			saveSession(id, session);
		}
		return session;
	}

	public void saveSession(String id, Map<Object, Object> session) {
		String key = this.REDIS_SESSION_PRE + id;
		Long redisExpire = expire / 1000;
		int timeout = redisExpire.intValue();
		getRedisClient().set(key, session, timeout);
		//memCacheUtil.replaceData(MemCachePrdfix.CP_SESSION + id, session);
	}

	public void removeSession(String id) {
		//memCacheUtil.remove(MemCachePrdfix.CP_SESSION + id);
		String key = this.REDIS_SESSION_PRE + id;
		getRedisClient().delete(key);
	}

}
