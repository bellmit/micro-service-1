package com.frame.sys.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.sys.service.SysDictTypeService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 字典分类
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class SysDictTypeController {

	private final static Logger LOGGER = Logger.getLogger(SysDictTypeController.class);
	@Autowired
	private SysDictTypeService sysDictTypeService;

	/**
	 * 获取对象
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/sysDictType/get")
	public ResponseFrame get(String code) {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(sysDictTypeService.get(code));
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}