# ThreadGroup API介绍之二

```java
ThreadGroup tg1 = new ThreadGroup("TG1");
Thread t1 = new Thread(tg1, () -> {
    while (true) {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            break;
        }
    }
}, "T1");
t1.start();

ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
Thread t2 = new Thread(tg2, () -> {
    while (true) {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            break;
        }
    }
}, "T2");
t2.start();
```

`int activeCount()`

> 返回此线程组及其子组中活动线程数的估计值。

`int activeGroupCount()`

> 返回此线程组及其子组中活动线程组的数量的估计值。

`void checkAccess()`

> 确定当前正在运行的线程是否有权修改此线程组。

`void destroy()`

> 销毁该线程组及其所有子组。如果线程组不为空或线程组已被销毁将抛出`IllegalThreadStateException`
>
> 销毁前需要确定线程组里没有活跃的线程

`int enumerate(Thread[] list)`

> 将此线程组及其子组中的每个活动线程复制到指定的数组中。

`int enumerate(Thread[] list, boolean recurse)`

> 将此线程组中(不包含子组)的每个活动线程复制到指定的数组中。

```java
Thread[] ts1 = new Thread[tg1.activeCount()];
tg1.enumerate(ts1);
System.out.println(Arrays.toString(ts1));// [Thread[T1,5,TG1], Thread[T2,5,TG2]]

Thread[] ts2 = new Thread[tg1.activeCount()];
tg1.enumerate(ts2, false);
System.out.println(Arrays.toString(ts2));// [Thread[T1,5,TG1], null]

ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
Thread[] ts3 = new Thread[mainThreadGroup.activeCount()];
mainThreadGroup.enumerate(ts3, false);
System.out.println(Arrays.toString(ts3));// [Thread[main,5,main], Thread[Monitor Ctrl-Break,5,main], null, null]
```

`void interrupt()`

> 中断该线程组（包括子组）中的所有线程。

`boolean isDaemon()`

> 测试此线程组是否是守护程序线程组。

`void setDaemon(boolean daemon)`

> 更改此线程组的守护程序状态。
>
> 守护程序线程组在其最后一个线程停止时或在其最后一个线程组被销毁时会自动销毁。
>
> 线程组创建完成后并没有被销毁，需要显示地销毁关闭，否则将一直存活。

```java
ThreadGroup tg3 = new ThreadGroup("TG3");
Thread t3 = new Thread(tg3, () -> {
    Thread.sleep(1_000);
}, "T3");
/*tg3.setDaemon(true);*/
Thread.sleep(2_000);
System.out.println(tg3.isDestroyed());// true
// 手动回收ThreadGroup
tg3.destroy();
System.out.println(tg3.isDestroyed());
```

`void list()`

> 将有关此线程组的信息输出到控制台。主要用来做测试用的。

`void uncaughtException(Thread t, Throwable e)`

> 当此线程组中的线程由于未捕获的异常而停止并且该线程未安装特定的Thread.UncaughtExceptionHandler时，由Java虚拟机调用。

`boolean parentOf(ThreadGroup g)`

> 测试此线程组是不是参数线程组的祖先线程组之一。（测试我是不是你老汉儿）