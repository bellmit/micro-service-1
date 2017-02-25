package com.module.admin.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.module.comm.service.BaseService;
import com.system.handle.model.ResponseFrame;

@Component
public class SysResService extends BaseService {

	/**
	 * 保存资源
	 * @param resId
	 * @param type			类型[10菜单、20功能]
	 * @param parentResId
	 * @param name
	 * @param showName
	 * @param url
	 * @param remark
	 * @param orderby
	 * @param addUserId
	 * @return
	 */
	public ResponseFrame save(String resId, Integer type, String parentResId, String name, String showName,
			String url, String remark, Integer orderby, String addUserId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("resId", resId);
		paramsMap.put("type", type);
		paramsMap.put("parentResId", parentResId);
		paramsMap.put("name", name);
		paramsMap.put("showName", showName);
		paramsMap.put("url", url);
		paramsMap.put("remark", remark);
		paramsMap.put("orderby", orderby);
		paramsMap.put("addUserId", addUserId);
		ResponseFrame frame = post("/sysRes/save", paramsMap);
		return frame;
	}

}