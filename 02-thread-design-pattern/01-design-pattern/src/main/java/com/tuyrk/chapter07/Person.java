package com.tuyrk.chapter07;

import lombok.Getter;
import lombok.ToString;

/**
 * 线程的不可变对象Immutable，人
 * 线程安全，该类没有提供修改属性数据的任何方法
 *
 * @author tuyrk
 */
@Getter
@ToString
public final class Person {
    private final String name;
    private final String address;

    public Person(final String name, final String address) {
        this.name = name;
        this.address = address;
    }
}
