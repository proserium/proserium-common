/*
 * SystemExecuterProgressData.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl.storage;

import java.io.Serializable;

/**
 * Defines the system executer progress data
 *
 * @author Patrick Meier
 */
public class SystemExecuterProgressData implements Serializable {
  /** serialVersionUID */
  private static final long serialVersionUID = 435627512902929993L;
  private String data;
  private boolean isError;
  private boolean hasEnded;


  /**
   * Constructor
   */
  public SystemExecuterProgressData() {
    this(null, false, true);
  }


  /**
   * Constructor
   *
   * @param data the data
   * @param isError true if it is error or standard out message
   */
  public SystemExecuterProgressData(String data, boolean isError) {
    this(data, isError, false);
  }


  /**
   * Constructor
   *
   * @param data the data
   * @param isError true if it is error or standard out message
   * @param hasEnded treu if it is ended
   */
  protected SystemExecuterProgressData(String data, boolean isError, boolean hasEnded) {
    this.data = data;
    this.isError = isError;
    this.hasEnded = hasEnded;
  }


  /**
   * Get the data
   *
   * @return the data
   */
  public String getData() {
    return data;
  }


  /**
   * Defines if the message is from error stream or standard out stream.
   *
   * @return true if it is from error stream.
   */
  public boolean isError() {
    return isError;
  }


  /**
   * Defines if the stream has ended.
   *
   * @return true if the stream has ended.
   */
  public boolean hasEnded() {
    return hasEnded;
  }


  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result;
    if (data != null) {
      result += data.hashCode();
    }

    result = prime * result;
    if (isError) {
      result += 1231;
    } else {
      result += 1237;
    }

    result = prime * result;
    if (hasEnded) {
      result += 1231;
    } else {
      result += 1237;
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

    SystemExecuterProgressData other = (SystemExecuterProgressData) obj;
    if (data == null) {
      if (other.data != null) {
        return false;
      }
    } else if (!data.equals(other.data)) {
      return false;
    }

    if (hasEnded != other.hasEnded) {
      return false;
    }

    if (isError != other.isError) {
      return false;
    }

    return true;
  }
}
