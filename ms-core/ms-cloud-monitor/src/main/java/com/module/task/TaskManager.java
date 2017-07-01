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

		//更新密钥
		new MsSecretTask().run();
	}
}