package com.ynthm.demo.web.web;

import com.google.common.base.Strings;
import com.ynthm.common.exception.BaseException;
import com.ynthm.demo.web.web.config.LocalSslProperties;
import feign.Client;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.MonthDay;
import java.time.ZonedDateTime;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
public class FeignHttpsConfig {

  @Bean
  @Scope("prototype")
  public Feign.Builder feignBuilder(Client.Default client) {
    return Feign.builder().client(client);
  }

  @Bean
  public Client.Default client(LocalSslProperties sslProperties) {
    try {
      SSLContext context = createSslContext(sslProperties);
      return new Client.Default(
          context.getSocketFactory(), new OkHttpClientFactory.TrustAllHostnames());
    } catch (Exception e) {
      log.error("Create feign client with SSL config failed", e);
      return new Client.Default(null, null);
    }
  }

  /** 创建并初始化 SSLContext */
  private SSLContext createSslContext(LocalSslProperties sslProperties) {
    try {
      TrustManager[] tm = null;
      if (!Strings.isNullOrEmpty(sslProperties.getTruststore())) {
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(
            FeignHttpsConfig.class.getResourceAsStream(sslProperties.getTruststore()),
            sslProperties.getTrustStorePassword().toCharArray());

        // 创建信任库
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
        trustManagerFactory.init(trustStore);
        tm = trustManagerFactory.getTrustManagers();
      }

      // 初始化 SSLContext
      SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
      sslContext.init(null, tm, new SecureRandom());

      return sslContext;
    } catch (Exception ex) {
      throw new BaseException(ex);
    }
  }

  @Bean
  public Feign okHttpClient() {
    okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
    OkHttpClient build = new DefaultOkHttpClientFactory(builder).createBuilder(true).build();
    return Feign.builder().client(new feign.okhttp.OkHttpClient(build)).build();
  }

  public static void main(String[] args) {
    System.out.println(ZonedDateTime.now());
    System.out.println(MonthDay.of(8, 4));
  }
}
