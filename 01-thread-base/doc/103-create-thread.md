# 创建并启动线程

```java
/**
 * 读数据
 */
private static void readFromDataBase() {
    // read data from database and handle it
    try {
        println("Begin read data from db.");
        Thread.sleep(1000 * 5L);
        println("Read data done and start handle it.");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    println("The data handle finish and successfully.");
}
```

```java
/**
 * 写数据
 */
private static void writeDataToFile() {
    // write data to file
    try {
        println("Begin write data to file.");
        Thread.sleep(1000 * 10L);
        println("Write data done and start handle it.");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    println("The data handle finish and successfully.");
}
```

1. 顺序执行，而非交替执行

   ```java
   // 例1
   readFromDataBase();
   writeDataToFile();
   // 例2
   for (int i = 0; i < 100; i++) {
       println("Task i=>" + i);
   }
   for (int j = 0; j < 100; j++) {
       println("Task j=>" + j);
   }
   ```

2. 交替执行

   ```java
   Thread t1 = new Thread("Custom-Thread") {
       @Override
       public void run() {
           for (int i = 0; i < 100; i++) {
               println("Task i=>" + i);
               try {
                   Thread.sleep(1000 * 1L);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }
   };
   t1.start();
   for (int j = 0; j < 100; j++) {
       println("Task j=>" + j);
   }
   ```

   <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8u68chvj9j30p00kwgnk.jpg" alt="Custom-Thread线程情况" style="zoom:50%;" />

3. 交替执行读写操作

   ```java
   new Thread("READ-Thread") {
       @Override
       public void run() {
           readFromDataBase();
       }
   }.start();
   new Thread("WRITE-Thread") {
       @Override
       public void run() {
           writeDataToFile();
       }
   }.start();
   ```

   

- 创建线程后不调用`start()`方法，此时他是一个线程么？

  不是。涉及到线程的生命周期。Thread被new出来之后只是一个Java实例，和其他普通Java实例一模一样。只有`.start()`启动之后才会变为一个线程，而且启动是立即返回的，不是BLOCKED阻塞。



JDK文档：https://docs.oracle.com/javase/8/docs/api/

查看`java.lang.Thread`

A *thread* is a thread of execution in a program. The Java Virtual Machine allows an application to have multiple threads of execution running concurrently.

Every thread has a priority. Threads with higher priority are executed in preference to threads with lower priority. Each thread may or may not also be marked as a daemon.

When a Java Virtual Machine starts up, there is usually a single non-daemon thread (which typically calls the method named `main` of some designated class).

There are two ways to create a new thread of execution. One is to declare a class to be a subclass of `Thread`. This subclass should override the `run` method of class `Thread`. An instance of the subclass can then be allocated and started. 



验证启动程序后JVM会自动创建main线程：

1. 编写程序并运行

   ```java
   public static void main(String[] args) {
       try {
           Thread.sleep(1000 * 100L);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
   ```

2. 显示当前所有Java进程pid

   ```shell
   jps
   ```

3. 基于JMX的可视化监视、管理工具。实时的监控Java程序在运行过程中的内存、CPU、线程的使用情况，并可以对加载的相关类进行分析

   ```shell
   jconsole <thread_pid>
   ```

   <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8u5g8s2ucj30p00kumz4.jpg" a lt="jconsole查看线程" style="zoom:50%;" />

4. 总结：

   JVM启动时：

   - 非守护线程：`main()`整个程序的入口。
   - 守护线程：`Finalizer()`垃圾回收器、`Reference Handler`引用具柄、`Signal Dispatcher`接收系统信号命令、JMX、RMI

   