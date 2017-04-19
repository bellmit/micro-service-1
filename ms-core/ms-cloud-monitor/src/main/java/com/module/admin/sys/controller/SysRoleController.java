package com.module.admin.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.sys.pojo.SysMenu;
import com.module.admin.sys.pojo.SysRole;
import com.module.admin.sys.service.SysMenuService;
import com.module.admin.sys.service.SysRoleService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * sys_role的Controller
 * @author duanbin
 * @date 2016-04-21 11:17:54
 * @version V1.0.0
 */
@Controller
public class SysRoleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 角色管理页面
	 */
	@RequestMapping(value = "/sysRole/f-view/manager")
	public String manager(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "admin/sys/sysRole-manager";
	}


	/**
	 * 分页获取信息
	 */
	@RequestMapping(value = "/sysRole/f-json/findAll")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response, SysRole sysRole) {
		ResponseFrame frame = new ResponseFrame();
		try {
			List<SysRole> roles = sysRoleService.findAll();
			frame.setBody(roles);
			frame.setCode(ResponseCode.SUCC.getCode());
		} catch (Exception e) {
			logger.error("分页获取信息异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

	/**
	 * 跳转到编辑用户
	 */
	@RequestMapping(value = "/sysRole/f-view/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Integer roleId) {
		List<SysMenu> sysMenuList = sysMenuService.findAllTreeMenu();
		if(roleId != null) {
			SysRole sysRole = sysRoleService.get(roleId);
			modelMap.put("sysRole", sysRole);
			String menuIds = sysRole.getGrantids();
			List<SysMenu> roleMenuList = sysMenuService.findByMenuIds(menuIds);
			for (SysMenu p : sysMenuList) {
				p.setIscheck(0);
				for (SysMenu rm : roleMenuList) {
					if(p.getMenuId().intValue() == rm.getMenuId().intValue()) {
						p.setIscheck(1);
						break;
					}
				}
				for (SysMenu cld1 : p.getCldMenus()) {
					cld1.setIscheck(0);
					for (SysMenu rm : roleMenuList) {
						if(cld1.getMenuId().intValue() == rm.getMenuId().intValue()) {
							cld1.setIscheck(1);
							break;
						}
					}
					if(cld1.getCldMenus() == null) {
						continue;
					}
					for (SysMenu cld2 : cld1.getCldMenus()) {
						cld2.setIscheck(0);
						for (SysMenu rm : roleMenuList) {
							if(cld2.getMenuId().intValue() == rm.getMenuId().intValue()) {
								cld2.setIscheck(1);
								break;
							}
						}
					}
				}
			}
		}
		modelMap.put("sysMenuList", sysMenuList);
		return "admin/sys/sysRole-edit";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/sysRole/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response, SysRole sysRole) {
		ResponseFrame frame = null;
		try {
			if(sysRole.getRoleId() == null) {
				frame = sysRoleService.save(sysRole);
			} else {
				frame = sysRoleService.update(sysRole);
			}
		} catch (Exception e) {
			logger.error("保存异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/sysRole/f-json/del")
	@ResponseBody
	public void del(HttpServletRequest request, HttpServletResponse response,
			Integer roleId) {
		ResponseFrame frame = null;
		try {
			frame = sysRoleService.delete(roleId);
		} catch (Exception e) {
			LOGGER.error("删除记录异常: " + e.getMessage(), e);
		}
		writerJson(response, frame);
	}
}