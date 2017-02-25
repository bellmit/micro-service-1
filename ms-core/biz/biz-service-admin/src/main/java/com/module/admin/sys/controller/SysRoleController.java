package com.module.admin.sys.controller;

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
import com.module.admin.sys.service.SysRoleService;
import com.module.comm.csrf.CsrfToken;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 角色的操作
 * @author yuejing
 * @date 2016-05-22 11:17:54
 * @version V1.0.0
 */
@Controller
public class SysRoleController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(SysRoleController.class);
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 管理中心
	 */
	@RequestMapping(value = "/sysRole/f-view/manager")
	@RequiresPermissions(value="ADMIN_GRANT_ROLE")
	public String main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		/*String menuIds = sysRoleService.get(userInfo.getRoleId()).getGrantids();
		List<SysMenu> menuList = sysMenuService.findTreeByMenuIds(menuIds);
		modelMap.put("menuList", menuList);
		
		setSession(request, SessionCons.USER_MENU, sysMenuService.findByMenuIds(menuIds));*/
		return "admin/sys/sysRole-manager";
	}
	@RequestMapping(value = "/sysRole/f-json/pageQuery")
	@ResponseBody
	@RequiresPermissions(value="ADMIN_GRANT_ROLE")
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			Integer page, Integer size, String name) {
		ResponseFrame frame = null;
		try {
			frame = sysRoleService.pageQuery(page, size, name);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	
	@RequestMapping(value = "/sysRole/f-view/edit")
	@RequiresPermissions(value="ADMIN_GRANT_ROLE")
	@CsrfToken(create=true)
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String roleId) {
		if(FrameStringUtil.isNotEmpty(roleId)) {
			Map<String, Object> userInfo = sysRoleService.get(roleId);
			modelMap.put("userInfo", userInfo);
		}
		return "admin/sys/sysRole-edit";
	}
}