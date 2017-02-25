package com.module.comm.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.shiro.SecurityUtils;

import com.module.admin.user.pojo.LoginUser;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameStringUtil;

/**
 * 判断是否有权限的类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2014年12月25日 下午2:39:55 
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class AuthTag extends TagSupport {
	
	//功能点id
	private String mid;
	
	@Override
	public int doStartTag() throws JspException {
		/*List<SysMenu> menus = (List<SysMenu>) pageContext.getSession().getAttribute(SessionCons.USER_MENU);
		if(menus == null) {
			return SKIP_BODY;
		}
		for(SysMenu m : menus) {
			if(m.getMenuId().intValue() == Integer.parseInt(mid)){
				return EVAL_BODY_INCLUDE;
			}
		}*/
		String userString = (String) SecurityUtils.getSubject().getPrincipal();
		if(FrameStringUtil.isEmpty(userString)) {
			return SKIP_BODY;
		}
		LoginUser user = FrameJsonUtil.toObject(userString, LoginUser.class);
		//LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if(user != null && user.getResIdSet() != null && user.getResIdSet().contains(mid)) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
		this.mid = null;
	}

	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
}