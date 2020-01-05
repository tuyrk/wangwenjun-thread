package com.tuyrk.chapter01;

/**
 * 单例模式-懒汉模式
 *
 * @author tuyrk
 */
public class SingletonObject2 {
    /**
     * 单例对象
     */
    private static SingletonObject2 INSTANCE;

    private SingletonObject2() {
    }

    /**
     * 获取单例模式对象
     * 多线程模式下有点问题：
     * 1. 多个线程可能同时进入if
     *
     * @return 对象
     */
    public static SingletonObject2 getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SingletonObject2();
        }
        return SingletonObject2.INSTANCE;
    }
}
