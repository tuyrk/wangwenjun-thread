package com.tuyrk.chapter03;

import java.util.Optional;

/**
 * Java内存模型(JMM)
 * 线程的每次修改操作都是在自己的内存么？比如两个线程分别做加1操作。
 * 缓存不一致问题
 *
 * @author tuyrk
 */
public class VolatileTest2 {
    /**
     * 共享数据，可能引起线程安全问题
     */
    private static int INIT_VALUE = 0;

    private static final int MAX_LIMIT = 50;

    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT) {
                Optional.of("T1->" + (++INIT_VALUE)).ifPresent(System.out::println);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-1").start();

        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT) {
                Optional.of("T2->" + (++INIT_VALUE)).ifPresent(System.out::println);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-2").start();
    }
}
