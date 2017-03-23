package com.module.task;

import org.springframework.stereotype.Component;

@Component
public class TaskManager {

	/**
	 * 初始化任务
	 */
	public void init() {
		//同步项目变化和监控的任务
		new PrjInfoMonitorTask().run();
		
		//检测客户端
		new ClientTask().run();
	}
}