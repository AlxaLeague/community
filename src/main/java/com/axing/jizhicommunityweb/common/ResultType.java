package com.axing.jizhicommunityweb.common;

/**
 * 返回类型
 * Created by YuChaofan on 2017/8/4.
 */
public enum ResultType {
    NORMAL_RETURNED(1000, "Normal Returned"),
    REQUEST_TYPE_ERROR(1001, "Request Type Error"),
    RESPONSE_ERROR(1002, "Response Error"),
    PARAMS_ERROR(1003, "Parameters Error"),
    DATABASE_ERROR(1004, "Database Error"),
    NOT_LOGIN(1005, "Not Login"),
    FORBIDDEN(1006, "Access Forbidden"),
    UNKNOWN(1007, "Unknow"),
    SERVER_ERROR(1008, "Server Error"),
    SERVICE_SUSPEND(1009, "Service Suspend");

    private int code;
    private String msg;

    ResultType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}