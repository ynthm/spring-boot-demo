package com.ynthm.common.util;

import java.net.URLConnection;
import java.nio.file.Paths;

/**
 * MimetypesFileTypeMap .getDefaultFileTypeMap() .getContentType(attachment.getFileName());
 *
 * @author Ynthm Wang
 * @version 1.0
 */
public class FileUtil {
  private FileUtil() {}

  public static String getMimeType(String filePath) {
    String contentType = URLConnection.getFileNameMap().getContentTypeFor(filePath);
    if (null == contentType) {
      // 补充一些常用的mimeType
      if (filePath.endsWith(".css")) {
        contentType = "text/css";
      } else if (filePath.endsWith(".js")) {
        contentType = "application/x-javascript";
      }
    }

    // 补充
    if (null == contentType) {
      contentType = getMimeType(Paths.get(filePath).toString());
    }

    return contentType;
  }
}
