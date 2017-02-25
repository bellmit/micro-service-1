package com.module.admin.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.user.utils.UserUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 用户信息的操作
 * @author yuejing
 * @date 2016-05-22 11:17:54
 * @version V1.0.0
 */
@Controller
public class SysAuthController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(SysAuthController.class);

	/**
	 * 管理中心
	 */
	@RequestMapping(value = "/sysAuth/f-view/manager")
	@RequiresPermissions(value="ADMIN_GRANT_AUTH")
	public String main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		/*String menuIds = sysRoleService.get(userInfo.getRoleId()).getGrantids();
		List<SysMenu> menuList = sysMenuService.findTreeByMenuIds(menuIds);
		modelMap.put("menuList", menuList);
		
		setSession(request, SessionCons.USER_MENU, sysMenuService.findByMenuIds(menuIds));*/
		return "admin/sys/sysAuth-manager";
	}
	
}