package com.client.rel.model;

import java.io.Serializable;
import java.util.Date;

import com.client.rel.enums.PrjInfoContainer;
import com.system.comm.utils.FrameTimeUtil;

public class ExecShell implements Serializable {

	private static final long serialVersionUID = 8386094393965894700L;

	private Date execTime;
	//shell或bat文件名称
	private String execName;
	//版本
	private String version;
	//路径
	private String path;
	private PrjInfoContainer piContainer;
	
	public ExecShell() {
		super();
	}
	public ExecShell(String execName, String version,
			String path, PrjInfoContainer piContainer) {
		super();
		this.execTime = FrameTimeUtil.getTime();
		this.execName = execName;
		this.version = version;
		this.path = path;
		this.piContainer = piContainer;
	}
	public Date getExecTime() {
		return execTime;
	}
	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}
	public String getExecName() {
		return execName;
	}
	public void setExecName(String execName) {
		this.execName = execName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public PrjInfoContainer getPiContainer() {
		return piContainer;
	}
	public void setPiContainer(PrjInfoContainer piContainer) {
		this.piContainer = piContainer;
	}
}
