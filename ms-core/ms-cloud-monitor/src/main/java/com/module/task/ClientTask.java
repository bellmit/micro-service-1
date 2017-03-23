package com.module.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.module.admin.cli.service.CliInfoService;
import com.system.comm.utils.FrameSpringBeanUtil;

/**
 * 任务调度
 * @author yuejing
 * @date 2016年10月22日 上午9:58:59
 * @version V1.0.0
 */
public class ClientTask {
	
	private static final Logger LOGGER = Logger.getLogger(ClientTask.class);

	public void run() {
		LOGGER.info("========================= 初始化客户端的任务 - 成功 ===========================");
		ScheduledExecutorService service = Executors.newScheduledThreadPool(100);
		//线程，每隔5秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				//判断客户端的心跳是否失败
				CliInfoService cliInfoService = FrameSpringBeanUtil.getBean(CliInfoService.class);
				cliInfoService.updateActivityStatusError();
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 10, 5, TimeUnit.SECONDS);
	}

}
