package com.tuyrk.chapter10;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 自定义显式锁。测试调用方法
 *
 * @author tuyrk
 */
public class LockTest {
    public static void main(String[] args) throws InterruptedException {
        final BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4").forEach(name -> {
            new Thread(() -> {
                try {
                    booleanLock.lock();
                    Optional.of(Thread.currentThread().getName() + " have the lock Monitor.").ifPresent(System.out::println);
                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    booleanLock.unlock();
                }
            }, name).start();
        });

        // 问题：违规非法操作。导致运行混乱
        /*Thread.sleep(100);
        booleanLock.unlock();*/
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working...").ifPresent(System.out::println);
        Thread.sleep(10_000);
    }
}
