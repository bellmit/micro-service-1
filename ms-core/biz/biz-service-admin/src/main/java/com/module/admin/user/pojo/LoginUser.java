package com.module.admin.user.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.module.admin.sys.pojo.SysRes;

/**
 * @description：自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 * @author：yuejing
 * @date：2017-02-22
 */
@JsonInclude(Include.NON_NULL)
public class LoginUser implements Serializable {

	private static final long serialVersionUID = -1373760761780840081L;
	private String userId;
	private String userName;
	//自己拥有的资源集合
	public List<SysRes> resList;
	//鉴权的resId
	public Set<String> resIdSet;

	public LoginUser() {
		super();
	}
	public LoginUser(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<SysRes> getResList() {
		return resList;
	}
	public void setResList(List<SysRes> resList) {
		this.resList = resList;
	}
	public Set<String> getResIdSet() {
		return resIdSet;
	}
	public void setResIdSet(Set<String> resIdSet) {
		this.resIdSet = resIdSet;
	}
}