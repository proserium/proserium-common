/*
 * StreamUtil.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import jptools.io.ChannelUtil;

/**
 * Stream utility class
 *
 * @author Patrick Meier
 */
public final class StreamUtil {
  /**
   * Private class, the only instance of the singelton which will be created by accessing the holder class.
   *
   * @author pmeier
   */
  private static class HOLDER {
    static final StreamUtil INSTANCE = new StreamUtil();
  }


  /**
   * Constructor
   */
  private StreamUtil() {
    // NOP
  }


  /**
   * Get the instance
   *
   * @return the instance
   */
  public static StreamUtil getInstance() {
    return HOLDER.INSTANCE;
  }


  /**
   * Convert the InputStream to String we use the Reader.read(char[] buffer) method. We iterate until the Reader return -1 which means
   * there's no more data to read.
   *
   * @param is the input stream
   * @return the result
   * @throws IOException in case of stream convertion issues
   */
  public String convertStreamToStr(InputStream is) throws IOException {
    if (is == null) {
      return "";
    }

    ByteArrayOutputStream dest = new ByteArrayOutputStream();
    ChannelUtil.getInstance().channelCopy(is, dest);
    return dest.toString();
  }


  /**
   * Convert the InputStream to String we use the Reader.read(char[] buffer) method. We iterate until the Reader return -1 which means
   * there's no more data to read.
   *
   * @param is the input stream
   * @param charsetName the charset name
   * @return the result
   * @throws IOException in case of stream convertion issues
   */
  public String convertStreamToStr(InputStream is, String charsetName) throws IOException {
    if (is == null) {
      return "";
    }

    ByteArrayOutputStream dest = new ByteArrayOutputStream();
    ChannelUtil.getInstance().channelCopy(is, dest);
    return dest.toString(charsetName);
  }
}
