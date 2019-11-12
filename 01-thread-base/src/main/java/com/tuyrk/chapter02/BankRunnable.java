package com.tuyrk.chapter02;

/**
 * 银行大厅
 */
public class BankRunnable {
    public static void main(String[] args) {
        TicketWindowRunnable ticketWindow1 = new TicketWindowRunnable();
        Thread windowThread1 = new Thread(ticketWindow1, "一号窗口");
        Thread windowThread2 = new Thread(ticketWindow1, "二号窗口");
        Thread windowThread3 = new Thread(ticketWindow1, "三号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }
}
