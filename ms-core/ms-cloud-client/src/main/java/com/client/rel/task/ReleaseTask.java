package com.client.rel.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.client.rel.data.DispatchFailData;
import com.client.rel.utils.ServerUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 任务调度
 * @author yuejing
 * @date 2016年10月22日 上午9:58:59
 * @version V1.0.0
 */
public class ReleaseTask {
	
	private static final Logger LOGGER = Logger.getLogger(ReleaseTask.class);

	public void run() {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(100);
		//线程，每隔5秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				//发送心跳
				sendHeartbeat();
				
				//处理发布项目成功，没有提交给服务端的数据
				dealReleaseSucc();
				//处理发布项目失败，没有提交给服务端的数据
				dealReleaseFail();
				
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 10, 5, TimeUnit.SECONDS);
	}

	private void sendHeartbeat() {
		try {
			//发送心跳
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			ResponseFrame resultFrame = ServerUtil.post("/api/client/heartbeat.shtml", paramsMap);
			if(ResponseCode.SUCC.getCode() == resultFrame.getCode().intValue()) {
				//LOGGER.info("发送心跳成功!");
			} else {
				LOGGER.error("发送心跳失败, 调用服务端接口失败!");
			}
		} catch (Exception e) {
			LOGGER.error("发送心跳失败, 调用服务端接口失败!" + e.getMessage());
		}
	}

	private void dealReleaseSucc() {
		for (Integer prjId : DispatchFailData.getUpdateReleaseSuccPrjIds()) {
			try {
				//发布成功调用接口，修改发布状态
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("prjId", prjId.toString());
				//状态[10待发布、20发布中、30发布失败、40发布成功]
				paramsMap.put("status", "40");
				ResponseFrame resultFrame = ServerUtil.post("/api/client/updateRelease.shtml", paramsMap);
				if(ResponseCode.SUCC.getCode() == resultFrame.getCode().intValue()) {
					LOGGER.info("项目[" + prjId + "]发布成功!");
					//移除任务的数据
					DispatchFailData.removeUpdateReleaseSuccPrjIds(prjId);
				} else {
					LOGGER.error("项目[" + prjId + "]发布成功, 调用服务端接口失败!");
				}
			} catch (Exception e) {
				LOGGER.error("项目[" + prjId + "]发布成功, 调用服务端接口失败!" + e.getMessage());
			}
		}
		
	}
	
	private void dealReleaseFail() {
		for (Integer prjId : DispatchFailData.getUpdateReleaseFailPrjIds()) {
			try {
				//发布失败调用接口，修改发布状态
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("prjId", prjId.toString());
				//状态[10待发布、20发布中、30发布失败、40发布成功]
				paramsMap.put("status", "30");
				ResponseFrame resultFrame = ServerUtil.post("/api/client/updateRelease.shtml", paramsMap);
				if(ResponseCode.SUCC.getCode() == resultFrame.getCode().intValue()) {
					LOGGER.info("项目[" + prjId + "]发布成功!");
					//移除任务的数据
					DispatchFailData.removeUpdateReleaseFailPrjIds(prjId);
				} else {
					LOGGER.error("项目[" + prjId + "]发布失败, 调用服务端接口失败!");
				}
			} catch (Exception e) {
				LOGGER.error("项目[" + prjId + "]发布失败, 调用服务端接口失败!" + e.getMessage());
			}
		}
		
	}
}
