package com.module.admin.tts.utils;

import java.io.IOException;
import java.util.Map;

import com.module.comm.constants.ConfigCons;
import com.monitor.rest.RestUtil;
import com.ms.env.Env;
import com.ms.env.EnvUtil;
import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.handle.model.ResponseFrame;

public class TaskUtil {

	public static ResponseFrame post(String url, Map<String, Object> paramsMap) throws IOException {
		String time = String.valueOf(System.currentTimeMillis());
		paramsMap.put("clientId", ConfigCons.clientId);
		paramsMap.put("time", time);
		paramsMap.put("sign", AuthUtil.auth(ConfigCons.clientId, time, ConfigCons.sercret));
		//String result = FrameHttpUtil.post(ConfigCons.taskServerHost + url, paramsMap);
		
		RestUtil restUtil = FrameSpringBeanUtil.getBean(RestUtil.class);
		String fallbackUri = ConfigCons.taskServerHost;
		String serviceId = EnvUtil.get(Env.CLIENT_TASK_SERVER_SERVICEID);
		return restUtil.request(serviceId, url, paramsMap, fallbackUri );
		//return FrameJsonUtil.toObject(result, ResponseFrame.class);
	}
}
