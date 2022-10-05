package com.ynthm.spring.jpa.demo;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午12:52
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {
  @CreatedBy protected U createdBy;

  @CreatedDate protected LocalDateTime createdDate;

  @LastModifiedBy protected U lastModifiedBy;

  @LastModifiedDate protected LocalDateTime lastModifiedDate;
}
