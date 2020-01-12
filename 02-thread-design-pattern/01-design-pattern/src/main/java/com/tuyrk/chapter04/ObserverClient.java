package com.tuyrk.chapter04;

/**
 * Observer观察者模式测试类
 *
 * @author tuyrk
 */
public class ObserverClient {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        System.out.println("=========");
        subject.setState(1);
        System.out.println("=========");
        subject.setState(1);
        System.out.println("=========");
        subject.setState(17);
    }
}
