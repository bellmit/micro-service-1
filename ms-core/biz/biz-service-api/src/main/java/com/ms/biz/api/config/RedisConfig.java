package com.ms.biz.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedisConfig {

    @Autowired
    private Environment env;
	
	/*@Bean
	public RedisClient redisClient() {
		RedisClient redis = new RedisClient();
		String hostString = env.getProperty("redis.hosts");
		List<String> hosts = FrameStringUtil.toArray(hostString, ";");
		RedisClient.setHosts(hosts);
		RedisClient.setPassword(env.getProperty("redis.password"));
		RedisClient.setMaxTotal(env.getProperty("redis.maxTotal", Integer.class));
		RedisClient.setMaxIdle(env.getProperty("redis.maxIdle", Integer.class));
		RedisClient.setMaxWaitMillis(env.getProperty("redis.maxWaitMillis", Integer.class));
		RedisClient.setKeyPrefix(env.getProperty("redis.keyPrefix"));
		return redis;
	}*/
}
