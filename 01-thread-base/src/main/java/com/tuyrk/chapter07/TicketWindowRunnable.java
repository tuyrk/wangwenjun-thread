package com.tuyrk.chapter07;

/**
 * 柜台，业务处理窗口。继承Runnable接口
 *
 * @author tuyrk
 */
public class TicketWindowRunnable implements Runnable {
    private int index = 1;
    private static final int MAX = 500;

    private final Object MONITOR = new Object();

    @Override
    public void run() {
        while (true) {
            // 单线程运行synchronized代码块里边的内容
            // 1
            synchronized (MONITOR) {
                if (index > MAX) {
                    break;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
            }
            // 2
        }
    }
}
