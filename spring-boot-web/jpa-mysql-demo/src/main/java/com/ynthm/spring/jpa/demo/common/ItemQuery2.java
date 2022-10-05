package com.ynthm.spring.jpa.demo.common;

import com.ynthm.spring.jpa.demo.user.entity.User;
import com.ynthm.spring.jpa.demo.user.entity.User_;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/** @author ethan */
@Data
public class ItemQuery2 extends AbstractBaseQuery<User> {
  @QueryWord(column = "item_id", func = MatchEnum.EQUAL)
  private Integer itemId;

  @QueryWord(func = MatchEnum.LIKE)
  private String itemName;

  // 价格范围查询
  private Integer itemPriceMin;
  private Integer itemPriceMax;

  @Override
  public Specification<User> toSpec() {
    Specification<User> spec = super.toSpecWithAnd();
    return ((root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicatesList = new ArrayList<>();
      predicatesList.add(spec.toPredicate(root, criteriaQuery, criteriaBuilder));

      if (itemPriceMin != null) {
        predicatesList.add(
            criteriaBuilder.and(criteriaBuilder.ge(root.get(User_.id), itemPriceMin)));
      }
      if (itemPriceMax != null) {
        predicatesList.add(
            criteriaBuilder.and(criteriaBuilder.le(root.get("itemPrice"), itemPriceMax)));
      }
      return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    });
  }
}
