package com.ynthm.common.mybatis.plus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author Ethan Wang
 */
@Getter
public enum DeletedEnum {
  /** 未删除 */
  NOT_DELETED(0),
  DELETED(1);

  DeletedEnum(int value) {
    this.value = value;
  }

  @JsonValue @EnumValue private final int value;
}
