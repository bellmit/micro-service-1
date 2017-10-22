package com.module.api.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.module.api.pojo.ZipkinAnnotations;
import com.module.api.service.ZipkinAnnotationsService;
import com.system.comm.utils.FrameTimeUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 客户端调用的接口
 * @author 岳静
 * @date 2016年3月4日 下午6:22:39 
 * @version V1.0
 */
@RestController
public class ApiLogController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogController.class);
	@Autowired
	private ZipkinAnnotationsService zipkinAnnotationsService;
	
	/**
	 * 修改客户端发送的心跳
	 */
	@RequestMapping(value = "/api/log/heartbeat")
	@ResponseBody
	public ResponseFrame heartbeat(HttpServletRequest request, HttpServletResponse response,
			String clientId) {
		ResponseFrame frame = null;
		try {
			//frame = zipkinAnnotationsService.updateHeartbeat(clientId);
			Date beginTime = FrameTimeUtil.parseDate("2017-10-22 10:10:10", "yyyy-MM-dd HH:mm:ss");
			Date endTime = FrameTimeUtil.parseDate("2017-10-23 10:10:10", "yyyy-MM-dd HH:mm:ss");
			List<ZipkinAnnotations> list = zipkinAnnotationsService.findByAKeyATimestamp("ERROR", beginTime, endTime);
		} catch (Exception e) {
			LOGGER.error("修改客户端发送的心跳异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		return frame;
	}

}