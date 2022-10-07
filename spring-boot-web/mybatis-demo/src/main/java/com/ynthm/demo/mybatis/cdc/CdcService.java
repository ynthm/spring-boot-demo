package com.ynthm.demo.mybatis.cdc;

import java.util.List;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public interface CdcService<T> {
  Class<T> getBeanClass();

  void insert(T item);

  T before(T update);

  void update(T update, T before);

  void batchInsert(List<T> list);

  void delete(String id);
}
