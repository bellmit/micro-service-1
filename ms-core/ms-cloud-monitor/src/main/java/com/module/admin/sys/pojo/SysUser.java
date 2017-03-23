package com.module.admin.sys.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.comm.model.BaseEntity;

/**
 * 后台用户的实体
 * @author duanbin
 * @date 2016年5月5日 上午9:39:21
 * @version V1.0.0
 */
@Alias("sysUser")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysUser extends BaseEntity implements Serializable {
	//编号
	private Integer userId;
	//用户名
	private String username;
	//昵称
	private String nickname;
	//密码
	private String password;
	//创建时间
	private Date createTime;
	//自定义编号
	private String customNo;
	//状态[10正常、20冻结]
	private Integer status;
	
	//============================== 扩展属性
	//状态名称
	private String statusName;

	public SysUser() {
		super();
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCustomNo() {
		return customNo;
	}
	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}