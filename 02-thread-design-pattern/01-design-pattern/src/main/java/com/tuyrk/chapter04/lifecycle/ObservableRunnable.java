package com.tuyrk.chapter04.lifecycle;

/**
 * 目标对象线程
 *
 * @author tuyrk
 */
public abstract class ObservableRunnable implements Runnable {
    /**
     * 一个线程对应一个观察者
     */
    protected final LifeCycleListener listener;

    public ObservableRunnable(LifeCycleListener listener) {
        this.listener = listener;
    }

    /**
     * 线程状态改变时，通知观察者对象
     *
     * @param event 线程状态事件
     */
    protected void notifyChange(RunnableEvent event) {
        listener.onEvent(event);
    }
}
