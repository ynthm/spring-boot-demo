package com.ynthm.common.context;

/**
 * @author Ethan Wang
 * @version 1.0
 */
public class UserContextHolder {

  private static UserContextHolderStrategy strategy;

  public UserContextHolder() {}

  static {
    initialize();
  }

  private static void initialize() {
    initializeStrategy();
  }

  private static void initializeStrategy() {
    strategy = new ThreadLocalUserContextHolderStrategy();
  }

  public static void clearContext() {
    strategy.clearContext();
  }

  public static UserContext getContext() {
    return strategy.getContext();
  }

  public static void setContext(UserContext context) {
    strategy.setContext(context);
  }

  public static void setContextHolderStrategy(UserContextHolderStrategy strategy) {
    UserContextHolder.strategy = strategy;
    initialize();
  }

  public static UserContextHolderStrategy getContextHolderStrategy() {
    return strategy;
  }
}
