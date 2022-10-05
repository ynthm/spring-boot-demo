package com.ynthm.spring.jpa.demo.user.controller;

import com.ynthm.spring.jpa.demo.user.entity.User;
import com.ynthm.spring.jpa.demo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * https://spring.io/guides/gs/testing-web/
 *
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午1:53
 */
@SpringBootTest(classes = {UserController.class})
@AutoConfigureMockMvc
// @WebMvcTest(UserController.class)
public class UserController1Test {
  @Autowired private MockMvc mockMvc;

  @Autowired private WebApplicationContext wac;

  @MockBean private UserRepository userRepository;

  //  @Before
  //  public void setup() {
  //    this.mockMvc =
  //        MockMvcBuilders.webAppContextSetup(wac)
  //            .apply(springSecurity()) // 这里配置Security认证
  //            .build();
  //  }

  @Test
  void getAllUsers() {}

  @Test
  @WithMockUser(username = "admin", password = "", roles = "ADMIN")
  void getUserById() throws Exception {
    User user = new User().setUsername("Ynthm");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    MvcResult mvcResult =
        mockMvc
            .perform(get("/api/v1/users/" + 3))
            // .andExpect(status().isOk())
            // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String result = mvcResult.getResponse().getContentAsString();
    System.out.println(result);
  }

  @Test
  void createUser() {}

  @Test
  void updateUser() {}

  @Test
  void deleteUser() {}
}
