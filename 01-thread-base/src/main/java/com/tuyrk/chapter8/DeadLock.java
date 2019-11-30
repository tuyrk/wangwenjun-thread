package com.tuyrk.chapter8;

/**
 * 死锁案例：对象1-DeadLock
 * 线程T1
 *
 * @author tuyrk
 */
public class DeadLock {
    private OtherService otherService;

    public void setOtherService(OtherService otherService) {
        this.otherService = otherService;
    }

    // DeadLock的实例的锁-资源A
    private final Object LOCK = new Object();

    public void m1() {
        synchronized (LOCK) {
            System.out.println("********m1********");
            otherService.s1();
        }
    }

    public void m2() {
        synchronized (LOCK) {
            System.out.println("********m2********");
        }
    }
}
