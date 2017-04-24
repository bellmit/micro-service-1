package com.module.admin.ms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.ms.pojo.MsConfigValue;
import com.module.admin.ms.service.MsConfigValueService;
import com.module.admin.sys.pojo.SysUser;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 微服务配置文件值的Controller
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
@Controller
public class MsConfigValueController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsConfigValueController.class);

	@Autowired
	private MsConfigValueService msConfigValueService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/msConfigValue/f-view/edit")
	public String manger(HttpServletRequest request, ModelMap modelMap, Integer configId) {
		List<MsConfigValue> values = msConfigValueService.findByConfigId(configId);
		modelMap.put("values", values);
		return "admin/ms/configValue-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/msConfigValue/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			Integer configId, String code, String value, String remark, String regex) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			Integer userId = user.getUserId();
			List<MsConfigValue> values = new ArrayList<MsConfigValue>();
			String[] codeArr = code.split(regex);
			String[] valueArr = value.split(regex);
			String[] remarkArr = remark.split(regex);
			for (int i = 0; i < codeArr.length; i++) {
				values.add(new MsConfigValue(configId, codeArr[i], valueArr[i], remarkArr[i], userId));
			}
			frame = msConfigValueService.saveList(configId, values);
		} catch (Exception e) {
			LOGGER.error("保存异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/msConfigValue/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer configId, String code) {
		ResponseFrame frame = null;
		try {
			frame = msConfigValueService.delete(configId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}