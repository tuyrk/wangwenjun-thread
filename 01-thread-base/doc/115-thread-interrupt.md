# Thread中断Interrupt方法详细讲解

- interrupt()

  中断此线程

- interrupted()

  测试**当前线程**是否已被中断。

- isInterrupted()

  测试**此线程**是否已被中断。



`interrupt()`仅仅是修改interrupt的状态。必须是`wait()`、`join()`、`sleep()`的时候才会抛出异常中断线程。

```java
Thread t1 = new Thread("t1") {
    @Override
    public void run() {
        while (true) {
            //// todo.
        }
    }
};
t1.start();

// start之后处于runnable，并不一定马上就会running。所以设置短暂休眠等待t1启动
Thread.sleep(100);

System.out.println(t1.isInterrupted());
t1.interrupt();
System.out.println(t1.isInterrupted());
```

1. 线程状态改变，不会捕获到打断信号

   ```java
   System.out.println(">>" + this.isInterrupted());
   ```

2. `sleep()`。线程状态改变，且捕获到打断信号

   ```java
   try {
       Thread.sleep(1_000);
   } catch (InterruptedException e) {
       System.out.println("收到打断信号。");
       e.printStackTrace();
   }
   ```

3. `wait()`。线程状态改变，且捕获到打断信号

   使用`wait()`必须给一个monitor，monitor需使用synchronized包裹。

   ```java
   private static final Object MONITOR = new Object();
   ```

   ```java
   synchronized (MONITOR) {
       try {
           MONITOR.wait(1_000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
   ```

4. `join()`。线程状态改变，且捕获到打断信号

   注意：`t3.join();`这里join的不是t3线程，而是main线程。所以需要对main线程进行`interrupt()`

   ```java
   Thread t3 = new Thread(() -> {
       while (true) {}
   }, "t3");
   t3.start();
   
   Thread main = Thread.currentThread();
   Thread t31 = new Thread(() -> {
       try {
           Thread.sleep(100);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   
       main.interrupt();
   });
   t31.start();
   
   try {
       // 这里join的不是t3线程，而是main线程
       t3.join();
   } catch (InterruptedException e) {
       e.printStackTrace();
   }
   ```

   

非静态`isInterrupted()`和静态`interrupted()`区别：

- 在实现Runnable接口创建任务实例时，`isInterrupted()`将不能被获取

  ```java
  Thread t2 = new Thread(() -> {
      while (true) {
          synchronized (MONITOR) {
              try {
                  MONITOR.wait(1_000);
              } catch (InterruptedException e) {
                  // 获取不到isInterrupted()
                  // System.out.println("wait()->" + isInterrupted());
                  System.out.println("wait()->" + Thread.interrupted());
                  e.printStackTrace();
              }
          }
      }
  }, "t2");
  ```

  



