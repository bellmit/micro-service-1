package com.module.admin.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.module.admin.BaseController;
import com.module.comm.csrf.CsrfToken;

/**
 * 用户登录退出等操作
 * @author yuejing
 * @date 2016-05-22 11:17:54
 * @version V1.0.0
 */
@Controller
public class ViewController extends BaseController {

	/**
	 * 首页
	 */
	@RequestMapping(value = "/index")
    @CsrfToken(create = true)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("loginUser", getCurrentUser());
		return "index";
	}

	@RequestMapping(value = "/help")
	public String help(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("loginUser", getCurrentUser());
		return "help";
	}
}