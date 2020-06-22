package com.ynthm.spring.jpa.demo.user.service;

import com.ynthm.spring.jpa.demo.user.entity.User;
import com.ynthm.spring.jpa.demo.user.repository.UserRepository;
import com.ynthm.spring.jpa.demo.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * SpringBoot 的启动速度很慢，这会延长单元测试的时间
 *
 * <p>spy 和 mock 的区别是，spy 只会部分模拟对象
 */
@PrepareForTest({UserService.class, UserRepository.class})
public class UserServiceTest {

  private UserService userService = PowerMockito.spy(new UserServiceImpl());

  @Test
  void test1() throws IllegalAccessException {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    Mockito.doReturn(new User()).when(userRepository).findByUsername(anyString());
    MemberModifier.field(UserServiceImpl.class, "userRepository").set(userService, userRepository);
    UserDetails userDetails = userService.loadUserByUsername("123");
    System.out.println("123");
  }
}
