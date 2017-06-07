package com.module.admin.ms.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * ms_secret实体
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
@Alias("msSecret")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class MsSecret extends BaseEntity implements Serializable {
	//客户端编号
	private String cliId;
	//名称
	private String name;
	//备注
	private String remark;
	//密钥
	private String token;
	//主路径
	private String domain;
	//是否使用
	private Integer isUse;
	//创建时间
	private Date createTime;
	
	//======================== 扩展属性
	//是否使用名称
	private String isUseName;
	
	public String getCliId() {
		return cliId;
	}
	public void setCliId(String cliId) {
		this.cliId = cliId;
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
	
	public String getIsUseName() {
		return isUseName;
	}
	public void setIsUseName(String isUseName) {
		this.isUseName = isUseName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
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
}
