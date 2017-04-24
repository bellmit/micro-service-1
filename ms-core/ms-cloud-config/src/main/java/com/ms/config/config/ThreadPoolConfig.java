package com.ms.config.config;


import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration // 该注解类似于spring配置文件
public class ThreadPoolConfig {
	
	private final Logger LOGGER = Logger.getLogger(getClass());

    /**
     * 配置事务管理器
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() throws Exception {
    	ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
    	//核心线程数
    	threadPool.setCorePoolSize(10);
    	//最大线程数
    	threadPool.setMaxPoolSize(50);
		//队列最大长度 >=mainExecutor.maxSize
    	threadPool.setQueueCapacity(5000);
		//线程池维护线程所允许的空闲时间
    	threadPool.setKeepAliveSeconds(300);
		//线程池对拒绝任务(无线程可用)的处理策略
		threadPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				LOGGER.error("Begin exception handler-----------");
				//执行失败任务
				new Thread(r, "exception by pool").start();
				//打印线程池的对象
				LOGGER.error("The pool RejectedExecutionHandler = "+executor.toString());
			}
		});
		/*<property name="rejectedExecutionHandler">
			<bean class="java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy" />
		</property>*/
    	return threadPool;
    }
    
}
