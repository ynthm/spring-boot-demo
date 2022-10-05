package com.ynthm.demo.jpa.repository;

import com.ynthm.demo.jpa.domain.UserLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ethan Wang
 */
@Repository
public interface UserLinkRepository
    extends JpaRepository<UserLink, Integer>, JpaSpecificationExecutor<UserLink> {

  UserLink findByOldUserId(String oldUserId);

  UserLink findByUsername(String username);

  @Query(value = "select u from UserLink u where u.id <= ?1")
  Page<UserLink> findMore(Integer maxId, Pageable pageable);

  /**
   * @param name
   * @param id
   * @return
   */
  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query(value = "update user_link u set u.name = ?1 where u.id = ?2", nativeQuery = true)
  int updateById(String name, Integer id);
}
