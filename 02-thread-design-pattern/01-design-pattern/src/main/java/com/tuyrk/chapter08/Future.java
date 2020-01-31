package com.tuyrk.chapter08;

/**
 * Future设计模式
 * 调用者线程A调用任务执行线程B的时候，首先线程B返回一个Future类，然后线程A通过get()方法获取执行结果
 *
 * @author tuyrk
 */
public interface Future<T> {
    /**
     * 返回任务执行线程的执行结果
     *
     * @return 任务执行线程的执行结果
     * @throws InterruptedException 方法异常
     */
    T get() throws InterruptedException;
}
