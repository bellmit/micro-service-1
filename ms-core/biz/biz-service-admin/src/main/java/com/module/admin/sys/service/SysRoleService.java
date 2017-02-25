package com.module.admin.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.module.comm.service.BaseService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class SysRoleService extends BaseService {

	/**
	 * 保存角色资源
	 * @param roleId
	 * @param resId
	 * @param addUserId
	 * @return
	 */
	public ResponseFrame save(String roleId, String resId, String addUserId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("roleId", roleId);
		paramsMap.put("resId", resId);
		paramsMap.put("addUserId", addUserId);
		ResponseFrame frame = post("/sysRoleRes/save", paramsMap);
		return frame;
	}

	public ResponseFrame pageQuery(Integer page, Integer size, String name) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("page", page);
		paramsMap.put("size", size);
		paramsMap.put("name", name);
		ResponseFrame frame = post("/sysRole/pageQuery", paramsMap);
		return frame;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> get(String roleId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("roleId", roleId);
		ResponseFrame frame = post("/sysRole/get", paramsMap);
		if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
			return (Map<String, Object>) frame.getBody();
		}
		return null;
	}

}