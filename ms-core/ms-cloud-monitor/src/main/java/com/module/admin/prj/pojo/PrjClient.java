package com.module.admin.prj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * prj_client实体
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Alias("prjClient")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class PrjClient extends BaseEntity implements Serializable {
	//项目编号
	private Integer prjId;
	//客户端编号
	private String clientId;
	//状态[10待发布、20发布中、30发布失败、40发布成功]PrjClientStatus
	private Integer status;
	//状态消息
	private String statusMsg;
	//发布时间
	private Date releaseTime;
	//客户端执行的Shell命令
	private String shellScript;
	//版本编号
	private String version;
	//日志路径
	private String logPath;
	
	//=========================== 扩展属性
	//状态名称
	private String statusName;
	//客户端ip
	private String ip;
	//客户端端口
	private Integer port;
	
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	public String getShellScript() {
		return shellScript;
	}
	public void setShellScript(String shellScript) {
		this.shellScript = shellScript;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}