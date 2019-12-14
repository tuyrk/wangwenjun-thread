# 用Runnable接口将线程的逻辑执行单元从控制中抽取出来

课程目标：

- **让业务数据和线程逻辑分离**。Runnable。@FunctionalInterface

  `run()`方法为线程的逻辑代码块

```java
/**
 * 叫号机。继承Runnable接口
 */
public class TicketWindowRunnable implements Runnable {
    private int index = 1;
    private static final int MAX = 50;

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println(Thread.currentThread().getName() + " 的号码是：" + (index++));
        }
    }
}
```

```java
/**
 * 银行大厅，柜台，业务处理窗口。
 */
public class BankRunnable {
    public static void main(String[] args) {
        TicketWindowRunnable ticketWindow1 = new TicketWindowRunnable();
        Thread windowThread1 = new Thread(ticketWindow1, "一号窗口");
        Thread windowThread2 = new Thread(ticketWindow1, "二号窗口");
        Thread windowThread3 = new Thread(ticketWindow1, "三号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }
}
```



不管定义多少个线程，业务逻辑的数据实例只有一个，不像Thread类中业务逻辑数据和线程混淆在一起，每一次`new Thread()`就会新创建一份业务逻辑数据。

三个线程使用同一份业务数据实例，此时很有可能发生数据安全问题。比如，将MAX=1，可能有以下输出：

```shell
一号窗口 的号码是：1
二号窗口 的号码是：2
```



想一想Runnable接口与多线程中的哪种设计模式比较接近？