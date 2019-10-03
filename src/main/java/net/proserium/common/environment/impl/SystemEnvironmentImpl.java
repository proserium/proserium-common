/*
 * SystemEnvironmentImpl.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.environment.impl;

import java.io.Serializable;
import net.proserium.common.environment.ISystemEnvironment;
import net.proserium.common.environment.ISystemPath;
import net.proserium.common.object.AbstractBaseObject;


/**
 * Implements the {@link ISystemEnvironment}.
 *
 * @author Patrick Meier
 */
public class SystemEnvironmentImpl extends AbstractBaseObject implements ISystemEnvironment, Serializable, Comparable<SystemEnvironmentImpl>, Cloneable {
  private static final long serialVersionUID = -3662420880608220446L;

  private String hostname;
  private String physicalHostname;
  private String domainName;
  private String environmentName;
  private ISystemPath systemPath;


  /**
   * Constructor
   *
   * @param hostname sets the hostname (the logical hostname)
   * @param physicalHostname the physical hostname
   * @param domainName sets the domain name
   * @param environmentName sets the environment name
   * @param systemPath sets the system path
   */
  public SystemEnvironmentImpl(String hostname, String physicalHostname, String domainName, String environmentName, ISystemPath systemPath) {
    super();
    this.hostname = hostname;
    this.physicalHostname = physicalHostname;
    this.domainName = domainName;
    this.environmentName = environmentName;
    this.systemPath = systemPath;
  }


  /**
   * @see net.proserium.common.environment.ISystemEnvironment#getHostname()
   */
  public String getHostname() {
    return hostname;
  }


  /**
   * @see net.proserium.common.environment.ISystemEnvironment#getPhysicalHostname()
   */
  public String getPhysicalHostname() {
    return physicalHostname;
  }


  /**
   * @see net.proserium.common.environment.ISystemEnvironment#getDomainName()
   */
  public String getDomainName() {
    return domainName;
  }


  /**
   * @see net.proserium.common.environment.ISystemEnvironment#getEnvironmentName()
   */
  public String getEnvironmentName() {
    return environmentName;
  }


  /**
   * @see net.proserium.common.environment.ISystemEnvironment#getSystemPath()
   */
  public ISystemPath getSystemPath() {
    return systemPath;
  }


  /**
   * @see net.proserium.common.object.AbstractBaseObject#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1000003;
    int result = super.hashCode();

    if (hostname != null) {
      result = prime * result + hostname.hashCode();
    }

    if (physicalHostname != null) {
      result = prime * result + physicalHostname.hashCode();
    }

    if (domainName != null) {
      result = prime * result + domainName.hashCode();
    }

    if (environmentName != null) {
      result = prime * result + environmentName.hashCode();
    }

    if (systemPath != null) {
      result = prime * result + systemPath.hashCode();
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

    SystemEnvironmentImpl other = (SystemEnvironmentImpl) oth;
    if (!equalsObjectHelper(hostname, other.hostname)) {
      return false;
    }

    if (!equalsObjectHelper(physicalHostname, other.physicalHostname)) {
      return false;
    }

    if (!equalsObjectHelper(domainName, other.domainName)) {
      return false;
    }

    if (!equalsObjectHelper(environmentName, other.environmentName)) {
      return false;
    }

    if (!equalsObjectHelper(systemPath, other.systemPath)) {
      return false;
    }

    return true;
  }


  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(SystemEnvironmentImpl other) {
    if (this == other) {
      return 0;
    }

    if (other == null) {
      return -1;
    }

    int result = -1;
    result = compareToObjectHelper(hostname, other.hostname);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(physicalHostname, other.physicalHostname);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(domainName, other.domainName);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(environmentName, other.environmentName);
    if (result != 0) {
      return result;
    }

    result = compareToObjectHelper(systemPath, other.systemPath);
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
    buffer.append("SystemEnvironmentImpl:" + nl);
    buffer.append(inAttr).append("hostname").append(stAttr).append(hostname).append(edAttr).append(nl);
    buffer.append(inAttr).append("physicalHostname").append(stAttr).append(physicalHostname).append(edAttr).append(nl);
    buffer.append(inAttr).append("domainName").append(stAttr).append(domainName).append(edAttr).append(nl);
    buffer.append(inAttr).append("environmentName").append(stAttr).append(environmentName).append(edAttr).append(nl);
    buffer.append(inAttr).append("systemPath").append(stAttr).append(systemPath).append(edAttr).append(nl);
    buffer.append(super.toString());
    return buffer.toString();
  }


  /**
   * @see net.proserium.common.object.AbstractBaseObject#clone()
   */
  @Override
  public SystemEnvironmentImpl clone() {
    SystemEnvironmentImpl obj = (SystemEnvironmentImpl) super.clone();
    obj.hostname = (String) cloneHelper(hostname);
    obj.physicalHostname = (String) cloneHelper(physicalHostname);
    obj.domainName = (String) cloneHelper(domainName);
    obj.environmentName = (String) cloneHelper(environmentName);
    obj.systemPath = (ISystemPath) cloneHelper(systemPath);
    return obj;
  }
}
