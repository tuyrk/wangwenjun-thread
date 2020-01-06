package com.tuyrk.chapter02;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * 多线程的休息室-WaitSet
 * 1. 所有的对象都会有一个wait set，用来存放调用了该对象wait方法之后进入blocked状态线程
 * 2. 线程被notify之后，不一定立即得到执行
 * 3. 线程从wait set中被唤醒顺序不一定是FIFO。
 *
 * @author tuyrk
 */
public class WaitSet {
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).forEach(i ->
                new Thread(() -> {
                    synchronized (LOCK) {
                        try {
                            Optional.of(Thread.currentThread().getName() + " will come to wait set.").ifPresent(System.out::println);
                            /**
                             * This method causes the current thread (call it <var>T</var>) to
                             * place itself in the wait set for this object and then to relinquish
                             * any and all synchronization claims on this object. Thread <var>T</var>
                             * becomes disabled for thread scheduling purposes and lies dormant
                             */
                            LOCK.wait();
                            Optional.of(Thread.currentThread().getName() + " will leave to wait set.").ifPresent(System.out::println);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, String.valueOf(i)).start()
        );

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 一个一个地notify唤醒线程，以验证线程notify唤醒策略
        IntStream.rangeClosed(1, 10).forEach(i -> {
            synchronized (LOCK) {
                LOCK.notify();
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}