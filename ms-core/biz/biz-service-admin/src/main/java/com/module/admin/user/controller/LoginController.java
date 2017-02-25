package com.module.admin.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.user.pojo.LoginUser;
import com.module.admin.user.service.UserInfoService;
import com.module.admin.user.utils.UserUtil;
import com.module.comm.csrf.CsrfToken;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 用户登录退出等操作
 * @author yuejing
 * @date 2016-05-22 11:17:54
 * @version V1.0.0
 */
@Controller
public class LoginController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(LoginController.class);
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 登录
	 */
	@RequestMapping(value = "/user/json/login")
    @CsrfToken(remove = true)
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		ResponseFrame frame = null;
		try {
			Subject user = SecurityUtils.getSubject();
			String md5Pwd = UserUtil.encryptionPassword(password);
			UsernamePasswordToken token = new UsernamePasswordToken(username, md5Pwd.toCharArray());
			// 默认设置为记住密码，你可以自己在表单中加一个参数来控制
			token.setRememberMe(false);
			user.login(token);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.SUCC.getCode());
		} catch (Exception e) {
			LOGGER.error("登录异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(-2);
			frame.setMessage(e.getMessage());
		}
		writerJson(response, frame);
	}

	/**
	 * 管理中心
	 */
	@RequestMapping(value = "/user/f-view/main")
	public String main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		/*String menuIds = sysRoleService.get(userInfo.getRoleId()).getGrantids();
		List<SysMenu> menuList = sysMenuService.findTreeByMenuIds(menuIds);
		modelMap.put("menuList", menuList);
		
		setSession(request, SessionCons.USER_MENU, sysMenuService.findByMenuIds(menuIds));*/
		modelMap.put("loginUser", getCurrentUser());
		return "admin/main";
	}

	/**
	 * 修改密码页面
	 */
	@RequestMapping(value = "/user/f-view/updatePwd")
    @CsrfToken(create = true)
	public String updatePwd(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		/*String menuIds = sysRoleService.get(userInfo.getRoleId()).getGrantids();
		List<SysMenu> menuList = sysMenuService.findTreeByMenuIds(menuIds);
		modelMap.put("menuList", menuList);
		
		setSession(request, SessionCons.USER_MENU, sysMenuService.findByMenuIds(menuIds));*/
		modelMap.put("loginUser", getCurrentUser());
		return "admin/user/loginUser-updatePwd";
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/user/f-json/updatePwd")
    @CsrfToken(remove = true)
	@ResponseBody
	public void updatePwd(HttpServletRequest request, HttpServletResponse response,
			String oldPwd, String newPwd) {
		ResponseFrame frame = null;
		try {
			LoginUser loginUser = getCurrentUser();
			oldPwd = UserUtil.encryptionPassword(oldPwd);
			newPwd = UserUtil.encryptionPassword(newPwd);
			frame = userInfoService.updatePassword(loginUser.getUserId(), oldPwd, newPwd);
		} catch (Exception e) {
			LOGGER.error("修改密码异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	
	/**
     * 未授权
     * @return {String}
     */
	@RequestMapping("/user/view/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/index.jsp";
        }
        return "unauth";
    }

    /**
     * 退出
     * @return {Result}
     */
	@RequestMapping(value = "/user/f-view/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("退出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index.jsp";
        /*ResponseFrame frame = new ResponseFrame();
        frame.setCode(ResponseCode.SUCC.getCode());
		writerJson(response, frame);*/
    }
}