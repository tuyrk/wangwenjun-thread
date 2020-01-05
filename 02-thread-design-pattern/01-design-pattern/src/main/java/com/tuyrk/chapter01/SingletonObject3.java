package com.tuyrk.chapter01;

/**
 * 单例模式-懒汉模式
 * 多线程模式下，多个线程可能同时进入if代码块
 *
 * @author tuyrk
 */
public class SingletonObject3 {
    /**
     * 单例对象
     */
    private static SingletonObject3 INSTANCE;

    private SingletonObject3() {
    }

    /**
     * 获取单例模式对象
     * 解决了在多线程模式下，多个线程可能同时进入if代码块，
     * 在第一次调用以后，只是读的操作也会进行加锁，串行化操作影响性能
     *
     * @return 对象
     */
    public synchronized static SingletonObject3 getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SingletonObject3();
        }
        return SingletonObject3.INSTANCE;
    }
}
