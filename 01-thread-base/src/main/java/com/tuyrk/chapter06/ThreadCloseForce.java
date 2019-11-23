package com.tuyrk.chapter06;

/**
 * 强制关闭线程
 *
 * @author tuyrk
 */
public class ThreadCloseForce {
    private static class Worker extends Thread {
        @Override
        public void run() {
            // connection or read file
            // 在读操作的过程中被阻塞住，线程blocked。此时没有机会打断或读flag，线程不能监听到打断操作
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        // 。。。
    }
}
