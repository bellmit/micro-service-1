package com.ms.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import zipkin.server.EnableZipkinServer;

@EnableEurekaClient
@SpringBootApplication
@EnableZipkinServer
//@EnableZipkinStreamServer
public class MsCloudLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCloudLogApplication.class, args);
	}
	
	/*@Bean
	public MySQLStorage mySQLStorage(DataSource datasource) {
		return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
	}*/
}
