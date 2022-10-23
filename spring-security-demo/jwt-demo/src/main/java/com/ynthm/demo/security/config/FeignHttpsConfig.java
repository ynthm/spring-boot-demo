package com.ynthm.demo.security.config;

import com.google.common.base.Strings;
import com.ynthm.common.exception.BaseException;
import feign.Client;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
// @Configuration
public class FeignHttpsConfig {

  @Value("${server.ssl.trust-store}")
  private String trustStorePath;

  @Value("${server.ssl.trust-store-password}")
  private String trustStorePassword;

  @Value("${server.ssl.open}")
  private boolean open;

  @Bean
  //  @Scope("prototype")
  @ConditionalOnMissingBean
  public Feign.Builder feignBuilder() {
    Client client;
    try {
      SSLContext context = createSslContext();
      client =
          new Client.Default(
              context.getSocketFactory(), new OkHttpClientFactory.TrustAllHostnames());
    } catch (Exception e) {
      log.error("Create feign client with SSL config failed", e);
      client = new Client.Default(null, null);
    }
    return Feign.builder().client(client);
  }

  /** 创建并初始化 SSLContext */
  private SSLContext createSslContext() {
    try {

      KeyManager[] km = null;
      if (Strings.isNullOrEmpty(trustStorePath)) {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(
            new ClassPathResource(trustStorePath).getInputStream(), "passwd".toCharArray());
        // 创建密钥管理器
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
        keyManagerFactory.init(keyStore, "passwd".toCharArray());
        km = keyManagerFactory.getKeyManagers();
      }

      TrustManager[] tm = null;
      if (Strings.isNullOrEmpty(trustStorePath)) {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(
            new ClassPathResource(trustStorePath).getInputStream(),
            trustStorePassword.toCharArray());

        // 创建信任库
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
        trustManagerFactory.init(trustStore);
        tm = trustManagerFactory.getTrustManagers();
      }

      if (!open) {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, null);

        return sslContext;
      }

      // 初始化 SSLContext
      SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
      sslContext.init(km, tm, null);

      return sslContext;
    } catch (Exception ex) {
      throw new BaseException(ex);
    }
  }

  public static void main(String[] args) throws NoSuchAlgorithmException {
    SSLContext aDefault = SSLContext.getDefault();
    String defaultType = KeyStore.getDefaultType();
    System.out.println(defaultType);
    System.out.println(KeyManagerFactory.getDefaultAlgorithm());
  }
}
