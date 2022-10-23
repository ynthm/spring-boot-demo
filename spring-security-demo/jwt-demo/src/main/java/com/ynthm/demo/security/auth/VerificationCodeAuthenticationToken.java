package com.ynthm.demo.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 仿造UsernamePasswordAuthenticationToken设计一个属于短信验证的认证 token 对象 框架不只提供了用户名+密码的验证方式，用户认证是否成功，
 * 最终看的就是SecurityContextHolder对象中是否有对应的AuthenticationToken，
 * 因此要设计一个认证对象，当认证成功之后，将其设置到SecurityContextHolder即可。
 *
 * @author ethan
 */
public class VerificationCodeAuthenticationToken extends AbstractAuthenticationToken {
  private static final long serialVersionUID = 1L;
  private final Object principal;
  private Object credentials;

  public VerificationCodeAuthenticationToken(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(false);
  }
  /**
   * Creates a token with the supplied array of authorities.
   *
   * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal represented
   *     by this authentication object.
   * @param principal
   * @param credentials
   */
  public VerificationCodeAuthenticationToken(
      Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }

    super.setAuthenticated(false);
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    credentials = null;
  }
}
