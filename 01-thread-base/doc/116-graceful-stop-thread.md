# 采用优雅的方式结束线程生命周期

Graceful thread stop

1. 使用开关变量控制是否启动

   ```java
   private volatile boolean start = true;
   @Override
   public void run() {
       while (start) {
           // ...
       }
   }
   public void shutdown() {
       this.start = false;
   }
   ```

   ```java
   worker.start();
   Thread.sleep(3_000);
   worker.shutdown();
   ```

   实用场景：当程序需要批量获取数据的时候，可以开辟多个线程执行任务，每个线程获取一部分数据，当程序执行完成后需要释放资源，即关闭线程。但是某些线程可能会出现问题导致无法正常关闭，此时需要使用开关变量来控制线程关闭。JVM中的线程主要是放在栈内存中，但是栈空间并不是很大，如果线程一直存在并越来越多可能出现栈内存溢出的情况。

2. 通过打断的方式实现

   - 打断sleep()

     ```java
     @Override
     public void run() {
         while (true) {
             // 1. sleep()
             try {
                 Thread.sleep(1);
             } catch (InterruptedException e) {
                 break;// return;
             }
         }
         // ... catch中使用break;可以在while()后执行其他操作
         System.out.println("break-opera after while");
     }
     ```

   - 打断if

     ```java
     @Override
     public void run() {
         while (true) {
             // 2. if
             if (Thread.interrupted()) {
                 break;// return;
             }
         }
         // ... catch中使用break;可以在while()后执行其他操作
         System.out.println("break-opera after while");
     }
     ```

   ```java
   worker.start();
   Thread.sleep(3_000);
   worker.interrupt();
   ```



思考：

在读操作的过程中线程被阻塞住blocked。此时没有机会打断或读flag，线程不能监听到打断操作。这种情况如何停止线程？暴力。

```java
@Override
public void run() {
    // connection or read file
}
```

