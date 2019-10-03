/*
 * ThreadUtil.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.util;

/**
 * Thread util class
 *
 * @author Patrick Meier
 */
public final class ThreadUtil {

  /**
   * Private class, the only instance of the singelton which will be created by accessing the holder class.
   *
   * @author pmeier
   */
  private static class HOLDER {
    static final ThreadUtil INSTANCE = new ThreadUtil();
  }


  /**
   * Constructor
   */
  private ThreadUtil() {
    // NOP
  }


  /**
   * Get the instance
   *
   * @return the instance
   */
  public static ThreadUtil getInstance() {
    return HOLDER.INSTANCE;
  }


  /**
   * Sleep
   *
   * @param sleepTime sleep time
   */
  public void threadSleep(long sleepTime) {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
