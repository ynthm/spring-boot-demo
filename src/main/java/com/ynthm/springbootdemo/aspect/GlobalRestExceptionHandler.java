package com.ynthm.springbootdemo.aspect;

import com.ynthm.springbootdemo.domain.ApiResult;
import com.ynthm.springbootdemo.domain.ErrorInfo;
import com.ynthm.springbootdemo.exception.ApiException;
import com.ynthm.springbootdemo.util.CheckUtil;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Ynthm
 * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException e) {
        HttpHeaders headers = new HttpHeaders();
        // 业务状态与系统状态分开
        HttpStatus status = HttpStatus.OK;

        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setCode(e.getErrorCode().getCode());
        List<ErrorInfo> errorInfos = new ArrayList<>();
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(e.getErrorCode().getCode());
        errorInfo.setMessage(CheckUtil.getMessage(e));
        errorInfos.add(errorInfo);

        apiResult.setErrors(errorInfos);


        return new ResponseEntity(apiResult, headers, status);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setCode(status.value());
        List<ErrorInfo> errorInfoList = new ArrayList<>();
        System.out.println(ex.getLocalizedMessage());

        apiResult.setErrors(errorInfoList);

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {

            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setCode(status.value());
            errorInfo.setMessage(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            errorInfoList.add(errorInfo);
        }

        return handleExceptionInternal(
                ex, apiResult, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setCode(status.value());
        apiResult.setData(builder.toString());

        return handleExceptionInternal(
                ex, apiResult, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setCode(status.value());
        apiResult.setData(ex.getLocalizedMessage());

        return handleExceptionInternal(
                ex, apiResult, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = " should be of type " + ex.getRequiredType().getName();
        System.out.println(error);

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    /**
     * 必须配置生效
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setCode(status.value());
        apiResult.setData(error);

        return handleExceptionInternal(
                ex, apiResult, headers, status, request);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setData(ex.getLocalizedMessage());
        return new ResponseEntity<>(
                apiResult, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
