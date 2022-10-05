package com.ynthm.demo.web.web.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据类
 *
 * @author Ethan Wang
 */
@Data
public class DownloadData implements Serializable {
  @ExcelProperty("字符串标题")
  private String string;

  @ExcelProperty("日期标题")
  private Date date;

  @ExcelProperty("数字标题")
  private Double doubleData;
}
