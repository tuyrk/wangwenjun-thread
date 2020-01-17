package com.tuyrk.chapter07;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 不可变对象中的可变对象测试类
 *
 * @author tuyrk
 */
@Getter
public class ImmutableTest {
    private final int age;
    private final String name;
    private final List<String> list;

    public ImmutableTest(int age, String name) {
        this.age = age;
        this.name = name;
        list = new ArrayList<>();
    }

    /**
     * 此处若直接返回list，则会返回引用类型的引用地址，调用者可进行修改
     *
     * @return list
     */
    public List<String> getList() {
        return Collections.unmodifiableList(list);
    }
}
