/*
 * ISystemPath.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */

package net.proserium.common.environment;


/**
 * Defines the system path.
 *
 * @author Patrick Meier
 */
public interface ISystemPath {

  /**
   * Gets the conf path.
   *
   * @return returns the conf path
   */
  String getConfPath();


  /**
   * Gets the data path.
   *
   * @return returns the data path
   */
  String getDataPath();


  /**
   * Gets the log path.
   *
   * @return returns the log path
   */
  String getLogPath();


  /**
   * Gets the temp path.
   *
   * @return returns the temp path
   */
  String getTempPath();


  /**
   * Gets the working path.
   *
   * @return returns the working path
   */
  String getWorkingPath();
}
