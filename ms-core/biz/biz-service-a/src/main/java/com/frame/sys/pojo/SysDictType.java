package com.frame.sys.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.comm.model.BaseEntity;

/**
 * 字典类型的实体
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
@SuppressWarnings("serial")
@Alias("sysDictType")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysDictType extends BaseEntity implements Serializable {
	//编号
	private String code;
	//类型名称
	private String name;
	//备注
	private String remark;
	//父类型编号[0代表一级]
	private String parentCode;
	//添加人
	private String addUserId;
	//添加时间
	private Date createTime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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