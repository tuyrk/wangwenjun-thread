package com.tuyrk.chapter07;

/**
 * 不可变对象方式与加锁方式的性能比较
 *
 * @author tuyrk
 */
public class ImmutablePerformance {
    private static final int SIZE = 10_000_000;

    public static void main(String[] args) throws InterruptedException {
        long startTimestamp;
        long endTimestamp;
        // 单线程下比较：
        // 1. 可变对象
        startTimestamp = System.currentTimeMillis();
        SynchObj synchObj = new SynchObj();
        synchObj.setName("Alex");
        for (int i = 0; i < SIZE; i++) {
            synchObj.toString();
        }
        endTimestamp = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTimestamp - startTimestamp));// 242
        // 2. 不可变对象
        startTimestamp = System.currentTimeMillis();
        ImmutableObj immutableObj = new ImmutableObj("Alex");
        for (int i = 0; i < SIZE; i++) {
            immutableObj.toString();
        }
        endTimestamp = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTimestamp - startTimestamp));// 94

        // 多线程下比较
        // 1. 可变对象
        SynchObj synchObj2 = new SynchObj();
        synchObj2.setName("Alex");
        startTimestamp = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < SIZE; i++) {
                synchObj2.toString();
            }
        }, "Thread-1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < SIZE; i++) {
                synchObj2.toString();
            }
        }, "Thread-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        endTimestamp = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTimestamp - startTimestamp));// 1005
        // 2. 不可变对象
        ImmutableObj immutableObj2 = new ImmutableObj("Alex");
        startTimestamp = System.currentTimeMillis();
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < SIZE; i++) {
                immutableObj2.toString();
            }
        }, "Thread-3");
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < SIZE; i++) {
                immutableObj2.toString();
            }
        }, "Thread-4");
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        endTimestamp = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTimestamp - startTimestamp));// 112
    }
}

/**
 * 不可变对象类
 */
final class ImmutableObj {
    private final String name;

    public ImmutableObj(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + this.name + "]";
    }
}

/**
 * 可变对象类
 */
class SynchObj {
    private String name;

    public synchronized void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized String toString() {
        return "[" + this.name + "]";
    }
}