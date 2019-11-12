package com.tuyrk.chapter02;

/**
 * 银行大厅
 */
public class BankThread {
    public static void main(String[] args) {
        TicketWindowThread ticketWindow1 = new TicketWindowThread("一号柜台");
        ticketWindow1.start();
        TicketWindowThread ticketWindow2 = new TicketWindowThread("二号柜台");
        ticketWindow2.start();
        TicketWindowThread ticketWindow3 = new TicketWindowThread("三号柜台");
        ticketWindow3.start();
    }
}
