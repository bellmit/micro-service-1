package com.module.admin.cli.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * cli_info实体
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
@Alias("cliInfo")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class CliInfo extends BaseEntity implements Serializable {
	//客户端编号
	private String clientId;
	//名称
	private String name;
	//描叙
	private String remark;
	//ip地址
	private String ip;
	//端口
	private Integer port;
	//密钥
	private String token;
	//添加时间
	private Date createTime;
	//添加人
	private Integer userId;
	//状态[10正常、20停用、30心跳失败]
	private Integer status;
	//活动状态[10正常、20心跳异常]
	private Integer activityStatus;
	//上次活动时间
	private Date activityTime;
	
	//============================== 扩展属性
	//状态名称
	private String statusName;
	//活动状态名称
	private String activityStatusName;
	//搜索字符串
	private String searchString;
	//发布时间
	private Date releaseTime;
	//日志路径
	private String logPath;
	//客户端shell
	private String shellScript;
	//版本编号
	private String version;
	//版本地址
	private String pathUrl;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public String getShellScript() {
		return shellScript;
	}
	public void setShellScript(String shellScript) {
		this.shellScript = shellScript;
	}
	public String getActivityStatusName() {
		return activityStatusName;
	}
	public void setActivityStatusName(String activityStatusName) {
		this.activityStatusName = activityStatusName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPathUrl() {
		return pathUrl;
	}
	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
}