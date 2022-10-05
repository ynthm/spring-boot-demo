package com.ynthm.springbootdemo.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** Author : Ynthm */
public class User {

  @NotBlank(message = "user.name.not.blank")
  private String userName;

  @Size(min = 8, max = 100, message = "password.length.illegal")
  private String password;

  @Email private String email;

  @Max(value = 150, message = "validation.max")
  private int age;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
