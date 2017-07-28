package com.module.admin.code.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * code_template实体
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Alias("codeTemplate")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class CodeTemplate extends BaseEntity implements Serializable {
	//源码编号
	private String code;
	//类型[10java、20jsp、30其它文件]CodeTemplateType
	private Integer type;
	//文件名称
	private String name;
	//描叙
	private String remark;
	//后缀
	private String suffix;
	//包名
	private String packageName;
	//模板内容
	private String content;
	//模板路劲
	private String path;
	
	//============================= 扩展属性
	//项目编号
	private Integer prjId;
	//创建人
	private Integer userId;
	//类型名称
	private String typeName;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getPrjId() {
		return prjId;
	}
	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}