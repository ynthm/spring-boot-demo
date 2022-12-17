package com.ynthm.demo.mybatis.plus.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.DecimalFormat;

/** @author Ethan Wang */
@JsonComponent
public class MyDoubleSerializer extends JsonSerializer<Double> {
  private final DecimalFormat df = new DecimalFormat("##.#####");

  @Override
  public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {

    gen.writeString(df.format(value));
  }
}
