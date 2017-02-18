package com.frame.sys.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.sys.pojo.SysRoleRes;
import com.frame.sys.service.SysRoleResService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 角色资源
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class SysRoleResController {

	private final static Logger LOGGER = Logger.getLogger(SysRoleResController.class);
	@Autowired
	private SysRoleResService sysRoleResService;

	/**
	 * 保存
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/sysRoleRes/save")
	public ResponseFrame save(SysRoleRes roleRes) {
		try {
			/*SysRoleRes roleRes = new SysRoleRes();
			roleRes.setRoleId(FrameMapUtil.getString(map, "roleId"));
			roleRes.setResId(FrameMapUtil.getString(map, "resId"));
			roleRes.setAddUserId(FrameMapUtil.getString(map, "addUserId"));*/
			ResponseFrame frame = sysRoleResService.save(roleRes);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}