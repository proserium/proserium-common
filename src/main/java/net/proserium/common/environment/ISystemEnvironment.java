/*
 * ISystemEnvironment.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */

package net.proserium.common.environment;


/**
 * Defines the system environment.
 *
 * @author Patrick Meier
 */
public interface ISystemEnvironment {
  /**
   * Gets the hostname (the logical hostname).
   *
   * @return returns the hostname (logical hostname).
   */
  String getHostname();


  /**
   * Gets the physical hostname.
   *
   * @return returns the physical hostname
   */
  String getPhysicalHostname();


  /**
   * Gets the domain name.
   *
   * @return returns the domain name
   */
  String getDomainName();


  /**
   * Gets the environment name.
   *
   * @return returns the environment name
   */
  String getEnvironmentName();


  /**
   * Gets the system path.
   *
   * @return returns the system path
   */
  ISystemPath getSystemPath();
}
