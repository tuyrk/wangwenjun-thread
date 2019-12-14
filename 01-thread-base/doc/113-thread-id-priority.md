# 线程ID，优先级讲解

Thread中一些简单的API

```java
Thread t1 = new Thread(() -> {
    Optional.of("Hello").ifPresent(System.out::println);
    try {
        Thread.sleep(1_000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}, "t1");
```

```java
// 线程名称。
Optional.of(t1.getName()).ifPresent(System.out::println);
// 线程ID。++threadSeqNumber
Optional.of(t1.getId()).ifPresent(System.out::println);
// 线程优先级，默认为5.
Optional.of(t1.getPriority()).ifPresent(System.out::println);
```

线程优先级范围是1-10，默认为5，通过修改线程优先级可以企图改变线程的执行顺序。

线程不一定会按照指定的优先级执行。

```java
Thread.MAX_PRIORITY// 最高优先级，10
Thread.NORM_PRIORITY// 默认优先级，5
Thread.MIN_PRIORITY// 最低优先级，1
```



线程t1、t2、t3交替运行。

```java
Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        Optional.of(Thread.currentThread().getName() + "-Index-" + i).ifPresent(System.out::println);
    }
}, "t1");
t1.setPriority(Thread.MAX_PRIORITY);

Thread t2 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        Optional.of(Thread.currentThread().getName() + "-Index-" + i).ifPresent(System.out::println);
    }
}, "t2");
t2.setPriority(Thread.NORM_PRIORITY);

Thread t3 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        Optional.of(Thread.currentThread().getName() + "-Index-" + i).ifPresent(System.out::println);
    }
}, "t3");
t3.setPriority(Thread.MIN_PRIORITY);

t1.start();
t2.start();
t3.start();
```

