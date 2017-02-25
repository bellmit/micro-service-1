package com.module.admin.sys.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * 系统资源的实体
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class SysRes extends BaseEntity implements Serializable {
	//资源编号
	private String resId;
	//类型[10菜单、20功能]
	private Integer type;
	//父级功能编码
	private String parentResId;
	//名称
	private String name;
	//显示名称
	private String showName;
	//跳转地址
	private String url;
	//备注
	private String remark;
	//排序
	private Integer orderby;
	//添加人
	private String addUserId;
	//添加时间
	private Date createTime;
	
	public SysRes() {
		super();
	}
	public SysRes(String resId, Integer type, String parentResId, String name,
			String showName, String url, String remark, Integer orderby) {
		super();
		this.resId = resId;
		this.type = type;
		this.parentResId = parentResId;
		this.name = name;
		this.showName = showName;
		this.url = url;
		this.remark = remark;
		this.orderby = orderby;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getParentResId() {
		return parentResId;
	}
	public void setParentResId(String parentResId) {
		this.parentResId = parentResId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Integer getOrderby() {
		return orderby;
	}
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
}