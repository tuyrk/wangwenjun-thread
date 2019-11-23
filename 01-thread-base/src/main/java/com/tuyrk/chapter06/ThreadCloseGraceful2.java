package com.tuyrk.chapter06;

/**
 * 优雅地停止线程 Graceful thread stop
 * 2. 通过打断的方式实现
 *
 * @author tuyrk
 */
public class ThreadCloseGraceful2 {
    private static class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                // 1. sleep()
                /*try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;// return;
                }*/

                // 2. if
                if (Thread.interrupted()) {
                    break;// return;
                }
            }
            // ... catch中使用break;可以在while()后执行其他操作
            System.out.println("break-opera after while");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();

        Thread.sleep(10_000);

        worker.interrupt();
    }
}
