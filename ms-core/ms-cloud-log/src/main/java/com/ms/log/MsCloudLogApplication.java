package com.ms.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import zipkin.server.EnableZipkinServer;

import com.ms.log.interceptor.AuthSecurityInterceptor;

@EnableEurekaClient
@EnableZipkinServer
@ComponentScan("com.*")
@EnableAutoConfiguration(exclude={
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		//HibernateJpaAutoConfiguration.class, //（如果使用Hibernate时，需要加）
})
@EnableTransactionManagement(order = 2)
@SpringBootApplication
//@EnableZipkinStreamServer
public class MsCloudLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCloudLogApplication.class, args);
	}
	
	/*@Bean
	public MySQLStorage mySQLStorage(DataSource datasource) {
		return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
	}*/
	
	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		WebMvcConfigurerAdapter wmca = new WebMvcConfigurerAdapter() {

			/**
			 * 添加拦截器
			 */
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new AuthSecurityInterceptor())
				.addPathPatterns("/traces/*", "/api/log/**");
				//.addPathPatterns("/api/log/**");
				//.excludePathPatterns("/service/*");
			}
		};
		return wmca;
	}
}
