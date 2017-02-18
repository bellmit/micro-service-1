package com.system.auth.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.comm.model.BaseEntity;

/**
 * 客户端实体
 * @author yuejing
 * @date 2014-01-11 14:09:30
 * @version V1.0.0
 */
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AuthClient extends BaseEntity implements Serializable {
	//编码
	private String id;
	//名称
	private String name;
	//域名
	private String domain;
	//sercret
	private String sercret;
	//回调地址
	private String redirectUri;
	
	public AuthClient(String id, String name, String domain, String sercret, String redirectUri) {
		super();
		this.id = id;
		this.name = name;
		this.domain = domain;
		this.sercret = sercret;
		this.redirectUri = redirectUri;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSercret() {
		return sercret;
	}
	public void setSercret(String sercret) {
		this.sercret = sercret;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
}