package com.frame.user.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.comm.model.BaseEntity;

/**
 * 用户角色的实体
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
@SuppressWarnings("serial")
@Alias("userRole")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserRole extends BaseEntity implements Serializable {
	//用户编号
	private String userId;
	//角色编号
	private String roleId;
	//添加人
	private String addUserId;
	//添加时间
	private Date createTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}