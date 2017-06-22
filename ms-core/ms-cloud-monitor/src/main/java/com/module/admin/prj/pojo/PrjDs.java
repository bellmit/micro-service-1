package com.module.admin.prj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * prj_ds实体
 * @author yuejing
 * @date 2017-06-21 14:43:51
 * @version V1.0.0
 */
@Alias("prjDs")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class PrjDs extends BaseEntity implements Serializable {
	//数据源编码
	private String code;
	//项目编号
	private Integer prjId;
	//数据库类型[mysql/oracle]
	private String type;
	//驱动类
	private String driverClass;
	//jdbc的url
	private String url;
	//用户名
	private String username;
	//密码
	private String password;
	//初始连接数
	private Integer initialSize;
	//最大连接数
	private Integer maxIdle;
	//最小连接数
	private Integer minIdle;
	//测试的sql语句
	private String testSql;
	//添加人
	private Integer userId;
	//添加时间
	private Date createTime;
	
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
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}
	
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	
	public Integer getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	
	public String getTestSql() {
		return testSql;
	}
	public void setTestSql(String testSql) {
		this.testSql = testSql;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
