package com.module.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import com.module.api.service.ApiServiceService;

@Component
public class ApiServiceServiceImpl implements ApiServiceService {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Override
	public List<ServiceInstance> serviceList(String serviceId) {
		List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
		return list;
	}
	
	@Override
	public List<ServiceInstance> services() {
		List<ServiceInstance> data = new ArrayList<ServiceInstance>();
		List<String> sList = discoveryClient.getServices();
		for (String serviceId : sList) {
			List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
			data.addAll(list);
		}
		return data;
	}
	
	@Override
	public List<String> serviceNames() {
		List<String> data = discoveryClient.getServices();
		return data;
	}
}