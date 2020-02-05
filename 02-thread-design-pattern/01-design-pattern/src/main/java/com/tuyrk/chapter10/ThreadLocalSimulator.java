package com.tuyrk.chapter10;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟ThreadLocal
 *
 * @author tuyrk
 */
public class ThreadLocalSimulator<T> {
    private final Map<Thread, T> storage = new HashMap<>();

    public void set(T t) {
        synchronized (this) {
            Thread key = Thread.currentThread();
            storage.put(key, t);
        }
    }

    public T get() {
        synchronized (this) {
            Thread key = Thread.currentThread();
            T value = storage.get(key);
            return value == null ? initialValue() : value;
        }
    }

    public T initialValue() {
        return null;
    }
}
