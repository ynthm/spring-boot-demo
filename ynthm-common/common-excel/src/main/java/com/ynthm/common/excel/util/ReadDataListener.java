package com.ynthm.common.excel.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 模板的读取类 每次都要新建 {@see PageReadListener}
 *
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
public class ReadDataListener<T> implements ReadListener<T> {
  /** 批量处理一定数量后清理 list 回收内存 */
  private static final int BATCH_COUNT = 10000;

  /** Temporary storage of data */
  private List<T> cachedDataList = new ArrayList<>();
  /** 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。 */
  private final Consumer<List<T>> consumer;

  public ReadDataListener(Consumer<List<T>> consumer) {
    this.consumer = consumer;
  }

  /**
   * 这个每一条数据解析都会来调用
   *
   * @param data one row value. Is is same as {@link AnalysisContext#readRowHolder()}
   * @param context AnalysisContext
   */
  @Override
  public void invoke(T data, AnalysisContext context) {
    cachedDataList.add(data);
    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (cachedDataList.size() >= BATCH_COUNT) {
      saveData();
      // 存储完成清理 list
      cachedDataList = new ArrayList<>();
    }
  }

  /**
   * 所有数据解析完成了 都会来调用
   *
   * @param context AnalysisContext
   */
  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    // 这里也要保存数据，确保最后遗留的数据也存储到数据库
    if (!cachedDataList.isEmpty()) {
      saveData();
    }
    log.info("所有数据解析完成！");
  }

  @Override
  public void onException(Exception exception, AnalysisContext context) {
    log.error("解析失败，但是继续解析下一行。", exception);
    // 如果是某一个单元格的转换异常 能获取到具体行号
    // 如果要获取头的信息 配合invokeHeadMap使用
    if (exception instanceof ExcelDataConvertException) {
      ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
      log.warn(
          "第{}行，第{}列解析异常，数据为:{}",
          excelDataConvertException.getRowIndex(),
          excelDataConvertException.getColumnIndex(),
          excelDataConvertException.getCellData());
    }
  }

  /** 加上存储数据库 */
  private void saveData() {
    log.info("{}条数据，开始存储数据库！", cachedDataList.size());
    consumer.accept(cachedDataList);
    log.debug("存储数据库成功！");
  }
}
