package com.ynthm.demo.jpa.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 标注为 @MappedSuperclass 的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
 *
 * @author Ethan Wang
 */
@Data
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @CreatedDate private LocalDateTime createTime;

  @LastModifiedDate private LocalDateTime lastUpdateTime;
}
