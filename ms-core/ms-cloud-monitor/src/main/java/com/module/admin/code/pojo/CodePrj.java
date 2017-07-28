package com.module.admin.code.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * code_prj实体
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Alias("codePrj")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class CodePrj extends BaseEntity implements Serializable {
	//编码
	private String code;
	//项目编号
	private Integer prjId;
	//名称
	private String name;
	//创建时间
	private Date createTime;
	//创建人
	private Integer userId;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
