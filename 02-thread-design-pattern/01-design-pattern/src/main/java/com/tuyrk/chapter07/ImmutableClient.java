package com.tuyrk.chapter07;

import java.util.stream.IntStream;

/**
 * 线程的不可变对象Immutable
 * 测试Person类线程安全，测试类
 *
 * @author tuyrk
 */
public class ImmutableClient {
    public static void main(String[] args) {
        // Shared data
        Person person = new Person("Alex", "Gansu");
        IntStream.rangeClosed(1, 5).forEach(i -> new UsePersonThread(person).start());
    }
}
