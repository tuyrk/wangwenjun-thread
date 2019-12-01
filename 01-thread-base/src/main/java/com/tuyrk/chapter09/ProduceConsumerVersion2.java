package com.tuyrk.chapter09;

import java.util.stream.Stream;

/**
 * 线程通信（生产者-消费者）
 * 生产者负责生产，生产完毕之后通知消费者，如果没有消费则不再进行生产。
 * 消费者负责消费，有数据则进行消费，没有数据则进行等待。
 *
 * @author tuyrk
 */
public class ProduceConsumerVersion2 {
    private int i = 0;
    private final Object LOCK = new Object();
    /**
     * 是否已经消费
     */
    private volatile boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("P->" + (++i));
                LOCK.notify();
                isProduced = true;
            }
        }
    }

    public void consume() {
        synchronized (LOCK) {
            if (isProduced) {
                System.out.println("C->" + i);
                LOCK.notify();
                isProduced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion2 pc = new ProduceConsumerVersion2();

        // 单个生产者、消费者运行程序
        /*new Thread(() -> {
            while (true) {
                pc.produce();
            }
        }, "P").start();

        new Thread(() -> {
            while (true) {
                pc.consume();
            }
        }, "C").start();*/


        // 多个生产者、消费者线程运行时，会产生一些问题
        Stream.of("P1", "P2").forEach(n ->
                new Thread(() -> {
                    while (true) {
                        pc.produce();
                    }
                }, n).start()
        );
        Stream.of("C1", "C2").forEach(n ->
                new Thread(() -> {
                    while (true) {
                        pc.consume();
                    }
                }, n).start()
        );
    }
}
