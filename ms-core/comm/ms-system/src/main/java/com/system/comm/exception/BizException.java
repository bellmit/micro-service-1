package com.system.comm.exception;

/**
 * 业务异常
 * @author yuejing
 * @date 2013-8-10 下午5:16:33
 * @version V1.0.0
 */
@SuppressWarnings("serial")
public class BizException extends Exception {
	
	private String code;
	private String msg;
	
	public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
	
	public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}