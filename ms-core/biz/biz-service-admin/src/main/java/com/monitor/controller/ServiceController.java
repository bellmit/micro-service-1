package com.monitor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.api.ApiInfo;
import com.monitor.api.ApiRes;
import com.netflix.discovery.EurekaClient;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@RestController
public class ServiceController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
	@Autowired
	private EurekaClient eurekaClient;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@RequestMapping(name = "停止服务", value = "/service/shutdown")
	@ApiInfo(params = {
		}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value="")
	})
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