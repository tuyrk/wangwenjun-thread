package com.tuyrk.chapter04;

public class DaemonThread2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            Thread innerThread = new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("innerThread do something for health check.");
                        Thread.sleep(1_000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, "innerThread");

            innerThread.setDaemon(true);
            innerThread.start();

            try {
                Thread.sleep(1_000);
                System.out.println("thread finish done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread");

        // thread.setDaemon(true);
        thread.start();

        while (true) {

        }
    }
}
