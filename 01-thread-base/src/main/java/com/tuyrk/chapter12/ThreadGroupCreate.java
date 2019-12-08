package com.tuyrk.chapter12;

import java.util.stream.Stream;

/**
 * ThreadGroup API介绍之一：ThreadGroup的创建
 *
 * @author tuyrk
 */
public class ThreadGroupCreate {
    public static void main(String[] args) {
        // main是一个线程，其ThreadGroup为main
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getThreadGroup().getName());

        // 1. use the name
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, () -> {
            while (true) {
                try {
                    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
                    System.out.println(threadGroup.getName());// TG1
                    System.out.println(threadGroup.getParent());// java.lang.ThreadGroup[name=main,maxpri=10]
                    System.out.println(threadGroup.getParent().activeCount());// 3
                    System.out.println(threadGroup.getParent().isDaemon());// false
                    System.out.println(threadGroup.getParent().getName());// main
                    // sleep不会放弃CPU执行权
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T1");
        t1.start();
        System.out.println("t1's thread group name = " + t1.getThreadGroup().getName());// TG1

        // 2. use the parent and group name
        ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
        System.out.println("tg2's name = " + tg2.getName());
        System.out.println("tg2's parent name = " + tg2.getParent().getName());

        // TG3与TG1为同一个父ThreadGroup-main
        // 测试TG3能否访问TG1的一些信息
        ThreadGroup tg3 = new ThreadGroup("TG3");
        Thread t3 = new Thread(tg3, () -> {
            System.out.println(">>>>" + t1.getName());// TG1
            Thread[] threads = new Thread[tg1.activeCount()];
            tg1.enumerate(threads);
            Stream.of(threads).forEach(System.out::println);// Thread[T1,5,TG1]
        }, "T3");
        t3.start();
    }
}
