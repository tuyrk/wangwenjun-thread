package com.tuyrk.chapter08;

import java.util.function.Consumer;

/**
 * Future设计模式
 * 桥接/组合Future和FutureTask
 * 任务执行线程立即向调用者线程返回凭证
 *
 * @author tuyrk
 */
public class FutureService {
    /**
     * 调用者调用任务执行线程，通过凭证轮询任务是否执行完成
     *
     * @param task 任务
     * @return Future凭证
     */
    public <T> Future<T> submit(final FutureTask<T> task) {
        AsyncFuture<T> asyncFuture = new AsyncFuture<>();
        // 唤起执行任务，并将执行结果赋值到Future
        new Thread(() -> {
            T result = task.call();
            asyncFuture.done(result);
        }).start();
        // 返回Future
        return asyncFuture;
    }

    /**
     * 调用者调用任务执行线程，任务执行完成自动通知调用者
     *
     * @param task     任务
     * @param consumer 消费者对象
     * @return Future凭证
     */
    public <T> Future<T> submit(final FutureTask<T> task, final Consumer<T> consumer) {
        AsyncFuture<T> asyncFuture = new AsyncFuture<>();
        // 唤起执行任务，并将执行结果赋值到Future
        new Thread(() -> {
            T result = task.call();
            asyncFuture.done(result);
            consumer.accept(result);
        }).start();
        // 返回Future
        return asyncFuture;
    }
}
