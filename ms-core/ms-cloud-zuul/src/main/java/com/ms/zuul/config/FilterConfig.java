package com.ms.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ms.zuul.filter.AccessFilter;
import com.ms.zuul.filter.ErrorExtFilter;
import com.ms.zuul.filter.ErrorFilter;

@Configuration
public class FilterConfig {

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}
	
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	
	@Bean
	public ErrorExtFilter errorExtFilter() {
		return new ErrorExtFilter();
	}
}