package com.ynthm.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ethan Wang
 */
@Slf4j
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfigurationSupport {

  @Bean
  public ElasticsearchTransport elasticsearchTransport(RestClientProperties restClientProperties) {
    RestClient restClient =
        RestClient.builder(
                restClientProperties.getUris().stream()
                    .map(HttpHost::create)
                    .toArray(HttpHost[]::new))
            .build();

    // Create the transport with a Jackson mapper
    return new RestClientTransport(restClient, new JacksonJsonpMapper());
  }

  @Bean
  public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
    return new ElasticsearchClient(transport);
  }

  @Bean
  public ElasticsearchAsyncClient elasticsearchAsyncClient(ElasticsearchTransport transport) {
    return new ElasticsearchAsyncClient(transport);
  }

  @Bean
  public ElasticsearchTemplate elasticsearchTemplate(
      ElasticsearchClient elasticsearchClient, ElasticsearchConverter elasticsearchConverter) {
    return new ElasticsearchTemplate(elasticsearchClient, elasticsearchConverter);
  }

  @WritingConverter
  static class LocalDateTimeToString implements Converter<LocalDateTime, String> {

    @Override
    public String convert(LocalDateTime source) {
      return source.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
  }

  @ReadingConverter
  static class StringToLocalDateTime implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
      return LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
  }
}
