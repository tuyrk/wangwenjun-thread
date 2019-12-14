# 数据同步的引入与Synchronized的简单介绍

逻辑执行单元-任务

```java
private int index = 1;
private static final int MAX = 500;

@Override
public void run() {
    while (true) {
        if (index > MAX) {
            break;
        }
        Thread.sleep(5);
        System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
    }
}
```

线程调用

```java
// 一个runnable实例被多个线程共享
TicketWindowRunnable ticketWindow = new TicketWindowRunnable();

Thread windowThread1 = new Thread(ticketWindow, "一号窗口");
Thread windowThread2 = new Thread(ticketWindow, "二号窗口");
Thread windowThread3 = new Thread(ticketWindow, "三号窗口");
windowThread1.start();
windowThread2.start();
windowThread3.start();
```

运行结果：

```
...
二号窗口 的号码是：501
三号窗口 的号码是：502
```



分析为什么会出现这种情况？

当index=499的时候，三个线程均不满足index > MAX，同时三个线程均还未执行完成index++，三个线程都不会进入if，所以都可以执行后边的输出语句。

解决方法：使用synchronized同步方法。

```java
synchronized (MONITOR) {
    if (index > MAX) {
        break;
    }
    try {
        Thread.sleep(5);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
}
```

