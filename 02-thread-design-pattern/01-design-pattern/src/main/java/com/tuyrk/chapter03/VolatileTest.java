package com.tuyrk.chapter03;

import java.util.Optional;

/**
 * 例子：volatile关键字
 * 比较变量INIT_VALUE添加和不添加volatile关键字的运行结果
 *
 * @author tuyrk
 */
public class VolatileTest {
    /**
     * 共享数据，可能引起线程安全问题
     */
    private volatile static int INIT_VALUE = 0;

    private static final int MAX_LIMIT = 50;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_LIMIT) {
                if (localValue != INIT_VALUE) {
                    Optional.of("The value updated to [" + INIT_VALUE + "]").ifPresent(System.out::println);
                    localValue = INIT_VALUE;
                }
            }
        }, "READER").start();

        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_LIMIT) {
                Optional.of("Update the value to " + (++localValue)).ifPresent(System.out::println);
                INIT_VALUE = localValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "UPDATER").start();
    }
}
