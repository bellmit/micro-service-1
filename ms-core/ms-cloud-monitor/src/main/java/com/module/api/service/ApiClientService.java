package com.module.api.service;

import org.springframework.stereotype.Component;

import com.system.handle.model.ResponseFrame;

@Component
public interface ApiClientService {

	/**
	 * 根据客户端编号，获取要发布的项目
	 * @param clientId
	 * @return
	 */
	//public ResponseFrame findRelease(String clientId);

	/**
	 * 修改客户端发布项目成功
	 * @param clientId
	 * @param prjId
	 * @param version 
	 * @param status
	 * @param statusMsg 
	 * @return
	 */
	public ResponseFrame updateRelease(String clientId, Integer prjId, String version, Integer status, String statusMsg);

	/**
	 * 修改客户端的活动状态
	 * @param clientId
	 * @return
	 */
	public ResponseFrame updateHeartbeat(String clientId);

}
