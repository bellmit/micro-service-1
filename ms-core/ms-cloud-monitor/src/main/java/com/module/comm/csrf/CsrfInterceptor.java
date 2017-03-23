package com.module.comm.csrf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.system.comm.utils.FrameSpringBeanUtil;

/**
 * Csrf拦截器，用来生成或去除CsrfToken
 * 
 * @author yuejing
 */
public class CsrfInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(CsrfInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		CsrfToken csrfToken = handlerMethod.getMethodAnnotation(CsrfToken.class);
		// 判断是否含有@CsrfToken注解
		if (null == csrfToken) {
			return true;
		}
		// create、remove同时为true时异常
		if (csrfToken.create() && csrfToken.remove()) {
			logger.error("CsrfToken attr create and remove can Not at the same time to true!");
			return renderError(request, response, Boolean.FALSE, "CsrfToken attr create and remove can Not at the same time to true!");
		}
		CsrfTokenRepository csrfTokenRepository = FrameSpringBeanUtil.getBean(CsrfTokenRepository.class);
		// 创建
		if (csrfToken.create()) {
			CsrfTokenBean token = csrfTokenRepository.generateToken(request);
			csrfTokenRepository.saveToken(token, request, response);
			// 缓存一个表单页面地址的url
			csrfTokenRepository.cacheUrl(request, response);
			request.setAttribute(token.getParameterName(), token);
			return true;
		}
		// 判断是否ajax请求
		boolean isAjax = isAjax(handlerMethod);
		// 校验
		CsrfTokenBean tokenBean = csrfTokenRepository.loadToken(request);
		if (tokenBean == null) {
			return renderError(request, response, isAjax, "CsrfToken is null!");
		}
		String actualToken = request.getHeader(tokenBean.getHeaderName());
		if (actualToken == null) {
			actualToken = request.getParameter(tokenBean.getParameterName());
		}
		if (!tokenBean.getToken().equals(actualToken)) {
			return renderError(request, response, isAjax, "CsrfToken not eq!");
		}
		return true;
	}
	/**
	 * 判断是否ajax请求
	 * spring ajax 返回含有 ResponseBody 或者 RestController注解
	 * @param handlerMethod HandlerMethod
	 * @return 是否ajax请求
	 */
	private boolean isAjax(HandlerMethod handlerMethod) {
		ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
		if (null != responseBody) {
			return true;
		}
		RestController restAnnotation = handlerMethod.getBean().getClass().getAnnotation(RestController.class);
		if (null != restAnnotation) {
			return true;
		}
		return false;
	}
	
	private boolean renderError(HttpServletRequest request, HttpServletResponse response, 
			boolean isAjax, String message) throws IOException {
		CsrfTokenRepository csrfTokenRepository = FrameSpringBeanUtil.getBean(CsrfTokenRepository.class);
		// 获取缓存的cacheUrl
		String cachedUrl = csrfTokenRepository.getRemoveCacheUrl(request, response);
		// ajax请求直接抛出异常，因为{@link ExceptionResolver}会去处理
		if (isAjax) {
			throw new RuntimeException(message);
		}
		// 非ajax CsrfToken校验异常，先清理token
		csrfTokenRepository.saveToken(null, request, response);
		logger.info("Csrf[redirectUrl]:\t" + cachedUrl);
		response.sendRedirect(cachedUrl);
		return false;
	}

	/**
	 * 用于清理@CsrfToken保证只能请求成功一次
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		CsrfTokenRepository csrfTokenRepository = FrameSpringBeanUtil.getBean(CsrfTokenRepository.class);
		// 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return;
		}
		CsrfToken csrfToken = handlerMethod.getMethodAnnotation(CsrfToken.class);
		if (csrfToken == null || !csrfToken.remove()) {
			return;
		}
		csrfTokenRepository.getRemoveCacheUrl(request, response);
		csrfTokenRepository.saveToken(null, request, response);
	}

}
