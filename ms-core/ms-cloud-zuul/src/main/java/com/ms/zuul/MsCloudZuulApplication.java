package com.ms.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@EnableEurekaClient
@ComponentScan("com.*")
@SpringBootApplication
public class MsCloudZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCloudZuulApplication.class, args);
	}
}
