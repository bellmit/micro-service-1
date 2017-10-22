package com.ms.log.config;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    	return new JdbcTemplate(dataSource);
    }
}
