package com.module.admin.prj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * prj_version实体
 * @author yuejing
 * @date 2016-10-19 15:55:36
 * @version V1.0.0
 */
@Alias("prjVersion")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class PrjVersion extends BaseEntity implements Serializable {
	//项目编号
	private Integer prjId;
	//版本号
	private String version;
	//描叙
	private String remark;
	//添加时间
	private Date createTime;
	//添加人
	private Integer userId;
	//是否发布
	private Integer isRelease;
	//版本所在的路径
	private String pathUrl;
	//回滚版本
	private String rbVersion;
	
	//========================= 扩展属性
	//是否发布名称
	private String isReleaseName;
	
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRbVersion() {
		return rbVersion;
	}
	public void setRbVersion(String rbVersion) {
		this.rbVersion = rbVersion;
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
	
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
	
	public String getPathUrl() {
		return pathUrl;
	}
	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
	public String getIsReleaseName() {
		return isReleaseName;
	}
	public void setIsReleaseName(String isReleaseName) {
		this.isReleaseName = isReleaseName;
	}
}