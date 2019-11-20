package com.ynthm.springbootdemo.domain;

import com.ynthm.springbootdemo.exception.ErrorCode;

import java.util.List;

/**
 * Author : Ynthm
 */
public class ApiResult<T> {

    private int code = ErrorCode.SUCCESS.getCode();
    private T data;
    private List<ErrorInfo> errors;


    public ApiResult() {

    }

    public ApiResult(T data) {
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorInfo> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorInfo> errors) {
        this.errors = errors;
    }
}
