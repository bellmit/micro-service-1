package com.frame.sys.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.comm.model.BaseEntity;

/**
 * 字典值的实体
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
@SuppressWarnings("serial")
@Alias("sysDict")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysDict extends BaseEntity implements Serializable {
	
	//编号
	private String dictId;
	//类型编号
	private String typeCode;
	//名称
	private String name;
	//值
	private String value;
	//备注
	private String remark;
	//添加人
	private String addUserId;
	//添加时间
	private Date createTime;
	
	private Integer num;
	private Double dou;
	private SysDictType sdt;
	
	public SysDictType getSdt() {
		return sdt;
	}
	public void setSdt(SysDictType sdt) {
		this.sdt = sdt;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getDou() {
		return dou;
	}
	public void setDou(Double dou) {
		this.dou = dou;
	}
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}