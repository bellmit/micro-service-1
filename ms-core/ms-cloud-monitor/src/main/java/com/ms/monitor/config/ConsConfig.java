package com.ms.monitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.module.comm.constants.ConfigCons;

@Configuration
public class ConsConfig {

    @Autowired
    private Environment env;
	
	@Bean
	public ConfigCons configCons() {
		ConfigCons cons = new ConfigCons();
		ConfigCons.projectName = env.getProperty("project.monitor.name");
		ConfigCons.clientId = env.getProperty("client.monitor.id");
		ConfigCons.sercret = env.getProperty("client.monitor.token");
		ConfigCons.taskServerHost = env.getProperty("client.task.server.host");
		//ConfigCons.webroot = env.getProperty("project.webroot");
		return cons;
	}
}
