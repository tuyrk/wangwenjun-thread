package com.tuyrk.chapter01;

/**
 * 单例模式-懒汉模式
 * 使用Holder方式。优雅，推荐使用
 *
 * @author tuyrk
 */
public class SingletonObject6 {
    private SingletonObject6() {
    }

    private static class InstanceHolder {
        /**
         * 单例对象
         */
        private final static SingletonObject6 INSTANCE = new SingletonObject6();
    }

    /**
     * 获取单例模式对象
     *
     * @return 对象
     */
    public static SingletonObject6 getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
