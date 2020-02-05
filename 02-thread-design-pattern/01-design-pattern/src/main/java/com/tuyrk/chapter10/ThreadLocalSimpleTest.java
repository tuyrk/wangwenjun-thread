package com.tuyrk.chapter10;

/**
 * ThreadLocal测试
 *
 * @author tuyrk
 */
public class ThreadLocalSimpleTest {

    private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Alex");

    /**
     * JVM start main thread
     *
     * @param args 启动参数
     */
    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("Alex2");
        Thread.sleep(1000);
        String value = threadLocal.get();
        System.out.println(value);
    }
}
