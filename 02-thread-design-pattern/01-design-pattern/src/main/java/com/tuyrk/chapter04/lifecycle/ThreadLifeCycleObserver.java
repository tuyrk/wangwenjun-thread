package com.tuyrk.chapter04.lifecycle;

/**
 * 线程生命周期观察者实现类
 *
 * @author tuyrk
 */
public class ThreadLifeCycleObserver implements LifeCycleListener {
    private static final Object LOCK = new Object();

    @Override
    public void onEvent(RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("The runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getThread().getState() + "].");
            if (event.getCause() != null) {
                System.out.println("THe runnable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}
