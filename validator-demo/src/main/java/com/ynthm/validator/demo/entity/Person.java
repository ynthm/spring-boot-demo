package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.validator.PersonGroupSequenceProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/** @author ethan */
@GroupSequenceProvider(PersonGroupSequenceProvider.class)
@Getter
@Setter
@ToString
public class Person {

  @NotNull private String name;

  @NotNull
  @Range(min = 10, max = 40)
  private Integer age;

  @NotNull(groups = {WhenAge20And30Group.class, WhenAge30And40Group.class})
  @Size(min = 1, max = 2, groups = WhenAge20And30Group.class)
  @Size(min = 3, max = 5, groups = WhenAge30And40Group.class)
  private List<String> hobbies;

  /** 定义专属的业务逻辑分组 */
  public interface WhenAge20And30Group {}

  public interface WhenAge30And40Group {}
}
