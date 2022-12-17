package com.ynthm.demo.mybatis.plus.config.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * PSQLException: Cannot convert the column of type TIMESTAMPTZ to requested type * timestam *
 *
 * <p>需要设置 mybatis-plus.type-handlers-package
 *
 * @author Ethan Wang
 */
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
    ps.setTimestamp(i, Timestamp.valueOf(parameter));
  }

  @Override
  public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnName);
    return getLocalDateTime(timestamp);
  }

  @Override
  public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnIndex);
    return getLocalDateTime(timestamp);
  }

  @Override
  public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    Timestamp timestamp = cs.getTimestamp(columnIndex);
    return getLocalDateTime(timestamp);
  }

  private static LocalDateTime getLocalDateTime(Timestamp timestamp) {
    if (timestamp != null) {
      return timestamp.toLocalDateTime();
    }
    return null;
  }
}
