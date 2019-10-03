/*
 * PathHelper.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.environment.util;

import java.io.File;
import java.io.IOException;
import jptools.logger.Logger;
import jptools.resource.FileAccess;


/**
 * Implements a path helper class
 *
 * @author Patrick Meier
 */
public final class PathHelper {
  private static final Logger log = Logger.getLogger(PathHelper.class);

  /**
   * Private class, the only instance of the singelton which will be created by accessing the holder class.
   *
   * @author Patrick Meier
   */
  private static class HOLDER {
    static final PathHelper INSTANCE = new PathHelper();
  }


  /**
   * Constructor
   */
  private PathHelper() {}


  /**
   * Get the instance
   *
   * @return the instance
   */
  public static PathHelper getInstance() {
    return HOLDER.INSTANCE;
  }


  /**
   * Create the path.
   *
   * @param path the path to create
   */
  public void createPath(String path) {
    try {
      boolean createPath = false;
      if (!FileAccess.getInstance().existFile(path)) {
        createPath = true;
        log.debug("Create path [" + path + "]...");
      }

      FileAccess.getInstance().checkFileAccess(path, true);

      if (createPath) {
        new File(path).mkdirs();
      }

    } catch (IOException e) {
      throw new IllegalStateException("Could not access to [" + path + "]!");
    }
  }


  /**
   * Append a slash at the end of the path if it is necessary.
   *
   * @param pathName the pathname
   * @return the prepared pathname
   */
  public String slashifyPath(String pathName) {
    String result = pathName;
    result = result.replace("\\\\", "/");

    if (!result.endsWith("/")) {
      result += "/";
    }

    return result;
  }
}
