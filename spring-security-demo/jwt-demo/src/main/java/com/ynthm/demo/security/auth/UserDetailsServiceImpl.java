// package com.ynthm.demo.security.auth;
//
// import com.baomidou.mybatisplus.core.toolkitFe.Wrappers;
// import com.ynthm.agent.api.user.entity.User;
// import com.ynthm.agent.api.user.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.LockedException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
//
/// **
// * @author ethan
// */
// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {
//  @Autowired private UserService userService;
//
//  @Override
//  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//
//    User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, s));
//    if (user == null) {
//      throw new UsernameNotFoundException("用户不存在");
//    }
//
//    if (!user.isEnabled()) {
//      throw new LockedException("用户账号被冻结，无法登陆请联系管理员！");
//    }
//
//    return user;
//  }
// }
