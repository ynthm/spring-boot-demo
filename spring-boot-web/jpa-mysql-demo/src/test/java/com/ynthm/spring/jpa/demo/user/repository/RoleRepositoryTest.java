package com.ynthm.spring.jpa.demo.user.repository;

import com.ynthm.spring.jpa.demo.AbstractContainerBaseTest;
import com.ynthm.spring.jpa.demo.user.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/** https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-testcontainers */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest extends AbstractContainerBaseTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private RoleRepository roleRepository;

  @Test
  void testSave() {
    Role role = new Role().setName("ROLE_AGENT");
    roleRepository.save(role);
  }

  @Test
  void testFind() {
    entityManager.persist(new Role().setName("ROLE_AGENT"));
    Role role = roleRepository.findByName("ROLE_AGENT");
    assertThat(role.getName()).isEqualTo("ROLE_AGENT");
  }
}
