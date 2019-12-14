# 如何给你的应用程序注入钩子程序，Linux下演示

如何获取线程的Exception？

> 我知道线程的`void run()`方法是不可以抛出异常的，如果线程死掉，调用者是不知道线程执行情况的。
>
> 如果没有捕获异常，在错误的情况下只是输出堆栈信息，调用者并不知道是什么异常导致。

线程的栈可以跟踪到方法调用。



JDK模式：client、server

server模式会进行运行时优化。JIT，即Just In Time

Runtime可以调用一些系统命令



```java
public class ExitCapture {
    public static void main(String[] args) {
        while (true) {
            Thread.sleep(1_000L);
            System.out.println("I am working...");
        }
    }
}
```

```shell
javac ExitCapture.java
java -cp . ExitCapture

nohup java -cp . ExitCapture &
tail -f nohup.out

kill 2181
```

如果程序占用了很多数据库连接或者是文件句柄，想要在kill程序的时候一并进行释放，或者在异常退出程序的情况下通知一下调用者程序知道它出错了，并不是调用者时时地去监控程序。（程序主动告诉，并不是被动发现）



```java
public class ExitCapture {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("The application will be exit.");
            notifyAndRelease();
        }));
        int i = 0;
        while (true) {
            Thread.sleep(1_000L);
            System.out.println("I am working...");
            i++;
            if (i > 20) {
                throw new RuntimeException("Error");
            }
        }
    }

    private static void notifyAndRelease() {
        System.out.println("notify to the admin.");
        Thread.sleep(1_000L);
        System.out.println("Will release resource(socket, file, connection.)");
        Thread.sleep(1_000L);
        System.out.println("Release and Notify Done!");
    }
}
```

运行程序，在i>20时程序抛出异常，被捕获到并执行`notifyAndRelease()`；通过`kill`杀死程序也可以被捕获并执行`notifyAndRelease()`，但是使用`kill -9`强制杀死程序则不会执行`notifyAndRelease()`。

