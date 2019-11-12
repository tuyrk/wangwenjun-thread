package com.tuyrk.chapter02;

/**
 * 银行大厅
 */
public class BankRunnable {
    private final static int MAX = 50;
    private int index = 1;

    public static void main(String[] args) {
        // 1. 一个runnable实例被多个线程共享
        /*TicketWindowRunnable ticketWindow = new TicketWindowRunnable();*/

        // 2. 策略模式思想改造runnable实例线程
        Runnable ticketWindow = new BankRunnable().getTicketWindow();


        Thread windowThread1 = new Thread(ticketWindow, "一号窗口");
        Thread windowThread2 = new Thread(ticketWindow, "二号窗口");
        Thread windowThread3 = new Thread(ticketWindow, "三号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }

    private Runnable getTicketWindow() {
        return () -> {
            while (index <= MAX) {
                System.out.println(Thread.currentThread() + " 的号码是:" + (index++));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
