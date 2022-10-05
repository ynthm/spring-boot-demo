package com.ynthm.common.util;

import org.junit.jupiter.api.Test;

import java.security.Security;
import java.util.Arrays;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
class RsaUtilTest {

  @Test
  void generateKeyPair() {

    Arrays.stream(Security.getProviders())
        .forEach(
            provider -> {
              System.out.println(provider.getName());
            });
  }
}
