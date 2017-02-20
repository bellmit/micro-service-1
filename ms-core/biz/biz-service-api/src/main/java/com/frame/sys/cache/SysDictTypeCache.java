package com.frame.sys.cache;

import org.springframework.stereotype.Component;

import com.system.cache.redis.BaseCache;

@Component
public class SysDictTypeCache extends BaseCache {

	//key的前缀
	private final static String PREFIX = "sys_dict_type.";
	//过期时间为300s
	private final static int FIND_EXPIRED_TIME = 10;

	private String keyGet(String id) {
		return super.key(PREFIX, "id.", id);
	}

	/*public SysDictType get(final String id) {
		SysDictType test = super.get(keyGet(id));
		if(test == null) {
			test = new Test();
			test.setId(id);
			test.setName("名称: " + id);

			super.set(keyGet(id), test, FIND_EXPIRED_TIME);
		}
		return test;
	}*/
}