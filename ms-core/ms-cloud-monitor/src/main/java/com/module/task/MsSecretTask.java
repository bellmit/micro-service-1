package com.module.task;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.module.admin.ms.pojo.MsSecret;
import com.module.admin.ms.service.MsSecretService;
import com.system.auth.AuthUtil;
import com.system.auth.model.AuthClient;
import com.system.comm.utils.FrameSpringBeanUtil;

/**
 * 更新密钥
 * @author yuejing
 * @date 2016年10月22日 上午9:58:59
 * @version V1.0.0
 */
public class MsSecretTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsSecretTask.class);

	public void run() {
		LOGGER.info("========================= 初始化更新Secret的任务 - 成功 ===========================");
		ScheduledExecutorService service = Executors.newScheduledThreadPool(20);
		final MsSecretService secretService = FrameSpringBeanUtil.getBean(MsSecretService.class);
		//线程，每隔5秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				List<MsSecret> list = secretService.findUse();
				for (MsSecret msSecret : list) {
					AuthUtil.updateAuthClient(new AuthClient(msSecret.getCliId(), msSecret.getName(),
							msSecret.getDomain(), msSecret.getToken(), msSecret.getDomain()));
				}
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 10, 60, TimeUnit.SECONDS);
	}

}
