package com.module.admin.ms.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * ms_secret_api实体
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
@Alias("msSecretApi")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class MsSecretApi extends BaseEntity implements Serializable {
	//客户端编号
	private String cliId;
	//项目编号
	private Integer prjId;
	//项目编码
	private String prjCode;
	//API地址
	private String url;
	//创建时间
	private Date createTime;
	
	//================================ 扩展属性
	//项目名称
	private String prjName;
	
	public String getCliId() {
		return cliId;
	}
	public void setCliId(String cliId) {
		this.cliId = cliId;
	}
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	public String getPrjCode() {
		return prjCode;
	}
	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
}