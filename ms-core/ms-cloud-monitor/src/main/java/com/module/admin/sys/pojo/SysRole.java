package com.module.admin.sys.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.comm.model.BaseEntity;

/**
 * 角色的实体
 * @author duanbin
 * @date 2016-05-08 09:47:06
 * @version V1.0.0
 */
@Alias("sysRole")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysRole extends BaseEntity implements Serializable {
	//编号
	private Integer roleId;
	//名称
	private String name;
	//备注
	private String remark;
	//权限编号集合
	private String grantids;
	//父编号
	private Integer pid;
	//负责人，多个,分隔
	private String dutyUserIds;
	
	//======================== 扩展属性
	//状态名称
	private String statusname;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGrantids() {
		return grantids;
	}

	public void setGrantids(String grantids) {
		this.grantids = grantids;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getDutyUserIds() {
		return dutyUserIds;
	}

	public void setDutyUserIds(String dutyUserIds) {
		this.dutyUserIds = dutyUserIds;
	}
}