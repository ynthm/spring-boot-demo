package com.ynthm.demo.jpa.service;

import com.ynthm.common.domain.PageReq;
import com.ynthm.common.domain.PageResp;
import com.ynthm.common.jpa.util.PageUtil;
import com.ynthm.demo.jpa.domain.UserLink;
import com.ynthm.demo.jpa.repository.UserLinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ethan Wang
 */
@Service
public class UserLinkService {
  private final UserLinkRepository repository;

  public UserLinkService(UserLinkRepository repository) {
    this.repository = repository;
  }

  public UserLink getUserById(Integer id) {
    return repository.getReferenceById(id);
  }

  public UserLink getUserByUsername(String username) {
    return repository.findByUsername(username);
  }

  public UserLink getUserByOldUserId(String id) {
    return repository.findByOldUserId(id);
  }

  public PageResp<UserLink> findPage(PageReq<?> req) {
    return PageUtil.page(repository::findAll, req);
  }

  public Page<UserLink> find(Integer maxId) {
    Pageable pageable = PageRequest.of(0, 10);
    return repository.findMore(maxId, pageable);
  }

  /**
   * 不存在会报错 EntityNotFoundException
   *
   * @param id
   * @return
   */
  public UserLink getOne(Integer id) {
    return repository.getReferenceById(id);
  }

  public void update(UserLink userLink) {
    if (repository.existsById(userLink.getId())) {
      save(userLink);
    }
  }

  public UserLink save(UserLink u) {
    return repository.save(u);
  }

  public boolean update(Integer id, String name) {
    repository
        .findById(id)
        .ifPresent(
            userLink -> {
              userLink.setOldUserId(name + "_update");
              repository.save(userLink);
            });

    return true;
  }

  public Boolean updateById(String name, Integer id) {
    return repository.updateById(name, id) == 1;
  }

  public void deleteAll() {
    repository.deleteAllInBatch();
  }

  public Optional<UserLink> test(String name, LocalDateTime start, LocalDateTime end) {
    Specification specification =
        (Specification<UserLink>)
            (root, query, cb) -> {
              List<Predicate> predicateList = new ArrayList<>();
              // 条件1：查询 name 为 海信 的数据，root.get 中的值与 TV 实体中的属性名称对应
              if (name != null && !"".equals(name)) {
                predicateList.add(cb.equal(root.get("name").as(String.class), name));
              }

              predicateList.add(
                  cb.greaterThanOrEqualTo(root.get("createTime").as(LocalDateTime.class), start));
              predicateList.add(
                  cb.lessThanOrEqualTo(root.get("createTime").as(LocalDateTime.class), end));

              return query
                  .where(predicateList.toArray(new Predicate[predicateList.size()]))
                  .getRestriction();
            };

    return repository.findOne(specification);
  }
}
