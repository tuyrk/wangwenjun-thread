package com.tuyrk.chapter04;

/**
 * 观察者的具体实现-二进制观察者
 *
 * @author tuyrk
 */
public class BinaryObserver extends Observer {
    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Binary String:" + Integer.toBinaryString(subject.getState()));
    }
}
