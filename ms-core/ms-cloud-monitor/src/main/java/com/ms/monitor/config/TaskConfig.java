package com.ms.monitor.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.module.task.MsSecretTask;
import com.module.task.PrjInfoMonitorTask;

@Configuration
public class TaskConfig {

	@Bean
	public MsSecretTask msSecretTask() {
		//更新密钥
		return new MsSecretTask().run(10, 1 * 60);
	}
	
	@Bean
	public PrjInfoMonitorTask prjInfoMonitorTask() {
		//同步项目变化和监控的任务
		return new PrjInfoMonitorTask().run(10, 8);
	}
}