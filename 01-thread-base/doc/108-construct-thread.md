# 构造Thread对象你也许不知道的几件事

构造函数：

- 构造方法没有传入ThreadGroup，是否会创建默认的ThreadGroup
- 如果传入ThreadGroup为null，是否会使用其他默认的ThreadGroup
- ThreadGroup和Thread之间的关系是什么
- 线程和栈的关系

```java
Thread()
Thread(String name)
Thread(Runnable target)
Thread(Runnable target, String name)

Thread(ThreadGroup group, String name)
Thread(ThreadGroup group, Runnable target)
Thread(ThreadGroup group, Runnable target, String name)
Thread(ThreadGroup group, Runnable target, String name, long stackSize)
```



- Thread()

  创建线程对象Thread，默认有一个线程名，以Thread-开头，从0开始计数

  ```java
  Thread t1 = new Thread();
  ```

  如果在构造Thread的时候没有传递Runnable或者没有复写Thread的`run()`方法，该Thread将不会调用任何东西，如果传递了Runnable的实例，或者复写了Thread的`run()`方法，则会执行该方法的逻辑单元（逻辑代码）

  ```java
  Thread t2 = new Thread() {
      @Override
      public void run() {
          System.out.println("================");
      }
  };
  ```

  如果构造线程对象时未传入ThreadGroup，Thread会默认获取父线程的ThreadGroup作为该线程的ThreadGroup。此时子线程和父线程将会在同一个ThreadGroup中。

  ```java
  System.out.println(t1.getThreadGroup()); // main
  System.out.println(Thread.currentThread().getName()); // main
  System.out.println(Thread.currentThread().getThreadGroup().getName()); // main
  ```

  通过ThreadGroup对象可以获得该线程组中有多少个线程

  ```java
  ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
  Thread[] threads = new Thread[threadGroup.activeCount()];
  threadGroup.enumerate(threads);
  Arrays.stream(threads).forEach(System.out::println);// main,Thread-0,Monitor
  ```

- Thread(String name)

  ```java
  Thread t3 = new Thread("MyName");
  ```

- Thread(Runnable target)

  ```java
  Thread t4 = new Thread(() -> {
      System.out.println("Runnable...");
  });
  ```

- Thread(Runnable target, String name)

  ```java
Thread t5 = new Thread(() -> {
      System.out.println("Runnable..." + Thread.currentThread().getName());
}, "RunnableThread");
  ```

- Thread(ThreadGroup group, Runnable target, String name, long stackSize)
  
  stackSize：The stack size is the approximate number of bytes of address space that the virtual machine is to allocate for this thread's stack.**The effect of the `stackSize` parameter, if any, is highly platform dependent.On some platforms, the value of the `stackSize` parameter may have no effect whatsoever.**
  
  构造Thread的时候传入stackSize代表着该线程占用的stack的大小。如果没有指定stackSize的大小，默认是0，0代表着会忽略该参数，该参数会被JNI函数去使用。需要注意：该参数在某些平台（操作系统）可能无效。通过JVM参数显式设置：`-Xss10M`
  
  ```java
  Thread t = new Thread(null, new Runnable() {
      @Override
      public void run() {
          try {
              add(0);
          } catch (Error e) {
              e.printStackTrace();
              System.out.println(counter);
          }
      }
  
      private void add(int i) {
          counter++;
          add(i + 1);
      }
  }, "stackSizeTest", 1 << 24);
  ```
  
  

**总结**：

1. 创建线程对象Thread，默认有一个线程名，以Thread-开头，从0开始计数
2. 如果在构造Thread的时候没有传递Runnable或者没有复写Thread的`run()`方法，该Thread将不会调用任何东西，如果传递了Runnable的实例，或者复写了Thread的`run()`方法，则会执行该方法的逻辑单元（逻辑代码）
3. 如果构造线程对象时未传入ThreadGroup，Thread会默认获取父线程的ThreadGroup作为该线程的ThreadGroup。此时子线程和父线程将会在同一个ThreadGroup中。
4. 构造Thread的时候传入stackSize代表着该线程占用的stack的大小。如果没有指定stackSize的大小，默认是0，0代表着会忽略该参数，该参数会被JNI函数去使用。需要注意：该参数在某些平台（操作系统）可能无效。通过JVM参数显式设置：`-Xss10M`