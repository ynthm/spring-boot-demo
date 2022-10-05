package com.ynthm.demo.web.web.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 */
@Data
public class UploadData implements Serializable {
  private String string;
  private Double doubleData;
}
