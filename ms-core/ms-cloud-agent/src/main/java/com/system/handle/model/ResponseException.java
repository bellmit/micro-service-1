package com.system.handle.model;

/**
 * 异常
 * @author yuejing
 * @date 2016年1月29日 下午5:19:01
 * @version V1.0.0
 */
@SuppressWarnings("serial")
public class ResponseException extends Exception {

	private ResponseCode responseCode;
	private int code;
	private String message;

	public ResponseException(int code) {
		this.responseCode = ResponseCode.get(code);
		setCode();
	}

	public ResponseException(ResponseCode responseCode) {
		this.responseCode = responseCode;
		setCode();
	}

	/*
	 * 设置code和message的信息
	 */
	private void setCode() {
		if(this.responseCode != null) {
			this.code = responseCode.getCode();
			this.message = this.responseCode.getMessage();
		}
	}

	public ResponseException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}