package com.ynthm.common.mybatis.plus.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ethan Wang
 */
@Data
public class BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 创建者 */
  private String createBy;

  /** 创建时间 */
  private LocalDateTime createTime;

  /** 更新者 */
  private String updateBy;

  /** 最后更新时间 */
  private LocalDateTime updateTime;
}
