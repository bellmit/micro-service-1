package com.module.api.service;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

public interface ApiServiceService {

	/**
	 * 根据服务ID获取列表
	 * @param serviceId
	 * @return
	 */
	public List<ServiceInstance> serviceList(String serviceId);

	/**
	 * 获取所有的服务
	 * @return
	 */
	public List<ServiceInstance> services();

	/**
	 * 获取所有服务的名称
	 * @return
	 */
	public List<String> serviceNames();
}
