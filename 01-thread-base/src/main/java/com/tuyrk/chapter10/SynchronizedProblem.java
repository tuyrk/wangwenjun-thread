package com.tuyrk.chapter10;

/**
 * synchronized机制导致的一个问题：
 * synchronized不能被打断，导致其他线程抢不到锁。
 *
 * @author tuyrk
 */
public class SynchronizedProblem {
    public static void main(String[] args) throws InterruptedException {
        new Thread(SynchronizedProblem::run, "T1").start();
        Thread.sleep(1000);
        Thread t2 = new Thread(SynchronizedProblem::run, "T2");
        t2.start();

        Thread.sleep(2000);
        t2.interrupt();
        System.out.println(t2.isInterrupted());// true，但是并没有打断t2
    }

    private synchronized static void run() {
        System.out.println(Thread.currentThread().getName());
        while (true) {
        }
    }
}
