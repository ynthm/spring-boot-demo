package com.ynthm.common.mybatis.plus.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynthm.common.domain.PageReq;
import com.ynthm.common.domain.PageResp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class PageUtil {

  private PageUtil() {}

  /**
   * 框架分页请求转化 三方框架请求参数
   *
   * @return IPage
   */
  public <T, P extends Serializable> IPage<T> pageable(PageReq<P> req) {
    Page<T> page = new Page<>(req.getPage(), req.getSize());
    page.setOrders(
        Optional.ofNullable(req.getOrderItems())
            .map(
                orderItems ->
                    orderItems.stream().map(i -> new OrderItem()).collect(Collectors.toList()))
            .orElse(new ArrayList<>()));
    page.setSearchCount(req.isSearchCount());
    return page;
  }

  /**
   * 分页查询结果转化
   *
   * @param page Mybatis Plus 框架结果
   * @return 统一分页结果
   * @param <T> 参数类型
   */
  public static <T extends Serializable> PageResp<T> from(IPage<T> page) {
    return new PageResp<T>()
        .setTotal((int) page.getTotal())
        .setTotalPage((int) page.getPages())
        .setRecords(page.getRecords());
  }
}
