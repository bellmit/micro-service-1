package com.ms.biz.api.config;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}
}
