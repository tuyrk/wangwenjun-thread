# Thread API综合实战，编写ThreadService实现暴力结束线程的综合实战

思考：

在读操作的过程中线程被阻塞住blocked。此时没有机会打断或读flag，线程不能监听到打断操作。这种情况如何停止线程？暴力。

```java
@Override
public void run() {
    // connection or read file
}
```

此时，在JDK提供的方法中只有stop()可能通过暴力的方式关闭线程，但是stop()已经被官方不推荐使用。可以通过一些小技巧来实现，操作方法如下。



JDK提供的方法不能使用，此时自己封装一个Service。

思路：把任务线程设置为调用线程的守护线程，通过main来启动调用线程，从而执行任务线程。当需要停止线程的时候只需要中断调用线程即可，调用线程生命周期结束时，为守护线程的任务线程会自动停止。

```java
public class ThreadService {
    // 调用线程
    private Thread executeThread;
    private boolean finished = false;

    /**
     * 执行线程
     * @param task 任务
     */
    public void execute(Runnable task) {
        this.executeThread = new Thread(() -> {
            // 任务执行线程
            Thread runner = new Thread(task);
            runner.setDaemon(true);

            runner.start();
            try {
                runner.join();
                finished = true;
            } catch (InterruptedException e) {}
        });
        this.executeThread.start();
    }

    /**
     * 关闭线程
     * @param mills 线程最长执行时间（单位：毫秒）
     */
    public void shutdown(long mills) {
        long currentTime = System.currentTimeMillis();
        // 当任务未完成执行。
        while (!finished) {
            // 如果超时。
            if (System.currentTimeMillis() - currentTime >= mills) {
                System.out.println("任务超时，需要结束线程！");
                executeThread.interrupt();
                break;
            }
            // 既未完成任务，也没超时
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e) {
                System.out.println("执行线程被打断！");
                break;
            }
        }
        finished = false;
    }
}
```

通过main调用实现启动线程和中断线程。

```java
public static void main(String[] args) {
    ThreadService service = new ThreadService();
    long startTime = System.currentTimeMillis();
    service.execute(() -> {
        // 1. load a very heavy resource.
        /*while (true) {
        }*/

        // 2. 线程执行5s后结束
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
        }
    });
    service.shutdown(10_000);
    long endTime = System.currentTimeMillis();
    System.out.println("执行时间为：" + (endTime - startTime));
}
```

