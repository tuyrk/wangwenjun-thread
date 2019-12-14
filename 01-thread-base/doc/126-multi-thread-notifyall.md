# 多线程下的生产者消费者模型，以及详细介绍notifyAll方法

`public final void wait()`方法

> 使当前线程等待，直到另一个线程为此对象调用`notify()`方法或`notifyAll()`方法。此方法的行为就像完全执行调用`wait(0)`一样。
>
> 当前线程必须拥有该对象的监视器。线程释放此监视器的所有权，并等待直到另一个线程通过调用notify方法或notifyAll方法通知等待在此对象监视器上等待的线程唤醒。然后，线程等待，直到它可以重新获得监视器的所有权并恢复执行（即可运行状态）。

`public final void notify()`方法

> 唤醒正在此对象的监视器上等待的单个线程。如果有任何线程在此对象上等待，则选择其中一个唤醒。选择是任意的，并且可以根据实现情况进行选择。线程通过调用其中一个wait方法在对象的监视器上等待。

`public final void notifyAll()`方法

> 唤醒正在此对象的监视器上等待的所有线程。线程通过调用其中一个wait方法在对象的监视器上等待。



改造多线程下的生产者-消费者模型：

```java
public void produce() {
    synchronized (LOCK) {
        // 分析此处为什么使用while，而不是用if？
        while (isProduced) {
            LOCK.wait();
        }
        System.out.println(Thread.currentThread().getName() + "->" + (++i));
        LOCK.notifyAll();
        isProduced = true;
    }
}
```

```java
public void consume() {
    synchronized (LOCK) {
        while (!isProduced) {
            LOCK.wait();
        }
        System.out.println(Thread.currentThread().getName() + "->" + i);
        LOCK.notifyAll();
        isProduced = false;
    }
}
```

```java
public static void main(String[] args) {
    ProduceConsumerVersion3 pc = new ProduceConsumerVersion3();
    Stream.of("P1", "P2", "P3").forEach(n -> new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                pc.produce();
                Thread.sleep(10);
            }
        }
    }, n).start());

    Stream.of("C1", "C2", "C3", "C4").forEach(n -> new Thread(() -> {
        while (true) {
            pc.consume();
            Thread.sleep(10);
        }
    }, n).start());
}
```



分析此处为什么使用while，而不是用if？

```java
while (isProduced) {
    LOCK.wait();
}
```

`notifyAll()`方法将会唤醒所有的生产者线程。

如果使用`if()`：线程A先抢到锁，运行时判断有没有生产数据，无则生产数据并运行完成，当它再次抢锁运行时发现已经生产过数据则会进行`wait()`操作；线程B抢到锁运行时将<u>会跳过判断</u>直接生产数据，从而造成了消费者还没有消费完数据又重复生产。

例：生产两次消费一次，生产一次消费两次

```
P2->2
P3->3
C3->3
P1->4
C4->4
C2->4
```

