package com.tuyrk.chapter12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ThreadGroup API介绍之二
 *
 * @author tuyrk
 */
public class ThreadGroupAPI {
    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, () -> {
            while (true) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }, "T1");
        /*tg1.setDaemon(true);*/
        t1.start();

        ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
        Thread t2 = new Thread(tg2, () -> {
            while (true) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }, "T2");
        t2.start();

        System.out.println("tg1 active count is " + tg1.activeCount());// 2。 t1 and t2
        System.out.println(tg1.activeGroupCount());// 1。 tg2
        tg1.checkAccess();
        // destroy前需要确定线程组里没有活跃的线程。
        // 如果线程组不为空或线程组已被销毁将抛出`IllegalThreadStateException`
        // tg1.destroy();

        System.out.println("===========");
        Thread[] ts1 = new Thread[tg1.activeCount()];
        tg1.enumerate(ts1);
        System.out.println(Arrays.toString(ts1));// [Thread[T1,5,TG1], Thread[T2,5,TG2]]
        System.out.println("===========");
        Thread[] ts2 = new Thread[tg1.activeCount()];
        tg1.enumerate(ts2, false);
        System.out.println(Arrays.toString(ts2));// [Thread[T1,5,TG1], null]
        System.out.println("===========");
        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        Thread[] ts3 = new Thread[mainThreadGroup.activeCount()];
        mainThreadGroup.enumerate(ts3, false);
        System.out.println(Arrays.toString(ts3));// [Thread[main,5,main], Thread[Monitor Ctrl-Break,5,main], null, null]

        // 中断该线程组（包括子组）中的所有线程。
        /*tg1.interrupt();*/

        tg1.list();
    }
}
