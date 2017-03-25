package com.ms.monitor.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MultipartConfig {
	
    @Autowired
    private Environment env;

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
		factory.setMaxFileSize(env.getProperty("multipart.maxFileSize")); //KB,MB
		//设置总上传数据总大小
		factory.setMaxRequestSize(env.getProperty("multipart.maxRequestSize"));
		//Sets the directory location wherefiles will be stored.
		//factory.setLocation("路径地址");
		return factory.createMultipartConfig();
	}
}
