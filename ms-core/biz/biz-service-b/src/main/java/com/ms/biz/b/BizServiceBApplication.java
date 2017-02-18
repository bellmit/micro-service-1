package com.ms.biz.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BizServiceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizServiceBApplication.class, args);
	}
}
