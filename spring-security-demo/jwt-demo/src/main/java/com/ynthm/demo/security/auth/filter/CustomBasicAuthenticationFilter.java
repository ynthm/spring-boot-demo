// package com.ynthm.demo.security.auth.filter;
//
// import com.ynthm.common.web.util.UserUtil;
// import com.ynthm.demo.security.auth.JwtTokenUtil;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.security.SignatureException;
// import java.util.List;
//
/// **
// * 考虑到 Basic 认证和 JWT 比较像，就选择了它。
// *
// * @author ynthm
// */
// @Slf4j
// public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {
//
//  private JwtTokenUtil jwtTokenUtil;
//  private UserDetailsService userDetailsService;
//
//  public CustomBasicAuthenticationFilter(
//      AuthenticationManager authenticationManager,
//      JwtTokenUtil jwtTokenUtil,
//      UserDetailsService userDetailsService) {
//    super(authenticationManager);
//    this.jwtTokenUtil = jwtTokenUtil;
//    this.userDetailsService = userDetailsService;
//  }
//
//  @Override
//  protected void doFilterInternal(
//      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//      throws IOException, ServletException {
//
//    String authorization = request.getHeader(jwtTokenUtil.tokenConfig.getHeader());
//    // 如果请求头中没有Authorization信息则直接放行了
//    if (authorization == null || !authorization.startsWith(jwtTokenUtil.tokenConfig.getPrefix()))
// {
//      chain.doFilter(request, response);
//      return;
//    }
//    // MDC.put("requestId", StringUtils.remove(UUID.randomUUID().toString(), "-"));
//    // 如果请求头中有token，则进行解析，并且设置授权信息
//    SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorization));
//
//    super.doFilterInternal(request, response, chain);
//  }
//
//  /** 这里从token中获取用户信息并新建一个token */
//  private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
//    String token = authorization.replace(jwtTokenUtil.tokenConfig.getPrefix(), "");
//
//    try {
//
//      String username = jwtTokenUtil.getUsernameFromToken(token);
//      User userDetails = (User) userDetailsServiceImpl.loadUserByUsername(username);
//      if (userDetails != null) {
//        if (!jwtTokenUtil.validateToken(token, userDetails)) {
//          return null;
//        }
//      }
//
//      // 通过 token 获取用户具有的角色
//      List<SimpleGrantedAuthority> userRolesByToken = jwtTokenUtil.getUserRolesFromToken(token);
//      if (!StringUtils.isEmpty(username)) {
//        UserUtil.setUser(userDetails);
//
//        return new UsernamePasswordAuthenticationToken(
//            userDetails, null, userDetails.getAuthorities());
//      }
//    } catch (SignatureException | ExpiredJwtException exception) {
//      log.warn("Request to parse JWT with invalid signature . Detail : " +
// exception.getMessage());
//    }
//    return null;
//  }
// }
