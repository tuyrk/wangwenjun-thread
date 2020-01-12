package com.tuyrk.chapter04.lifecycle;

import java.util.Arrays;

/**
 * 线程生命周期观察者测试类
 *
 * @author tuyrk
 */
public class ThreadLifeCycleClient {
    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}
