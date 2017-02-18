package com.frame.sys.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.sys.pojo.SysRole;
import com.frame.sys.pojo.SysRoleRes;
import com.frame.sys.service.SysRoleService;
import com.system.comm.model.Orderby;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 角色资源
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class SysRoleController {

	private final static Logger LOGGER = Logger.getLogger(SysRoleController.class);
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 保存
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/sysRole/saveOrUpdate")
	public ResponseFrame saveOrUpdate(SysRoleRes roleRes) {
		try {
			/*SysRoleRes roleRes = new SysRoleRes();
			roleRes.setRoleId(FrameMapUtil.getString(map, "roleId"));
			roleRes.setResId(FrameMapUtil.getString(map, "resId"));
			roleRes.setAddUserId(FrameMapUtil.getString(map, "addUserId"));*/
			/*ResponseFrame frame = sysRoleService.save(roleRes);
			return frame;*/
			return null;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sysRole/pageQuery")
	public ResponseFrame pageQuery(SysRole role, String orderby) {
		try {
			/*SysRole role = new SysRole();
			role.setPage(FrameMapUtil.getInteger(map, "page"));
			role.setSize(FrameMapUtil.getInteger(map, "size"));
			role.setName(FrameMapUtil.getString(map, "name"));
			String orderbyString = FrameMapUtil.getString(map, "orderby");*/
			if(FrameStringUtil.isNotEmpty(orderby)) {
				List<Orderby> orderbys = FrameJsonUtil.toList(orderby, Orderby.class);
				role.setOrderbys(orderbys);
			}
			ResponseFrame frame = sysRoleService.pageQuery(role);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sysRole/get")
	public ResponseFrame get(String roleId) {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(sysRoleService.get(roleId));
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}