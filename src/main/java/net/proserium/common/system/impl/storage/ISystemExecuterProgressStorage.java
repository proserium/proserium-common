/*
 * ISystemExecuterProgressStorage.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl.storage;


/**
 * Defines the system executer progress storage interface.
 *
 * @author Patrick Meier
 */
public interface ISystemExecuterProgressStorage {
  /**
   * Mark start processing
   *
   * @param systemExecuterProgressKey the system executer progress key
   */
  void startProcessing(SystemExecuterProgressKey systemExecuterProgressKey);


  /**
   * Check if processing exists
   *
   * @param systemExecuterProgressKey the system executer progress key
   * @return true if it is existing
   */
  boolean exitProcessing(SystemExecuterProgressKey systemExecuterProgressKey);


  /**
   * Mark end processing
   *
   * @param systemExecuterProgressKey the system executer progress key
   */
  void endProcessing(SystemExecuterProgressKey systemExecuterProgressKey);


  /**
   * Read the server progress
   *
   * @param systemExecuterProgressKey the system executer progress key
   * @return the server instance progress data
   */
  SystemExecuterProgressData readServerProgress(SystemExecuterProgressKey systemExecuterProgressKey);


  /**
   * Adds the input
   *
   * @param systemExecuterProgressKey the system executer progress key
   * @param data the input
   */
  void addInput(SystemExecuterProgressKey systemExecuterProgressKey, String data);


  /**
   * Adds the error
   *
   * @param systemExecuterProgressKey the system executer progress key
   * @param data the input
   */
  void addError(SystemExecuterProgressKey systemExecuterProgressKey, String data);
}
