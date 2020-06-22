package com.ynthm.spring.jpa.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/** @author ethan */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  @Bean
  public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    // 对于空的对象转json的时候不抛出错误
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // 禁用遇到未知属性抛出异常
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    // 序列化BigDecimal时不使用科学计数法输出
    objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

    jsonConverter.setObjectMapper(objectMapper);
    return jsonConverter;
  }

  /**
   * 继承了WebMvcConfigurationSupport，就是他！以前是用 WebMvcConfigurerAdapter ，springboot 2.0 建议使用
   * WebMvcConfigurationSupport 。但是在添加拦截器并继承 WebMvcConfigurationSupport
   * 后会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置！从而导致所有的Date返回都变成时间戳！
   *
   * <p>这种单独设置全局的，@JsonFormat 无效 可是试试 Jackson2ObjectMapperBuilderCustomizer
   *
   * <p>单独设置用@JsonSerialize(using = LocalDateTimeSerializer.class)将LocalDateTime序列化
   *
   * <p>如果想要反序列特定的日期格式， 使用@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")注解
   *
   * @param converters
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(customJackson2HttpMessageConverter());
  }
}
