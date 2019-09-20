/*
 * NodeKey.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.sdo;

import java.io.Serializable;

/**
 * Defines the node key.
 *
 * @author Patrick Meier
 */
public class NodeKey implements Serializable {
  /** serialVersionUID */
  private static final long serialVersionUID = 2785964409451226870L;

  /** The hostname */
  private String hostname;

  /** The server environment type, e.g. prod, demo, acpt, int, dev, build */
  private String environmentType;

  /** The name */
  private String name;

  /** The alias name */
  private String aliasName;


  /**
   * Constructor
   *
   * @param hostname the hostname
   * @param environmentType the environment type
   * @param name the name
   */
  public NodeKey(String hostname, String environmentType, String name) {
    this(hostname, environmentType, name, null);
  }


  /**
   * Constructor
   *
   * @param hostname the hostname
   * @param environmentType the environment type
   * @param name the name
   * @param aliasName the alias name
   */
  public NodeKey(String hostname, String environmentType, String name, String aliasName) {
    this.hostname = hostname;
    this.environmentType = environmentType;
    this.name = name;
    this.aliasName = aliasName;
  }


  /**
   * Gets the server environment type.
   *
   * @return Returns the server environment type
   */
  public String getHostname() {
    return hostname;
  }


  /**
   * Sets the hostname.
   *
   * @param hostname the hostname
   */
  protected void setHostname(String hostname) {
    this.hostname = hostname;
  }


  /**
   * Gets the server environment type.
   *
   * @return Returns the server environment type
   */
  public String getEnvironmentType() {
    return environmentType;
  }


  /**
   * Sets the server environment type.
   *
   * @param environmentType The environment type
   */
  protected void setEnvironmentType(String environmentType) {
    this.environmentType = environmentType;
  }


  /**
   * Gets the name.
   *
   * @return Returns the name
   */
  public String getName() {
    return name;
  }


  /**
   * Sets the name.
   *
   * @param name the name
   */
  protected void setName(String name) {
    this.name = name;
  }


  /**
   * Gets the alias name.
   *
   * @return Returns the alias name
   */
  public String getAliasName() {
    return aliasName;
  }


  /**
   * Sets the alias name.
   *
   * @param aliasName the alias name
   */
  protected void setAliasName(String aliasName) {
    this.aliasName = aliasName;
  }


  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result;
    if (hostname != null) {
      result += hostname.hashCode();
    }

    result = prime * result;
    if (environmentType != null) {
      result += environmentType.hashCode();
    }

    result = prime * result;
    if (name != null) {
      result += name.hashCode();
    }

    result = prime * result;
    if (aliasName != null) {
      result += aliasName.hashCode();
    }

    return result;
  }


  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    NodeKey other = (NodeKey) obj;
    if (hostname == null) {
      if (other.hostname != null) {
        return false;
      }
    } else if (!hostname.equals(other.hostname)) {
      return false;
    }

    if (environmentType == null) {
      if (other.environmentType != null) {
        return false;
      }
    } else if (!environmentType.equals(other.environmentType)) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    if (aliasName == null) {
      if (other.aliasName != null) {
        return false;
      }
    } else if (!aliasName.equals(other.aliasName)) {
      return false;
    }

    return true;
  }


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "NodeKey [hostname=" + hostname + ", environmentType=" + environmentType + ", name=" + name + ", aliasName=" + aliasName + "]";
  }


  /**
   * Get a unique key
   *
   * @return the unique key as string
   */
  public String getUniqueKey() {
    return environmentType + "|" + hostname + "@" + name;
  }
}
