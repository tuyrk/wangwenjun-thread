package com.tuyrk.chapter8;

/**
 * 死锁案例：对象2-OtherService
 * 线程T2
 *
 * @author tuyrk
 */
public class OtherService {

    private DeadLock deadLock;

    public void setDeadLock(DeadLock deadLock) {
        this.deadLock = deadLock;
    }

    // OtherService的实例的锁-资源B
    private final Object LOCK = new Object();

    public void s1() {
        synchronized (LOCK) {
            System.out.println("========s1========");
        }
    }

    public void s2() {
        synchronized (LOCK) {
            System.out.println("========s2========");
            deadLock.m2();
        }
    }
}
