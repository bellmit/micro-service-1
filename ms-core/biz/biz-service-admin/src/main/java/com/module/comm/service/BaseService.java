package com.module.comm.service;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.module.comm.constants.ConfigCons;
import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameHttpUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseFrame;

/**
 * 牛人牛基api
 * @author 岳静
 * @date 2016年3月23日 下午5:26:23 
 * @version V1.0
 */
public class BaseService {
	
	private static final Logger LOGGER = Logger.getLogger(BaseService.class);
	
	/**
	 * 请求api
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws IOException 
	 */
	public static ResponseFrame post(String url, Map<String, Object> params) {
		String clientId = ConfigCons.adminClientId;
		String time = String.valueOf(System.currentTimeMillis());
		String sercret = ConfigCons.adminSercret;
		params.put("clientId", clientId);
		params.put("time", time);
		params.put("sign", AuthUtil.auth(clientId, time, sercret));
		try {
			String result = FrameHttpUtil.post(ConfigCons.apiBizUrl + url, params);
			return FrameJsonUtil.toObject(result, ResponseFrame.class);
		} catch (Exception e) {
			LOGGER.error("调用接口异常: " + e.getMessage(), e);
		}
		return null;
	}
}
