package com.tuyrk.chapter02;

/**
 * 多线程的休息室-WaitSet
 * 4. 线程被唤醒后，必须重新获取锁。地址保存并恢复，不会重复执行。
 *
 * @author tuyrk
 */
public class WaitSet2 {
    private static final Object LOCK = new Object();

    private static void work() {
        synchronized (LOCK) {
            // wait再notify后，不会重复执行。
            // wait有保存执行地址，notify后自动恢复
            System.out.println("Begin...");
            try {
                System.out.println("Thread will coming.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread will out.");
        }
    }

    public static void main(String[] args) {
        new Thread(WaitSet2::work).start();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (LOCK) {
            // notify前必须先获取LOCK锁
            LOCK.notify();
        }
    }
}
