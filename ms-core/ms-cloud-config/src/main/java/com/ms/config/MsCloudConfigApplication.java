package com.ms.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ms.config.task.UpdateConfigTask;
import com.system.comm.utils.FrameSpringBeanUtil;

/**
 * 启动spring的config
 * @author yuejing
 * @date 2017年2月16日 下午6:20:15
 */
//通过该注解，实现服务发现，注册
@EnableEurekaClient
@EnableConfigServer
@ComponentScan("com.*")
@EnableTransactionManagement(order = 2)
@SpringBootApplication
public class MsCloudConfigApplication extends WebMvcConfigurerAdapter {

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
