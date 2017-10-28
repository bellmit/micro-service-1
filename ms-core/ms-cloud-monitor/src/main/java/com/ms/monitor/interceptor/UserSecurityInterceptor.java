package com.ms.monitor.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.module.admin.sys.pojo.SysUser;
import com.module.comm.constants.SessionCons;
import com.system.comm.utils.FrameStringUtil;

/**
 * 用户Session拦截器
 * @author duanbin
 * @date 2013-8-16 下午10:09:43
 * @version V1.0.0
 */
public class UserSecurityInterceptor implements HandlerInterceptor {

	//登录的URL
	private String loginUrl;

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
		SysUser user = (SysUser) request.getSession().getAttribute(SessionCons.USER);
		if(user == null) {
			//获取当前请求的地址
			String url = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			if(FrameStringUtil.isNotEmpty(queryString)) {
				url = url + "?" + queryString;
			}
			response.sendRedirect(request.getContextPath() + loginUrl + "?link=" + url);
			return false;
		}
		return true;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}