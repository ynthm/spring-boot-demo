// package com.ynthm.demo.security.auth.filter;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ynthm.common.domain.Result;
// import com.ynthm.common.enums.BaseResultCode;
// import com.ynthm.common.web.util.ServletUtil;
// import com.ynthm.common.web.util.UserUtil;
// import com.ynthm.demo.security.auth.BaseAuthenticationException;
// import com.ynthm.demo.security.auth.JwtTokenUtil;
// import io.micrometer.core.instrument.util.StringUtils;
// import org.springframework.beans.BeanUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.*;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
// import javax.servlet.FilterChain;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.io.InputStream;
//
/// **
// * 仿照 UsernamePasswordAuthenticationFilter
// *
// * @author ynthm
// */
// public class VerificationCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//  private ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
//  private final AuthenticationManager authenticationManager;
//
//  private JwtTokenUtil jwtTokenUtil;
//
//  private ObjectMapper objectMapper;
//
//  @Autowired
//  public void setObjectMapper(ObjectMapper objectMapper) {
//    this.objectMapper = objectMapper;
//  }
//
//  public VerificationCodeAuthenticationFilter(
//      AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
//    this.authenticationManager = authenticationManager;
//    this.jwtTokenUtil = jwtTokenUtil;
//    // 设置登录请求的 URL
//    // super.setFilterProcessesUrl("/login");
//    super.setPostOnly(true);
//  }
//
//  /**
//   * 和UsernamePasswordAuthenticationFilter.attemptAuthentication 方法中的源码是一样的
//   *
//   * @param request
//   * @param response
//   * @return
//   * @throws AuthenticationException
//   */
//  @Override
//  public Authentication attemptAuthentication(
//      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//    if (!HttpMethod.POST.name().equals(request.getMethod())) {
//      throw new AuthenticationServiceException(
//          "Authentication method not supported: " + request.getMethod());
//    } else {
//      ObjectMapper objectMapper = new ObjectMapper();
//      // 从输入流中获取到登录的信息
//      LoginRequest loginRequest = null;
//      String username;
//      try (InputStream io = request.getInputStream()) {
//        loginRequest = objectMapper.readValue(io, LoginRequest.class);
//        username = UserUtil.getUsername(loginRequest);
//        if (StringUtils.isBlank(username)) {
//          throw new BaseAuthenticationException(ResponseEnum.EMPTY_PARAMETER_ERROR);
//        }
//      } catch (IOException e) {
//        // 登录没有输入参数
//        throw new BaseAuthenticationException(ResponseEnum.EMPTY_PARAMETER_ERROR);
//      }
//      // rememberMe.set(loginUser.get);
//
//      //      VerificationCodeAuthenticationToken authRequest =
//      //          new VerificationCodeAuthenticationToken(username, loginRequest);
//
//      UsernamePasswordAuthenticationToken authRequest =
//          new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword());
//      return authenticationManager.authenticate(authRequest);
//    }
//  }
//
//  /** 如果验证成功，就生成token并返回 */
//  @Override
//  protected void successfulAuthentication(
//      HttpServletRequest request,
//      HttpServletResponse response,
//      FilterChain chain,
//      Authentication authentication)
//      throws IOException {
//    // 将认证信息放入 Session
//    // SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    User jwtUser = (User) authentication.getPrincipal();
//    UserResponse userResponse = new UserResponse();
//    BeanUtils.copyProperties(jwtUser, userResponse);
//    //    List<String> roles =
//    //        jwtUser.getAuthorities().stream()
//    //            .map(GrantedAuthority::getAuthority)
//    //            .collect(Collectors.toList());
//    // 创建 Token
//    String token = jwtTokenUtil.generateToken(jwtUser);
//    // Http Response Header 中返回 Token
//    userResponse.setToken(token);
//    Result<UserResponse> result = new Result<>();
//    result.setCode(ResponseEnum.OK.getCode());
//    result.setMessage("获取授权成功。");
//    result.setData(userResponse);
//    // 用户登陆以后更新用户的最迟登陆时间  此位置还没有登录成功
//    //    user.setUpdateTime(LocalDateTime.now());
//    //    userService.updateById(user);
//    ServletUtil.renderString(response, result.toJson());
//  }
//
//  @Override
//  protected void unsuccessfulAuthentication(
//      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
//      throws IOException {
//    Result<String> result = new Result<>();
//    result.setCode(BaseResultCode.ERROR.getCode());
//    result.setData(exception.getMessage());
//    if (exception instanceof LockedException) {
//      result.setMsg("账户被冻结,请联系管理员。");
//    } else if (exception instanceof CredentialsExpiredException) {
//      result.setMsg("密码过期,请联系管理员。");
//    } else if (exception instanceof AccountExpiredException) {
//      result.setMsg("账户过期,请联系管理员。");
//    } else if (exception instanceof DisabledException) {
//      result.setMsg("账户被禁用,请联系管理员。");
//    } else if (exception instanceof BadCredentialsException) {
//      result = Result.error(BaseResultCode.BAD_CREDENTIALS);
//    } else if (exception instanceof UsernameNotFoundException) {
//      result = Result.error(BaseResultCode.USERNAME_NOT_FOUND);
//    } else if (exception instanceof BaseAuthenticationException) {
//      result = Result.error(((BaseAuthenticationException) exception).getResultCode());
//    }
//
//    ServletUtil.renderString(response, objectMapper.writeValueAsString(result));
//  }
// }
