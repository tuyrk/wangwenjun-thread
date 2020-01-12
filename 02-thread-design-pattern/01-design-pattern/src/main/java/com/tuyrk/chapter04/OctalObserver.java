package com.tuyrk.chapter04;

/**
 * 观察者的具体实现-八进制观察者
 *
 * @author tuyrk
 */
public class OctalObserver extends Observer {
    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Octal String:" + Integer.toOctalString(subject.getState()));
    }
}
