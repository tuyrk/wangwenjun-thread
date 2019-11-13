package com.tuyrk.chapter05;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Thread的join()方法
 * 执行完t1线程再执行main线程
 * 执行完t1、t2线程再执行main线程
 */
public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            IntStream.range(1, 1_000).forEach(i ->
                    System.out.println(Thread.currentThread().getName() + "->" + i)
            );
        }, "t1");
        Thread t2 = new Thread(() -> {
            IntStream.range(1, 1_000).forEach(i ->
                    System.out.println(Thread.currentThread().getName() + "->" + i)
            );
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        Optional.of("All of tasks finish done.").ifPresent(System.out::println);
        IntStream.range(1, 1_000).forEach(i ->
                System.out.println(Thread.currentThread().getName() + "->" + i)
        );
    }
}
