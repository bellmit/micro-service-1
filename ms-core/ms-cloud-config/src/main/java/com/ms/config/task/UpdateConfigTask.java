package com.ms.config.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动线程3分钟更新一次配置文件
 * @author yuejing
 * @date 2016年10月22日 上午9:58:59
 * @version V1.0.0
 */
public class UpdateConfigTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateConfigTask.class);

	public void run() {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(100);
		//线程，每隔5秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				LOGGER.info("根据配置文件");
				//获取要更新的配置文件
				
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 20, 60, TimeUnit.SECONDS);
	}

}