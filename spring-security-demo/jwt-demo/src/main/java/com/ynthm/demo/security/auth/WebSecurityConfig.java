// package com.ynthm.demo.security.auth;
//
// import com.ynthm.common.web.util.ServletUtil;
// import com.ynthm.common.web.util.UserUtil;
// import com.ynthm.demo.security.auth.filter.CustomBasicAuthenticationFilter;
// import com.ynthm.demo.security.auth.filter.VerificationCodeAuthenticationFilter;
// import com.ynthm.demo.security.auth.handler.CustomAccessDeniedHandler;
// import com.ynthm.demo.security.auth.handler.CustomLogoutSuccessHandler;
// import com.ynthm.demo.security.auth.handler.MyAuthenticationFailureHandler;
// import com.ynthm.demo.security.auth.handler.MyAuthenticationSuccessHandler;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import
// org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import
// org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.CorsUtils;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
// import java.time.Duration;
// import java.util.Arrays;
// import java.util.Collections;
//
/// **
// * AuthenticationEntryPoint 该类用来统一处理 AuthenticationException 异常；
// *
// * <p>AccessDeniedHandler 该类用来统一处理 AccessDeniedException 异常。
// *
// * @author ynthm
// */
// @Slf4j
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// @Configuration
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//  private final UserDetailsServiceImpl userDetailsService;
//  @Autowired private CustomAccessDeniedHandler customAccessDeniedHandler;
//  @Autowired private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
//  @Autowired private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
//  @Autowired private JwtTokenUtil jwtTokenUtil;
//  private PasswordEncoder passwordEncoder;
//
//  public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
//    this.userDetailsService = userDetailsService;
//  }
//
//  @Autowired
//  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
//    this.passwordEncoder = passwordEncoder;
//  }
//
//  /**
//   * Spring Security会自动寻找name=corsConfigurationSource的Bean
//   *
//   * @return
//   */
//  @Bean
//  public CorsConfigurationSource corsConfigurationSource() {
//    final CorsConfiguration configuration = new CorsConfiguration();
//    // 指定允许跨域的请求(*所有)：
//    configuration.setAllowedOrigins(Collections.singletonList("*"));
//    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE",
// "PATCH"));
//    // setAllowCredentials(true) is important, otherwise:
//    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the
//    // wildcard '*' when the request's credentials mode is 'include'.
//    configuration.setAllowCredentials(true);
//    // setAllowedHeaders is important! Without it, OPTIONS preflight request
//    // will fail with 403 Invalid CORS request
//    configuration.setAllowedHeaders(
//        Arrays.asList("Authorization", "Cache-Control", "X-User-Agent", "Content-Type"));
//    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//  }
//
//  /**
//   * 注入自定义权限管理
//   *
//   * @param auth
//   * @throws Exception
//   */
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//  }
//
//  /**
//   * authorizeRequests() 就是请求授权，然后其中的antMatchers() 就是匹配对应的url。
//   *
//   * <p>我们看到后面的permitAll() 、hasRole("ROLE_DOCKER") 这些都可以叫做权限表达式。
//   *
//   * @param http
//   * @throws Exception
//   */
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//    // 设置放行目录
//    http.authorizeRequests()
//        .antMatchers(SwaggerConst.MATCHERS)
//        .permitAll()
//        .antMatchers(HttpMethod.POST, "/register", "/auth/**", "/login", "/logout")
//        .permitAll();
//    // 由于使用的是JWT，不需要csrf
//    // anyRequest() 后只要验证权限就可以访问，之前的要相应路径匹配相应权限访问
//    http.csrf()
//        .disable()
//        .authorizeRequests()
//        // .antMatchers("/login")
//        // .permitAll()
//        .requestMatchers(CorsUtils::isPreFlightRequest)
//        .permitAll()
//        .antMatchers(HttpMethod.DELETE, "/users/**")
//        .hasRole("ADMIN")
//        .anyRequest()
//        .authenticated()
//        .and()
//        // 配置登录地址
//        .formLogin()
//        .loginProcessingUrl(Constant.LOGIN_URI)
//        // 配置登录成功自定义处理类
//        .successHandler(myAuthenticationSuccessHandler)
//        // 配置登录失败自定义处理类
//        .failureHandler(myAuthenticationFailureHandler)
//        .and()
//        .rememberMe()
//        .and()
//        // 配置登出地址
//        .logout()
//        .logoutUrl("/logout")
//        .addLogoutHandler(
//            (request, response, authentication) ->
//                log.info("{} logout.", UserUtil.getPrincipalUser().getUsername()))
//
//        // 配置用户登出自定义处理类
//        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
//        .and()
//        // 授权异常处理
//        .exceptionHandling()
//        .accessDeniedHandler(customAccessDeniedHandler)
//        .and()
//        .cors();
//    http.exceptionHandling()
//        .authenticationEntryPoint(
//            (request, response, authenticationException) -> {
//              ServletUtil.renderString(
//                  response,
//                  Result.errorMessage(
//                          ResponseEnum.UNAUTHORIZED, authenticationException.getMessage())
//                      .toJson());
//            });
//    // 基于Token不需要session
//    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    // 禁用缓存
//    http.headers().cacheControl();
//    http.headers()
//        .frameOptions()
//        .sameOrigin()
//        .httpStrictTransportSecurity()
//        .includeSubDomains(true)
//        .preload(true)
//        .maxAgeInSeconds(Duration.ofDays(365).getSeconds());
//    // 添加JWT过滤器
//
//    // 验证码登录
//    // http.addFilterBefore(verificationCodeFilter,
// AbstractPreAuthenticatedProcessingFilter.class);
//
//    //    VerificationCodeAuthenticationFilter filter =
//    //        new VerificationCodeAuthenticationFilter(authenticationManager(), jwtTokenUtil);
//    //    filter.setAuthenticationSuccessHandler(loginSuccessHandle());
//    //    filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
//    //
//    //    VerificationCodeProvider provider = new VerificationCodeProvider();
//    //    provider.setUserDetailsService(userDetailsService);
//    //    http.authenticationProvider(provider)
//    //        .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
//
//    // 登录过滤及 登录前 token 验证
//    http.addFilter(new VerificationCodeAuthenticationFilter(authenticationManager(),
// jwtTokenUtil))
//        .addFilterBefore(
//            new CustomBasicAuthenticationFilter(
//                authenticationManager(), jwtTokenUtil, userDetailsService),
//            UsernamePasswordAuthenticationFilter.class);
//  }
// }
