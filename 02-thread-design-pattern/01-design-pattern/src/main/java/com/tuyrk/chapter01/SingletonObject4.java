package com.tuyrk.chapter01;

/**
 * 单例模式-懒汉模式
 * 解决synchronized加锁情况下，在第一次以后，只是读的操作也会进行加锁，串行化操作影响性能
 *
 * @author tuyrk
 */
public class SingletonObject4 {
    /**
     * 单例对象
     */
    private static SingletonObject4 INSTANCE;

    private SingletonObject4() {
    }

    /**
     * 获取单例模式对象
     * 使用double-checked
     * 解决了synchronized加锁情况下，在第一次以后，只是读的操作也会进行加锁，串行化操作影响性能
     * <p>
     * 解决问题：
     * 1. 单例
     * 2. 懒加载
     * 3. 读取性能
     * 但也有一个隐患：可能引起空指针异常。
     * 1. 第一个线程创建对象时先在堆内存中创建实例，然后在构造函数中有多个需要初始化的操作还未完成，
     *    第二个线程判断INSTANCE不为空则立即返回实例，此时就引起了空指针异常。
     *    深层原因是JVM在运行的时候进行优化重排序。
     *
     * @return 对象
     */
    public static SingletonObject4 getInstance() {
        if (null == INSTANCE) {
            synchronized (SingletonObject4.class) {
                if (null == INSTANCE) {
                    INSTANCE = new SingletonObject4();
                }
            }
        }
        return SingletonObject4.INSTANCE;
    }
}
