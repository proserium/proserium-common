/*
 * QueueSystemExecuterImpl.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import jptools.logger.LogInformationHolder;
import jptools.logger.Logger;
import jptools.logger.SimpleLogInformation;
import jptools.util.RandomGenerator;
import net.proserium.common.environment.SystemEnvironmentFactory;
import net.proserium.common.sdo.NodeKey;
import net.proserium.common.system.IQueueSystemExecuter;
import net.proserium.common.system.SystemExecuterFactory;
import net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage;
import net.proserium.common.system.impl.storage.SystemExecuterProgressKey;


/**
 * Implements the {@link IQueueSystemExecuter}.
 *
 * @author Patrick Meier
 */
public class QueueSystemExecuterImpl extends AbstractSystemExecuterImpl implements IQueueSystemExecuter {
  private static final Logger log = Logger.getLogger(QueueSystemExecuterImpl.class);
  private static final String SLASH = "/";
  private static final String CONTROL_SH = "control.sh";

  private String consoleEncoding;
  private int bufferSize;
  private long sleepPollTime;


  /**
   * Constructor
   */
  public QueueSystemExecuterImpl() {
    // StandardCharsets.UTF_8.name() > JDK 7
    consoleEncoding = "UTF-8";
    bufferSize = 100; // 100 byte buffer size
    sleepPollTime = 200;
  }


  /**
   * Starts the exceution
   *
   * @param systemExecuterProgressStorage the system executer progress storage
   * @param nodeKey the node key
   * @param parameter the parameter
   * @return the system executer progress key
   */
  public SystemExecuterProgressKey startExecution(ISystemExecuterProgressStorage systemExecuterProgressStorage,
                                                  NodeKey nodeKey,
                                                  String parameter) {
    return startExecution(systemExecuterProgressStorage, nodeKey.getHostname(), nodeKey.getEnvironmentType(), nodeKey.getName(), parameter);
  }


  /**
   * Starts the exceution
   *
   * @param systemExecuterProgressStorage the system executer progress storage
   * @param environmentType the environment type
   * @param parameter the parameter
   * @return the system executer progress key
   */
  public SystemExecuterProgressKey startExecution(final ISystemExecuterProgressStorage systemExecuterProgressStorage,
                                                  final String hostname,
                                                  final String environmentType,
                                                  final String name,
                                                  final String parameter) {
    final String relativePath = prepareRelativePath(environmentType, name);
    final SystemExecuterProgressKey serverProgressKey = createServerProgressKey(hostname, environmentType, name);

    try {
      Thread thread = new Thread(QueueSystemExecuterImpl.class.getName() + ": execution (progress id:" + serverProgressKey.getIdentifier() + ")") {
        @Override
        public void run() {
          // set the proper log information
          LogInformationHolder.set(new SimpleLogInformation("" + serverProgressKey.getIdentifier()));
          String command = relativePath + CONTROL_SH;

          try {
            log.debug("Start execution...");
            systemExecuterProgressStorage.startProcessing(serverProgressKey);
            Process process = null;

            try {
              log.increaseHierarchyLevel();
              if (parameter != null && !parameter.isEmpty()) {
                command += " " + parameter;
              }

              log.info("Execute [" + command + "], path [" + SystemEnvironmentFactory.getInstance().getSystemEnvironment().getSystemPath().getWorkingPath() + "]...");
              process = SystemExecuterFactory.getInstance().createProcessSystemExecuter().executeCommand(null, command);

              do {
                systemExecuterProgressStorage.addInput(serverProgressKey, processInputStream(process.getInputStream(), getConsoleEncoding(), bufferSize, false));
                systemExecuterProgressStorage.addError(serverProgressKey, processInputStream(process.getErrorStream(), getConsoleEncoding(), bufferSize, false));
                threadSleep(sleepPollTime);
              } while (isProcessAlive(process));
            } finally {
              if (process != null) {
                try {
                  systemExecuterProgressStorage.addInput(serverProgressKey, processInputStream(process.getInputStream(), getConsoleEncoding(), bufferSize, true));
                  systemExecuterProgressStorage.addError(serverProgressKey, processInputStream(process.getErrorStream(), getConsoleEncoding(), bufferSize, true));

                  process = null;
                } catch (Exception e) {
                  // NOP
                }
              }

              log.info("End of execution [" + command + "], path [" + new File("").getAbsolutePath() + "].");
              systemExecuterProgressStorage.endProcessing(serverProgressKey);

              log.decreaseHierarchyLevel();
            }
          } catch (Exception e) {
            log.warn("Catched exception during execution of " + "[" + command + "] on [" + serverProgressKey.getUniqueKey() + "]");
          }
        }
      };

      thread.setDaemon(true);
      thread.start();
    } catch (Exception e) {
      log.error("Could not run application job step runner:" + e.getMessage(), e);
    }

    return serverProgressKey;
  }

  private String getConsoleEncoding() {
    return consoleEncoding;
  }


  /**
   * Check if the process is alive or not
   *
   * @param process the procss
   * @return true if it is alive or not
   */
  protected boolean isProcessAlive(Process process) {
    // jdk 1.8return e.getValue().isAlive();

    try {
      process.exitValue();
      return false;
    } catch (IllegalThreadStateException e) {
      return true;
    }
  }

  /**
   * Prepares the relative path
   *
   * @param environmentType the environment type
   * @param name the name
   * @return the relative path
   */
  protected String prepareRelativePath(final String environmentType, final String name) {
    String currentPath = SystemEnvironmentFactory.getInstance().getSystemEnvironment().getSystemPath().getWorkingPath();

    String relativePath = "";
    if (SystemEnvironmentFactory.getInstance().getSystemEnvironment().getSystemPath().getWorkingPath().startsWith(SLASH)) {
      relativePath = SLASH;
    }

    String[] pathSplit = currentPath.split(SLASH);
    for (int i = 0; i < pathSplit.length; i++) {
      if (pathSplit[i].trim().length() > 0) {
        relativePath += pathSplit[i] + SLASH;

        if (environmentType.equalsIgnoreCase(pathSplit[i].trim())) {
          break;
        }
      }
    }

    if (name != null && !name.isEmpty()) {
      relativePath += name + SLASH;
    }
    return relativePath;
  }


  /**
   * Create a system executer progress key
   *
   * @param hostname the hostname
   * @param environmentType the environment type
   * @param name the name
   * @return the system executer progress key
   */
  protected SystemExecuterProgressKey createServerProgressKey(String hostname, String environmentType, String name) {
    return new SystemExecuterProgressKey(new NodeKey(hostname, environmentType, name), RandomGenerator.getInstance().getLongRandom());
  }


  /**
   * Process the input stream
   *
   * @param inputStream the input stream
   * @param bufferSize the buffer size
   * @param encoding the encoding
   * @param untilEnd read until end of input buffer
   * @return the processed input stream
   */
  protected String processInputStream(InputStream inputStream, String encoding, int bufferSize,
      boolean untilEnd) {
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[bufferSize];
    try {
      int length;

      if (untilEnd) {
        while ((length = inputStream.read(buffer)) != -1) {
          result.write(buffer, 0, length);
        }
      } else {
        length = inputStream.read(buffer);
        if (length != -1) {
          result.write(buffer, 0, length);
        }
      }

      return result.toString(encoding);
    } catch (IOException e) {
      log.debug("Error: " + e.getMessage(), e);
      return e.getMessage();
    }
  }


  /**
   * Sleep
   *
   * @param sleepTime sleep time
   */
  protected void threadSleep(long sleepTime) {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
