package com.ynthm.spring.jpa.demo.user.controller;

import com.ynthm.spring.jpa.demo.exceptions.ResourceNotFoundException;
import com.ynthm.spring.jpa.demo.user.entity.Role;
import com.ynthm.spring.jpa.demo.user.entity.User;
import com.ynthm.spring.jpa.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午1:02
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
  @Autowired private UserRepository userRepository;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @PostMapping("/users/queries")
  public Page<User> queryUsers(PageRequest pageable) {

    Specification<User> spec =
        (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
          // 谓词
          List<Predicate> predicates = new ArrayList<>();
          predicates.add(criteriaBuilder.greaterThan(root.get("grade"), 60));
          criteriaQuery.orderBy(criteriaBuilder.desc((root.get("grade"))));
          criteriaQuery.where(
              criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

          return criteriaQuery.getRestriction();
        };

    return userRepository.findAll(pageable);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
      throws ResourceNotFoundException {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
    return ResponseEntity.ok().body(user);
  }

  @PostMapping("/users")
  public User createUser(@Valid @RequestBody User user) {
    List<Role> roles = new ArrayList<>();
    roles.add(new Role().setName("ROLE_ADMIN"));
    user.setRoles(roles);
    return userRepository.save(user);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(
      @PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails)
      throws ResourceNotFoundException {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

    user.setEmailAddress(userDetails.getEmailAddress());
    user.setLastName(userDetails.getLastName());
    user.setFirstName(userDetails.getFirstName());
    user.setPassword("123");
    // user.setLastModifiedDate(new Date());
    final User updatedUser = userRepository.save(user);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/users/{id}")
  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
      throws ResourceNotFoundException {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

    userRepository.delete(user);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
