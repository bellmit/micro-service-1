package com.system.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.comm.model.BaseEntity;
import com.system.comm.utils.FrameMapUtil;

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
	//有权访问的地址
	private Map<String, List<String>> urlMap = new HashMap<String, List<String>>();

	public AuthClient(String id, String name, String domain, String sercret, String redirectUri) {
		this(id, name, domain, sercret, redirectUri, null);
	}
	public AuthClient(String id, String name, String domain, String sercret, String redirectUri, List<Map<String, Object>> urls) {
		super();
		this.id = id;
		this.name = name;
		this.domain = domain;
		this.sercret = sercret;
		this.redirectUri = redirectUri;
		if(urls != null) {
			for (Map<String, Object> apiMap : urls) {
				String prjCode = FrameMapUtil.getString(apiMap, "prjCode");
				String url = FrameMapUtil.getString(apiMap, "url");
				List<String> apis = urlMap.get(prjCode);
				if(apis == null) {
					apis = new ArrayList<String>();
				}
				apis.add(url);
				urlMap.put(prjCode, apis);
			}
		}
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
	public Map<String, List<String>> getUrlMap() {
		return urlMap;
	}
	public void setUrlMap(Map<String, List<String>> urlMap) {
		this.urlMap = urlMap;
	}
}