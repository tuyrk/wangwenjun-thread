package com.tuyrk.chapter07;

/**
 * 银行大厅
 *
 * @author tuyrk
 */
public class BankRunnable {
    public static void main(String[] args) {
        // 一个runnable实例被多个线程共享
        TicketWindowRunnable ticketWindow = new TicketWindowRunnable();

        Thread windowThread1 = new Thread(ticketWindow, "一号窗口");
        Thread windowThread2 = new Thread(ticketWindow, "二号窗口");
        Thread windowThread3 = new Thread(ticketWindow, "三号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }
}
