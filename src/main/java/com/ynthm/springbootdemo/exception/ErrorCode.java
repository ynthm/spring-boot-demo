package com.ynthm.springbootdemo.exception;

/**
 * Author : Ynthm
 */
public enum ErrorCode {

    /**
     * 非 0 可以有异常数据
     */
    SUCCESS(0, "success"),
    NO_LOGIN(4001, "no.login"),
    VALIDATE_FAILED(4002, "validate.failed");



    private int code;
    private String msgCode;


    ErrorCode(int code, String msgCode) {
        this.code = code;
        this.msgCode = msgCode;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}
