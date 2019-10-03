/*
 * PathStack.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.environment.util;

import java.util.Iterator;
import java.util.Stack;

/**
 * Implements a path stack to support temporary sub path.
 *
 * @author Patrick Meier
 */
public final class PathStack {
  private static final ThreadLocal<Stack<String>> tempPathThreadLocal = new ThreadLocal<Stack<String>>();


  /**
   * Constructor
   */
  private PathStack() {
    // NOP
  }


  /**
   * Gets the path including sub path.
   *
   * @return the path
   */
  public static String getPath() {
    Stack<String> subPathStack = tempPathThreadLocal.get();

    String result = "";
    if (subPathStack != null) {
      for (Iterator<String> it = subPathStack.iterator(); it.hasNext();) {
        result += PathHelper.getInstance().slashifyPath(it.next());
      }
    }

    return result;
  }


  /**
   * Adds a sub path.
   *
   * @param tempSubPath the sub path to add
   */
  public static void addSubPath(String tempSubPath) {
    Stack<String> subPathStack = tempPathThreadLocal.get();
    if (subPathStack == null) {
      subPathStack = new Stack<String>();
    }

    subPathStack.push(tempSubPath);
    tempPathThreadLocal.set(subPathStack);
  }


  /**
   * Removes a sub path.
   *
   * @return the removed sub path
   */
  public static String removeSubPath() {
    Stack<String> subPathStack = tempPathThreadLocal.get();
    if (subPathStack == null) {
      return null;
    }

    String result = subPathStack.pop();
    tempPathThreadLocal.set(subPathStack);
    return result;
  }


  /**
   * Unset data.
   */
  public static void unset() {
    tempPathThreadLocal.remove();
  }
}
