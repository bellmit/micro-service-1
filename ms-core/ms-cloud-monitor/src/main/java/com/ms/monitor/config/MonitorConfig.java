package com.ms.monitor.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.module.comm.constants.ConfigCons;

@Configuration // 该注解类似于spring配置文件
public class MonitorConfig {

	//private final Logger LOGGER = LoggerFactory.getLogger(MonitorConfig.class);

	@Autowired
	private Environment env;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ConfigCons configCons() {
		ConfigCons cons = new ConfigCons();
		ConfigCons.projectName = env.getProperty("project.monitor.name");
		ConfigCons.clientId = env.getProperty("client.monitor.id");
		ConfigCons.sercret = env.getProperty("client.monitor.token");
		ConfigCons.taskServerHost = env.getProperty("client.task.server.host");
		ConfigCons.logServerHost = env.getProperty("client.log.server.host");
		//ConfigCons.webroot = env.getProperty("project.webroot");
		return cons;
	}
}