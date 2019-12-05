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
        blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeOutException {

    }

    @Override
    public synchronized void unlock() {
        // 释放锁
        this.initValue = false;
        Optional.of(Thread.currentThread().getName() + " release the lock monitor.").ifPresent(System.out::println);
        this.notifyAll();
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
