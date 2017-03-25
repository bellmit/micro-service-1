package com.client.rel.model;

import java.io.Serializable;

/**
 * 项目对象
 * @author yuejing
 * @date 2016年10月21日 下午10:38:23
 * @version V1.0.0
 */
public class Project implements Serializable {

	private static final long serialVersionUID = -1878111502460993514L;

	//项目编号
	private Integer prjId;
	//项目编码
	private String code;
	//项目名称
	private String name;
	//项目执行的shell命令
	private String shellScript;
	//当前发布版本
	private String curVersion;
	//当前版本下载路径
	private String pathUrl;
	
	public Project() {
		super();
	}
	public Project(Integer prjId, String code, String name, String shellScript,
			String curVersion, String pathUrl) {
		super();
		this.prjId = prjId;
		this.code = code;
		this.name = name;
		this.shellScript = shellScript;
		this.curVersion = curVersion;
		this.pathUrl = pathUrl;
	}
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
	public String getShellScript() {
		return shellScript;
	}
	public void setShellScript(String shellScript) {
		this.shellScript = shellScript;
	}
	public String getCurVersion() {
		return curVersion;
	}
	public void setCurVersion(String curVersion) {
		this.curVersion = curVersion;
	}
	public String getPathUrl() {
		return pathUrl;
	}
	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
}
