package com.tuyrk.chapter06;

/**
 * 1. 中断线程：interrupt();
 * sleep().
 * join().
 * wait().
 * <p>
 * 2. isInterrupted()和interrupted()方法
 */
public class ThreadInterrupt {
    private static final Object MONITOR = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                while (true) {
                    // 1. 线程状态改变，不会捕获到打断信号
                    /*System.out.println(">>" + this.isInterrupted());*/
                    // 2. sleep().线程状态改变，且捕获到打断信号
                    /*try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        System.out.println("收到打断信号。");
                        e.printStackTrace();
                    }*/
                    // 3. wait().线程状态改变，且捕获到打断信号
                    synchronized (MONITOR) {
                        try {
                            MONITOR.wait(1_000);
                        } catch (InterruptedException e) {
                            System.out.println("wait()->" + isInterrupted());
                            e.printStackTrace();
                        }
                    }
                }

            }
        };
        t1.start();

        // start之后处于runnable，并不一定马上就会running。所以设置短暂休眠等待t1启动
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t1.isInterrupted());
        t1.interrupt();
        System.out.println(t1.isInterrupted());

        // t1.stop();

        /*=============================================*/
        // isInterrupted()和interrupted()方法
        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (MONITOR) {
                    try {
                        MONITOR.wait(1_000);
                    } catch (InterruptedException e) {
                        // 获取不到
                        // System.out.println("wait()->" + isInterrupted());
                        System.out.println("wait()->" + Thread.interrupted());
                        e.printStackTrace();
                    }
                }
            }
        }, "t2");


        /*=============================================*/
        // join().
        Thread t3 = new Thread(() -> {
            while (true) {

            }
        }, "t3");
        t3.start();

        Thread main = Thread.currentThread();
        Thread t31 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            main.interrupt();
        });
        t31.start();

        try {
            // 这里join的不是t3线程，而是main线程
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
