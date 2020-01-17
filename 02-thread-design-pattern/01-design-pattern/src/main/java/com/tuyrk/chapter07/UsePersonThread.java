package com.tuyrk.chapter07;

/**
 * 线程的不可变对象Immutable
 * 测试Person类线程安全，线程类
 *
 * @author tuyrk
 */
public class UsePersonThread extends Thread {
    private Person person;

    public UsePersonThread(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " print " + person.toString());
        }
    }
}
