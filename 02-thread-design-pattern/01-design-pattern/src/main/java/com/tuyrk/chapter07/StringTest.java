package com.tuyrk.chapter07;

/**
 * 测试java.lang.String为不可变对象
 *
 * @author tuyrk
 */
public class StringTest {
    public static void main(String[] args) {
        String s = "Hello";
        System.out.println(s);
        System.out.println(s.getClass() + " " + s.hashCode());

        s = s.replace("l", "k");

        System.out.println(s);
        System.out.println(s.getClass() + " " + s.hashCode());
    }
}
