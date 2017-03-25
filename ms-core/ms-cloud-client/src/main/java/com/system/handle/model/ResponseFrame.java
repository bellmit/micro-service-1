package com.system.handle.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 响应框架实体
 * @author yuejing
 * @date 2016年1月29日 下午9:29:33
 * @version V1.0.0
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseFrame implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code = 0;
    private String message = "success";
    private Long time = System.currentTimeMillis();
    private String version = "1.0.0";
    private Object body = new HashMap<String, Object>();

    /**
     * code默认为成功
     */
    public ResponseFrame() {
        //默认为成功
    	this.code = ResponseCode.SUCC.getCode();
    }

    public ResponseFrame(Integer code, String message) {
    	this.code = code;
        this.message = message;
    }

    public ResponseFrame(Integer code, Object body) {
    	this.code = code;
        this.body = body;
    }

    public ResponseFrame(ResponseCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ResponseFrame(ResponseCode errorCode, Map<String, Object> body) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.body = body;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public static ResponseFrame ok() {
        return new ResponseFrame();
    }

}
