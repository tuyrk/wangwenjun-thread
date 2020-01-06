package com.tuyrk.chapter01;

import java.util.stream.IntStream;

/**
 * 单例模式-懒汉模式
 * 枚举方式。最推荐使用，但不优雅
 *
 * @author tuyrk
 */
public class SingletonObject7 {
    private SingletonObject7() {
    }

    private enum Singleton {
        /**
         * 单例对象
         */
        INSTANCE;

        private final SingletonObject7 instance;

        /**
         * 线程安全，且只会被构造一次
         */
        Singleton() {
            instance = new SingletonObject7();
        }

        public SingletonObject7 getInstance() {
            return instance;
        }
    }

    public static SingletonObject7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).forEach(i ->
                new Thread(
                        () -> System.out.println(SingletonObject7.getInstance()),
                        String.valueOf(i)
                ).start()
        );
    }
}
