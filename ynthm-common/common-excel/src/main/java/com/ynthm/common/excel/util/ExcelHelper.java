package com.ynthm.common.excel.util;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynthm.common.domain.PageReq;
import com.ynthm.common.domain.PageResp;
import com.ynthm.common.domain.Result;
import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.function.Function;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
public class ExcelHelper {

  private ObjectMapper objectMapper;

  @Resource
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public <T extends Serializable, P extends Serializable> void export(
      HttpServletResponse response,
      Class<T> type,
      String fileName,
      String sheetName,
      P param,
      Function<PageReq<P>, PageResp<T>> function)
      throws IOException {
    try {
      String tempFileName =
          fileName + "_" + Instant.now().getEpochSecond() + ExcelTypeEnum.XLSX.getValue();
      ServletUtil.responseForDownload(response, tempFileName);

      ExcelUtil<T> excelUtil = new ExcelUtil<>(type);
      excelUtil.export(sheetName, response.getOutputStream(), param, function);
    } catch (Exception e) {
      // 重置 response
      log.error("导出 Excel 失败", e);
      ServletUtil.renderString(
          response,
          objectMapper.writeValueAsString(
              Result.error(BaseResultCode.EXCEL_EXPORT_FAILED, e.getMessage())));
    }
  }
}
