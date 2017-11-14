package com.system.cache.test;

import java.util.List;
import java.util.Set;

import com.system.cache.redis.RedisClient;
import com.system.comm.utils.FrameStringUtil;

public class RedisTest {


	public static RedisClient init() {
		RedisClient redis = new RedisClient();
		String hostString = "10.201.224.175:6379";
		List<String> hosts = FrameStringUtil.toArray(hostString, ";");
		RedisClient.setHosts(hosts);
		RedisClient.setPassword("");
		RedisClient.setMaxTotal(500);
		RedisClient.setMaxIdle(50);
		RedisClient.setMaxWaitMillis(2000);
		RedisClient.setKeyPrefix("test_");
		return redis;
	}

	public static void main(String[] args) {
		RedisClient client = init();
		Set<String> ks = client.keys("momapi-pi_nm*");
		for (String key : ks) {
			System.out.println("key: " + key);
		}
		System.out.println(ks.size());

		client.deleteBatch("momapi-pi_nm*");
	}
}
