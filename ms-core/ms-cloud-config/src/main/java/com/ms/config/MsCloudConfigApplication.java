package com.ms.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ms.config.task.UpdateConfigTask;
import com.system.comm.utils.FrameSpringBeanUtil;

/**
 * 启动spring的config
 * @author yuejing
 * @date 2017年2月16日 下午6:20:15
 */
@EnableConfigServer
@SpringBootApplication
public class MsCloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCloudConfigApplication.class, args);
		
		ThreadPoolTaskExecutor threadPoolTaskExecutor = FrameSpringBeanUtil.getBean(ThreadPoolTaskExecutor.class);
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				new UpdateConfigTask().run();
			}
		});
	}
	
}
