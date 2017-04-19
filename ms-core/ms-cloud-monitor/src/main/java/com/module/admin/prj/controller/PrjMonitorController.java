package com.module.admin.prj.controller;

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
import com.module.admin.prj.pojo.PrjMonitor;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.prj.service.PrjMonitorService;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_monitor的Controller
 * @author yuejing
 * @date 2016-11-30 13:30:00
 * @version V1.0.0
 */
@Controller
public class PrjMonitorController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrjMonitorController.class);

	@Autowired
	private PrjMonitorService prjMonitorService;
	@Autowired
	private PrjInfoService prjInfoService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjMonitor/f-view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap) {
		//获取所有服务
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		return "admin/prj/monitor-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/prjMonitor/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			PrjMonitor prjMonitor) {
		ResponseFrame frame = null;
		try {
			frame = prjMonitorService.pageQuery(prjMonitor);
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
	@RequestMapping(value = "/prjMonitor/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer prjmId) {
		if(prjmId != null) {
			modelMap.put("prjMonitor", prjMonitorService.get(prjmId));
		}
		//获取所有项目
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		return "admin/prj/monitor-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/prjMonitor/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			PrjMonitor prjMonitor) {
		ResponseFrame frame = null;
		try {
			frame = prjMonitorService.saveOrUpdate(prjMonitor);
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
	@RequestMapping(value = "/prjMonitor/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer prjmId) {
		ResponseFrame frame = null;
		try {
			frame = prjMonitorService.delete(prjmId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}
