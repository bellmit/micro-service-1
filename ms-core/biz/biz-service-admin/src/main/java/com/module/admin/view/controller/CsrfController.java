package com.module.admin.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.comm.csrf.CsrfToken;
import com.module.comm.csrf.CsrfTokenBean;
import com.module.comm.csrf.CsrfTokenRepository;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * csrf
 * @author yuejing
 * @date 2016-05-22 11:17:54
 * @version V1.0.0
 */
@Controller
public class CsrfController extends BaseController {

	@Autowired 
	private CsrfTokenRepository csrfTokenRepository;
	
	@RequestMapping(value = "/csrf")
    @CsrfToken(create = true)
	@ResponseBody
	public void csrf(HttpServletRequest request, HttpServletResponse response) {
		ResponseFrame frame = new ResponseFrame();
		try {
			CsrfTokenBean tokenBean = csrfTokenRepository.loadToken(request);
			frame.setBody(tokenBean);
			frame.setCode(ResponseCode.SUCC.getCode());
		} catch (Exception e) {
			LOGGER.error("异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}