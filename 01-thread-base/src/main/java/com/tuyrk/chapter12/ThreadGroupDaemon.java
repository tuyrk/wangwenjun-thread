package com.tuyrk.chapter12;

/**
 * ThreadGroup API介绍之二
 * 测试setDaemon
 *
 * @author tuyrk
 */
public class ThreadGroupDaemon {
    public static void main(String[] args) {
        ThreadGroup tg3 = new ThreadGroup("TG3");
        Thread t3 = new Thread(tg3, () -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T3");
        /*tg3.setDaemon(true);*/
        t3.start();
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(tg3.isDestroyed());// false
        // 手动回收ThreadGroup
        tg3.destroy();
        System.out.println(tg3.isDestroyed());// true
    }
}
