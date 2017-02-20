package com.frame.user.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.sys.pojo.SysRes;
import com.frame.user.service.UserRoleService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * userRole接口
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class UserRoleController {

    private final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 根据用户编号获取资源集合
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/userRole/findResListByUserId")
	public ResponseFrame findResListByUserId(String userId) {
		try {
			ResponseFrame frame = new ResponseFrame();
			List<SysRes> data = userRoleService.findResListByUserId(userId);
			frame.setBody(data);
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

}