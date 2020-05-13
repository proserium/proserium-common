/*
 * IProcessSystemExecuter.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system;

/**
 * Defines the process system executer
 *
 * @author Patrick Meier
 */
public interface IProcessSystemExecuter {
  /**
   * Execute a new command
   *
   * @param path the path to execute the command or null
   * @param commandList the command list
   * @return the process
   */
  Process executeCommand(String path, String... commandList);


  /**
   * Execute a new command
   *
   * @param path the path to execute the command or null
   * @param numberOfSecondsToWait the number of seconds to wait. If the value less or equals 0, it waits until
   *        it is executed
   * @param commandList the command list
   * @return the process
   */
  Process executeCommand(String path, int numberOfSecondsToWait, String... commandList);
}
