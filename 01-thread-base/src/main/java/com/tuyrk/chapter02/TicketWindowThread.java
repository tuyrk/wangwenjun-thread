package com.tuyrk.chapter02;

/**
 * 柜台，业务处理窗口。实现Thread类
 */
public class TicketWindowThread extends Thread {
    private final String name;
    private static final int MAX = 50;
    private static int index = 1;

    public TicketWindowThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        // 当当前号码数小于最大号码时，进行售票操作
        while (index <= MAX) {
            System.out.println("柜台：" + name + "，当前的号码是：" + (index++));
        }
    }
}
