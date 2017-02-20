package com.frame.user.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.user.pojo.UserInfo;
import com.frame.user.service.UserInfoService;
import com.system.comm.enums.Boolean;
import com.system.comm.model.Orderby;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * userInfo接口
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class UserInfoController {

    private final Logger LOGGER = Logger.getLogger(getClass());
    
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 获取对象
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/userInfo/get")
	public ResponseFrame get(String userId) {
		try {
			//String userId = FrameMapUtil.getString(map, "userId");
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(userInfoService.get(userId));
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userInfo/login")
	public ResponseFrame login(String userName, String password, Integer isEncryption) {
		try {
			/*String userName = FrameMapUtil.getString(map, "userName");
			String password = FrameMapUtil.getString(map, "password");
			Integer isEncryption = FrameMapUtil.getInteger(map, "isEncryption");*/
			ResponseFrame frame = userInfoService.login(userName, password, isEncryption);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userInfo/updatePassword")
	public ResponseFrame updatePassword(String userId, String oldPwd, String newPwd, Integer isEncryption) {
		try {
			/*String userId = FrameMapUtil.getString(map, "userId");
			String oldPwd = FrameMapUtil.getString(map, "oldPwd");
			String newPwd = FrameMapUtil.getString(map, "newPwd");
			Integer isEncryption = FrameMapUtil.getInteger(map, "isEncryption");*/
			ResponseFrame frame = userInfoService.updatePassword(userId, oldPwd, newPwd, isEncryption);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/userInfo/resetPwd")
	public ResponseFrame resetPwd(String userId, String password, Integer isEncryption) {
		try {
			/*String userId = FrameMapUtil.getString(map, "userId");
			String password = FrameMapUtil.getString(map, "password");
			Integer isEncryption = FrameMapUtil.getInteger(map, "isEncryption");*/
			ResponseFrame frame = userInfoService.resetPwd(userId, password, isEncryption);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userInfo/pageQuery")
	public ResponseFrame pageQuery(UserInfo userInfo, String orderby) {
		try {
			/*UserInfo userInfo = new UserInfo();
			userInfo.setPage(FrameMapUtil.getInteger(map, "page"));
			userInfo.setSize(FrameMapUtil.getInteger(map, "size"));
			userInfo.setUserName(FrameMapUtil.getString(map, "userName"));
			String orderbyString = FrameMapUtil.getString(map, "orderby");
			if(FrameStringUtil.isNotEmpty(orderbyString)) {
				List<Orderby> orderbys = FrameJsonUtil.toList(orderbyString, Orderby.class);
				userInfo.setOrderbys(orderbys);
			}*/
			if(FrameStringUtil.isNotEmpty(orderby)) {
				List<Orderby> orderbys = FrameJsonUtil.toList(orderby, Orderby.class);
				userInfo.setOrderbys(orderbys);
			}
			userInfo.setIsDelete(Boolean.FALSE.getCode());
			ResponseFrame frame = userInfoService.pageQuery(userInfo);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userInfo/delete")
	public ResponseFrame delete(String userId, Integer isDelete) {
		try {
			/*String userId = FrameMapUtil.getString(map, "userId");
			Integer isDelete = FrameMapUtil.getInteger(map, "isDelete");*/
			ResponseFrame frame = userInfoService.updateIsDelete(userId, isDelete);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userInfo/saveOrUpdate")
	public ResponseFrame saveOrUpdate(UserInfo userInfo, Integer isEncryption) {
		try {
			/*UserInfo userInfo = new UserInfo();
			userInfo.setUserId(FrameMapUtil.getString(map, "userId"));
			userInfo.setUserName(FrameMapUtil.getString(map, "userName"));
			userInfo.setPassword(FrameMapUtil.getString(map, "password"));
			userInfo.setName(FrameMapUtil.getString(map, "name"));
			userInfo.setStatus(FrameMapUtil.getInteger(map, "status"));
			userInfo.setAddUserId(FrameMapUtil.getString(map, "addUserId"));
			Integer isEncryption = FrameMapUtil.getInteger(map, "isEncryption");*/
			ResponseFrame frame = userInfoService.saveOrUpdate(userInfo, isEncryption);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}