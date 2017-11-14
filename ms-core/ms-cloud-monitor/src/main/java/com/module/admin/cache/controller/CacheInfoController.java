package com.module.admin.cache.controller;

import java.util.List;
import java.util.Map;

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
import com.module.admin.cache.utils.CacheUtil;
import com.module.admin.prj.pojo.PrjInfo;
import com.module.admin.prj.service.PrjInfoService;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 缓存的Controller
 * @author yuejing
 * @date 2016-11-30 13:30:00
 * @version V1.0.0
 */
@Controller
public class CacheInfoController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheInfoController.class);

	@Autowired
	private PrjInfoService prjInfoService;

	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cacheInfo/f-view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap) {
		//获取所有服务
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		return "admin/cache/info-manager";
	}

	/**
	 * 获取信息
	 * @return
	 */
	@RequestMapping(value = "/cacheInfo/f-json/findKey")
	@ResponseBody
	public void findKey(HttpServletRequest request, HttpServletResponse response,
			Integer prjId) {
		Map<String, Object> paramsMap = getParamsMap(request);
		ResponseFrame frame = null;
		try {
			PrjInfo prjInfo = prjInfoService.get(prjId);
			if(prjInfo == null) {
				frame = new ResponseFrame();
				frame.setCode(-2);
				frame.setMessage("不存在的项目");
				writerJson(response, frame);
				return;
			}
			String serviceId = prjInfo.getCode();
			frame = CacheUtil.post(serviceId, "/cache/findKey", paramsMap);
		} catch (Exception e) {
			LOGGER.error("获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	@RequestMapping(value = "/cacheInfo/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer prjId) {
		Map<String, Object> paramsMap = getParamsMap(request);
		ResponseFrame frame = null;
		try {
			PrjInfo prjInfo = prjInfoService.get(prjId);
			if(prjInfo == null) {
				frame = new ResponseFrame();
				frame.setCode(-2);
				frame.setMessage("不存在的项目");
				writerJson(response, frame);
				return;
			}
			String serviceId = prjInfo.getCode();
			frame = CacheUtil.post(serviceId, "/cache/delete", paramsMap);
		} catch (Exception e) {
			LOGGER.error("获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	@RequestMapping(value = "/cacheInfo/f-json/deleteBatch")
	@ResponseBody
	public void deleteBatch(HttpServletRequest request, HttpServletResponse response,
			Integer prjId) {
		Map<String, Object> paramsMap = getParamsMap(request);
		ResponseFrame frame = null;
		try {
			PrjInfo prjInfo = prjInfoService.get(prjId);
			if(prjInfo == null) {
				frame = new ResponseFrame();
				frame.setCode(-2);
				frame.setMessage("不存在的项目");
				writerJson(response, frame);
				return;
			}
			String serviceId = prjInfo.getCode();
			frame = CacheUtil.post(serviceId, "/cache/deleteBatch", paramsMap);
		} catch (Exception e) {
			LOGGER.error("获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}