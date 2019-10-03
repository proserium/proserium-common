/*
 * SystemEnvironmentTest.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.environment;

import org.junit.jupiter.api.Test;

public class SystemEnvironmentTest {

  /**
   * TEst access system environment
   */
  @Test
  public void accessSystemEnvironment() {
    SystemEnvironmentFactory.getInstance().getSystemEnvironment().getHostname();

  }
}
