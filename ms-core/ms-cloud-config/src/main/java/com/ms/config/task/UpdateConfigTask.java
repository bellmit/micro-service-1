package com.ms.config.task;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ms.config.constants.ConfigCons;
import com.ms.config.utils.ServerUtil;
import com.system.comm.utils.FrameFileUtil;
import com.system.comm.utils.FrameMapUtil;
import com.system.comm.utils.FrameStringUtil;
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
		ScheduledExecutorService service = Executors.newScheduledThreadPool(20);
		//线程，每隔5秒调用一次
		Runnable runnable = new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				//获取要更新的配置文件
				try {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					ResponseFrame frame = ServerUtil.post("/api/msConfig/findAll.shtml", paramsMap);
					if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
						List<Map<String, Object>> data = (List<Map<String, Object>>) frame.getBody();
						for (Map<String, Object> map : data) {
							String fileName = FrameMapUtil.getString(map, "name");
							@SuppressWarnings("rawtypes")
							List<Map> values = FrameMapUtil.getListMap(map, "values");
							StringBuffer fileString = new StringBuffer();
							for (Map<String, Object> valueMap : values) {
								fileString.append("#").append(FrameMapUtil.getString(valueMap, "remark")).append("\n")
								.append(FrameMapUtil.getString(valueMap, "code"))
								.append("=").append(FrameMapUtil.getString(valueMap, "value"))
								.append("\n");
							}

							//读取本地文件
							String dirPath = ConfigCons.configSearchLocations;
							if(dirPath.startsWith("file:")) {
								dirPath = dirPath.substring(5);
							}
							String path = dirPath + File.separator + fileName;
							String orgString = FrameFileUtil.readFileString(path);
							if(FrameStringUtil.isEmpty(orgString)) {
								//新增文件
								LOGGER.info("新增配置文件[" + path + "]");
								FrameFileUtil.createDir(dirPath);
								FrameFileUtil.writeFile(fileString.toString(), new File(path));
							} else if(!fileString.toString().equals(orgString)) {
								//更新内容
								LOGGER.info("更新配置文件[" + path + "]");
								FrameFileUtil.createDir(dirPath);
								FrameFileUtil.writeFile(fileString.toString(), new File(path));
							} else {
								//无需更新
							}
						}
					} else {
						LOGGER.error("请求服务端失败");
					}
				} catch (Exception e) {
					LOGGER.error("更新配置文件异常: " + e.getMessage());
				}
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 40, 45, TimeUnit.SECONDS);
	}

}