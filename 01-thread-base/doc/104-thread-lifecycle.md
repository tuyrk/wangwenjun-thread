# 线程生命周期以及start方法源码剖析

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8u9nc9l7qj30y20fewgo.jpg" alt="线程生命周期简图" style="zoom:50%;" />

线程生命周期共有6种状态：

1. 初始(NEW)：新创建了一个线程对象，但还没有调用start()方法。

2. 运行(RUNNABLE)：Java线程中将就绪（ready）和运行中（running）两种状态笼统的称为“运行”。
    线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取CPU的使用权，此时处于就绪状态（ready）。就绪状态的线程在获得CPU时间片后变为运行中状态（running）。

3. 阻塞(BLOCKED)：表示线程阻塞于锁。

4. 等待(WAITING)：进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）。

5. 超时等待(TIMED_WAITING)：该状态不同于WAITING，它可以在指定的时间后自行返回。

6. 终止(TERMINATED)：表示该线程已经执行完毕。

    <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8uagny1khj30w30lf0tx.jpg" alt="线程状态图" style="zoom:80%;" />

    <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8uafgd9wbj30sx0eejrw.jpg" alt="线程生命周期" style="zoom:80%;" />



`start()`

> ```java
> public void start()
> ```
>
> Causes this thread to begin execution; the Java Virtual Machine calls the `run` method of this thread.
>
> The result is that two threads are running concurrently: the current thread (which returns from the call to the `start` method) and the other thread (which executes its `run` method).
>
> **It is never legal to start a thread more than once.** In particular, a thread may not be restarted once it has completed execution.

- 程序启动会创建两个线程：main主线程、run用户子线程
- `start()`不能被执行两次：IllegalThreadStateException



不执行`start()`方法，而直接执行`run()`方法，此时没有启动线程，只是调用了一个实例方法而已。这是使用了模版方法的技巧。

```java
Thread t1 = new Thread("READ-Thread") {
  @Override
  public void run() {
    println(Thread.currentThread().getName());
    readFromDataBase();
  }
};
t1.run();
```

此时输出的线程名称为：`main`



模版方法：

例：在实际程序中，算法已经基本固定，但是其中有一些逻辑可能是多变的，此时将其抽象出去并让子类去实现。

```java
public abstract class BaseTemplateMethod {
    public static void main(String[] args) {
        BaseTemplateMethod t1 = new BaseTemplateMethod() {
            @Override
            protected void wrapPrint(String message) {
                System.out.println("*" + message + "*");
            }
        };
        t1.print("Hello Thread");

        BaseTemplateMethod t2 = new BaseTemplateMethod() {
            @Override
            protected void wrapPrint(String message) {
                System.out.println("+" + message + "+");
            }
        };
        t2.print("Hello Thread");
    }

    public final void print(String message) {
        System.out.println("#####################");
        wrapPrint(message);
        System.out.println("#####################");
    }

    protected abstract void wrapPrint(String message);
}
```

注：真正的Template方法需要写成抽象类，并把`print()`方法写成`final`方法（必须为`final`方法），`wrapPrint()`方法写成抽象方法。



总结：

1. Java应用程序的main函数是一个线程，是被JVM启动的时候调用，线程的名字叫main
2. 实现一个线程必须创建Thread实例，override重写`run()`方法，并且调用`start()`方法
3. 在JVM启动后，实际上有多个线程，但是至少有一个非守护线程（main线程）
4. 当你调用一个线程的`start()`方法的时候，此时至少有两个线程，一个是调用你的线程（main方法），还有一个是执行`run()`方法的线程
5. 线程的生命周期分为：New、Runnable、Running、Block、Terminate