package com.module.admin.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.user.pojo.LoginUser;
import com.module.admin.user.service.UserInfoService;
import com.module.comm.csrf.CsrfToken;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 用户信息的操作
 * @author yuejing
 * @date 2016-05-22 11:17:54
 * @version V1.0.0
 */
@Controller
public class UserInfoController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = "/userInfo/f-view/manager")
	@RequiresPermissions(value="ADMIN_GRANT_USER")
	public String main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		/*String menuIds = sysRoleService.get(userInfo.getRoleId()).getGrantids();
		List<SysMenu> menuList = sysMenuService.findTreeByMenuIds(menuIds);
		modelMap.put("menuList", menuList);
		
		setSession(request, SessionCons.USER_MENU, sysMenuService.findByMenuIds(menuIds));*/
		return "admin/user/userInfo-manager";
	}

	@RequestMapping(value = "/userInfo/f-view/edit")
	@RequiresPermissions(value="ADMIN_GRANT_USER")
	@CsrfToken(create=true)
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String userId) {
		if(FrameStringUtil.isNotEmpty(userId)) {
			Map<String, Object> userInfo = userInfoService.get(userId);
			modelMap.put("userInfo", userInfo);
		}
		return "admin/user/userInfo-edit";
	}
	
	/**
	 * 分页获取信息
	 */
	@RequestMapping(value = "/userInfo/f-json/pageQuery")
	@ResponseBody
	@RequiresPermissions(value="ADMIN_GRANT_USER")
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			Integer page, Integer size, String userName) {
		ResponseFrame frame = null;
		try {
			frame = userInfoService.pageQuery(page, size, userName);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/userInfo/f-json/delete")
	@ResponseBody
	@RequiresPermissions(value="ADMIN_GRANT_USER")
	public void delete(HttpServletRequest request, HttpServletResponse response,
			String userId) {
		ResponseFrame frame = null;
		try {
			frame = userInfoService.delete(userId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	@RequestMapping(value = "/userInfo/f-json/saveOrUpdate")
	@ResponseBody
	@RequiresPermissions(value="ADMIN_GRANT_USER")
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response,
			String userId, String userName, String password, String name, Integer status) {
		ResponseFrame frame = null;
		try {
			LoginUser loginUser = getCurrentUser();
			frame = userInfoService.saveOrUpdate(userId, userName, password, name, status, loginUser.getUserId());
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	@RequestMapping(value = "/userInfo/f-json/resetPwd")
	@ResponseBody
	@RequiresPermissions(value="ADMIN_GRANT_USER")
	public void resetPwd(HttpServletRequest request, HttpServletResponse response,
			String userId) {
		ResponseFrame frame = null;
		try {
			frame = userInfoService.resetPwd(userId);
		} catch (Exception e) {
			LOGGER.error("重置密码异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}