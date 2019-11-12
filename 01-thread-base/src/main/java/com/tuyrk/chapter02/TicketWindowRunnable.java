package com.tuyrk.chapter02;

/**
 * 柜台，业务处理窗口。继承Runnable接口
 */
public class TicketWindowRunnable implements Runnable {
    private int index = 1;
    private static final int MAX = 50;

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
