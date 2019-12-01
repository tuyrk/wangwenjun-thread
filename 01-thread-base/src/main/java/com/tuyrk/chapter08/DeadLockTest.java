package com.tuyrk.chapter08;

/**
 * 死锁案例：
 * 对象1：DeadLock
 * 对象2：OtherService
 *
 * @author tuyrk
 */
public class DeadLockTest {
    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        OtherService otherService = new OtherService();
        deadLock.setOtherService(otherService);
        otherService.setDeadLock(deadLock);

        new Thread(() -> {
            while (true) {
                deadLock.m1();
            }
        }, "T1").start();
        new Thread(() -> {
            while (true) {
                otherService.s2();
            }
        }, "T2").start();
    }
}
