package com.module.admin.cache.utils;

import java.io.IOException;
import java.util.Map;

import com.module.comm.constants.ConfigCons;
import com.monitor.rest.RestUtil;
import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.handle.model.ResponseFrame;

public class CacheUtil {

	public static ResponseFrame post(String serviceId, String url, Map<String, Object> paramsMap) throws IOException {
		String time = String.valueOf(System.currentTimeMillis());
		paramsMap.put("clientId", ConfigCons.clientId);
		paramsMap.put("time", time);
		paramsMap.put("sign", AuthUtil.auth(ConfigCons.clientId, time, ConfigCons.sercret));
		//String result = FrameHttpUtil.post(ConfigCons.taskServerHost + url, paramsMap);
		
		RestUtil restUtil = FrameSpringBeanUtil.getBean(RestUtil.class);
		String fallbackUri = ConfigCons.logServerHost;
		return restUtil.request(serviceId, url, paramsMap, fallbackUri);
		//return FrameJsonUtil.toObject(result, ResponseFrame.class);
	}
}
