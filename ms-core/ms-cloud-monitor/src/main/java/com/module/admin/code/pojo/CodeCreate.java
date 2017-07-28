package com.module.admin.code.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * code_create实体
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Alias("codeCreate")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class CodeCreate extends BaseEntity implements Serializable {
	//编号
	private Integer id;
	//源码编号
	private String code;
	//功能包路径
	private String packagePath;
	//状态[10待生成、20生成中、30生成失败、40生成成功]
	private Integer status;
	//下载地址
	private String download;
	//生成完成时间
	private Date finishTime;
	//数据源编号
	private String dsCode;
	//数据库名或sid
	private String dbName;
	//生成的表集合[多个,分隔]
	private String tables;
	//创建时间
	private Date createTime;
	//创建人
	private Integer userId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getDsCode() {
		return dsCode;
	}
	public void setDsCode(String dsCode) {
		this.dsCode = dsCode;
	}
	
	public String getTables() {
		return tables;
	}
	public void setTables(String tables) {
		this.tables = tables;
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
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}