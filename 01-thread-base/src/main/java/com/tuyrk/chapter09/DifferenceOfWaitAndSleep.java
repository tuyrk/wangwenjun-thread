package com.tuyrk.chapter09;

import java.util.stream.Stream;

/**
 * wait和sleep的本质区别是什么，深入分析（面试常见问题）
 *
 * @author tuyrk
 */
public class DifferenceOfWaitAndSleep {
    public static final Object LOCK = new Object();

    public static void main(String[] args) {
        // 3. Use sleep not depend on the monitor(synchronized), but wait need.
        /*m1();
        m2();*/
        // 2. sleep will not release the Object monitor(LOCK), but the wait will be release the monitor and add to the Object monitor waiting queue.
        Stream.of("T1", "T2").forEach(name -> new Thread(DifferenceOfWaitAndSleep::m4, name).start());
    }

    // 休眠2秒正常结束
    public static void m1() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 未使用synchronized则抛出java.lang.IllegalMonitorStateException异常
    public static void m2() {
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 两个线程依次运行
    public static void m3() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                Thread.sleep(20_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 两个线程几乎同时运行，两个线程最终都加入到了LOCK的waiting queue
    public static void m4() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
