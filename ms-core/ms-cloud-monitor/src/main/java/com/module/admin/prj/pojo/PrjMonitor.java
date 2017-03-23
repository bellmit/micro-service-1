package com.module.admin.prj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * prj_monitor实体
 * @author yuejing
 * @date 2016-11-30 13:29:59
 * @version V1.0.0
 */
@Alias("prjMonitor")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class PrjMonitor extends BaseEntity implements Serializable {
	//编号
	private Integer prjmId;
	//项目编号
	private Integer prjId;
	//监控类型[10服务、20数据库、30缓存、40其它]
	private Integer type;
	//描叙，地址
	private String remark;
	//是否检测
	private Integer monitorIs;
	//检测成功标识
	private String monitorSuccStr;
	//检测状态[10正常、20异常]
	private Integer monitorStatus;
	//检测地址
	private String monitorUrl;
	//检测失败最大次数提醒[0代表不提醒]
	private Integer monitorFailNumRemind;
	//检测失败接收邮箱
	private String monitorFailEmail;
	//检测失败发送信息间隔[单位：分钟]
	private Integer monitorFailSendInterval;
	//检测时间
	private Date monitorTime;
	//检测失败次数
	private Integer monitorFailNum;
	//检测失败发送信息时间
	private Date monitorFailSendTime;
	
	//==================================== 扩展属性
	//项目名称
	private String prjName;
	//类型名称
	private String typeName;
	//是否检测名称
	private String monitorIsName;
	//监控状态名称
	private String monitorStatusName;
	
	public Integer getPrjmId() {
		return prjmId;
	}
	public void setPrjmId(Integer prjmId) {
		this.prjmId = prjmId;
	}
	
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getMonitorIs() {
		return monitorIs;
	}
	public void setMonitorIs(Integer monitorIs) {
		this.monitorIs = monitorIs;
	}
	
	public String getMonitorSuccStr() {
		return monitorSuccStr;
	}
	public void setMonitorSuccStr(String monitorSuccStr) {
		this.monitorSuccStr = monitorSuccStr;
	}
	
	public Integer getMonitorStatus() {
		return monitorStatus;
	}
	public void setMonitorStatus(Integer monitorStatus) {
		this.monitorStatus = monitorStatus;
	}
	
	public String getMonitorUrl() {
		return monitorUrl;
	}
	public void setMonitorUrl(String monitorUrl) {
		this.monitorUrl = monitorUrl;
	}
	
	public Date getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Date monitorTime) {
		this.monitorTime = monitorTime;
	}
	
	public Integer getMonitorFailNum() {
		return monitorFailNum;
	}
	public void setMonitorFailNum(Integer monitorFailNum) {
		this.monitorFailNum = monitorFailNum;
	}
	
	public Integer getMonitorFailNumRemind() {
		return monitorFailNumRemind;
	}
	public void setMonitorFailNumRemind(Integer monitorFailNumRemind) {
		this.monitorFailNumRemind = monitorFailNumRemind;
	}
	
	public String getMonitorFailEmail() {
		return monitorFailEmail;
	}
	public void setMonitorFailEmail(String monitorFailEmail) {
		this.monitorFailEmail = monitorFailEmail;
	}
	
	public Date getMonitorFailSendTime() {
		return monitorFailSendTime;
	}
	public void setMonitorFailSendTime(Date monitorFailSendTime) {
		this.monitorFailSendTime = monitorFailSendTime;
	}
	
	public Integer getMonitorFailSendInterval() {
		return monitorFailSendInterval;
	}
	public void setMonitorFailSendInterval(Integer monitorFailSendInterval) {
		this.monitorFailSendInterval = monitorFailSendInterval;
	}
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	public String getMonitorIsName() {
		return monitorIsName;
	}
	public void setMonitorIsName(String monitorIsName) {
		this.monitorIsName = monitorIsName;
	}
	public String getMonitorStatusName() {
		return monitorStatusName;
	}
	public void setMonitorStatusName(String monitorStatusName) {
		this.monitorStatusName = monitorStatusName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}