package com.module.admin.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.module.admin.user.pojo.LoginUser;
import com.module.admin.user.utils.UserUtil;
import com.module.comm.service.BaseService;
import com.system.comm.enums.Boolean;
import com.system.comm.utils.FrameMapUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class UserInfoService extends BaseService {

	public ResponseFrame login(String userName, String password) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userName", userName);
		paramsMap.put("password", password);
		//标记密码已经加过密了
		paramsMap.put("isEncryption", 1);
		ResponseFrame frame = post("/userInfo/login", paramsMap);
		if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
			@SuppressWarnings("unchecked")
			Map<String, Object> loginRes = (Map<String, Object>) frame.getBody();
			LoginUser loginUser = new LoginUser();
			loginUser.setUserId(FrameMapUtil.getString(loginRes, "userId"));
			loginUser.setUserName(FrameMapUtil.getString(loginRes, "userName"));
			frame.setBody(loginUser);
		}
		return frame;
	}

	public ResponseFrame findResListByUserId(String userId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		ResponseFrame frame = post("/userRole/findResListByUserId", paramsMap);
		return frame;
	}

	public ResponseFrame updatePassword(String userId, String oldPwd,
			String newPwd) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("oldPwd", oldPwd);
		paramsMap.put("newPwd", newPwd);
		//标记密码已经加过密了
		paramsMap.put("isEncryption", 1);
		ResponseFrame frame = post("/userInfo/updatePassword", paramsMap);
		return frame;
	}

	public ResponseFrame pageQuery(Integer page, Integer size, String userName) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("page", page);
		paramsMap.put("size", size);
		paramsMap.put("userName", userName);
		ResponseFrame frame = post("/userInfo/pageQuery", paramsMap);
		return frame;
	}

	public ResponseFrame delete(String userId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("isDelete", Boolean.TRUE.getCode());
		ResponseFrame frame = post("/userInfo/delete", paramsMap);
		return frame;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> get(String userId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		ResponseFrame frame = post("/userInfo/get", paramsMap);
		if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
			return (Map<String, Object>) frame.getBody();
		}
		return null;
	}

	public ResponseFrame saveOrUpdate(String userId,
			String userName, String password, String name,
			Integer status, String addUserId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("userName", userName);
		paramsMap.put("password", UserUtil.encryptionPassword(password));
		paramsMap.put("name", name);
		paramsMap.put("status", status);
		paramsMap.put("addUserId", addUserId);
		//标记密码已经加过密了
		paramsMap.put("isEncryption", 1);
		ResponseFrame frame = post("/userInfo/saveOrUpdate", paramsMap);
		return frame;
	}

	public ResponseFrame resetPwd(String userId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("password", UserUtil.encryptionPassword("123456"));
		//标记密码已经加过密了
		paramsMap.put("isEncryption", 1);
		ResponseFrame frame = post("/userInfo/resetPwd", paramsMap);
		return frame;
	}

}