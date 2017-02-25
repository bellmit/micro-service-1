package com.ms.biz.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan("com.*")
@SpringBootApplication
public class BizServiceThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizServiceThymeleafApplication.class, args);
	}
}
