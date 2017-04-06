package com.module.admin.prj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * prj_info实体
 * @author yuejing
 * @date 2016-10-19 15:56:37
 * @version V1.0.0
 */
@Alias("prjInfo")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class PrjInfo extends BaseEntity implements Serializable {
	//编号
	private Integer prjId;
	//项目编码
	private String code;
	//名称
	private String name;
	//描叙
	private String remark;
	//添加时间
	private Date createTime;
	//添加人
	private Integer userId;
	//状态[10正常、20停用]
	private Integer status;
	//容器类型[10tomcat、20自定义服务、100其它]
	private Integer container;
	//shell脚本
	private String shellScript;
	//监控状态是否正常
	private Integer monitorStatus;
	//监控消息
	private String monitorMsg;
	
	//======================= 扩展属性
	//状态名称
	private String statusName;
	
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	
	public Integer getContainer() {
		return container;
	}
	public void setContainer(Integer container) {
		this.container = container;
	}
	
	public String getShellScript() {
		return shellScript;
	}
	public void setShellScript(String shellScript) {
		this.shellScript = shellScript;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getMonitorStatus() {
		return monitorStatus;
	}
	public void setMonitorStatus(Integer monitorStatus) {
		this.monitorStatus = monitorStatus;
	}
	public String getMonitorMsg() {
		return monitorMsg;
	}
	public void setMonitorMsg(String monitorMsg) {
		this.monitorMsg = monitorMsg;
	}
}