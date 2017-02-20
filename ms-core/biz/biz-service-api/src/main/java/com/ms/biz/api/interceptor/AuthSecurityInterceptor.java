package com.ms.biz.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 拦截器
 * @author yuejing
 * @date 2013-8-16 下午10:09:43
 * @version V1.0.0
 */
@Component
public class AuthSecurityInterceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = Logger.getLogger(AuthSecurityInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在Controller方法前进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//校验客户端提交的client_sercret是否正确
		String clientId = request.getParameter("clientId");
		String sign = request.getParameter("sign");
		String time = request.getParameter("time");

		LOGGER.info("{ clientId:" + clientId + ", time:" + time + ", sign:" + sign + " } 请求地址: " + request.getRequestURI());
		if(!AuthUtil.authVerify(clientId, time, sign)) {
			LOGGER.error("非法请求(abnormal signature): { clientId:" + clientId + ", time:" + time + ", sign:" + sign + " } 请求地址: " + request.getRequestURI());
			ResponseFrame frame = new ResponseFrame();
			frame.setCode(ResponseCode.ABNORMAL_SIGNATURE.getCode());
			frame.setMessage(ResponseCode.ABNORMAL_SIGNATURE.getMessage());
			response.getWriter().print(FrameJsonUtil.toString(frame));
			return false;
		}
		return true;
	}
}