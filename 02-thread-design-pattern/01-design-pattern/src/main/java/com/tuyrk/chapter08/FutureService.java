package com.tuyrk.chapter08;

/**
 * Future设计模式
 * 桥接/组合Future和FutureTask
 * 任务执行线程立即向调用者线程返回凭证
 *
 * @author tuyrk
 */
public class FutureService {
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
}
