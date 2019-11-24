package com.tuyrk.chapter06;

/**
 * 通过小技巧实现暴力关闭线程
 * 思路：把任务线程设置为调用线程的守护线程，通过main来启动调用线程，从而执行任务线程。
 * 当需要停止线程的时候只需要中断调用线程即可，调用线程生命周期结束时，为守护线程的任务线程会自动停止。
 *
 * @author tuyrk
 */
public class ThreadCloseForce2 {
    public static void main(String[] args) {
        ThreadService service = new ThreadService();
        long startTime = System.currentTimeMillis();
        service.execute(() -> {
            // 1. load a very heavy resource.
            /*while (true) {
            }*/

            // 2. 线程执行5s后结束
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
            }
        });
        service.shutdown(10_000);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间为：" + (endTime - startTime));
    }
}
