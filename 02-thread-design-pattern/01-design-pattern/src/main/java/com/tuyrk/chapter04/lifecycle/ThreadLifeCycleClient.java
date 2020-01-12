package com.tuyrk.chapter04.lifecycle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 线程生命周期观察者测试类
 *
 * @author tuyrk
 */
public class ThreadLifeCycleClient {
    public static void main(String[] args) {
        ThreadLifeCycleObserver observer = new ThreadLifeCycleObserver();

        Stream.of("1", "2").forEach(id -> new Thread(new ObservableRunnable(observer) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id " + id);
                    Thread.sleep(1000L);
                    // int x = 1 / 0;
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }
}
