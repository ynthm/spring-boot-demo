package com.ynthm.demo.security.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ynthm
 */
@Component
public class JwtTokenUtil {

  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_CREATED = "created";
  private static final String CLAIM_KEY_ROLES = "roles";

  String secret = "secret";

  /** 根据提供的用户详细信息生成token */
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>(1);
    List<String> roles = new ArrayList<>();
    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
    for (GrantedAuthority authority : authorities) {
      // SimpleGrantedAuthority是GrantedAuthority实现类
      // GrantedAuthority包含类型为String的获取权限的getAuthority()方法
      // 提取角色并放入List中
      roles.add(authority.getAuthority());
    }
    // 放入用户权限
    claims.put(CLAIM_KEY_ROLES, roles.stream().collect(Collectors.joining(",")));

    return generateToken(userDetails.getUsername(), claims);
  }

  /** 生成token（JWT令牌） */
  private String generateToken(String subject, Map<String, Object> claims) {

    JWSHeader jwsHeader =
        new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
    final Date createdDate = new Date();
    final Date expirationDate = calculateExpirationDate(createdDate);
    JWTClaimsSet.Builder builder =
        new JWTClaimsSet.Builder()
            .subject(subject)
            .issueTime(createdDate)
            .expirationTime(expirationDate);

    for (Map.Entry<String, Object> entry : claims.entrySet()) {
      builder.claim(entry.getKey(), entry.getValue());
    }

    Payload payload = new Payload(new SignedJWT(jwsHeader, builder.build()));
    payload.toBase64URL();
    // 签名（sign）
    JWSObject jwsObject = new JWSObject(jwsHeader, payload);
    try {
      JWSSigner jwsSigner = new MACSigner(secret);
      // 进行签名（根据前两部分生成第三部分）
      jwsObject.sign(jwsSigner);
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
    return jwsObject.serialize();
  }

  /** 获取用户所有角色 */
  public List<SimpleGrantedAuthority> getUserRolesFromToken(String token) throws ParseException {
    JWSObject jwsObject = getJWSObject(token);

    JWTClaimsSet jwtClaimsSet = jwsObject.getPayload().toSignedJWT().getJWTClaimsSet();
    List<String> stringListClaim = jwtClaimsSet.getStringListClaim(CLAIM_KEY_ROLES);

    return stringListClaim.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  /**
   * 验证令牌
   *
   * @param token 令牌
   * @param userDetails 用户
   * @return 是否有效
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    JWSVerifier jwsVerifier = null;
    try {
      jwsVerifier = new MACVerifier(secret);
      JWSObject jwsObject = JWSObject.parse(token);

      return isBeforeExpirationTime(jwsObject) && !jwsObject.verify(jwsVerifier);
    } catch (JOSEException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isBeforeExpirationTime(JWSObject jwsObject) throws ParseException {
    SignedJWT signedJWT = jwsObject.getPayload().toSignedJWT();
    return signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date());
  }

  /**
   * 从令牌中获取用户名
   *
   * @param token 令牌
   * @return 用户名
   */
  public String getUsernameFromToken(String token) throws ParseException {
    JWSObject jwsObject = getJWSObject(token);
    return GetSetUtil.get(
        jwsObject.getPayload().toSignedJWT().getJWTClaimsSet(), JWTClaimsSet::getSubject);
  }

  private JWSObject getJWSObject(String token) {
    try {
      return JWSObject.parse(token);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + 7200 * 1000);
  }
}
