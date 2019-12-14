# ThreadGroup API介绍之一

通过`new Thread()`创建线程的时候，如果没有指定ThreadGroup，则默认使用创建线程的ThreadGroup。

ThreadGroup和Thread关系：

> 线程组代表一组线程。此外，线程组还可以包括其他线程组。线程组形成一棵树，其中除初始线程组之外的每个线程组都有一个父级。
>
> 允许线程访问有关其自己的线程*组的信息，但不允许访问有关其线程组的*父线程组或任何其他线程组的信息。（测试结果：**可以访问父线程组的只读信息，也可以访问其他线程组的只读信息**）

main是一个线程，其ThreadGroup为main

```java
public static void main(String[] args) {
    Thread.currentThread().getName()// main
    Thread.currentThread().getThreadGroup().getName()// main
}
```

main线程的优先级为10（最高），StackSize也是比手动创建的线程大。



线程组的创建：

1. use the name

   > `ThreadGroup(String name)`

   ```java
   ThreadGroup tg1 = new ThreadGroup("TG1");
   Thread t1 = new Thread(tg1, () -> {
       while (true) {
           try {
               ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
               System.out.println(threadGroup.getName());// TG1
               // 访问线程父ThreadGroup的信息
               System.out.println(threadGroup.getParent().getName());// main
               System.out.println(threadGroup.getParent().activeCount());// 3
               System.out.println(threadGroup.getParent().isDaemon());// false
               // sleep不会放弃CPU执行权
               Thread.sleep(10_000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   }, "T1");
   t1.start();
   System.out.println("t1's thread group name = "+t1.getThreadGroup().getName());// TG1
   ```

2. use the parent and group name

   > `ThreadGroup(ThreadGroup parent, String name)`

   ```java
   ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
   System.out.println("tg2's name = "+tg2.getName());
   System.out.println("tg2's parent name = "+tg2.getParent().getName());
   ```

   

TG3与TG1为同一个父ThreadGroup-main，测试TG3能否访问TG1的一些信息

```java
ThreadGroup tg3 = new ThreadGroup("TG3");
Thread t3 = new Thread(tg3, () -> {
    System.out.println(">>>>" + t1.getName());// TG1
    Thread[] threads = new Thread[tg1.activeCount()];
    tg1.enumerate(threads);
    Stream.of(threads).forEach(System.out::println);// Thread[T1,5,TG1]
}, "T3");
t3.start();
```

测试结果：**可以访问其他线程组的信息**。