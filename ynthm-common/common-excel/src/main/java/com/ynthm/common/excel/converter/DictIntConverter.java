package com.ynthm.common.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.util.Assert;

/** @author Ethan Wang */
public class DictIntConverter implements Converter<Number> {

  @Override
  public Class<Number> supportJavaTypeKey() {
    return Number.class;
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return CellDataTypeEnum.NUMBER;
  }

  @Override
  public Number convertToJavaData(
      ReadCellData cellData,
      ExcelContentProperty excelContentProperty,
      GlobalConfiguration globalConfiguration)
      throws Exception {

    ExcelDictFormat annotation =
        excelContentProperty.getField().getAnnotation(ExcelDictFormat.class);

    Assert.notNull(annotation, "枚举值不合法， 请使用 @ExcelDictFormat 指定枚举");
    String parentCode = annotation.parentCode();

    return DictHelper.getInstance().getValue(parentCode, cellData.getStringValue());
  }

  @Override
  public WriteCellData<String> convertToExcelData(
      Number t, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration)
      throws Exception {

    ExcelDictFormat annotation =
        excelContentProperty.getField().getAnnotation(ExcelDictFormat.class);

    Assert.notNull(annotation, "枚举值不合法， 请使用 @ExcelDictFormat 指定枚举");
    String parentCode = annotation.parentCode();
    return new WriteCellData<>(DictHelper.getInstance().getLabel(parentCode, t.intValue()));
  }
}
