# 通过实验分析Class锁的存在

静态方法、静态代码块加锁，锁的MONITOR是什么东西？

1. 顺序访问。两个线程抢占同一个锁。（Class锁）

   ```java
   public synchronized static void m1() {}
   ```

   ```java
   public synchronized static void m2() {}
   ```

   其到底是什么锁呢？
   
   **静态方法加的锁其实是Class锁。**
   
2. 增加静态代码块，判定锁MONITOR是什么。

   - 暂未添加静态代码块时：

     ```java
     public static void m3() {}
     ```

     `m3()`为静态非加锁方法，启动程序会立即运行，此时`m1()`会同时运行。

   - 添加静态代码块后：

     ```java
     static {
         synchronized (SynchronizedStatic.class) {}
     }
     ```

     先进行一次实例化，运行静态代码块，运行完成才执行`m3()`和`m1()`

   **静态代码块加的锁其实是Class锁。**
