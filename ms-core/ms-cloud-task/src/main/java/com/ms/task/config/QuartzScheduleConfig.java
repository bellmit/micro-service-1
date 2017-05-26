package com.ms.task.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定时任务的配置
 * @author yuejing
 * @date 2017年5月26日 上午9:44:52
 */
@Configuration
public class QuartzScheduleConfig {

    @Autowired
    private Environment env;

    @Bean
    @Lazy(false)
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        // 延时启动
        factory.setStartupDelay(20);
        // 加载quartz数据源配置
        //factory.setQuartzProperties(quartzProperties());
        // 自定义Job Factory，用于Spring注入
        //factory.setJobFactory(myJobFactory);
        return factory;
    }
}