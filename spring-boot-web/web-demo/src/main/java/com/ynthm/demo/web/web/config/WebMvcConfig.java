package com.ynthm.demo.web.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * WebMvcConfigurationSupport 会覆盖别的配置
 *
 * @author Ynthm Wang
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  public static final String LANG = "lang";

  @Bean
  public LocaleResolver localeResolver() {
    // Spring MVC将会读取浏览器的accept-language标题，来确定浏览器接受哪个语言区域. 如果与应用程序支持的语言匹配，这就会使用这个语言区域，否则就会使用默认的语言区域。
    AcceptHeaderLocaleResolver lr = new AcceptHeaderLocaleResolver();
    // 默认使用的语言
    // UserUtil.setLocale(Locale.CHINESE);
    lr.setDefaultLocale(Locale.CHINESE);
    return lr;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    // 语言的参数名
    localeChangeInterceptor.setParamName(LANG);
    return localeChangeInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    WebMvcConfigurer.super.addInterceptors(registry);

    registry.addInterceptor(localeChangeInterceptor());
    registry.addInterceptor(new OneInterceptor());
  }
}
