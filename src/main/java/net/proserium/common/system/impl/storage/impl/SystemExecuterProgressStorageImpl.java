/*
 * SystemExecuterProgressStorageImpl.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.system.impl.storage.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import jptools.logger.Logger;
import net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage;
import net.proserium.common.system.impl.storage.SystemExecuterProgressData;
import net.proserium.common.system.impl.storage.SystemExecuterProgressKey;


/**
 * Implements the {@link ISystemExecuterProgressStorage}.
 *
 * @author Patrick Meier
 */
public class SystemExecuterProgressStorageImpl implements ISystemExecuterProgressStorage {
  private static final Logger log = Logger.getLogger(SystemExecuterProgressStorageImpl.class);
  private Map<SystemExecuterProgressKey, ConcurrentLinkedQueue<SystemExecuterProgressData>> systemExecuterProgressStorageMap;


  /**
   * Constructor
   */
  public SystemExecuterProgressStorageImpl() {
    systemExecuterProgressStorageMap = new ConcurrentHashMap<SystemExecuterProgressKey, ConcurrentLinkedQueue<SystemExecuterProgressData>>();
  }


  /**
   * @see net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage#startProcessing(net.proserium.common.system.impl.storage.SystemExecuterProgressKey)
   */
  @Override
  public void startProcessing(SystemExecuterProgressKey systemExecuterProgressKey) {
    log.debug("Start processing " + systemExecuterProgressKey.getUniqueKey());

    systemExecuterProgressStorageMap.put(systemExecuterProgressKey, new ConcurrentLinkedQueue<SystemExecuterProgressData>());
  }


  /**
   * @see net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage#exitProcessing(net.proserium.common.system.impl.storage.SystemExecuterProgressKey)
   */
  @Override
  public boolean exitProcessing(SystemExecuterProgressKey systemExecuterProgressKey) {
    return systemExecuterProgressStorageMap.containsKey(systemExecuterProgressKey);
  }


  /**
   * @see net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage#endProcessing(net.proserium.common.system.impl.storage.SystemExecuterProgressKey)
   */
  @Override
  public void endProcessing(SystemExecuterProgressKey systemExecuterProgressKey) {
    log.debug("End processing " + systemExecuterProgressKey.getUniqueKey());

    ConcurrentLinkedQueue<SystemExecuterProgressData> queue = systemExecuterProgressStorageMap.get(systemExecuterProgressKey);
    if (queue != null) {
      queue.add(new SystemExecuterProgressData());
    }
  }


  /**
   * @see net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage#readServerProgress(net.proserium.common.system.impl.storage.SystemExecuterProgressKey)
   */
  @Override
  public SystemExecuterProgressData readServerProgress(SystemExecuterProgressKey systemExecuterProgressKey) {
    ConcurrentLinkedQueue<SystemExecuterProgressData> queue = systemExecuterProgressStorageMap.get(systemExecuterProgressKey);

    if (queue != null) {
      SystemExecuterProgressData result = queue.poll();
      if (result != null && result.hasEnded()) {
        log.debug("Read last progress information " + systemExecuterProgressKey.getUniqueKey() + ", close up.");
        systemExecuterProgressStorageMap.remove(systemExecuterProgressKey);
      }

      return result;
    }

    return null;
  }


  /**
   * @see net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage#addInput(net.proserium.common.system.impl.storage.SystemExecuterProgressKey, java.lang.String)
   */
  @Override
  public void addInput(SystemExecuterProgressKey systemExecuterProgressKey, String data) {
    ConcurrentLinkedQueue<SystemExecuterProgressData> queue = systemExecuterProgressStorageMap.get(systemExecuterProgressKey);
    if (queue != null) {
      log.debug("Add " + systemExecuterProgressKey.getUniqueKey() + ": " + data);
      queue.add(new SystemExecuterProgressData(data, false));
    }
  }


  /**
   * @see net.proserium.common.system.impl.storage.ISystemExecuterProgressStorage#addError(net.proserium.common.system.impl.storage.SystemExecuterProgressKey, java.lang.String)
   */
  @Override
  public void addError(SystemExecuterProgressKey systemExecuterProgressKey, String data) {
    ConcurrentLinkedQueue<SystemExecuterProgressData> queue = systemExecuterProgressStorageMap.get(systemExecuterProgressKey);

    if (queue != null) {
      log.debug("Add error " + systemExecuterProgressKey.getUniqueKey() + ": " + data);
      queue.add(new SystemExecuterProgressData(data, true));
    }
  }
}
