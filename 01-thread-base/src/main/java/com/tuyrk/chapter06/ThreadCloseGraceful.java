package com.tuyrk.chapter06;

/**
 * 优雅地停止线程 Graceful thread stop
 * 1. 使用开关变量控制是否启动
 *
 * @author tuyrk
 */
public class ThreadCloseGraceful {
    private static class Worker extends Thread {
        private volatile boolean start = true;

        @Override
        public void run() {
            while (start) {
                // ...
            }
        }

        public void shutdown() {
            this.start = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();

        Thread.sleep(10_000);

        worker.shutdown();
    }
}
