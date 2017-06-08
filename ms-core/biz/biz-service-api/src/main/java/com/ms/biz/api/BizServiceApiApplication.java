package com.ms.biz.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ms.biz.api.interceptor.AuthSecurityInterceptor;
import com.system.auth.AuthUtil;
import com.system.auth.model.AuthClient;

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
public class BizServiceApiApplication extends WebMvcConfigurerAdapter {


	public static void main(String[] args) {
		SpringApplication.run(BizServiceApiApplication.class, args);

		initAuthClient();

	}

	private static void initAuthClient() {
		AuthClient client = new AuthClient("196845682", "测试调用", "http://127.0.0.1", "708c80644e3f868c429c24cd2cdb7c8e", "http://127.0.0.1/callback.htm");
		AuthUtil.addAuthClient(client);

	}

	/**
	 * 添加拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthSecurityInterceptor())
		.addPathPatterns("/**");
		//.excludePathPatterns("/service/*");
	}
}