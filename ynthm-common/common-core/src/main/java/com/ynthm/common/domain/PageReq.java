package com.ynthm.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan Wang
 */
@Accessors(chain = true)
@Data
public class PageReq<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 查询參數 */
  protected T param;

  /** 每页显示条数，默认 10 */
  protected int size = 10;

  /** 当前页 */
  protected int page = 1;

  /** 排序字段信息 */
  private List<OrderItem> orderItems = new ArrayList<>();

  private boolean searchCount = true;

  public PageReq() {
    // 空构造器
  }

  /** --------------- 以下为静态构造方式 --------------- */
  public static <T extends Serializable> PageReq<T> of(int page, int size) {
    return new PageReq<T>().setPage(page).setSize(size);
  }
}
