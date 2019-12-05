package com.tuyrk.chapter10;

import java.util.Collection;

/**
 * 自定义显式锁
 *
 * @author tuyrk
 */
public interface Lock {
    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }

    /**
     * 加锁
     *
     * @throws InterruptedException 打断异常
     */
    void lock() throws InterruptedException;

    /**
     * 加锁
     *
     * @param mills 加锁时间
     * @throws InterruptedException 打断异常
     * @throws TimeOutException     超时异常
     */
    void lock(long mills) throws InterruptedException, TimeOutException;

    /**
     * 解锁
     */
    void unlock();

    /**
     * 获取争抢锁时被阻塞的线程
     *
     * @return 被阻塞线程的集合
     */
    Collection<Thread> getBlockedThread();

    /**
     * 获取争抢锁时被阻塞的线程的数量
     *
     * @return 被阻塞的线程的数量
     */
    int getBlockedSize();
}
