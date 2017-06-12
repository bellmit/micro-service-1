package com.module.admin.prj.controller;

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
import com.module.admin.prj.pojo.PrjVersion;
import com.module.admin.prj.service.PrjVersionService;
import com.module.admin.sys.pojo.SysUser;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_version的Controller
 * @author yuejing
 * @date 2016-10-19 15:55:36
 * @version V1.0.0
 */
@Controller
public class PrjVersionController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrjVersionController.class);

	@Autowired
	private PrjVersionService prjVersionService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjVersion/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/prj/version-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/prjVersion/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			PrjVersion prjVersion) {
		ResponseFrame frame = null;
		try {
			frame = prjVersionService.pageQuery(prjVersion);
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
	@RequestMapping(value = "/prjVersion/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer prjId, String version) {
		if(prjId != null) {
			modelMap.put("prjVersion", prjVersionService.get(prjId, version));
		}
		List<PrjVersion> versions = prjVersionService.findByPrjId(prjId);
		List<KvEntity> list = new ArrayList<KvEntity>();
		for (PrjVersion v : versions) {
			list.add(new KvEntity(v.getVersion(), v.getVersion()));
		}
		modelMap.put("list", list);
		return "admin/prj/version-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/prjVersion/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			PrjVersion prjVersion) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			prjVersion.setUserId(user.getUserId());
			frame = prjVersionService.saveOrUpdate(prjVersion);
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
	@RequestMapping(value = "/prjVersion/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer prjId, String version) {
		ResponseFrame frame = null;
		try {
			frame = prjVersionService.delete(prjId, version);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}
