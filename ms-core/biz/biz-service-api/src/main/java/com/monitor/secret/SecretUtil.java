package com.monitor.secret;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.api.ApiUtil;
import com.system.auth.AuthUtil;
import com.system.auth.model.AuthClient;
import com.system.comm.utils.FrameMapUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

public class SecretUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecretUtil.class);

	public void init() {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		//线程，每隔60秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				long start = System.currentTimeMillis();
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				try {
					ResponseFrame frame = ApiUtil.post("/api/msSecret/findUse", paramsMap);
					if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> data = (List<Map<String, Object>>) frame.getBody();
						for (Map<String, Object> map : data) {
							String cliId = FrameMapUtil.getString(map, "cliId");
							String name = FrameMapUtil.getString(map, "name");
							String domain = FrameMapUtil.getString(map, "domain");
							String sercret = FrameMapUtil.getString(map, "token");
							AuthUtil.updateAuthClient(new AuthClient(cliId, name, domain, sercret, domain));
						}
					}
					long diff = System.currentTimeMillis() - start;
					if(diff > 100) {
						LOGGER.info("更新应用密钥 - 耗时： " + diff + "ms");
					}
				} catch (IOException e) {
					LOGGER.error("获取密钥异常: " + e.getMessage());
				}
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 10, 30, TimeUnit.SECONDS);
	}
}
