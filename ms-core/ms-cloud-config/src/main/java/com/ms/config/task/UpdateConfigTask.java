package com.ms.config.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ms.config.utils.ServerUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

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
				LOGGER.info("更新配置文件");
				//获取要更新的配置文件
				try {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					ResponseFrame frame = ServerUtil.post("/api/msConfig/findAll.shtml", paramsMap);
					if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> data = (List<Map<String, Object>>) frame.getBody();
						for (Map<String, Object> map : data) {
							System.out.println(FrameJsonUtil.toString(map));
						}
					} else {
						LOGGER.error("请求服务端失败");
					}
				} catch (IOException e) {
					LOGGER.error("根据配置文件异常: " + e.getMessage(), e);
				}
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 15, 30, TimeUnit.SECONDS);
	}

}