package com.tuyrk.chapter07;

/**
 * 验证Class锁的存在
 * 静态方法、静态代码块加的锁其实是Class锁。
 */
public class SynchronizedStatic {
    static {
        synchronized (SynchronizedStatic.class) {
            System.out.println("static " + Thread.currentThread().getName());
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static void m1() {
        System.out.println("m1 " + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void m2() {
        System.out.println("m2 " + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m3() {
        System.out.println("m3 " + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
