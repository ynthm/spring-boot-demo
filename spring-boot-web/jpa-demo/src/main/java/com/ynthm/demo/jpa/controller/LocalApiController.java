package com.ynthm.demo.jpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.ynthm.common.domain.Result;
import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.exception.BaseException;
import com.ynthm.common.util.HmacHelper;
import com.ynthm.demo.jpa.common.Const;
import com.ynthm.demo.jpa.config.LocalServiceProperties;
import com.ynthm.demo.jpa.domain.UserLink;
import com.ynthm.demo.jpa.service.UserLinkService;
import com.ynthm.demo.jpa.vo.LoginReq;
import com.ynthm.demo.jpa.vo.OldTokenVo;
import com.ynthm.demo.jpa.vo.TokenVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 新旧平台相互登陆
 *
 * @author Ethan Wang
 */
@Slf4j
@RestController
@RequestMapping("/local/userLink")
public class LocalApiController {

  private RestTemplate restTemplate;

  @Autowired
  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  private LocalServiceProperties localServiceProperties;

  @Autowired
  public void setLocalServiceProperties(LocalServiceProperties localServiceProperties) {
    this.localServiceProperties = localServiceProperties;
  }

  private ObjectMapper objectMapper;

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  private HmacHelper hmacHelper;

  @Autowired
  public void setHmacHelper(HmacHelper hmacHelper) {
    this.hmacHelper = hmacHelper;
  }

  private UserLinkService userService;

  @Autowired
  public void setUserService(UserLinkService userService) {
    this.userService = userService;
  }

  @PostMapping("/loginNew")
  public Result<Result<TokenVo>> loginNew(@RequestBody LoginReq req)
      throws JsonProcessingException {
    String oldUserId = req.getOldUserId();
    if (Strings.isNullOrEmpty(oldUserId)) {
      return Result.error(BaseResultCode.VALID_ERROR);
    } else {
      UserLink user = userService.getUserByOldUserId(oldUserId);
      if (user == null) {
        return Result.error(BaseResultCode.DB_NOT_EXIST);
      }
      req.setUsername(user.getUsername());
    }

    HttpEntity<String> request = sign4Request(req);

    String url = localServiceProperties.getNewRootUrl() + "/auth/local/login";
    try {
      JavaType javaType =
          objectMapper.getTypeFactory().constructParametricType(Result.class, TokenVo.class);
      ResponseEntity<Result<TokenVo>> response =
          restTemplate.exchange(
              url, HttpMethod.POST, request, ParameterizedTypeReference.forType(javaType));
      return Result.ok(response.getBody());
    } catch (Exception e) {
      log.info("url {}", url);
      throw new BaseException(BaseResultCode.S_AUTH_ERROR, e);
    }
  }

  @PostMapping("/loginOld")
  public Result<OldTokenVo> loginOld(@RequestBody LoginReq req) throws JsonProcessingException {
    String username = req.getUsername();
    if (Strings.isNullOrEmpty(username)) {
      return Result.error(BaseResultCode.VALID_ERROR);
    } else {
      UserLink user = userService.getUserByUsername(username);
      if (user == null) {
        return Result.error(BaseResultCode.DB_NOT_EXIST);
      }
      req.setOldUserId(user.getOldUserId());
    }

    HttpEntity<String> request = sign4Request(req);

    String url = localServiceProperties.getOldRootUrl() + "/login/new/platform";
    try {
      JavaType javaType =
          objectMapper.getTypeFactory().constructParametricType(Result.class, OldTokenVo.class);
      ResponseEntity<Result<OldTokenVo>> response =
          restTemplate.exchange(
              url, HttpMethod.POST, request, ParameterizedTypeReference.forType(javaType));

      return Result.externalResult(response.getBody(), BaseResultCode.S_SYSTEM_ERROR);
    } catch (Exception e) {
      log.info("url {}", url);
      throw new BaseException(BaseResultCode.S_AUTH_ERROR, e);
    }
  }

  private HttpEntity<String> sign4Request(LoginReq req) throws JsonProcessingException {
    String body = objectMapper.writeValueAsString(req);
    String timestamp = String.valueOf(System.currentTimeMillis());

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add(
        Const.HTTP_HEADER_SIGNATURE, hmacHelper.base64Sign(body + timestamp + hmacHelper.getKey()));
    headers.add(Const.HTTP_HEADER_TIMESTAMP, timestamp);
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    return new HttpEntity<>(body, headers);
  }
}
