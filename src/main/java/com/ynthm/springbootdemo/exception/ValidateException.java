package com.ynthm.springbootdemo.exception;

/**
 * Author : Ynthm
 */
public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    private ErrorCode errorCode = ErrorCode.SUCCESS;
    private Object[]  params;

    public ValidateException(Object... params) {
        this.params = params;
    }

}
