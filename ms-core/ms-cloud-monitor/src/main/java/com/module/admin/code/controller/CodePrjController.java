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
import com.module.admin.code.pojo.CodePrj;
import com.module.admin.code.service.CodePrjService;
import com.module.admin.sys.pojo.SysUser;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 源码项目表的Controller
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Controller
public class CodePrjController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodePrjController.class);

	@Autowired
	private CodePrjService codePrjService;

	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/codePrj/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/code/prj-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/codePrj/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			CodePrj codePrj) {
		ResponseFrame frame = null;
		try {
			frame = codePrjService.pageQuery(codePrj);
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
	@RequestMapping(value = "/codePrj/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer prjId, String code) {
		if(prjId != null && code != null) {
			modelMap.put("codePrj", codePrjService.get(code));
		}
		return "admin/code/prj-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/codePrj/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			CodePrj codePrj) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			codePrj.setUserId(user.getUserId());
			frame = codePrjService.saveOrUpdate(codePrj);
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
	@RequestMapping(value = "/codePrj/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			String code, String name) {
		ResponseFrame frame = null;
		try {
			frame = codePrjService.delete(code);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

}