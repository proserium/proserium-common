/*
 * LinuxProcessSystemCommandExecuterImpl.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl.process;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a linux based system command executer
 *
 * @author Patrick Meier
 */
public class LinuxProcessSystemCommandExecuterImpl extends AbstractProcessSystemCommandExecuterImpl {
  /**
   * @see net.proserium.common.system.impl.process.AbstractProcessSystemCommandExecuterImpl#preparePlatformDependentCommandList(java.lang.String, java.util.List)
   */
  @Override
  protected List<String> preparePlatformDependentCommandList(String osName, List<String> commandList) {
    List<String> commandParameterList = new ArrayList<String>();
    commandParameterList.add("bash");
    commandParameterList.add("-c");
    commandParameterList.addAll(commandList);
    // to proper use under linux you have to close streams: > my.log 2>&1 </dev/zero &
    return commandParameterList;
  }
}
