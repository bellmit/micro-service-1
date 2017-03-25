package com.module.comm.utils;

import java.io.IOException;
import java.util.Map;

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
public class ClientUtil {

	/**
	 * 请求客户端的api
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static ResponseFrame post(String clientId, String clientToken, String ip, Integer port, String url, Map<String, Object> params) {
		String time = String.valueOf(System.currentTimeMillis());
		String sercret = clientToken;
		params.put("clientId", clientId);
		params.put("time", time);
		params.put("sign", AuthUtil.auth(clientId, time, sercret));
		try {
			String result = FrameHttpUtil.post("http://" + ip + ":" + port + url, params);
			return FrameJsonUtil.toObject(result, ResponseFrame.class);
		} catch (IOException e) {
			return null;
		}
	}
}
