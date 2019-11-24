package com.tuyrk.chapter07;

/**
 * synchronized关键字到底是什么？
 * synchronized同步代码块
 *
 * @author tuyrk
 */
public class SynchronizedTest {
    // 代码规范：final命名的变量需要使用大写
    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        // 任务
        Runnable runnable = () -> {
            synchronized (LOCK) {
                try {
                    Thread.sleep(500_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 创建并启动线程
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();
    }
}
