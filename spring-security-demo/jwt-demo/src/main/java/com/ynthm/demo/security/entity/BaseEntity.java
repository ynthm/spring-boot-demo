package com.ynthm.demo.security.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.OffsetDateTime;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
public class BaseEntity {
  @Id private Long id;
  private OffsetDateTime createTime;
  private OffsetDateTime lastUpdateTime;
  @Version private Long version;
}
