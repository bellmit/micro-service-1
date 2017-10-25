package com.ms.log.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.system.ds.DynamicDataSource;

@Configuration
public class JdbcTemplateConfig {

    /*@Autowired
    private Environment env;*/

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
