package com.ynthm.demo.security.auth.filter; // package com.ztx.kol.api.security.filter;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ztx.kol.api.common.constant.Constant;
// import com.ztx.kol.common.enums.ResponseEnum;
// import com.ztx.kol.api.common.exception.BaseAuthenticationException;
// import com.ztx.kol.api.common.util.CacheUtil;
// import com.ztx.kol.api.common.util.UserUtil;
// import com.ztx.kol.api.security.MyServletRequestWrapper;
// import com.ztx.kol.api.user.request.LoginRequest;
// import org.apache.commons.lang3.StringUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import javax.validation.ConstraintViolation;
// import javax.validation.Validation;
// import javax.validation.Validator;
// import javax.validation.ValidatorFactory;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.Set;
//
/// **
// * 图片、手机、邮箱随机验证码进行登录验证
// *
// * <p>实现 Spring Security 最前端的过滤器 OncePerRequestFilter 保证只被调用一次
// *
// * <p>WebSecurityConfig#configure(HttpSecurity) http.addFilterBefore(verificationCodeFilter,
// * AbstractPreAuthenticatedProcessingFilter.class);
// *
// * @author ethan
// */
// @Component
// public class VerificationCodeFilter extends OncePerRequestFilter {
//  @Autowired private AuthenticationFailureHandler authenticationFailureHandler;
//
//  @Override
//  protected void doFilterInternal(
//      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//      throws ServletException, IOException {
//    if (Constant.LOGIN_URI.equals(request.getRequestURI())
//        && HttpMethod.POST.matches(request.getMethod())) {
//      if (!(request instanceof MyServletRequestWrapper)) {
//        request = new MyServletRequestWrapper(request);
//      }
//      ObjectMapper om = new ObjectMapper();
//      try (InputStream is = request.getInputStream()) {
//        LoginRequest loginRequest = om.readValue(is, LoginRequest.class);
//        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
//        Validator validator = vf.getValidator();
//        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
//        if (violations.size() > 0) {
//          StringBuffer sb = new StringBuffer();
//          for (ConstraintViolation<LoginRequest> violation : violations) {
//            sb.append(violation.getPropertyPath() + ": " + violation.getMessage() + "; ");
//          }
//          throw new BaseAuthenticationException(ResponseEnum.VALID_ERROR, sb.toString());
//        }
//        String username = Constant.CAPTCHA_PREFIX + UserUtil.getUsername(loginRequest);
//        String cacheCode = CacheUtil.getInstance().cache.getIfPresent(username);
//        if (StringUtils.isBlank(loginRequest.getVerificationCode())
//            || !loginRequest.getVerificationCode().trim().toLowerCase().equals(cacheCode)) {
//          throw new BaseAuthenticationException(ResponseEnum.VERIFICATION_CODE_ERROR);
//        }
//      } catch (AuthenticationException e) {
//        authenticationFailureHandler.onAuthenticationFailure(request, response, e);
//        return;
//      }
//    }
//
//    chain.doFilter(request, response);
//  }
// }
