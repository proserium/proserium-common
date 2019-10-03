/*
 * SystemPathImpl.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.environment.impl;

import java.io.Serializable;
import net.proserium.common.environment.ISystemPath;
import net.proserium.common.environment.util.PathHelper;
import net.proserium.common.environment.util.PathStack;
import net.proserium.common.object.AbstractBaseObject;


/**
 * Implements the {@link ISystemPath}.
 *
 * @author Patrick Meier
 */
public class SystemPathImpl extends AbstractBaseObject implements ISystemPath, Serializable, Comparable<SystemPathImpl>, Cloneable {
  private static final long serialVersionUID = 558963703627473141L;

  private String confPath;
  private String dataPath;
  private String logPath;
  private String tempPath;
  private String workingPath;


  /**
   * Constructor
   *
   * @param confPath sets the conf path
   * @param dataPath sets the data path
   * @param logPath sets the log path
   * @param tempPath sets the temp path
   * @param workingPath sets the working path
   */
  public SystemPathImpl(String confPath, String dataPath, String logPath, String tempPath, String workingPath) {
    super();
    this.confPath = PathHelper.getInstance().slashifyPath(confPath);
    this.dataPath = PathHelper.getInstance().slashifyPath(dataPath);
    this.logPath = PathHelper.getInstance().slashifyPath(logPath);
    this.tempPath = PathHelper.getInstance().slashifyPath(tempPath);
    this.workingPath = PathHelper.getInstance().slashifyPath(workingPath);
  }


  /**
   * @see net.proserium.common.environment.ISystemPath#getConfPath()
   */
  @Override
  public String getConfPath() {
    PathHelper.getInstance().createPath(confPath);
    return confPath;
  }


  /**
   * @see net.proserium.common.environment.ISystemPath#getDataPath()
   */
  @Override
  public String getDataPath() {
    PathHelper.getInstance().createPath(dataPath);
    return dataPath;
  }


  /**
   * @see net.proserium.common.environment.ISystemPath#getLogPath()
   */
  @Override
  public String getLogPath() {
    return logPath;
  }


  /**
   * @see net.proserium.common.environment.ISystemPath#getTempPath()
   */
  @Override
  public String getTempPath() {
    String tmpPath = tempPath;

    String subPath = PathStack.getPath();
    if (subPath != null && !subPath.trim().isEmpty()) {
      tmpPath += subPath;
    }

    tmpPath = PathHelper.getInstance().slashifyPath(tmpPath);
    PathHelper.getInstance().createPath(tmpPath);
    return tmpPath;
  }


  /**
   * @see net.proserium.common.environment.ISystemPath#getWorkingPath()
   */
  @Override
  public String getWorkingPath() {
    return workingPath;
  }


  /**
   * @see net.proserium.common.object.AbstractBaseObject#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1000003;
    int result = super.hashCode();

    if (confPath != null) {
      result = prime * result + confPath.hashCode();
    }

    if (dataPath != null) {
      result = prime * result + dataPath.hashCode();
    }

    if (logPath != null) {
      result = prime * result + logPath.hashCode();
    }

    if (tempPath != null) {
      result = prime * result + tempPath.hashCode();
    }

    if (workingPath != null) {
      result = prime * result + workingPath.hashCode();
    }

    return result;
  }


  /**
   * @see net.proserium.common.object.AbstractBaseObject#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if (oth == null) {
      return false;
    }

    if (oth.getClass() != getClass()) {
      return false;
    }

    if (!super.equals(oth)) {
      return false;
    }

    SystemPathImpl other = (SystemPathImpl) oth;
    if (!equalsObjectHelper(confPath, other.confPath)) {
      return false;
    }

    if (!equalsObjectHelper(dataPath, other.dataPath)) {
      return false;
    }

    if (!equalsObjectHelper(logPath, other.logPath)) {
      return false;
    }

    if (!equalsObjectHelper(tempPath, other.tempPath)) {
      return false;
    }

    if (!equalsObjectHelper(workingPath, other.workingPath)) {
      return false;
    }

    return true;
  }


  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(SystemPathImpl other) {
    if (this == other) {
      return 0;
    }

    if (other == null) {
      return -1;
    }

    int result = -1;

    result = compareToObjectHelper(confPath, other.confPath);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(dataPath, other.dataPath);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(logPath, other.logPath);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(tempPath, other.tempPath);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(workingPath, other.workingPath);
    if (result != 0) {
      return result;
    }

    return result;
  }


  /**
   * @see net.proserium.common.object.AbstractBaseObject#toString()
   */
  @Override
  public String toString() {
    final StringBuilder stAttr = new StringBuilder("': '");
    final StringBuilder edAttr = new StringBuilder("'");
    final StringBuilder inAttr = new StringBuilder("  ");
    final StringBuilder nl = new StringBuilder("\n");

    StringBuilder buffer = new StringBuilder();
    buffer.append("SystemPathImpl:" + nl);
    buffer.append(inAttr).append("confPath").append(stAttr).append(confPath).append(edAttr).append(nl);
    buffer.append(inAttr).append("dataPath").append(stAttr).append(dataPath).append(edAttr).append(nl);
    buffer.append(inAttr).append("logPath").append(stAttr).append(logPath).append(edAttr).append(nl);
    buffer.append(inAttr).append("tempPath").append(stAttr).append(tempPath).append(edAttr).append(nl);
    buffer.append(inAttr).append("workingPath").append(stAttr).append(workingPath).append(edAttr).append(nl);
    buffer.append(super.toString());
    return buffer.toString();
  }


  /**
   * @see net.proserium.common.object.AbstractBaseObject#clone()
   */
  @Override
  public SystemPathImpl clone() {
    SystemPathImpl obj = (SystemPathImpl) super.clone();
    obj.confPath = (String)cloneHelper(confPath);
    obj.dataPath = (String)cloneHelper(dataPath);
    obj.logPath = (String)cloneHelper(logPath);
    obj.tempPath = (String)cloneHelper(tempPath);
    obj.workingPath = (String) cloneHelper(workingPath);
    return obj;
  }
}
