package com.module.admin.prj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * prj_version_script实体
 * @author yuejing
 * @date 2017-07-04 09:46:05
 * @version V1.0.0
 */
@Alias("prjVersionScript")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class PrjVersionScript extends BaseEntity implements Serializable {
	//编号
	private Integer pvsId;
	//项目编号
	private Integer prjId;
	//版本号
	private String version;
	//备注
	private String remark;
	//数据源编号
	private String dsCode;
	//升级脚本
	private String upSql;
	//回滚脚本
	private String callbackSql;
	//是否升级
	private Integer isUp;
	//添加时间
	private Date createTime;
	//添加人
	private Integer userId;
	
	//========================== 扩展属性
	//是否升级
	private String isUpName;
	
	public Integer getPvsId() {
		return pvsId;
	}
	public void setPvsId(Integer pvsId) {
		this.pvsId = pvsId;
	}
	
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
	
	public Integer getIsUp() {
		return isUp;
	}
	public void setIsUp(Integer isUp) {
		this.isUp = isUp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDsCode() {
		return dsCode;
	}
	public void setDsCode(String dsCode) {
		this.dsCode = dsCode;
	}
	
	public String getUpSql() {
		return upSql;
	}
	public void setUpSql(String upSql) {
		this.upSql = upSql;
	}
	
	public String getCallbackSql() {
		return callbackSql;
	}
	public void setCallbackSql(String callbackSql) {
		this.callbackSql = callbackSql;
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
	public String getIsUpName() {
		return isUpName;
	}
	public void setIsUpName(String isUpName) {
		this.isUpName = isUpName;
	}
}