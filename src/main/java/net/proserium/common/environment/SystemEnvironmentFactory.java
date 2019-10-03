/*
 * SystemEnvironmentFactory.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.environment;

import java.io.File;
import jptools.logger.Logger;
import jptools.resource.FileAccess;
import jptools.util.JavaInformation;
import jptools.util.StringHelper;
import net.proserium.common.environment.impl.SystemEnvironmentImpl;
import net.proserium.common.environment.impl.SystemPathImpl;
import net.proserium.common.environment.util.PathStack;


/**
 * Defines the system environment factory.
 *
 * @author Patrick Meier
 */
public final class SystemEnvironmentFactory {
  private static final Logger log = Logger.getLogger(SystemEnvironmentFactory.class);

  /**
   * Private class, the only instance of the singelton which will be created by accessing the holder class.
   */
  private static class HOLDER {
    static final SystemEnvironmentFactory INSTANCE = new SystemEnvironmentFactory();
  }

  /** PROSRIUM_ENVIRONMENT_TYPE of the environment */
  public static final String PROSRIUM_ENVIRONMENT_TYPE = "PROSRIUM_ENVIRONMENT_TYPE";

  /** PROSRIUM_DOMAIN_NAME of the environment */
  public static final String PROSRIUM_DOMAIN_NAME = "PROSRIUM_DOMAIN_NAME";

  /** PROSRIUM_HOST_ALIAS of the environment */
  public static final String PROSRIUM_HOST_ALIAS = "PROSRIUM_HOST_ALIAS";

  /** PROSRIUM_CONF_PATHNAME of the environment */
  public static final String PROSRIUM_CONF_PATHNAME = "PROSRIUM_CONF_PATHNAME";

  /** PROSRIUM_DATA_PATHNAME of the environment */
  public static final String PROSRIUM_DATA_PATHNAME = "PROSRIUM_DATA_PATHNAME";

  /** PROSRIUM_LOG_PATHNAME of the environment */
  public static final String PROSRIUM_LOG_PATHNAME = "PROSRIUM_LOG_PATHNAME";

  private static final String SLASH = "/";
  private ISystemEnvironment systemEnvironment;
  private String basePath;


  /**
   * Constructor
   */
  private SystemEnvironmentFactory() {
    basePath = "";
    systemEnvironment = null;
    PathStack.unset();

    init();
  }


  /**
   * Get the application environment instance
   *
   * @return the instance
   */
  public static SystemEnvironmentFactory getInstance() {
    return HOLDER.INSTANCE;
  }


  /**
   * Get the system environment
   *
   * @return the system environment
   */
  public ISystemEnvironment getSystemEnvironment() {
    return systemEnvironment;
  }


  /**
   * Initialize the application enironment
   */
  protected void init() {

    log.debug("Initialize the proserium system environment...");
    log.increaseHierarchyLevel();

    try {
      this.basePath = "";

      String p = (new File("")).getAbsolutePath();
      p = FileAccess.getInstance().simplifyPath(p.replace('\\', '/'));
      String workingPath = FileAccess.getInstance().simplifyPath(p);

      // read from environment
      String environmentType = readEnvironment(PROSRIUM_ENVIRONMENT_TYPE);
      String environmentName = "dev";
      if (environmentType != null && !environmentType.trim().isEmpty()) {
        environmentName = environmentType;
      }

      ISystemPath systemPath = new SystemPathImpl(preparePath(basePath, readEnvironment(PROSRIUM_CONF_PATHNAME), "conf"),
                                                  preparePath(basePath, readEnvironment(PROSRIUM_DATA_PATHNAME), "data"),
                                                  preparePath(basePath, readEnvironment(PROSRIUM_LOG_PATHNAME), "logs"),
                                                  preparePath(System.getProperty("java.io.tmpdir"), null, null),
                                                  workingPath);

      String physicalHostname = new JavaInformation().getHostInformation();
      String hostname = readEnvironment(PROSRIUM_HOST_ALIAS);
      if (hostname == null || hostname.trim().isEmpty()) {
        hostname = physicalHostname;
      }

      String domainName = readEnvironment(PROSRIUM_DOMAIN_NAME);
      systemEnvironment = new SystemEnvironmentImpl(hostname, physicalHostname, domainName, environmentName, systemPath);
      log.info("Initialized system environment: " + systemEnvironment);
    } finally {
      log.decreaseHierarchyLevel();
    }
  }


  /**
   * Prepare path
   *
   * @param startPath the start path
   * @param pathName the path name
   * @return the prepared path
   */
  private String preparePath(String startPath, String pathName, String defaultName) {
    String path = "";
    if (startPath != null && !startPath.trim().isEmpty()) {
      path = StringHelper.replace(startPath, "\\", SLASH);
    }

    if (pathName != null && !pathName.isEmpty()) {
      if (!path.trim().isEmpty() && !path.endsWith(SLASH)) {
        path += SLASH;
      }

      path += FileAccess.getInstance().simplifyPath(pathName);
    } else if (defaultName != null && !defaultName.isEmpty()) {
      if (!path.trim().isEmpty() && !path.endsWith(SLASH)) {
        path += SLASH;
      }

      path += FileAccess.getInstance().simplifyPath(defaultName);
    }

    if (!path.trim().isEmpty() && !path.endsWith(SLASH)) {
      path += SLASH;
    }

    return path;
  }


  /**
   * Read environment setting (first prio: environment, second system property)
   *
   * @param key the key
   * @return the value
   */
  private String readEnvironment(String key) {
    String result = System.getenv(key);
    if (result == null || result.isEmpty()) {
      result = System.getProperty(key);
    }

    return result;
  }
}
