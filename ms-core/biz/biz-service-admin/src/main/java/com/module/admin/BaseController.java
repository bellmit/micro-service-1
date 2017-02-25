package com.module.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import com.module.admin.user.pojo.LoginUser;
import com.module.comm.constants.SessionCons;
import com.system.comm.utils.FrameJsonUtil;

/**
 * Base Controller
 * @author yuejing
 * @version 2016/05/21 1.0.0
 */
@Component
public class BaseController {

	public static final Logger LOGGER = Logger.getLogger(BaseController.class);

	public static final String UTF_8 = "utf-8";

	public void writerJson(HttpServletResponse response, Object obj) {
		/*try {
			response.setContentType("text/html;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
			mapper.writeValue(gen, obj);
			response.getWriter().print(sw.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}*/
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(FrameJsonUtil.toString(obj));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
	public static void main(String[] args) {
		LoginUser l = new LoginUser("a", "b");
		String str = FrameJsonUtil.toString(l);
		System.out.println(str);
		l = FrameJsonUtil.toObject(str, LoginUser.class);
		System.out.println(l);
	}

	/**
	 * 获取当前登录用户对象
	 * @return
	 */
	public LoginUser getCurrentUser() {
		try {
			String userString = (String) SecurityUtils.getSubject().getPrincipal();
			LoginUser user = FrameJsonUtil.toObject(userString, LoginUser.class);
			//LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			return user;
		} catch (Exception e) {
			LOGGER.error("异常: " + e.getMessage());
		}
		return null;
	}
	/**
	 * 从Session中获取用户信息
	 * @param request
	 * @return
	 */
	/*public SysUser getSessionUser(HttpServletRequest request) {
		return (SysUser) request.getSession().getAttribute(SessionCons.USER);
	}*/
	/**
	 * 设置用户信息到Session中
	 * @param request
	 * @param user
	 */
	/*public void setSessionUser(HttpServletRequest request, SysUser loginUser) {
		request.getSession().setAttribute(SessionCons.USER, loginUser);
	}*/
	/**
	 * 移除授权了的Session中的用户信息
	 * @param request
	 * @param response 
	 */
	public void removeSessionUser(HttpServletRequest request) {
		request.getSession().removeAttribute(SessionCons.USER);
	}

	/**
	 * 设置值到Session中
	 * @param request
	 * @param key
	 * @param value
	 */
	public void setSession(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}
	/**
	 * 获取Session中的值
	 * @param request
	 * @param key
	 * @return
	 */
	public Object getSession(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}
	/**
	 * 移除Session中的信息
	 * @param request
	 * @param key
	 */
	public void removeSession(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	public Map<String, Object> getParamsMap(HttpServletRequest request) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<Entry<String, String[]>> entryKeyIterator = map.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, String[]> e = entryKeyIterator.next();
			paramsMap.put(e.getKey(), request.getParameter(e.getKey()));
		}
		return paramsMap;
	}
}