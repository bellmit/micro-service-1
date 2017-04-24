package com.module.admin.ms.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * ms_config实体
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
@Alias("msConfig")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class MsConfig extends BaseEntity implements Serializable {
	//编号
	private Integer configId;
	//文件名称
	private String name;
	//备注
	private String remark;
	//是否使用
	private Integer isUse;
	//创建日期
	private Date createTime;
	//添加人
	private Integer userId;
	
	//========================== 扩展属性
	//是否使用显示值
	private String isUseName;
	//属性集合
	private List<MsConfigValue> values;
	
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	
	public String getIsUseName() {
		return isUseName;
	}
	public void setIsUseName(String isUseName) {
		this.isUseName = isUseName;
	}
	public List<MsConfigValue> getValues() {
		return values;
	}
	public void setValues(List<MsConfigValue> values) {
		this.values = values;
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
	
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
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
}
