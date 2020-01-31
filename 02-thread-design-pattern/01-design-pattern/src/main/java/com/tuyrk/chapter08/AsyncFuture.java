package com.tuyrk.chapter08;

/**
 * Future设计模式
 * 任务执行线程返回的 包含任务执行结果的对象
 *
 * @author tuyrk
 */
public class AsyncFuture<T> implements Future<T> {
    /**
     * 任务是否执行完成
     */
    private volatile boolean done = false;

    /**
     * 任务执行结果
     */
    private T result;

    /**
     * 设置任务执行完成的结果
     *
     * @param result 任务执行结果
     */
    public synchronized void done(T result) {
        this.result = result;
        this.done = true;
        this.notifyAll();
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            // 如果调用get()时任务还未执行完成，则让其wait()等待
            while (!done) {
                this.wait();
            }
        }
        return result;
    }
}
