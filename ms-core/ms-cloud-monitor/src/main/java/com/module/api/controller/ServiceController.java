package com.module.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.module.api.service.ServiceService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * service接口
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class ServiceController {

    private final Logger LOGGER = Logger.getLogger(getClass());
    
	@Autowired
	private ServiceService serviceService;

	/**
	 * 根据服务ID获取服务列表的信息
	 * @param serviceId
	 * @return
	 */
	@RequestMapping(value = "/api/service/serviceList")
	public ResponseFrame serviceList(String serviceId) {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(serviceService.serviceList(serviceId));
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
	
	/**
	 * 获取所有服务
	 * @return
	 */
	@RequestMapping(value = "/api/service/services")
	public ResponseFrame serviceList() {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(serviceService.services());
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

}