package com.ynthm.common.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自动完善某些字段信息
 *
 * @author Ethan Wang
 */
@Slf4j
public class EntityMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    log.debug("start insert fill ....");
    this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    log.debug("start insert fill ....");
    this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
  }
}
