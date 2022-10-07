package com.ynthm.services.account.user;

import com.ynthm.services.account.entity.Permission;
import com.ynthm.services.account.entity.Role;
import com.ynthm.services.account.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** @author ethan */
@Component("authorizer")
public class MyShiroRealm extends AuthorizingRealm {

  @Autowired private UserService userService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    // 能进入这里说明用户已经通过验证了
    User user = (User) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    for (Role role : user.getRoles()) {
      simpleAuthorizationInfo.addRole(role.getName());
      for (Permission permission : role.getPermissions()) {
        simpleAuthorizationInfo.addStringPermission(permission.getName());
      }
    }
    return simpleAuthorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    // 获取用户输入的账户
    String username = (String) token.getPrincipal();
    // 通过username从数据库中查找 UserInfo 对象
    // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro 自己也是有时间间隔机制，2分钟内不会重复执行该方法
    User user = userService.findByUsername(username);
    if (null == user) {
      return null;
    }

    return new SimpleAuthenticationInfo(
        user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
  }
}
