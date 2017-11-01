package com.monitor.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ms.env.EnvUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class RestUtil {

	@Autowired
	private LoadBalancerClient loadBalancer;
	@Autowired
	private RestTemplate restTemplate;

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
	 * 发送请求[如果是开发环境，需要配置dev.host.服务的serviceId=http://xxx.xxx.xx.xx:xx]
	 * @param serviceId
	 * @param url
	 * @param params
	 * @param fallbackUri
	 * @return
	 */
	public ResponseFrame request(String serviceId, String url, Map<String, Object> params, String fallbackUri) {
		//判断是否为开发环境
		String baseUrl = null;
		if(EnvUtil.projectModeIsDev()) {
			baseUrl = EnvUtil.get("dev.host." + serviceId);
		} else {
			baseUrl = getRestUrl(serviceId, fallbackUri);
		}
		try {
			HttpHeaders headers = new HttpHeaders();
			//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
			MultiValueMap<String, String> paramsMvm = new LinkedMultiValueMap<String, String>();
			Iterator<Entry<String, Object>> entryKeyIterator = params.entrySet().iterator();
			while (entryKeyIterator.hasNext()) {
				Entry<String, Object> e = entryKeyIterator.next();
				String value = null;
				if(e.getValue() != null) {
					value = e.getValue().toString();
				}
				paramsMvm.add(e.getKey(), value);
			}
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(paramsMvm, headers);
			String result = restTemplate.postForObject(baseUrl + url, requestEntity, String.class);
			//String result = FrameHttpUtil.post(baseUrl + url, params);
			return FrameJsonUtil.toObject(result, ResponseFrame.class);
		} catch (Exception e) {
			ResponseFrame frame = new ResponseFrame(ResponseCode.SERVER_ERROR);
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("requestParams", params);
			body.put("exceptionInfo", e.getMessage());
			frame.setBody(body);
			return frame;
		}
	}
}