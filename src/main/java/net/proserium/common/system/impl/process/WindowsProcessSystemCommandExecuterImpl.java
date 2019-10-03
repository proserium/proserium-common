/*
 * WindowsProcessSystemCommandExecuterImpl.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl.process;

import java.util.ArrayList;
import java.util.List;


/**
 * Implements a windows based process system command executer
 *
 * @author Patrick Meier
 */
public class WindowsProcessSystemCommandExecuterImpl extends AbstractProcessSystemCommandExecuterImpl {
  /**
   * @see net.proserium.common.system.impl.process.AbstractProcessSystemCommandExecuterImpl#preparePlatformDependentCommandList(java.lang.String, java.util.List)
   */
  @Override
  protected List<String> preparePlatformDependentCommandList(String osName, List<String> commandList) {
    List<String> commandParameterList = new ArrayList<String>();
    if (osName.equals("Windows 95")) {
      commandParameterList.add("command.com");
      commandParameterList.add("/C");
    } else {
      commandParameterList.add("cmd.exe");
      commandParameterList.add("/c");
    }

    commandParameterList.addAll(commandList);
    return commandParameterList;
  }
}
