package com.tuyrk.chapter06;

/**
 * 读写锁分离，读写锁
 *
 * @author tuyrk
 */
public class ReadWriteLock {
    /**
     * 正在读线程数量
     */
    private int readingReaders = 0;
    /**
     * 等待读线程数量
     */
    private int waitingReaders = 0;
    /**
     * 正在写线程数量，最多只有1个
     */
    private int writingWriters = 0;
    /**
     * 等待写线程数据
     */
    private int waitingWriters = 0;

    /**
     * 写线程是否优先执行
     */
    private boolean preferWriter;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    /**
     * 读加锁
     *
     * @throws InterruptedException
     */
    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders++;
        try {
            // 有写线程正在操作，则不允许读操作执行
            while (writingWriters > 0
                    || (preferWriter && waitingWriters > 0)) {
                this.wait();
            }
            // 没有写线程，则读操作执行
            this.readingReaders++;
        } finally {
            this.waitingReaders--;
        }
    }

    /**
     * 读解锁
     */
    public synchronized void readUnLock() {
        this.readingReaders--;
        this.notifyAll();
    }

    /**
     * 写加锁
     *
     * @throws InterruptedException
     */
    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters++;
        try {
            while (readingReaders > 0 || writingWriters > 0) {
                this.wait();
            }
            this.writingWriters++;
        } finally {
            this.waitingWriters--;
        }
    }

    /**
     * 写解锁
     */
    public synchronized void writeUnlock() {
        this.writingWriters--;
        this.notifyAll();
    }
}
