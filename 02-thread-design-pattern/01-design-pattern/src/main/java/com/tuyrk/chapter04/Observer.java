package com.tuyrk.chapter04;

/**
 * 观察者抽象类
 *
 * @author tuyrk
 */
public abstract class Observer {
    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    protected abstract void update();
}
