package com.frame.user.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * 用户日志的实体
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
@SuppressWarnings("serial")
@Alias("userLog")
@JsonInclude(Include.NON_NULL)
public class UserLog extends BaseEntity implements Serializable {
	//编号
	private String logId;
	//用户编号
	private String userId;
	//ip地址
	private String ip;
	//类型[10登录、20操作]
	private Integer type;
	//说明
	private String remark;
	//添加时间
	private Date createTime;
	
	//========================== 扩展属性
	
	public String getUserId() {
		return userId;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}