package com.ms.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.client.ConfigCons;
import com.system.comm.utils.FrameStringUtil;

@Configuration
public class ConsConfig {

    @Autowired
    private Environment env;
	
	@Bean
	public ConfigCons configCons() {
		ConfigCons cons = new ConfigCons();
		ConfigCons.versionDir = env.getProperty("version.dir");
		ConfigCons.clientId = env.getProperty("client.id");
		ConfigCons.clientToken = env.getProperty("client.token");
		ConfigCons.clientServerHost = env.getProperty("client.server.host");
		String clientShellFailTime = env.getProperty("client.shell.fail.time");
		ConfigCons.clientShellFailTime = FrameStringUtil.isEmpty(clientShellFailTime) ? 5 : Integer.parseInt(clientShellFailTime);
		return cons;
	}
}
