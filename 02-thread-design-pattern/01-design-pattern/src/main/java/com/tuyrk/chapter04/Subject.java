package com.tuyrk.chapter04;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式：目标对象
 *
 * @author tuyrk
 */
@Getter
public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    /**
     * 更新目标对象的状态
     *
     * @param state 目标对象的状态
     */
    public void setState(int state) {
        if (state == this.state) {
            return;
        }
        this.state = state;
        notifyAllObservers();
    }

    /**
     * 添加观察者对象
     *
     * @param observer 观察者对象
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 通知所有的观察者对象
     */
    private void notifyAllObservers() {
        observers.forEach(Observer::update);
    }
}
