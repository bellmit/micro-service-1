package com.ms.config.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ms.config.constants.ConfigCons;

@Configuration
public class ConsConfig {

    @Autowired
    private Environment env;
	
	@Bean
	public ConfigCons configCons() {
		ConfigCons cons = new ConfigCons();
		ConfigCons.clientId = env.getProperty("client.id");
		ConfigCons.clientToken = env.getProperty("client.token");
		ConfigCons.clientServerHost = env.getProperty("client.server.host");
		ConfigCons.configSearchLocations = env.getProperty("spring.cloud.config.server.native.searchLocations");
		return cons;
	}
}
