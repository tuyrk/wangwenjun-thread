package com.tuyrk.chapter08;

/**
 * Future设计模式
 * 任务执行线程
 *
 * @author tuyrk
 */
public interface FutureTask<T> {
    /**
     * 唤起任务
     *
     * @return 执行结果
     */
    T call();
}
