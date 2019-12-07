package com.tuyrk.chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 测试StackTrace test2
 *
 * @author tuyrk
 */
public class Test2 {
    public void test() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Stream.of(stackTrace)
                .filter(e -> !e.isNativeMethod())
                .forEach(e ->
                        Optional.of(e.getClassName() + "." + e.getMethodName() + ":" + e.getLineNumber())
                                .ifPresent(System.out::println)
                );

    }
}
