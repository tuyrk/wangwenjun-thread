package com.tuyrk.chapter05;

/**
 * 	join(long millis)
 * 	join(long millis, int nanos)
 */
public class ThreadJoin2 {
    public static void main(String[] args) throws InterruptedException {
        /*Thread t1 = new Thread(() -> {
            try {
                System.out.println("t1 is running...");
                Thread.sleep(10_000);
                System.out.println("t1 is done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        // t1.join(100);
        t1.join(100, 10);

        Optional.of("All of tasks finish done.").ifPresent(System.out::println);
        IntStream.range(1, 1000).forEach(i ->
                System.out.println(Thread.currentThread().getName() + "->" + i));*/

        // start httpServer
        // JettyHttpServer.start();
        // 问题：一些嵌入式的HTTP Server，比如jetty，为什么把任务启动，一会之后会自动挂掉？
        // 原因：在主线程退出之后会把http server挂掉（守护线程），避免占用端口、浪费资源。
        // 解决：使用Thread.currentThread().join();。让当前线程执行，直到当前线程死掉


        // 一直等待main线程结束。结果main线程一直运行。
        Thread.currentThread().join();
    }
}
