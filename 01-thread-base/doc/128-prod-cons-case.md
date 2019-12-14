# 线程生产者消费者的综合实战结合Java8语法

数据采集功能：利用多个线程采集多台服务器运行状态信息。当服务器数量较少时，可以采取一个线程采集一台服务器；但是服务器数量非常大时，将不可能采取这种方式，可以开启一定数量的线程采集完成后再采集其他服务器，即运行的线程始终保持着稳定数量。

操作系统可以支持一个应用开启的线程数量是有限制的，取决于stack size。多线程的性能是有一个峰值的，超过峰值性能反而会下降。这是因为线程数量过大时，应用的上下文切换会消耗很大一部分资源。

```java
public class CaptureService {
    private static final LinkedList<Control> CONTROLS = new LinkedList<>();
    private static final int MAX_WORKER = 5;

    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();
        Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .map(CaptureService::createCaptureService)
                .forEach(t -> {
                    t.start();
                    worker.add(t);
                });
        worker.forEach(t -> {
            t.join();
        });

        Optional.of("All of capture work finished.").ifPresent(System.out::println);
    }

    public static Thread createCaptureService(String name) {
        return new Thread(() -> {
            Optional.of("The worker [" + Thread.currentThread().getName() + "] BEGIN capture data.").ifPresent(System.out::println);
            synchronized (CONTROLS) {
                while (CONTROLS.size() > MAX_WORKER) {
                    CONTROLS.wait();
                }

                CONTROLS.addLast(new Control());
            }

            Optional.of("The worker [" + Thread.currentThread().getName() + "] is WORKING...").ifPresent(System.out::println);
            Thread.sleep(10_000);

            synchronized (CONTROLS) {
                Optional.of("The worker [" + Thread.currentThread().getName() + "] END capture data.").ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        }, name);

    }

    private static class Control {}
}
```

Optional可以防止NPE空指针异常

解决的问题：

1. 多个线程上下文切换带来的损耗问题
2. 多个线程占多个资源，比如同时创建多个数据库连接、打开多个文件（文件句柄是有限的）。

学习了线程池之后，可以不必这么麻烦啦。