package com.ynthm.common.util;

import com.google.common.io.ByteSource;
import com.google.common.io.CharStreams;
import com.ynthm.common.exception.UtilException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class IoUtil {

  private IoUtil() {}

  public static String read(InputStream inputStream, Charset cs) {
    try (Reader reader = new InputStreamReader(inputStream, cs)) {
      return CharStreams.toString(reader);
    } catch (IOException e) {
      throw new UtilException(e);
    }
  }

  public static long copy(InputStream inputStream, OutputStream outputStream) {
    ByteSource byteSource =
        new ByteSource() {
          @Override
          public InputStream openStream() throws IOException {
            return inputStream;
          }
        };

    try {
      return byteSource.copyTo(outputStream);
    } catch (IOException e) {
      throw new UtilException(e);
    }
  }

  public static InputStream inputStream(String initialString) {
    return new ByteArrayInputStream(initialString.getBytes(StandardCharsets.UTF_8));
  }
}
