package com.frame.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.test.pojo.Test;
import com.frame.test.service.TestService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * test接口
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class TestController {

    private final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    
	@Autowired
	private TestService testService;

	/**
	 * 获取对象
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/test/get")
	public ResponseFrame get(HttpServletRequest request, String id) {
		try {
			ResponseFrame frame = new ResponseFrame();
			Test data = testService.get(id);
			frame.setBody(data);
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/test/save")
	public ResponseFrame save(Test test) {
		try {
			ResponseFrame frame = testService.save(test);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}