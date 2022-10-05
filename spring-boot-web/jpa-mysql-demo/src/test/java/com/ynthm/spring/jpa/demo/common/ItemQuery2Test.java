package com.ynthm.spring.jpa.demo.common;

import com.ynthm.spring.jpa.demo.user.entity.User;
import com.ynthm.spring.jpa.demo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class ItemQuery2Test {

  @Autowired private UserRepository userRepository;

  @Test
  void getItemId() {

    ItemQuery2 itemQuery = new ItemQuery2();
    itemQuery.setItemName("车");
    itemQuery.setItemPriceMax(50);
    itemQuery.setItemPriceMax(200);
    Pageable pageable = itemQuery.toPageable(Sort.by(Sort.Direction.ASC, "itemId"));
    Page<User> all = userRepository.findAll(itemQuery.toSpec(), pageable);
  }
}
