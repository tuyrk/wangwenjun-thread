# Thread的join方法详细介绍，结合一个典型案例

`join()`方法

> 调用线程一直等待被调用线程的执行，直到被调用线程死亡。

```java
Thread t1 = new Thread(() -> {
    IntStream.range(1, 1000).forEach(i ->
            System.out.println(Thread.currentThread().getName() + "->" + i)
    );
}, "t1");

t1.start();
t1.join();

IntStream.range(1, 1000).forEach(i ->
        System.out.println(Thread.currentThread().getName() + "->" + i)
);
```

注：`join()`必须放在`start()`之后。



```java
Thread t1 = new Thread(() -> {
    IntStream.range(1, 9000).forEach(i ->
            System.out.println(Thread.currentThread().getName() + "->" + i)
    );
}, "t1");
Thread t2 = new Thread(() -> {
    IntStream.range(1, 9000).forEach(i ->
            System.out.println(Thread.currentThread().getName() + "->" + i)
    );
}, "t2");

t1.start();
t2.start();
t1.join();
t2.join();

Optional.of("All of tasks finish done.").ifPresent(System.out::println);
IntStream.range(1, 1000).forEach(i ->
        System.out.println(Thread.currentThread().getName() + "->" + i)
);
```

注意：

- t1、t2交替执行

  ```java
  t1.start();
  t2.start();
  t1.join();
  t2.join();
  ```

- 先执行t1，在执行t2。此时t1加入join，t2还没启动。

  ```java
  t1.start();
  t1.join();
  t2.start();
  t2.join();
  ```

  

问题：一些嵌入式的HTTP Server，比如jetty，为什么把任务启动，一会之后会自动挂掉？
原因：在主线程退出之后会把http server挂掉（守护线程），避免占用端口、浪费资源。
解决：使用`Thread.currentThread().join();`。让当前线程执行，直到当前线程死掉

```java
public static void main(String[] args) {
    // 一直等待main线程结束,直到main线程结束。
    // 结果main线程一直运行。
    Thread.currentThread().join();
}
```





例：采集服务器节点的信息的例子。

问题：多个线程如何得到唯一的采集结束时间？

提示：使用`join()`方法

```java
public class ThreadJoin3 {
    public static void main(String[] args) throws InterruptedException {
        long startTimestamp = System.currentTimeMillis();

        // 假设有三台机器，开启三个线程。
        Thread m1 = new Thread(new CaptureRunnable("M1", 10_000L));
        Thread m2 = new Thread(new CaptureRunnable("M2", 30_000L));
        Thread m3 = new Thread(new CaptureRunnable("M3", 15_000L));
        m1.start();m2.start();m3.start();
        m1.join();m2.join();m3.join();

        long endTimestamp = System.currentTimeMillis();

        System.out.printf("Save data begin timestamp is %s, end timestamp is %s\n", startTimestamp, endTimestamp);
        System.out.printf("Spend time is %s", endTimestamp - startTimestamp);
    }
}

/**
 * 采集服务器节点的任务。
 */
@Data
class CaptureRunnable implements Runnable {
    // 机器节点的名称
    private String machineName;
    // 采集花费时间
    private long spendTime;

    public CaptureRunnable(String machineName, long spendTime) {
        this.machineName = machineName;
        this.spendTime = spendTime;
    }

    @Override
    public void run() {
        // do the really capture data.
        Thread.sleep(spendTime);
        System.out.printf(machineName + " completed data capture at timestamp [%s] and successful.\n", System.currentTimeMillis());
    }
}
```

