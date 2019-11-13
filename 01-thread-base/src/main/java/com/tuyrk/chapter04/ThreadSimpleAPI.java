package com.tuyrk.chapter04;

import java.util.Optional;

/**
 * Thread中一些简单的API：
 * getName()、getId()、getPriority()
 */
public class ThreadSimpleAPI {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            Optional.of("Hello").ifPresent(System.out::println);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();

        // 线程名称。
        Optional.of(t1.getName()).ifPresent(System.out::println);
        // 线程ID。++threadSeqNumber
        Optional.of(t1.getId()).ifPresent(System.out::println);
        // 线程优先级，默认为5.
        Optional.of(t1.getPriority()).ifPresent(System.out::println);
    }
}
