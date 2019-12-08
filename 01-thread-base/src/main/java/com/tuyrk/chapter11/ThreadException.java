package com.tuyrk.chapter11;

/**
 * 捕获线程运行期间的异常
 *
 * @author tuyrk
 */
public class ThreadException {
    private final static int A = 10;
    private static final int B = 0;

    public static void main(String[] args) {
        /// 捕获线程运行期间的异常
        /*Thread t = new Thread(() -> {
            try {
                Thread.sleep(1_000L);
                int result = A / B;
                System.out.println("result = " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        t.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println(e);
            System.out.println(thread.getName());
            System.out.println(thread.getThreadGroup().getName());
            System.out.println(thread.getThreadGroup().getParent().getName());
        });*/

        // stack trace
        new Test1().test();
    }
}
