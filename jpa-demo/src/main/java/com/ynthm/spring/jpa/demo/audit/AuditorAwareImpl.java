package com.ynthm.spring.jpa.demo.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午12:59
 */
public class AuditorAwareImpl implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of("Ynthm");
  }

  // ------------------ Use below code for spring security --------------------------
  //  return Optional.ofNullable(SecurityContextHolder.getContext())
  //          .map(SecurityContext::getAuthentication)
  //              .filter(Authentication::isAuthenticated)
  //              .map(Authentication::getPrincipal)
  //              .map(User.class::cast);
  /*class SpringSecurityAuditorAware implements AuditorAware<User> {

   public User getCurrentAuditor() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
     return null;
    }

    return ((MyUserDetails) authentication.getPrincipal()).getUser();
   }
  }*/
}
