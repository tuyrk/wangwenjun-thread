# 多Produce多Consume之间的通讯导致出现程序假死的原因分析

多个生产者、消费者线程运行时，会产生一些问题

```java
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
```

此时运行程序会先正常运行一段时间，然后卡住。使用`jstack`查看程序也并没有发生死锁。那么，产生的原因是什么？

**分析**：在消费者1消费完成后进行`notify()`操作时，并没有指定唤醒哪个线程，此时有可能唤醒的是消费者2线程。然后消费者2唤醒后发现后并没有可以消费的数据，又自行wait，此时所有生产者线程也是处于wait状态，所有线程都BLOACKED,在等待被唤醒，进而造成了线程假死的情况。

`notify()`唤醒的是持有同样MONITOR锁的线程，且只唤醒一个线程。

