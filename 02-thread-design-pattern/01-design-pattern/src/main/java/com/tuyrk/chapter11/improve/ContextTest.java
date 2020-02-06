package com.tuyrk.chapter11.improve;

import java.util.stream.IntStream;

/**
 * 线程运行上下文设计模式
 * 上下文测试
 *
 * @author tuyrk
 */
public class ContextTest {
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 4).forEach(i -> {
            new Thread(new ExecutionTask(), "T" + i).start();
        });
    }
}
