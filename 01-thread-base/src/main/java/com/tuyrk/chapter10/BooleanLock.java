package com.tuyrk.chapter10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * 自定义显式锁。Boolean Lock
 *
 * @author tuyrk
 */
public class BooleanLock implements Lock {
    /**
     * The initValue is true indicated the lock has been get.
     * The initValue is false indicated the lock is free (other thread can get this).
     */
    private boolean initValue;

    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    private Thread currentThread;

    public BooleanLock() {
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        // 锁已经被其他线程使用
        while (initValue) {
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        // 锁未被使用，抢到锁立即设置initValue的值
        this.initValue = true;
        this.currentThread = Thread.currentThread();
        blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if (mills <= 0) {
            lock();
        }

        long hasRemaining = mills;
        long endTime = System.currentTimeMillis() + mills;
        while (initValue) {
            if (hasRemaining <= 0) {
                throw new TimeOutException("Time Out");
            }
            blockedThreadCollection.add(Thread.currentThread());
            this.wait(mills);
            hasRemaining = endTime - System.currentTimeMillis();
            // System.out.println(Thread.currentThread().getName() + ">>" + hasRemaining);
        }
        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        // 释放锁
        if (Thread.currentThread() == currentThread) {
            this.initValue = false;
            Optional.of(Thread.currentThread().getName() + " release the lock monitor.").ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        // 可以直接返回，但是不安全。此处返回的是一个实例，调用者可以随意更改（null，clear()等操作）。
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
