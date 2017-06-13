package com.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统配置的Controller
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @date    2015年4月5日 下午10:21:22 
 * @version 1.0.0
 */
@Controller
public class ViewController extends BaseController {

	@RequestMapping(value = "/")
	public String index(HttpServletRequest request) {
		return "redirect:index.jsp";
	}
}