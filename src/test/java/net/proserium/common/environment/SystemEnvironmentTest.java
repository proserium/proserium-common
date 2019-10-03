/*
 * SystemEnvironmentTest.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.environment;

import org.junit.jupiter.api.Test;
import jptools.logger.Logger;

public class SystemEnvironmentTest {

  
  private static final Logger log = Logger.getLogger(SystemEnvironmentTest.class);

  @Test
  public void accessSystemEnvironment() {
    SystemEnvironmentFactory.getInstance().getSystemEnvironment().getHostname();

  }
}
