package com.module.admin.ms.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * ms_config_value实体
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
@Alias("msConfigValue")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class MsConfigValue extends BaseEntity implements Serializable {
	//配置编号
	private Integer configId;
	//key的编码
	private String code;
	//value
	private String value;
	//备注
	private String remark;
	//排序
	private Integer orderby;
	//创建时间
	private Date createTime;
	//添加人
	private Integer userId;
	
	public MsConfigValue() {
		super();
	}
	public MsConfigValue(Integer configId, String code, String value,
			String remark, Integer orderby, Integer userId) {
		super();
		this.configId = configId;
		this.code = code;
		this.value = value;
		this.remark = remark;
		this.orderby = orderby;
		this.userId = userId;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOrderby() {
		return orderby;
	}
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
}