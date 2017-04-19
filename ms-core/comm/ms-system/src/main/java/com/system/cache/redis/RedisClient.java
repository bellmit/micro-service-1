package com.system.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.system.comm.utils.FrameStringUtil;
import com.system.comm.utils.FrameTimeUtil;

/**
 * Redis客户端<br>
 * 参考地址：https://camel.apache.org/maven/camel-2.11.0/camel-spring-redis/apidocs/src-html/org/apache/camel/component/redis/RedisClient.html
 * <br>
 * 可以考虑：2级缓存 http://www.oschina.net/p/j2cache
 * @author 岳静
 * @date 2016年5月6日 上午10:15:47 
 * @version V1.0
 */
public class RedisClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);
	//判断是否可用
	private static int redisFailNum = 0;
	//失败达到10次，停止使用redis
	private static final int REDIS_STOP_NUM = 10;
	//redis停止使用的时间
	private static Date redisStopTime = null;
	//redis停止后，在3分钟内不使用
	private static final long REDIS_STOP_DIFF_TIME = 3 * 60;

	/** Redis的key前缀 */
	private static String keyPrefix = null;
	//hosts
	private static List<String> hosts;
	//maxIdle
	private static int maxIdle;
	//maxTotal
	private static int maxTotal;
	//maxWaitMillis
	private static int maxWaitMillis;
	//password
	private static String password;

	private static ShardedJedisPool pool;

	public RedisClient() {
	}

	/**
	 * 建立连接池 真实环境，一般把配置参数缺抽取出来。
	 */
	private static void createJedisPool() {
		// 生成多机连接信息列表
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (String hostInfo : hosts) {
			String[] hostArr = hostInfo.split(":");
			JedisShardInfo jsi = new JedisShardInfo(hostArr[0], Integer.parseInt(hostArr[1]));
			if(FrameStringUtil.isNotEmpty(password)) {
				jsi.setPassword(password);
			}
			shards.add(jsi);
		}
		//shards.add( new JedisShardInfo("192.168.56.102", 6379) );

		// 生成连接池配置信息
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(maxWaitMillis);

		// 在应用初始化的时候生成连接池
		pool = new ShardedJedisPool(config, shards);
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (pool == null) {
			createJedisPool();
		}
	}

	/**
	 * 获取一个jedis 对象
	 * 
	 * @return
	 */
	public static ShardedJedis getJedis() {
		if (pool == null) {
			poolInit();
		}
		return pool.getResource();
	}


	public static void setKeyPrefix(String keyPrefix) {
		RedisClient.keyPrefix = keyPrefix;
	}

	/**
	 * 获取key
	 * @param strings
	 * @return
	 */
	public String getKey(String ...strings) {
		StringBuilder builder = new StringBuilder(keyPrefix);
		if(strings != null) {
			for (String string : strings) {
				builder.append(string);
			}
		}
		return builder.toString();
	}

	/**
	 * 归还一个连接
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public void returnRes(ShardedJedis jedis) {
		if(jedis != null) {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 获取值
	 * @param t
	 * @return
	 */
	public <T> T get(String key) {
		if(!isUse()) {
			return null;
		}
		try {
			ShardedJedis jedis = getJedis();
			byte[] bytes = jedis.get(key.getBytes());
			@SuppressWarnings("unchecked")
			T value = (T) unserialize(bytes);
			returnRes(jedis);
			return value;
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 模糊匹配key
	 * @param key:  传入：abc*（代表匹配abc开头的key）
	 * @return
	 */
	public Set<String> keys(String key) {
		if(!isUse()) {
			return null;
		}
		try {
			ShardedJedis jedis = getJedis();
			Collection<Jedis> list = jedis.getAllShards();
			Set<String> keySets = new HashSet<String>();
			for (Jedis j : list) {
				keySets.addAll(j.keys(key));
			}
			returnRes(jedis);
			return keySets;
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
		return null;
	}

	/**
	 * 设置值
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.set(key.getBytes(), serialize(value));
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}

	/**
	 * 设置值
	 * @param key
	 * @param timeout	过期时间值(单位秒)
	 * @param value
	 */
	public void set(String key, Object value, int timeout) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.setex(key.getBytes(), timeout, serialize(value));
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}

	/**
	 * 删除数据
	 * @param key
	 */
	public void delete(String key) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.del(key.getBytes());
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}
	/**
	 * list: 根据key获取list中指定位置的集合
	 * @param key
	 * @param start	0表示第一条
	 * @param end	-1表示最后一条
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		if(!isUse()) {
			return null;
		}
		try {
			ShardedJedis jedis = getJedis();
			List<byte[]> list = jedis.lrange(key.getBytes(), start, end);
			List<String> resList = new ArrayList<String>();
			for (byte[] bytes : list) {
				try {
					String value = (String) unserialize(bytes);
					resList.add(value);
				} catch (Exception e) {
					LOGGER.error("转换异常: " + e.getMessage());
				}
			}
			returnRes(jedis);
			return resList;
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
		return null;
	}

	/**
	 * list: 添加值到list中
	 * @param key
	 * @param value
	 */
	public void lpush(String key, String value) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.lpush(key.getBytes(), serialize(value));
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}
	/**
	 * list: 移除key中list中value的值
	 * @param key
	 * @param value
	 */
	public void lrem(String key, String value) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.lrem(key.getBytes(), 0, serialize(value));
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}
	
	/**
	 * hash: 获取map的所有信息
	 * @param key
	 * @param start	0表示第一条
	 * @param end	-1表示最后一条
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		if(!isUse()) {
			return null;
		}
		try {
			ShardedJedis jedis = getJedis();
			Map<String, String> map = jedis.hgetAll(key);
			returnRes(jedis);
			return map;
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
		return null;
	}
	/**
	 * hash: 移除key中map中value的值
	 * @param key
	 * @param value
	 */
	public void hdel(String key, String... fields) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.hdel(key, fields);
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}

	/**
	 * hash: 添加值到map中
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field, String value) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.hset(key, field, value);
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}

	/**
	 * hash: key添加map
	 * @param key
	 * @param hash
	 */
	public void hmset(String key, Map<String, String> hash) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.hmset(key, hash);
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}

	/**
	 * 获取计数器的值
	 * @param t
	 * @return
	 */
	public int getCr(String key) {
		if(!isUse()) {
			return 0;
		}
		try {
			ShardedJedis jedis = getJedis();
			String cr = jedis.get(key);
			returnRes(jedis);
			return FrameStringUtil.isEmpty(cr) ? 0 : Integer.valueOf(cr);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
		return 0;
	}
	
	/**
	 * 计算器：+1
	 * @param key
	 */
	public void incr(String key) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.incr(key.getBytes());
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}
	/**
	 * 计算器：-1
	 * @param key
	 */
	public void decr(String key) {
		if(!isUse()) {
			return;
		}
		try {
			ShardedJedis jedis = getJedis();
			jedis.decr(key.getBytes());
			returnRes(jedis);
		} catch (JedisConnectionException e) {
			LOGGER.error("redis 链接异常: " + e.getMessage());
			counter();
		} catch (Exception e) {
			LOGGER.error("redis 异常: " + e.getMessage());
		}
	}

	/**
	 * 序列号
	 * @param object
	 * @return
	 */
	private static byte[] serialize(Object object) {
		if(object == null) {
			return null;
		}
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			//序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			LOGGER.error("序列化异常: " + e.getMessage());
		}
		return null;
	}

	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	private static Object unserialize( byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		ByteArrayInputStream bais = null;
		try {
			//反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			LOGGER.error("反序列化异常: " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 判断是否使用redis
	 * @return
	 */
	private boolean isUse() {
		if(redisStopTime == null) {
			return true;
		}
		long diff = FrameTimeUtil.getDateDiff(redisStopTime, FrameTimeUtil.getTime(), 0);
		if(diff < REDIS_STOP_DIFF_TIME) {
			return false;
		}
		redisStopTime = null;
		redisFailNum = 0;
		return true;
	}
	
	/**
	 * 计数
	 */
	private void counter() {
		if(REDIS_STOP_NUM < redisFailNum) {
			redisStopTime = FrameTimeUtil.getTime();
		}
		LOGGER.error("redis异常次数: " + redisFailNum);
		redisFailNum ++;
	}

	public static void setHosts(List<String> hosts) {
		RedisClient.hosts = hosts;
	}
	public static void setMaxIdle(int maxIdle) {
		RedisClient.maxIdle = maxIdle;
	}
	public static void setMaxTotal(int maxTotal) {
		RedisClient.maxTotal = maxTotal;
	}
	public static void setMaxWaitMillis(int maxWaitMillis) {
		RedisClient.maxWaitMillis = maxWaitMillis;
	}
	public static void setPassword(String password) {
		RedisClient.password = password;
	}
}