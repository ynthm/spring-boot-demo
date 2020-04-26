package com.ynthm.springbootdemo.domain;

import java.io.Serializable;

/**
 * Author : Ynthm
 */
public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

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
