package com.tuyrk.chapter01;

/**
 * 单例模式-懒汉模式
 * 解决double-checked加锁情况下引起的空指针异常
 *
 * @author tuyrk
 */
public class SingletonObject5 {
    /**
     * 单例对象
     * 使用volatile关键字解决double-checked加锁情况下引起的空指针异常
     */
    private static volatile SingletonObject5 INSTANCE;

    private SingletonObject5() {
    }

    /**
     * 获取单例模式对象
     *
     * @return 对象
     */
    public static SingletonObject5 getInstance() {
        if (null == INSTANCE) {
            synchronized (SingletonObject5.class) {
                if (null == INSTANCE) {
                    INSTANCE = new SingletonObject5();
                }
            }
        }
        return SingletonObject5.INSTANCE;
    }
}
