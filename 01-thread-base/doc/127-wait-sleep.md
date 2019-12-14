# wait和sleep的本质区别是什么，深入分析（面试常见问题）

The difference of sleep and wait:

1. sleep is the method of Thread, but wait is the method of Object.
2. sleep will not release the Object monitor(LOCK), but the wait will be release the monitor and add to the Object monitor waiting queue.
3. Use sleep not depend on the monitor(synchronized), but wait need.
4. The sleep method not need be wakeup, but wait need. except wait(long timeout)



验证不同点3：

```java
public static void m1() {
    Thread.sleep(2_000);
}
public static void m2() {
    synchronized (LOCK) {
        LOCK.wait();
    }
}
```

`LOCK.wait();`没有使用`synchronized`则会抛出`java.lang.IllegalMonitorStateException`异常



验证不同点2：

```java
// 两个线程依次运行
public static void m3() {
    synchronized (LOCK) {
        System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
        Thread.sleep(20_000);
    }
}
// 两个线程几乎同时运行，两个线程最终都加入到了LOCK的waiting queue
public static void m4() {
    synchronized (LOCK) {
        System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
        LOCK.wait();
    }
}
```

```java
public static void main(String[] args) {
    Stream.of("T1", "T2").forEach(name -> new Thread(DifferenceOfWaitAndSleep::m4, name).start());
}
```

使用`sleep()`两个线程将会依次运行；使用`wait()`两个线程几乎同时运行，最终都加入到了LOCK的waiting queue