package com.system.comm.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Key/Value的实体类
 * @author yuejing
 * @date 2013-8-10 下午5:16:33
 * @version V1.0.0
 */
@Alias("kvEntity")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KvEntity implements Serializable {

	private static final long serialVersionUID = -4495987129630008126L;
	private String code;
	private String value;
	private String pcode;
	private String exp;
	
	public KvEntity() {
		super();
	}

	public KvEntity(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}
	public KvEntity(String code, String value, String exp) {
		super();
		this.code = code;
		this.value = value;
		this.exp = exp;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
}