package com.module.admin.sys.controller;

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
import com.module.admin.sys.pojo.SysUser;
import com.module.admin.sys.service.SysMenuService;
import com.module.admin.sys.service.SysRoleService;
import com.module.admin.sys.service.SysUserService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * @author duanbin
 * @date 2016-05-22 11:17:54
 * @version V1.0.0
 */
@Controller
public class SysUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "/sysUser/json/login")
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		ResponseFrame frame = null;
		try {
			frame = sysUserService.login(username, password);
			if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
				setSessionUser(request, (SysUser) frame.getBody());
			}
		} catch (Exception e) {
			logger.error("登录异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	/**
	 * 管理中心
	 */
	@RequestMapping(value = "/sysUser/f-view/main")
	public String main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		//SysUser userInfo = getSessionUser(request);
		/*String menuIds = sysRoleService.get(userInfo.getRoleId()).getGrantids();
		List<SysMenu> menuList = sysMenuService.findTreeByMenuIds(menuIds);
		modelMap.put("menuList", menuList);
		
		setSession(request, SessionCons.USER_MENU, sysMenuService.findByMenuIds(menuIds));*/
		return "admin/main";
	}
	/**
	 * 退出
	 */
	@RequestMapping(value = "/sysUser/f-view/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		removeSessionUser(request);
		return "redirect:/index.jsp";
	}
	
	/**
	 * 用户管理页面
	 */
	@RequestMapping(value = "/sysUser/f-view/manager")
	public String manager(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "admin/sys/user-manager";
	}


	/**
	 * 分页获取信息
	 */
	@RequestMapping(value = "/sysUser/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response, SysUser userInfo) {
		ResponseFrame frame = null;
		try {
			frame = sysUserService.pageQuery(userInfo);
		} catch (Exception e) {
			logger.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	
	/**
	 * 跳转到编辑用户
	 */
	@RequestMapping(value = "/sysUser/f-view/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Integer userId) {
		if(userId != null) {
			modelMap.put("sysUser", sysUserService.get(userId));
		}
		return "admin/sys/user-edit";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/sysUser/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response, SysUser userInfo) {
		ResponseFrame frame = null;
		try {
			if(userInfo.getUserId() == null) {
				frame = sysUserService.save(userInfo);
			} else {
				frame = sysUserService.update(userInfo);
			}
		} catch (Exception e) {
			logger.error("保存异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param userId
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f-json/resetPwd")
	@ResponseBody
	public void resetPwd(HttpServletRequest request, HttpServletResponse response, Integer userId, String password) {
		ResponseFrame frame = null;
		try {
			frame = sysUserService.resetPwd(userId, password);
		} catch (Exception e) {
			logger.error("重置密码异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param orgpwd
	 * @param newpwd
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f-json/updatePwd")
	@ResponseBody
	public void updatePwd(HttpServletRequest request, HttpServletResponse response,
			String orgpwd, String newpwd) {
		ResponseFrame frame = null;
		try {
			SysUser sysUser = getSessionUser(request);
			frame = sysUserService.updatePwd(sysUser, orgpwd, newpwd);
			if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
				setSessionUser(request, (SysUser) frame.getBody());
			}
		} catch (Exception e) {
			logger.error("修改密码异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	/**
	 * 修改状态
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f-json/updateStatus")
	@ResponseBody
	public void updateStatus(HttpServletRequest request, HttpServletResponse response,
			String userId, String status) {
		ResponseFrame frame = null;
		try {
			frame = sysUserService.updateStatus(userId, status);
		} catch (Exception e) {
			logger.error("操作异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response, Integer userId) {
		ResponseFrame frame = null;
		try {
			frame = sysUserService.delete(userId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}