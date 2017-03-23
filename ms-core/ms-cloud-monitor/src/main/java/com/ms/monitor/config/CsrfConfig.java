package com.ms.monitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.module.comm.csrf.CsrfTokenRepository;
import com.module.comm.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@Order(5)
public class CsrfConfig {

    @Autowired
    private Environment env;
	
	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}
}
