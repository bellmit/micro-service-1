package com.ms.zuul.filter.core;

import java.util.List;

import com.system.cache.redis.BaseCache;
import com.system.cache.redis.RedisClient;
import com.system.comm.utils.FrameStringUtil;
import com.system.comm.utils.FrameTimeUtil;

/**
 * 限制器
 * @author yuejing
 * @date 2017年11月27日 下午2:39:53
 */
public class RateLimiter {
	
	private static BaseCache baseCache;
	//应用编码
	private String appCode;
	//ip
	private String ip;
	
	/**
	 * 根据应用code限流
	 * @param appCode
	 */
	public RateLimiter(String appCode) {
		this(appCode, null);
	}
	/**
	 * 根据应用code和ip做限流
	 * @param appCode
	 * @param ip
	 */
	public RateLimiter(String appCode, String ip) {
		super();
		this.appCode = (appCode == null ? "" : appCode);
		this.ip = (ip == null ? "" : ip);
	}
	private BaseCache getCache() {
		if(baseCache == null) {
			return new BaseCache();
		}
		return baseCache;
	}
	/**
	 * 提供给test类调度
	 * @param redis
	 */
	public void setCache(RedisClient redis) {
		baseCache = new BaseCache();
		baseCache.setRedisClient(redis);
	}
	
	/**
	 * 根据应用code和ip来处理每秒的限流
	 * @param limit	每秒最大并发数
	 * @return
	 * @throws Exception
	 */
	public boolean acquireSecond(Long limit) throws Exception {
		String luaScript = "local key = KEYS[1]	\n" +		//限流KEY（一秒一个）
				"local limit = tonumber(ARGV[1])	\n" +	//限流大小
				"local current = tonumber(redis.call('get', key) or \"0\")	\n" +
				"if current + 1 > limit then	\n" +		//如果超出限流大小
				"return 0	\n" +
				"else	\n" +								//请求数+1，并设置2秒过期
				"redis.call(\"INCRBY\", key,\"1\")	\n" +
				"redis.call(\"expire\", key,\"2\")	\n" +
				"return 1	\n" +
				"end";//Files.toString(new File("limit.lua"), Charset.defaultCharset());
		//Jedis jedis = new Jedis("10.201.224.175", 6379);
		String key = appCode + ip + ":" + System.currentTimeMillis()/1000;
		//限流大小
		return (Long)getCache().eval(luaScript, key, String.valueOf(limit)) == 1;
	}
	/**
	 * 根据应用code和ip来处理每小时的限流
	 * @param limit	每小时最大并发数
	 * @return
	 * @throws Exception
	 */
	public boolean acquireHour(Long limit) throws Exception {
		String luaScript = "local key = KEYS[1]	\n" +		//限流KEY（一小时一个）
				"local limit = tonumber(ARGV[1])	\n" +	//限流大小
				"local current = tonumber(redis.call('get', key) or \"0\")\n" +
				"if current + 1 > limit then	\n" +		//如果超出限流大小
				"return 0	\n" +
				"else	\n" +								//请求数+1，并设置1小时过期
				"redis.call(\"INCRBY\", key,\"1\")	\n" +
				"redis.call(\"expire\", key,\"3600\")	\n" +
				"return 1	\n" +
				"end";//Files.toString(new File("limit.lua"), Charset.defaultCharset());
		//Jedis jedis = new Jedis("10.201.224.175", 6379);
		String key = appCode + ip + ":" + FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYYMMDDHH);
		//限流大小
		return (Long)getCache().eval(luaScript, key, String.valueOf(limit)) == 1;
	}

	public static void main(String[] args) throws Exception {
		RedisClient redis = new RedisClient();
		String hostString = "10.201.224.175:6379";
		List<String> hosts = FrameStringUtil.toArray(hostString, ";");
		RedisClient.setHosts(hosts);
		RedisClient.setPassword("");
		RedisClient.setMaxTotal(500);
		RedisClient.setMaxIdle(50);
		RedisClient.setMaxWaitMillis(2000);
		RedisClient.setKeyPrefix("msm-");
		
		long limit = 5;
		String appCode = "test";
		String ip = "127.0.0.1";
		RateLimiter rate = new RateLimiter(appCode, ip);
		rate.setCache(redis);
		System.out.println("================= 每秒钟 ==================");
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println(rate.acquireSecond(limit));
		System.out.println("================= 每小时 ==================");
		limit = 50;
		System.out.println(rate.acquireHour(limit));
		System.out.println(rate.acquireHour(limit));
	}
}
