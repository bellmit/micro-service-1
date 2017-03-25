package com.client.rel.utils;

import java.io.IOException;
import java.util.Map;

import com.client.ConfigCons;
import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameHttpUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseFrame;

/**
 * 请求服务端的工具类
 * @author yuejing
 * @date 2016年10月21日 下午7:57:10
 * @version V1.0.0
 */
public class ServerUtil {

	/**
	 * 请求服务端的api
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws IOException 
	 */
	public static ResponseFrame post(String url, Map<String, Object> paramsMap) throws IOException {
		String clientId = ConfigCons.clientId;
		String time = String.valueOf(System.currentTimeMillis());
		String sercret = ConfigCons.clientToken;
		paramsMap.put("clientId", clientId);
		paramsMap.put("time", time);
		paramsMap.put("sign", AuthUtil.auth(clientId, time, sercret));
		String result = FrameHttpUtil.post(ConfigCons.clientServerHost + url, paramsMap);
		return FrameJsonUtil.toObject(result, ResponseFrame.class);
	}
}
