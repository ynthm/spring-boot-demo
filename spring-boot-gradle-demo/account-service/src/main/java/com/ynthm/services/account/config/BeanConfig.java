package com.ynthm.services.account.config;

import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;
import java.nio.charset.StandardCharsets;

/** @author ethan */
@Configuration
public class BeanConfig {

  /**
   * 参数校验资源文件乱码
   *
   * <p>名字不能 messageSource 会替换 messages.properties
   *
   * @return
   */
  @Bean
  public MessageSource validationMessageSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    // 读取配置文件的编码格式
    source.setDefaultEncoding(StandardCharsets.UTF_8.name());
    // 缓存时间，-1表示不过期
    source.setCacheMillis(-1);
    //  // 配置文件前缀名，设置为Messages,那你的配置文件必须以Messages.properties/Message_en.properties...
    source.setBasename("ValidationMessages");
    return source;
  }

  @Bean
  public Validator validator() {
    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
    factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
    factoryBean.setValidationMessageSource(validationMessageSource());
    return factoryBean;
  }

  /**
   * 方法级参数验证
   *
   * @return 方法验证
   */
  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    MethodValidationPostProcessor methodValidationPostProcessor =
        new MethodValidationPostProcessor();
    methodValidationPostProcessor.setValidator(validator());
    return methodValidationPostProcessor;
  }
}
