package com.tuyrk.chapter11.improve;

import com.tuyrk.chapter11.Context;

/**
 * 线程运行上下文设计模式
 * 线程单例上下文
 *
 * @author tuyrk
 */
public class ActionContext {
    private ActionContext() {
    }

    private static class ContextHolder {
        private static final ActionContext actionContext = new ActionContext();
    }

    public static ActionContext getInstance() {
        return ContextHolder.actionContext;
    }

    private static final ThreadLocal<Context> threadLocal = ThreadLocal.withInitial(Context::new);

    public Context getContext() {
        return threadLocal.get();
    }
}
