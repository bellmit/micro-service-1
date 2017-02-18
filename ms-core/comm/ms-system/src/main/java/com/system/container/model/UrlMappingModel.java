package com.system.container.model;

import java.io.Serializable;

public class UrlMappingModel implements Serializable {

	private static final long serialVersionUID = 5417553993278124018L;
	private String url;
	private String beanName;
	private String method;
	
	public UrlMappingModel() {
		super();
	}
	public UrlMappingModel(String url, String beanName, String method) {
		super();
		this.url = url;
		this.beanName = beanName;
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}