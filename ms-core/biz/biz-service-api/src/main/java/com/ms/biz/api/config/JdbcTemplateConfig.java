package com.ms.biz.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.system.ds.DynamicDataSource;

@Configuration // 该注解类似于spring配置文件
public class JdbcTemplateConfig {
	
    /**
     * 配置jdbc模板
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DynamicDataSource dataSource) {
    	return new JdbcTemplate(dataSource);
    }
}
