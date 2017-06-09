package com.module.admin.prj.controller;

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
import com.module.admin.prj.pojo.PrjInfo;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.sys.pojo.SysUser;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_info的Controller
 * @author yuejing
 * @date 2016-10-19 15:56:37
 * @version V1.0.0
 */
@Controller
public class PrjInfoController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrjInfoController.class);

	@Autowired
	private PrjInfoService prjInfoService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjInfo/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/prj/info-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/prjInfo/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			PrjInfo prjInfo) {
		ResponseFrame frame = null;
		try {
			frame = prjInfoService.pageQuery(prjInfo);
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
	@RequestMapping(value = "/prjInfo/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer prjId) {
		if(prjId != null) {
			modelMap.put("prjInfo", prjInfoService.get(prjId));
		}
		return "admin/prj/info-edit";
	}

	@RequestMapping(name = "发布等的流程操作", value = "/prjInfo/f-view/process")
	public String process(HttpServletRequest request, ModelMap modelMap, Integer prjId) {
		if(prjId != null) {
			modelMap.put("prjInfo", prjInfoService.get(prjId));
		}
		return "admin/prj/info-process";
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/prjInfo/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			PrjInfo prjInfo) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			prjInfo.setUserId(user.getUserId());
			frame = prjInfoService.saveOrUpdate(prjInfo);
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
	@RequestMapping(value = "/prjInfo/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer prjId) {
		ResponseFrame frame = null;
		try {
			frame = prjInfoService.delete(prjId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}
