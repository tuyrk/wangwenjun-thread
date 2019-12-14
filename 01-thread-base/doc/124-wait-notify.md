# 线程间通信快速入门，使用wait和notify进行线程间的数据通信

线程通信例子：线程A负责从数据库读取数据，线程B负责分析处理数据。当线程A获取到数据并放入内存后需要通知线程B去处理数据。线程A为**生产者**，线程B为**消费者**。



版本1：生产者一直负责生产，消费者一直负责消费。没有进行通信，生产者生产完毕没有通知消费者，消费者也不知道是否是最新数据、是否已经消费过。

```java
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
```

运行代码会发现，有些时候线程P不断生产数据，而线程C没有消费；而有些时候线程C只消费最新的数据，并且不断重复的消费最新数据。

造成这种情况的原因是：线程间没有进行通信。



版本2：生产者负责生产，生产完毕之后通知消费者，如果没有消费则不再进行生产。消费者负责消费，有数据则进行消费，没有数据则进行等待。

```java
public class ProduceConsumerVersion2 {
    private int i = 0;
    private final Object LOCK = new Object();
    // 是否已经消费
    private volatile boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                LOCK.wait();
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
                LOCK.wait();
            }
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion2 pc = new ProduceConsumerVersion2();

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
```

此代码实现了简单的生产者-消费者模型，但是在多个生产者、消费者线程运行时，会产生一些问题。思考问题是什么？欲知后事如何，且听下回分解。