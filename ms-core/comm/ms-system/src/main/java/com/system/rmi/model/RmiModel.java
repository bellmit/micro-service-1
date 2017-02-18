package com.system.rmi.model;

import java.io.Serializable;

/**
 * Rmi信息对应的实体
 * @author yuejing
 *
 */
@SuppressWarnings("serial")
public class RmiModel implements Serializable {

	private String url;
	private String host;
	private Integer port;
	private String clsFullName;
	
	public RmiModel() {
		super();
	}
	public RmiModel(String host, Integer port, String clsFullName) {
		super();
		this.host = host;
		this.port = port;
		this.clsFullName = clsFullName;
		this.url = String.format("rmi://%s:%d/%s", host, port, clsFullName);
	}
	public RmiModel(String url, String host, Integer port, String clsFullName) {
		super();
		this.url = url;
		this.host = host;
		this.port = port;
		this.clsFullName = clsFullName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getClsFullName() {
		return clsFullName;
	}
	public void setClsFullName(String clsFullName) {
		this.clsFullName = clsFullName;
	}
}
