package com.ynthm.services.account.validator;

import com.ynthm.services.account.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

/** @author ethan */
@Slf4j
public class PersonGroupSequenceProvider implements DefaultGroupSequenceProvider<Person> {
  @Override
  public List<Class<?>> getValidationGroups(Person object) {
    List<Class<?>> defaultGroupSequence = new ArrayList<>();
    // 这一步不能省,否则Default分组都不会执行了，会抛错的
    defaultGroupSequence.add(Person.class);

    if (object != null) {
      Integer age = object.getAge();
      log.info("年龄为：" + age + "，执行对应校验逻辑");
      if (age >= 20 && age < 30) {
        defaultGroupSequence.add(Person.WhenAge20And30Group.class);
      } else if (age >= 30 && age < 40) {
        defaultGroupSequence.add(Person.WhenAge30And40Group.class);
      }
    }
    return defaultGroupSequence;
  }
}
