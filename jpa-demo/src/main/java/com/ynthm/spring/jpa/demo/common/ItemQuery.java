package com.ynthm.spring.jpa.demo.common;

import com.ynthm.spring.jpa.demo.user.entity.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author ethan
 */
@Data
public class ItemQuery extends AbstractBaseQuery<User> {
  @QueryWord(column = "item_id", func = MatchEnum.EQUAL)
  private Integer itemId;

  @QueryWord(func = MatchEnum.LIKE)
  private String itemName;

  @QueryWord(func = MatchEnum.LE)
  private Integer itemPrice;

  @Override
  public Specification<User> toSpec() {
    return super.toSpecWithAnd();
  }
}
