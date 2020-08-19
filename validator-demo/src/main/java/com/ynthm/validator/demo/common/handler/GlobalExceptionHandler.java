package com.ynthm.validator.demo.common.handler;

import com.ynthm.validator.demo.common.Result;
import com.ynthm.validator.demo.common.ResultCode;
import com.ynthm.validator.demo.common.exception.BaseException;
import com.ynthm.validator.demo.common.util.ExceptionUtil;
import com.ynthm.validator.demo.common.util.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/** @author ethan */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  public static final String MESSAGE_CODE_PREFIX = "response.";

  @Autowired private I18nUtil i18nUtil;

  /**
   * 获取国际化消息
   *
   * @param e 异常
   * @return
   */
  private String getMessage(BaseException e) {
    String code = MESSAGE_CODE_PREFIX + e.getResultCode().getCode();
    String message = i18nUtil.getMessage(code, e.getArgs());

    if (StringUtils.isBlank(message)) {
      return ExceptionUtil.printStackTrace(e);
    }
    return message;
  }

  /**
   * 自定义异常
   *
   * @param e 异常
   * @return 异常结果
   */
  @ExceptionHandler(value = BaseException.class)
  public Result<Boolean> handleBaseException(BaseException e) {
    log.error(e.getMessage(), e);

    return Result.errorMessage(e.getResultCode(), getMessage(e));
  }

  /**
   * Controller上一层相关异常
   *
   * @param e 异常
   * @return 异常结果
   */
  @ExceptionHandler({
    NoHandlerFoundException.class,
    HttpRequestMethodNotSupportedException.class,
    HttpMediaTypeNotSupportedException.class,
    MissingPathVariableException.class,
    MissingServletRequestParameterException.class,
    TypeMismatchException.class,
    HttpMessageNotReadableException.class,
    HttpMessageNotWritableException.class,
    HttpMediaTypeNotAcceptableException.class,
    ServletRequestBindingException.class,
    ConversionNotSupportedException.class,
    MissingServletRequestPartException.class,
    AsyncRequestTimeoutException.class
  })
  public Result<String> handleServletException(Exception e) {
    log.error(e.getMessage(), e);
    int code = ResultCode.SERVER_ERROR.getCode();
    try {
      ResultCode servletExceptionEnum = ResultCode.valueOf(e.getClass().getSimpleName());
      if (servletExceptionEnum != null) {
        code = servletExceptionEnum.getCode();
      }

    } catch (IllegalArgumentException e1) {
      log.error("class [{}] not defined ", e.getClass().getName());
    }

    return Result.errorMessage(ResultCode.SERVER_ERROR, e.getMessage());
  }

  /**
   * 参数绑定异常
   *
   * @param e 异常
   * @return 异常结果
   */
  @ExceptionHandler(value = BindException.class)
  public Result<String> handleBindException(BindException e) {
    log.error(e.getMessage(), e);

    return wrapperBindingResult(e.getBindingResult());
  }

  /**
   * 参数校验异常，将校验失败的所有异常组合成一条错误信息
   *
   * @param e 异常
   * @return 异常结果
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public Result<String> handleValidException(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);

    return wrapperBindingResult(e.getBindingResult());
  }

  /** ConstraintViolationException */
  @ExceptionHandler(ConstraintViolationException.class)
  public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
    log.error(e.getMessage(), e);
    Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    for (ConstraintViolation<?> constraintViolation : constraintViolations) {
      System.out.println(constraintViolation.getMessage());
    }
    return Result.errorMessage(ResultCode.VALID_ERROR, e.getMessage());
  }

  /**
   * 未定义异常
   *
   * @param e 异常
   * @return 异常结果
   */
  @ExceptionHandler(value = Exception.class)
  public Result<String> handleException(Exception e) {
    log.error(ExceptionUtil.printStackTrace(e));

    return Result.errorMessage(ResultCode.SERVER_ERROR, e.getMessage());
  }

  /**
   * 包装绑定异常结果
   *
   * @param bindingResult 绑定结果
   * @return 异常结果
   */
  private Result<String> wrapperBindingResult(BindingResult bindingResult) {
    StringBuilder msg = new StringBuilder();

    for (ObjectError error : bindingResult.getAllErrors()) {
      msg.append(", ");
      if (error instanceof FieldError) {
        msg.append(((FieldError) error).getField()).append(": ");
      }
      msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
    }

    return Result.errorMessage(ResultCode.VALID_ERROR, msg.substring(2));
  }
}
