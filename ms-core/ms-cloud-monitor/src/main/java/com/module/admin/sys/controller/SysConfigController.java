package com.module.admin.sys.controller;

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
import com.module.admin.sys.pojo.SysConfig;
import com.module.admin.sys.service.SysConfigService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * sys_config的Controller
 * @author yuejing
 * @date 2016-10-19 13:50:15
 * @version V1.0.0
 */
@Controller
public class SysConfigController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigController.class);

	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysConfig/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/sys/config-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/sysConfig/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
		SysConfig sysConfig) {
		ResponseFrame frame = null;
		try {
			frame = sysConfigService.pageQuery(sysConfig);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	/**
	 * 跳转到编辑页[包含新增和编辑]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysConfig/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, String code) {
		if(code != null) {
			modelMap.put("sysConfig", sysConfigService.get(code));
		}
		return "admin/sys/config-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/sysConfig/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
		SysConfig sysConfig) {
		ResponseFrame frame = null;
		try {
			frame = sysConfigService.saveOrUpdate(sysConfig);
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
	@RequestMapping(value = "/sysConfig/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
		String code) {
		ResponseFrame frame = null;
		try {
			frame = sysConfigService.delete(code);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}
