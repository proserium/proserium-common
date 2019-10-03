/*
 * SystemExecuterProgressKey.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl.storage;

import java.io.Serializable;
import net.proserium.common.sdo.NodeKey;


/**
 * Defines the system executer progress key
 *
 * @author Patrick Meier
 */
public class SystemExecuterProgressKey implements Serializable {
  /** serialVersionUID */
  private static final long serialVersionUID = -7910092835445007989L;
  private NodeKey nodeKey;
  private Long identifier;


  /**
   * Constructor
   *
   * @param nodeKey server instance information key
   * @param identifier the identifier
   */
  public SystemExecuterProgressKey(NodeKey nodeKey, Long identifier) {
    this.nodeKey = nodeKey;
    this.identifier = identifier;
  }


  /**
   * Gets the server instance information key
   *
   * @return Returns the server instance information key
   */
  public NodeKey getNodeKey() {
    return nodeKey;
  }


  /**
   * Gets the identifier
   *
   * @return Returns the identifier
   */
  public Long getIdentifier() {
    return identifier;
  }


  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result;
    if (nodeKey != null) {
      result += nodeKey.hashCode();
    }

    result = prime * result;
    if (identifier != null) {
      result += identifier.hashCode();
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

    SystemExecuterProgressKey other = (SystemExecuterProgressKey) obj;
    if (nodeKey == null) {
      if (other.nodeKey != null) {
        return false;
      }
    } else if (!nodeKey.equals(other.nodeKey)) {
      return false;
    }

    if (identifier == null) {
      if (other.identifier != null) {
        return false;
      }
    } else if (!identifier.equals(other.identifier)) {
      return false;
    }

    return true;
  }


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SystemExecuterProgressKey [nodeKey=" + nodeKey + ", identifier=" + identifier + "]";
  }


  /**
   * Get a unique key
   *
   * @return the unique key as string
   */
  public String getUniqueKey() {
    return nodeKey.getUniqueKey() + "#" + getIdentifier();
  }
}
