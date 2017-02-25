package com.module.comm.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.module.admin.sys.pojo.SysRes;
import com.module.admin.user.pojo.LoginUser;
import com.module.admin.user.service.UserInfoService;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameMapUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * shiro权限认证
 * @author yuejing
 * @version 2016/05/21 1.0.0
 */
public class ShiroDbRealm extends AuthorizingRealm {
    private static final Logger LOGGER = Logger.getLogger(ShiroDbRealm.class);

    @Autowired
    private UserInfoService userInfoService;
    /*@Autowired
    private IRoleService roleService;*/

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        LOGGER.info("Shiro开始登录认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String password = new String(token.getPassword());
        ResponseFrame loginFrame = userInfoService.login(token.getUsername(), password);
        if(ResponseCode.SUCC.getCode() != loginFrame.getCode().intValue()) {
        	throw new AuthenticationException(loginFrame.getMessage());
        }
        LoginUser loginUser = (LoginUser) loginFrame.getBody();
        
        //获取资源信息
        ResponseFrame resFrame = userInfoService.findResListByUserId(loginUser.getUserId());
        if(ResponseCode.SUCC.getCode() != resFrame.getCode().intValue()) {
        	throw new AuthenticationException("获取权限失败");
        }
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> resMapList = (List<Map<String, Object>>) resFrame.getBody();
        List<SysRes> resList = new ArrayList<SysRes>();
        Set<String> resIdSet = new HashSet<String>();
        for (Map<String, Object> map : resMapList) {
        	String resId = FrameMapUtil.getString(map, "resId");
        	Integer type = FrameMapUtil.getInteger(map, "type");
        	String parentResId = FrameMapUtil.getString(map, "parentResId");
        	String name = FrameMapUtil.getString(map, "name");
        	String showName = FrameMapUtil.getString(map, "showName");
        	String url = FrameMapUtil.getString(map, "url");
        	String remark = FrameMapUtil.getString(map, "remark");
        	Integer orderby = FrameMapUtil.getInteger(map, "orderby");
			SysRes res = new SysRes(resId, type, parentResId, name, showName, url, remark, orderby);
			resList.add(res);
			resIdSet.add(res.getResId());
		}
        loginUser.setResList(resList);
        loginUser.setResIdSet(resIdSet);
        // 认证缓存信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(FrameJsonUtil.toString(loginUser), password.toCharArray(), getName());
        return info;
    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {

        LoginUser loginUser = (LoginUser) principals.getPrimaryPrincipal();
        //List<SysRes> resList = loginUser.getResList();
        //Set<String> urlSet = new HashSet<String>();
        //urlSet.add("USER_MAIN");
        //urlSet.add("/user/f-view/main.shtml");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(loginUser.getResIdSet());
        /*Set<String> roles = new HashSet<String>();
        roles.add("user");
        info.setRoles(roles);*/
        return info;
    }
}
