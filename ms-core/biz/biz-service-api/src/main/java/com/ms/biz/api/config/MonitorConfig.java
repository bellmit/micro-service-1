package com.ms.biz.api.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.monitor.api.MonitorUtil;

@Configuration // 该注解类似于spring配置文件
public class MonitorConfig {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MonitorConfig.class);

    @Autowired
    private Environment env;

    /**
     * 配置事务管理器
     */
    @Bean
    public MonitorUtil monitorUtil() throws Exception {
    	LOGGER.info("发送系统的API可请求的方法");
    	MonitorUtil.clientId = env.getProperty("client.monitor.id");
    	MonitorUtil.sercret = env.getProperty("client.monitor.token");
    	MonitorUtil.serverHost = env.getProperty("client.monitor.server.host");
    	
    	MonitorUtil.prjId = "196845682";
    	MonitorUtil.prjToken = "708c80644e3f868c429c24cd2cdb7c8e";
    	String appName = env.getProperty("spring.application.name");
    	MonitorUtil.init(appName);
    	return new MonitorUtil();
    }
}
