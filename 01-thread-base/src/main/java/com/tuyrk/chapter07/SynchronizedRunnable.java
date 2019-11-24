package com.tuyrk.chapter07;

/**
 * synchronized同步方法
 *
 * @author tuyrk
 */
public class SynchronizedRunnable implements Runnable {
    private int index = 1;
    // readonly shared data.
    private static final int MAX = 500;

    // 单线程运行synchronized方法里边的内容。this锁
    @Override
    public void run() {
        while (true) {
            // 1. synchronized同步方法1
            /*// 1
            if (index > MAX) {
                break;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
            // 2*/

            // 2. synchronized同步方法2
            if (ticket()) {
                break;
            }
        }
    }

    private synchronized boolean ticket() {
        // 1. getField
        if (index > MAX) {
            return true;
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // index++; => index = index + 1;
        // 1. get Field index.
        // 2. index = index + 1;
        // 3. put Field index.
        System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
        return false;
    }

    private boolean ticket2() {
        synchronized (this) {
            // 1. getField
            if (index > MAX) {
                return true;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // index++; => index = index + 1;
            // 1. get Field index.
            // 2. index = index + 1;
            // 3. put Field index.
            System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
            return false;
        }
    }
}
