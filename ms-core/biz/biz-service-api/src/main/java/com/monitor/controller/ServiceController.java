package com.monitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 部门信息
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class ServiceController {

	private final static Logger LOGGER = Logger.getLogger(ServiceController.class);
	@Autowired
	private EurekaClient eurekaClient;

	@RequestMapping(name = "停止服务", value = "/service/shutdown")
	public ResponseFrame shutdown() {
		try {
			eurekaClient.shutdown();
	        System.exit(0);
			return new ResponseFrame(ResponseCode.SUCC);
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

}