package com.module.admin.code.controller;

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
import com.module.admin.code.pojo.CodeTemplate;
import com.module.admin.code.service.CodeTemplateService;
import com.module.admin.sys.pojo.SysUser;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 源码项目模板表的Controller
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Controller
public class CodeTemplateController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeTemplateController.class);

	@Autowired
	private CodeTemplateService codeTemplateService;

	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/codeTemplate/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/code/prj-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/codeTemplate/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			CodeTemplate codeTemplate) {
		ResponseFrame frame = null;
		try {
			frame = codeTemplateService.pageQuery(codeTemplate);
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
	@RequestMapping(value = "/codeTemplate/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap,
			String code, String name) {
		if(FrameStringUtil.isNotEmpty(code) && FrameStringUtil.isNotEmpty(name)) {
			modelMap.put("codeTemplate", codeTemplateService.get(code, name));
		}
		return "admin/code/template-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/codeTemplate/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			CodeTemplate codeTemplate) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			codeTemplate.setUserId(user.getUserId());
			frame = codeTemplateService.saveOrUpdate(codeTemplate);
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
	@RequestMapping(value = "/codeTemplate/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			String code, String name) {
		ResponseFrame frame = null;
		try {
			frame = codeTemplateService.delete(code, name);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

}