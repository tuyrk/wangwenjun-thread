# 采用多线程方式模拟银行排队叫号

课程目标：

- 通过继承Thread类实现线程的创建
- 并且知道使用Runnable的必要性，使用它的原因是什么。

案例：

> 实现银行排队叫号功能（粗糙版）。银行有多个柜台，当一个人需要去办理业务的时候，先到叫号机上领取一个号码。当银行柜员处理完成当前客户就会使用叫号器呼叫下一位客户到窗口办理业务。

```java
/**
 * 叫号机。实现Thread类
 */
public class TicketWindowThread extends Thread {
    private final String name;
    private static final int MAX = 50;
    private static int index = 1;

    public TicketWindowThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        // 当当前号码数小于最大号码时，进行售票操作
        while (index <= MAX) {
            System.out.println("柜台：" + name + "，当前的号码是：" + (index++));
        }
    }
}
```

```java
/**
 * 银行大厅，柜台，业务处理窗口
 */
public class BankThread {
    public static void main(String[] args) {
        TicketWindowThread ticketWindow1 = new TicketWindowThread("一号柜台");
        ticketWindow1.start();
        TicketWindowThread ticketWindow2 = new TicketWindowThread("二号柜台");
        ticketWindow2.start();
        TicketWindowThread ticketWindow3 = new TicketWindowThread("三号柜台");
        ticketWindow3.start();
    }
}
```

此时三个线程出现的结果是：线程线程分别执行0-50号，拥有独立的index，并没有达到三个线程共同使用一个index的效果。

怎们达到共享index变量，使用同一份资源的效果呢？

1. 给index设置为静态变量static。此时始终只会实例化一次。

   使用static实现了共享变量的效果，但是static也有一个问题。就是static生命周期较长，伴随JVM的启动，一直到销毁；从类加载就会开始存在，即使是类实例销毁之后也不会销毁，static不是在类中堆栈空间的信息，它有自己独立的一份存储空间。[java 静态变量生命周期（类生命周期）](https://www.cnblogs.com/hf-cherish/p/4970267.html)



本节通过继承Thread类实现了多线程方式模拟银行排队叫号的粗糙版，接下来将会实现比较友好的银行排队叫号功能，**把业务和线程分离出来**。



