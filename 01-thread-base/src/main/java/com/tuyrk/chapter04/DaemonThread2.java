package com.tuyrk.chapter04;

/**
 * 问题：
 * 1. 在非守护线程A里创建守护线程B，当线程A结束后，线程B会自动结束么？
 * 线程B会自动结束
 * 2. 在非守护线程A里创建非守护线程B，当线程A结束后，线程B会自动结束么，JVM可以正常退出么？
 * 线程B不会自动结束，仍然在运行。
 */
public class DaemonThread2 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            Thread innerThread = new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("innerThread do something for health check.");
                        Thread.sleep(1_000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, "innerThread");

            innerThread.setDaemon(true);
            innerThread.start();

            try {
                Thread.sleep(1_000);
                System.out.println("thread finish done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread");

        // thread.setDaemon(true);
        thread.start();
    }
}
