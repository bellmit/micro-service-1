package com.system.handle.model;

/**
 * 响应编码
 * @author yuejing
 * @date 2016年1月29日 下午5:18:37
 * @version V1.0.0
 */
public enum ResponseCode {

    SUCC(0, "success"),
    FAIL(-1, "failed"),

    // 系统级错误
    SERVER_ERROR(10002, "server error"),
    SQL_INSERT_FAULT(10003, "server error"),

    // 数据级错误
    INVALID_TOKEN(20002, "invalid token,non userid"),

    /**
     * http部分
     */
    // 入参错误
    /** 签名异常 */
    ABNORMAL_SIGNATURE(50001, "abnormal signature"),
    INVALID_REQUEST(50003, "invalid request"),
    INVALID_URL(50005, "server error"),
    INVALID_REQUEST_PARAMETERS(50010, "invalid request parameters"),
    UNHANDLE_METHOD(50020, "request method is not handled"),

    NOT_AUTHOR(-998, "用户没有权限"),
    NOT_LOGIN(-999, "用户没有登录"),
    ;

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseCode get(int codeValue) {
        for (ResponseCode code : ResponseCode.values()) {
            if (code.getCode() == codeValue) {
                return code;
            }
        }
        return ResponseCode.SERVER_ERROR;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
