/*
 * AbstractProcessSystemCommandExecuterImpl.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import jptools.logger.Logger;
import net.proserium.common.system.IProcessSystemExecuter;
import net.proserium.common.system.impl.AbstractSystemExecuterImpl;
import net.proserium.common.util.StreamUtil;


/**
 * Abstract base class for the {@link IProcessSystemExecuter}.
 *
 * @author Patrick Meier
 */
public abstract class AbstractProcessSystemCommandExecuterImpl extends AbstractSystemExecuterImpl implements IProcessSystemExecuter {
  private static final Logger log = Logger.getLogger(AbstractProcessSystemCommandExecuterImpl.class);


  /**
   * @see net.proserium.common.system.IProcessSystemExecuter#executeCommand(java.lang.String, java.lang.String[])
   */
  @Override
  public Process executeCommand(String path, String... commandList) {
    if (commandList == null || commandList.length == 0) {
      throw new IllegalArgumentException("Invalid command!");
    }

    List<String> commandParameterList = Arrays.asList(commandList);

    // create process builder
    ProcessBuilder processBuilder = createProcessBuilder(commandParameterList);
    processBuilder.redirectErrorStream(true);

    Process process = null;
    try {
      String pathInfo = " in current path.";

      if (path != null) {
        // System.getProperty("user.home")
        processBuilder.directory(new File(path));
        pathInfo = " in path [" + path + "].";
      }

      log.debug("Execute command: [" + commandParameterList + "]" + pathInfo);
      process = processBuilder.start();
    } catch (Exception e) {
      log.warn("Error occured while executing command " + commandParameterList + ": " + e.getMessage(), e);
    }

    return process;
  }


  /**
   * @see net.proserium.common.system.IProcessSystemExecuter#executeCommand(java.lang.String, int, java.lang.String[])
   */
  @Override
  public Process executeCommand(String path, int numberOfSecondsToWait, String... commandList) {
    if (commandList == null || commandList.length == 0) {
      throw new IllegalArgumentException("Invalid command!");
    }

    List<String> commandParameterList = Arrays.asList(commandList);

    Process process = null;
    InputStream processOutput = null;
    InputStream processErrorOutput = null;
    try {
      process = executeCommand(path, commandList);

      // to capture output from the shell
      processOutput = process.getInputStream();
      processErrorOutput = process.getErrorStream();
      boolean hasEnded = false;

      // wait for the shell to finish and get the return code
      if (numberOfSecondsToWait <= 0) {
        process.waitFor();
        hasEnded = true;
      } else {
        process.waitFor();
        hasEnded = true;
        // TODO java 8: hasEnded = process.waitFor(numberOfSecondsToWait, TimeUnit.SECONDS);
      }

      if (hasEnded) {
        String message = "Command [" + commandParameterList + "] -> returns " + process.exitValue();
        boolean messageLogged = false;

        String outputMessage = convertStreamToStr(processOutput);
        String errorMessage = convertStreamToStr(processErrorOutput);

        if (outputMessage != null && !outputMessage.isEmpty()) {
          log.debug(message + ", output:\n" + outputMessage);
          messageLogged = true;
        }

        if (errorMessage != null && !errorMessage.isEmpty()) {
          if (!messageLogged) {
            log.debug(message + ", error:\n" + errorMessage);
          } else {
            log.debug("\nError:\n" + errorMessage);
          }
        }
      }
    } catch (Exception e) {
      log.warn(
          "Error occured while executing command " + commandParameterList + ": " + e.getMessage(),
          e);
    } finally {
      if (processOutput != null) {
        try {
          processOutput.close();
        } catch (Exception e) {
          // NOP
        }
      }
      if (processErrorOutput != null) {
        try {
          processErrorOutput.close();
        } catch (Exception e) {
          // NOP
        }
      }
    }

    return process;
  }


  /**
   * Prepare platform dependent command list.
   *
   * @param osName the os name
   * @param commandList the command list to execute
   * @return the platform dependent command list
   */
  protected abstract List<String> preparePlatformDependentCommandList(String osName,
      List<String> commandList);


  /**
   * Create a process builder
   *
   * @param commandParameterList the command parameter list to execute
   * @return the process builder
   */
  protected ProcessBuilder createProcessBuilder(List<String> commandParameterList) {
    String osName = System.getProperty("os.name").toLowerCase();
    List<String> commandList = preparePlatformDependentCommandList(osName, commandParameterList);
    log.debug("Execute command: " + commandList);
    return new ProcessBuilder(commandList);
  }


  /**
   * Convert the InputStream to String we use the Reader.read(char[] buffer) method. We iterate
   * until the Reader return -1 which means there's no more data to read. We use the StringWriter
   * class to produce the string.
   *
   * @param is the input stream
   * @return the result
   * @throws IOException in case of stream convert issues
   */
  protected String convertStreamToStr(InputStream is) throws IOException {
    if (is == null) {
      return "";
    }

    try {
      return StreamUtil.getInstance().convertStreamToStr(is);
    } finally {
      is.close();
    }
  }
}
