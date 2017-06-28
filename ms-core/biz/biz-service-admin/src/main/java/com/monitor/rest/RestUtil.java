package com.monitor.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import com.system.comm.utils.FrameHttpUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class RestUtil {

	@Autowired
	private LoadBalancerClient loadBalancer;

	public String getRestUrl(String serviceId, String fallbackUri) {
		URI uri = null;
		try {
			ServiceInstance instance = loadBalancer.choose(serviceId);
			uri = instance.getUri();
		} catch (RuntimeException e) {
			uri = URI.create(fallbackUri);
		}
		return uri.toString();
	}

	/**
	 * 发送请求
	 * @param serviceId
	 * @param url
	 * @param params
	 * @param fallbackUri
	 * @return
	 */
	public ResponseFrame request(String serviceId, String url, Map<String, Object> params, String fallbackUri) {
		String baseUrl = getRestUrl(serviceId, fallbackUri);
		try {
			String result = FrameHttpUtil.post(baseUrl + url, params);
			return FrameJsonUtil.toObject(result, ResponseFrame.class);
		} catch (IOException e) {
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}