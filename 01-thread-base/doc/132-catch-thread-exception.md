# 如何捕获线程运行期间的异常

捕获线程异常

```java
public static void main(String[] args) {
    Thread t = new Thread(() -> {
        try {
            Thread.sleep(1_000L);
            int result = 10 / 0;
            System.out.println("result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    t.start();

    t.setUncaughtExceptionHandler((thread, e) -> {
        System.out.println(e);
        System.out.println(thread.getName());
    });
}
```



StackTrace。方法调用。

```java
public class ThreadException {
    public static void main(String[] args) {
        new Test1().test();
    }
}
```

```java
public class Test1 {
    private Test2 test2 = new Test2();

    public void test() {
        test2.test();
    }
}
```

```java
public class Test2 {
    public void test() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Stream.of(stackTrace)
                .filter(e -> !e.isNativeMethod())
                .forEach(e ->
                        Optional.of(e.getClassName() + "." + e.getMethodName() + ":" + e.getLineNumber()).ifPresent(System.out::println)
                );
    }
}
```





<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>

<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>