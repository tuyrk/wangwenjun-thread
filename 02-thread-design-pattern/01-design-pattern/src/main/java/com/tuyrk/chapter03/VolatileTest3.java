package com.tuyrk.chapter03;

import java.util.Optional;

/**
 * volatile关键字
 * 验证未保证原子性
 *
 * @author tuyrk
 */
public class VolatileTest3 {
    /**
     * 共享数据，可能引起线程安全问题
     */
    private static volatile int INIT_VALUE = 0;

    private static final int MAX_LIMIT = 500;

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
/*
输出：
T2->18
T2->19
T1->19
T1->20
T2->20
 */
