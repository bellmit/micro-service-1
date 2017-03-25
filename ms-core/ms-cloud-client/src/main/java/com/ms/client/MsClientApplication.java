package com.ms.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.client.rel.task.ReleaseTask;
import com.ms.client.interceptor.AuthSecurityInterceptor;
import com.system.comm.utils.FrameSpringBeanUtil;

//通过该注解，实现服务发现，注册
@EnableEurekaClient
@SpringBootApplication
@EnableAutoConfiguration(exclude={
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		//HibernateJpaAutoConfiguration.class, //（如果使用Hibernate时，需要加）
})
@Configuration
@ComponentScan("com.*")
//启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableTransactionManagement(order = 2)
//@ImportResource(locations={"classpath:applicationContext.xml"})
public class MsClientApplication extends WebMvcConfigurerAdapter {

	
	public static void main(String[] args) {
		SpringApplication.run(MsClientApplication.class, args);
		
		ThreadPoolTaskExecutor threadPoolTaskExecutor = FrameSpringBeanUtil.getBean(ThreadPoolTaskExecutor.class);
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				new ReleaseTask().run();
			}
		});
		
	}
	
	/**
	 * 添加拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthSecurityInterceptor())
		.addPathPatterns("/*", "/*/*");
	}
}