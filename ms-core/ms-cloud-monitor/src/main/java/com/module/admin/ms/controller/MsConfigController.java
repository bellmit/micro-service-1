package com.module.admin.ms.controller;

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
import com.module.admin.ms.pojo.MsConfig;
import com.module.admin.ms.pojo.MsConfigValue;
import com.module.admin.ms.service.MsConfigService;
import com.module.admin.ms.service.MsConfigValueService;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.sys.pojo.SysUser;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 微服务配置文件的Controller
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
@Controller
public class MsConfigController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsConfigController.class);

	@Autowired
	private MsConfigService msConfigService;
	@Autowired
	private MsConfigValueService msConfigValueService;
	@Autowired
	private PrjInfoService prjInfoService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/msConfig/f-view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap,
			Integer prjId) {
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		if(prjId != null) {
			//关联项目和配置文件
			msConfigService.relationPrjId(prjId);
		}
		return "admin/ms/config-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/msConfig/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			MsConfig msConfig) {
		ResponseFrame frame = null;
		try {
			frame = msConfigService.pageQuery(msConfig);
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
	@RequestMapping(value = "/msConfig/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer configId) {
		if(configId != null) {
			modelMap.put("msConfig", msConfigService.get(configId));
			//查询值
			List<MsConfigValue> values = msConfigValueService.findByConfigId(configId);
			modelMap.put("values", values);
		}
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		return "admin/ms/config-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/msConfig/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			MsConfig msConfig) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			msConfig.setUserId(user.getUserId());
			frame = msConfigService.saveOrUpdate(msConfig);
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
	@RequestMapping(value = "/msConfig/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer configId) {
		ResponseFrame frame = null;
		try {
			frame = msConfigService.delete(configId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}