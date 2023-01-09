package com.ynthm.common.context;

/**
 * @author Ethan Wang
 * @version 1.0
 */
final class ThreadLocalUserContextHolderStrategy implements UserContextHolderStrategy {
  private static final ThreadLocal<UserContext> contextHolder = new ThreadLocal();

  public ThreadLocalUserContextHolderStrategy() {}

  public void clearContext() {
    contextHolder.remove();
  }

  public UserContext getContext() {
    UserContext ctx = contextHolder.get();
    if (ctx == null) {
      contextHolder.set(ctx);
    }

    return ctx;
  }

  public void setContext(UserContext context) {
    //    Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
    contextHolder.set(context);
  }
}
