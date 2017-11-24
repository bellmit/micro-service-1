package com.client.rel.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.rel.ReleaseClient;
import com.system.comm.utils.FrameFileUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * service接口
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class ProjectController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
    
	/**
	 * 根据服务ID获取服务列表的信息
	 * @param serviceId
	 * @return
	 */
	@RequestMapping(value = "/project/release")
	public ResponseFrame serviceList(Integer prjId, Integer container, String shellScript,
			String pathUrl, String version) {
		try {
			ResponseFrame frame = new ResponseFrame();
			/*
			FrameMapUtil.getString(map, "code"),
			FrameMapUtil.getString(map, "name")*/
			//发布项目
			ReleaseClient client = new ReleaseClient();
			client.release(prjId, version, container, shellScript, pathUrl);
			
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	/**
	 * 根据项目ID和指定的日志路劲获取日志信息
	 * @return
	 */
	@RequestMapping(value = "/project/log")
	public ResponseFrame log(Integer prjId, String logPath, Integer readLine) {
		try {
			ResponseFrame frame = new ResponseFrame();
			if(readLine == null) {
				readLine = 50;
			}
			List<String> logs = FrameFileUtil.readLastNLine(logPath, readLine);
			/*
			FrameMapUtil.getString(map, "code"),
			FrameMapUtil.getString(map, "name")*/
			frame.setBody(logs);
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}