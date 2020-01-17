package com.tuyrk.chapter06;

import java.util.Arrays;

/**
 * 读写锁分离，共享数据
 *
 * @author tuyrk
 */
public class SharedData {
    private final char[] buffer;
    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedData(int size) {
        this.buffer = new char[size];
        Arrays.fill(buffer, '*');
    }

    /**
     * 读取buffer的数据
     *
     * @return 读取的数据
     * @throws InterruptedException
     */
    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return this.doRead();
        } finally {
            lock.readUnLock();
        }
    }

    /**
     * 向buffer写数据
     *
     * @param c 需要写的数据
     * @throws InterruptedException
     */
    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            this.doWrite(c);
        } finally {
            lock.writeUnlock();
        }
    }

    private char[] doRead() {
        char[] newBuf = new char[buffer.length];
        System.arraycopy(buffer, 0, newBuf, 0, buffer.length);
        slowly(50);
        return newBuf;
    }

    private void doWrite(char c) {
        Arrays.fill(buffer, c);
        slowly(10);
    }

    private void slowly(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
