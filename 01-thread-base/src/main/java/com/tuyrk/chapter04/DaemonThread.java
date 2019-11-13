package com.tuyrk.chapter04;

/**
 * 守护线程
 */
public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        // new
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " running");
                    Thread.sleep(1000_000);
                    System.out.println(Thread.currentThread().getName() + " done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(true);
        // runnable -> running| -> blocked -> dead
        t.start();

        Thread.sleep(10_000);// JDK1.7之后。
        // 此时逻辑单元已经结束，为什么还没有退出程序呢？
        // 因为此时ThreadGroup中还有线程处于active状态。
        System.out.println(Thread.currentThread().getName());
    }
}
/*

 */