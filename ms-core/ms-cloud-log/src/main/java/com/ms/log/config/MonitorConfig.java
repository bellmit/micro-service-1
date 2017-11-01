package com.ms.log.config;


import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.monitor.MonitorCons;
import com.monitor.api.ApiUtil;
import com.monitor.secret.SecretUtil;
import com.system.comm.utils.FrameSpringBeanUtil;

@Configuration // 该注解类似于spring配置文件
public class MonitorConfig {

	private final Logger LOGGER = LoggerFactory.getLogger(MonitorConfig.class);

	@Autowired
	private Environment env;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public MonitorCons monitorCons() throws Exception {
		LOGGER.info("初始化Monitor的信息");
		MonitorCons.clientId = env.getProperty("client.monitor.id");
		MonitorCons.sercret = env.getProperty("client.monitor.token");
		MonitorCons.serverHost = env.getProperty("client.monitor.server.host");
		return new MonitorCons();
	}

	/**
	 * 配置API发送信息
	 */
	@Bean
	public ApiUtil monitorUtil() throws Exception {
		LOGGER.info("发送系统的API可请求的方法");
		ApiUtil.prjId = "196845682";
		ApiUtil.prjToken = "708c80644e3f868c429c24cd2cdb7c8e";
		final String appName = env.getProperty("spring.application.name");
		Executor task = FrameSpringBeanUtil.getBean("executor");
		task.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(30 * 1000);
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage());
				}
				ApiUtil.init(appName);
			}
		});
		return new ApiUtil();
	}
	
	/**
	 * 配置密钥信息
	 */
	@Bean
	public SecretUtil secretUtil() throws Exception {
		SecretUtil secretUtil = new SecretUtil();
		secretUtil.init();
		return secretUtil;
	}
}