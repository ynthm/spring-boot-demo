package com.ynthm.springbootdemo.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * The following CurrentUser annotation is a wrapper around @AuthenticationPrincipal
 *
 * @author Ynthm Wang.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {}
