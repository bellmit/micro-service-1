package com.monitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@RestController
public class ServiceController {

	private final static Logger LOGGER = Logger.getLogger(ServiceController.class);
	@Autowired
	private EurekaClient eurekaClient;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@RequestMapping(name = "停止服务", value = "/service/shutdown")
	public ResponseFrame shutdown() {
		try {
			threadPoolTaskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					eurekaClient.shutdown();
			        System.exit(0);
				}
			});
			return new ResponseFrame(ResponseCode.SUCC);
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	/*@RequestMapping(name = "启动服务", value = "/service/startup")
	public ResponseFrame startup() {
		try {
			threadPoolTaskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					eurekaClient.getin();
			        //System.exit(0);
				}
			});
			return new ResponseFrame(ResponseCode.SUCC);
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}*/
}