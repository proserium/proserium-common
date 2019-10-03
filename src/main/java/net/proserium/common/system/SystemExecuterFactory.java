/*
 * SystemExecuterFactory.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system;

import jptools.logger.Logger;
import net.proserium.common.system.impl.QueueSystemExecuterImpl;
import net.proserium.common.system.impl.process.LinuxProcessSystemCommandExecuterImpl;
import net.proserium.common.system.impl.process.UnixProcessSystemCommandExecuterImpl;
import net.proserium.common.system.impl.process.WindowsProcessSystemCommandExecuterImpl;


/**
 * Defines the system executer factory
 *
 * @author Patrick Meier
 */
public final class SystemExecuterFactory {
  private static final Logger log = Logger.getLogger(SystemExecuterFactory.class);

  /**
   * Private class, the only instance of the singelton which will be created by accessing the holder class.
   */
  private static class HOLDER {
    static final SystemExecuterFactory INSTANCE = new SystemExecuterFactory();
  }


  /**
   * Constructor
   */
  private SystemExecuterFactory() {
  }


  /**
   * Get the instance
   *
   * @return the instance
   */
  public static SystemExecuterFactory getInstance() {
    return HOLDER.INSTANCE;
  }


  /**
   * Creates a process system executer
   *
   * @return the process system executer
   */
  public IProcessSystemExecuter createProcessSystemExecuter() {
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.startsWith("windows")) {
      log.debug("Choose " + WindowsProcessSystemCommandExecuterImpl.class.getName() + " as executer.");
      return new WindowsProcessSystemCommandExecuterImpl();
    } else if (osName.startsWith("linux")) {
      log.debug("Choose " + LinuxProcessSystemCommandExecuterImpl.class.getName() + " as executer.");
      return new LinuxProcessSystemCommandExecuterImpl();
    }

    log.debug("Choose " + UnixProcessSystemCommandExecuterImpl.class.getName() + " as executer.");
    return new UnixProcessSystemCommandExecuterImpl();
  }


  /**
   * Creates a queue system executer
   *
   * @return the queue system executer
   */
  public IQueueSystemExecuter createQueueSystemExecuter() {
    return new QueueSystemExecuterImpl();
  }
}
