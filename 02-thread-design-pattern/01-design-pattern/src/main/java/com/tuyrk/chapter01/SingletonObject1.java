package com.tuyrk.chapter01;

/**
 * 单例模式-饿汉模式
 *
 * @author tuyrk
 */
public class SingletonObject1 {
    /**
     * 单例对象
     * 使用static关键字，类主动加载，调用类时则加载
     * can't lazy load.
     */
    private static final SingletonObject1 INSTANCE = new SingletonObject1();

    private SingletonObject1() {
    }

    /**
     * 获取单例模式对象
     * 方法名可以使用getInstance()、buildInstance()等，
     * 最好不要使用newInstance()，避免每次调用则新创建对象的误导
     *
     * @return 对象
     */
    public static SingletonObject1 getInstance() {
        return INSTANCE;
    }
}
