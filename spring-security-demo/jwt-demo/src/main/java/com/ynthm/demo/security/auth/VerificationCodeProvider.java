package com.ynthm.demo.security.auth;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * DaoAuthenticationProvider
 *
 * @author ethan
 */
@Data
public class VerificationCodeProvider implements AuthenticationProvider {
  private UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    VerificationCodeAuthenticationToken authenticationToken =
        (VerificationCodeAuthenticationToken) authentication;

    UserDetails user =
        userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
    if (Objects.isNull(user)) {
      throw new InternalAuthenticationServiceException("无法获取用户信息");
    }
    VerificationCodeAuthenticationToken authenticationResult =
        new VerificationCodeAuthenticationToken(
            user, authenticationToken.getCredentials(), user.getAuthorities());
    authenticationResult.setDetails(authenticationToken.getDetails());
    return authenticationResult;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return VerificationCodeAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
