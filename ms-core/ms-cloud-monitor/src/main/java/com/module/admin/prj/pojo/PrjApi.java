package com.module.admin.prj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * prj_api实体
 * @author yuejing
 * @date 2017-05-18 16:00:41
 * @version V1.0.0
 */
@Alias("prjApi")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class PrjApi extends BaseEntity implements Serializable {
	//项目编号
	private Integer prjId;
	//路径
	private String path;
	//名称
	private String name;
	//方法详情
	private String method;
	//参数
	private String params;
	//结果
	private String response;
	//是否使用
	private Integer isUse;
	//新增时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	
	//============================ 扩展属性
	//是否使用名称
	private String isUseName;
	
	public PrjApi() {
		super();
	}
	public PrjApi(Integer prjId, String path, String name, String method, String params, String response) {
		super();
		this.prjId = prjId;
		this.path = path;
		this.name = name;
		this.method = method;
		this.params = params;
		this.response = response;
	}
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getIsUseName() {
		return isUseName;
	}
	public void setIsUseName(String isUseName) {
		this.isUseName = isUseName;
	}
}