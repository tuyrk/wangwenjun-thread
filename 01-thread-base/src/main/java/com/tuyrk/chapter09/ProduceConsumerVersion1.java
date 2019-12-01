package com.tuyrk.chapter09;

/**
 * 线程通信（生产者-消费者）
 * 生产者一直负责生产，消费者一直负责消费。没有进行通信。
 * 生产者生产完毕没有通知消费者，消费者也不知道是否是最新数据、是否已经消费过。
 *
 * @author tuyrk
 */
public class ProduceConsumerVersion1 {
    private int i = 1;
    private final Object LOCK = new Object();

    private void produce() {
        synchronized (LOCK) {
            System.out.println("P->" + (i++));
        }
    }

    private void consume() {
        synchronized (LOCK) {
            System.out.println("C->" + (i));
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion1 pc = new ProduceConsumerVersion1();

        new Thread(() -> {
            while (true) {
                pc.produce();
            }
        }, "P").start();
        new Thread(() -> {
            while (true) {
                pc.consume();
            }
        }, "C").start();
    }
}
