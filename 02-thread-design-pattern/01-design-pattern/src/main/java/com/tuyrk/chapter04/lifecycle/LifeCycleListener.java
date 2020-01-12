package com.tuyrk.chapter04.lifecycle;

/**
 * 线程生命周期观察者接口
 *
 * @author tuyrk
 */
public interface LifeCycleListener {
    /**
     * 线程状态改变时触发方法
     *
     * @param event 触发事件
     */
    void onEvent(RunnableEvent event);
}
