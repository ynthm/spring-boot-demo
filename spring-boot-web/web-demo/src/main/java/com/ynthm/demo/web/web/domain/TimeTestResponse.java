package com.ynthm.demo.web.web.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.*;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Ynthm
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class TimeTestResponse {
  private Instant instant;
  private LocalDateTime localDateTime;
  private LocalTime localTime;
  private LocalDate localDate;

  private OffsetDateTime offsetDateTime;
  private OffsetTime offsetTime;
  private ZonedDateTime zonedDateTime;
  private Date date;
  private TimeZone timeZone;
}
